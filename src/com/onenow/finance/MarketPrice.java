package com.onenow.finance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.influxdb.dto.Serie;

import com.onenow.analyst.Candle;
import com.onenow.database.DBname;
import com.onenow.database.Lookup;
import com.onenow.database.TSDB;
import com.onenow.investor.DataType;
import com.onenow.investor.QuoteDepth.DeepRow;

public class MarketPrice {

	Lookup lookup;

	HashMap<String, Double> 				prices; // $
	HashMap<String, Integer> 				size; 	// volume
	HashMap<String, ArrayList<DeepRow>>		depth;	// market depth
	HashMap<String, Boolean>				flag;	// flag
	HashMap<String, List<Long>>				times;

	TSDB DB;
	
	public MarketPrice() {
		setLookup(new Lookup());
		setPrices(new HashMap<String, Double>());
		setSize(new HashMap<String, Integer>());
		setDepth(new HashMap<String, ArrayList<DeepRow>>());
		setTimes(new HashMap<String, List<Long>>());
//		setDB(new TSDB());
		setDB(new TSDB());
	}

	
	// REAL-TIME
	public Long setRealTime(Investment inv, String rtvolume) {
		String lastTradedPrice="";
		String lastTradeSize="";
		String lastTradeTime="";
		String totalVolume="";
		String VWAP="";
		String splitFlag="";
		
		int i=1;
		for(String split:rtvolume.split(";")) {
//			System.out.println("SPLIT " + split + " " + i);
			if(i==1) { //	Last trade price
				lastTradedPrice = split;
			}
			if(i==2) { //	Last trade size
				lastTradeSize = split;
			}
			if(i==3) { //	Last trade time
				lastTradeTime = split;
//				System.out.println("LAST " + split + " " + lastTradeTime);
			}
			if(i==4) { //	Total volume
				totalVolume = split;
			}
			if(i==5) { //	VWAP
				VWAP = split;
			}
			if(i==6) { //	Single trade flag - True indicates the trade was filled by a single market maker; False indicates multiple market-makers helped fill the trade
				splitFlag = split;
			}
//			System.out.println(split);
			i++;
		}
		Long time = Long.parseLong(lastTradeTime); 	// TODO: *1000 ?
		fillRealTime(time, inv, Double.parseDouble(lastTradedPrice), Integer.parseInt(lastTradeSize),  
					Integer.parseInt(totalVolume), Double.parseDouble(VWAP), Boolean.parseBoolean(splitFlag));
		return time;
	}
 	
	private void fillRealTime(	Long lastTradeTime, Investment inv, Double lastPrice, Integer lastSize, 
								Integer volume, Double VWAP, boolean splitFlag) {

		if(lastSize>0) { // TODO: ignore busts with negative size
			setSizeDB(lastTradeTime, inv, TradeType.LAST.toString(), lastSize);		
			setPriceDB(lastTradeTime, inv, TradeType.LAST.toString(), lastPrice);
			setSizeDB(lastTradeTime, inv, DataType.VOLUME.toString(), volume);		
			setPriceDB(lastTradeTime, inv, DataType.VWAP.toString(), VWAP);
			//writeFlag(lastTradeTime, inv, DataType.TRADEFLAG.toString(), splitFlag);
			
			System.out.println(realTimeToString(lastTradeTime, inv)); // see what written
		}
	}
	//
				
	// SIZE
	public Integer getSizeFromDB(	Investment inv, String dataType, 
									String fromDate, String toDate, String sampling) {
		Integer size=0;
		List<Serie> series = getDB().readSize(	inv, dataType,
												fromDate, toDate, sampling);
//		String result = getDB().queryToString(series);
//		System.out.println("SIZE" + result);
		Integer candles = getDB().queryToTotalSize(series); 
		return size;
	}

	private void setSizeDB(Long lastTradeTime, Investment inv, String type, Integer lastSize) {
		// getSize().put(getLookup().getTimedKey(lastTradeTime, inv, type), lastSize);
		getDB().writeSize(lastTradeTime, inv, type, lastSize);
	}

	public void setSizeMap(Investment inv, Integer size, String dataType) {
		getSize().put(getLookup().getKey(inv, dataType), size);
		System.out.println(dataType.toString() + " " +	getSizeFromMap(inv, dataType) + " " + inv.toString()); // log
	}
	public Integer getSizeFromTimedMap(Long time, Investment inv, String dataType) {
		String key = getLookup().getTimedKey(time, inv, dataType);
		Integer size=0;
		try {
			size = (Integer) (getSize().get(key)); 
		} catch (Exception e) {
			e.printStackTrace();
		} 		
		return size;		
	}
	public Integer getSizeFromMap(Investment inv, String dataType) {
		String key = getLookup().getKey(inv, dataType);
		Integer size=0;
		try {
			size = (Integer) (getSize().get(key)); 
		} catch (Exception e) {
			e.printStackTrace();
		} 		
		return size;
	}
	
	// PRICE
	public List<Candle> getPriceFromDB(	Investment inv, String dataType, 
									String fromDate, String toDate, String sampling) {
//		Double price=0.0;
		List<Serie> series = getDB().readPrice(	inv, dataType,
												fromDate, toDate, sampling);
//		String result = getDB().queryToString(series);
//		System.out.println("PRICE" + result);
		List<Candle> candles = getDB().queryToPriceCandles(series); 
		return candles;
	}

	private void setPriceDB(Long lastTradeTime, Investment inv, String type, Double lastPrice) {
		//getPrices().put(getLookup().getTimedKey(lastTradeTime, inv, type), lastPrice);
		getDB().writePrice(lastTradeTime, inv, type, lastPrice);
	}

	public void setPriceMap(Investment inv, Double price, String dataType) {
		getPrices().put(getLookup().getKey(inv, dataType), price);
		System.out.println(dataType.toString() + " $" +  getPriceFromMap(inv, dataType)  + " " + inv.toString() + "\n"); // log
	}
	
	public Double getPriceFromTimedMap(Long time, Investment inv, String dataType) {
		String key = getLookup().getTimedKey(time, inv, dataType);
		Double price=0.0;
		try {
			price = (Double) (getPrices().get(key)); // let price be null to know it's not set
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return price;		
	}
	public Double getPriceFromMap(Investment inv, String dataType) {
		String key = getLookup().getKey(inv, dataType);
		Double price=0.0;
		try {
			price = (Double) (getPrices().get(key)); // let price be null to know it's not set
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return price;
	}

	// FLAG
	private void writeFlag(Long lastTradeTime, Investment inv, String type, boolean splitFlag) { // TODO: DB write
		//getFlag().put(getLookup().getTimedKey(lastTradeTime, inv, type), splitFlag); // TODO
	}

	public void setFlagMap(Investment inv, boolean flag) {
		getFlag().put(getLookup().getKey(inv, DataType.TRADEFLAG.toString()), flag);	
	}	
	public boolean getFlagFromTimedMap(Long time, Investment inv, String dataType) {
		String key = getLookup().getTimedKey(time, inv, dataType);
		boolean flag=false;
		try {
			flag = (boolean) (getFlag().get(key)); 
		} catch (Exception e) {
			e.printStackTrace();
		} 		
		return flag;			
	}
	public boolean getFlag(Investment inv) {
		String key = getLookup().getKey(inv, DataType.TRADEFLAG.toString());
		boolean flag = false;
		try {
			flag = (boolean) (getFlag().get(key)); // let price be null to know it's not set
		} catch (Exception e) {
			e.printStackTrace();
		} 	
		return flag;		
	}

	// TIME
	public void setTimeMap(Investment inv, Long time) {
		getTimeFromMap(inv).add(time);
		System.out.println("Last time " +  	getTimeFromMap(inv).toString() + " " + inv.toString()); // log
	}
	public List<Long> getTimeFromMap(Investment inv) {
		String dataType = DataType.LASTTIME.toString();
		String key = getLookup().getKey(inv, dataType);
		List<Long> timeList=new ArrayList<Long>();
		try {
			timeList = (ArrayList<Long>) (getTimes().get(key)); // let price be null to know it's not set
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return timeList;
	}
	
	// DEPTH
	public void setDepth(Investment inv, ArrayList<DeepRow> depth) {
		getDepth().put(getLookup().getKey(inv, DataType.MARKETDEPTH.toString()), depth);
		System.out.println("Depth " +  	getDepthFromMap(inv).toString() + " " + inv.toString());
	}
	public ArrayList<DeepRow> getDepthFromMap(Investment inv) {
		String key = getLookup().getKey(inv, DataType.MARKETDEPTH.toString());
		ArrayList<DeepRow> depth = new ArrayList<DeepRow>();
		try {
			depth = (ArrayList<DeepRow>) (getDepth().get(key)); // let price be null to know it's not set
		} catch (Exception e) {
			e.printStackTrace();
		} 	
		if(depth==null) {
			depth=new ArrayList<DeepRow>(); // return empty
		}
		return depth;
	}
	// PRINT
	public String toString() {
		String s="";
		s = getPrices().toString();
		return s;
	}
	
	public String realTimeToString(Long tradeTime, Investment inv) {
		
		Integer size = getSizeFromTimedMap(tradeTime, inv, TradeType.LAST.toString());
		Integer volume = getSizeFromTimedMap(tradeTime, inv, DataType.VOLUME.toString());
		
		String sizeS = size.toString();
		String volumeS = volume.toString();
		
		if(size<0) {
			sizeS = "(" + sizeS + ")";
		}
		
		boolean print = true;
		if(size>500) {
			sizeS = "***" + sizeS;
			print = true;
		}
		if(volume>5000) {
			volumeS = "***" + volumeS;
			print = true;
		}
		
		String s = "";
		if(print) {
			s = "\n" + inv.toString() + "\n";
			s = s +	"REAL TIME " +
					"Price " + getPriceFromTimedMap(tradeTime, inv, TradeType.LAST.toString()) + " " +
					"Size " + sizeS + " " + 
					"Volume " + volumeS + " " +
					"VWAP " + getPriceFromTimedMap(tradeTime, inv, DataType.VWAP.toString()) + "\n\n" ; // +
	//				"Trade Flag " + getTimedFlag(tradeTime, inv, DataType.TRADEFLAG.toString()); // TODO
		}
		return s;
	}

	
	// TEST
	
	// SET GET
	private HashMap<String, Double> getPrices() {
		return prices;
	}

	private void setPrices(HashMap<String, Double> prices) {
		this.prices = prices;
	}

	private HashMap<String, Integer> getSize() {
		return size;
	}

	private void setSize(HashMap<String, Integer> size) {
		this.size = size;
	}

	private HashMap<String, ArrayList<DeepRow>> getDepth() {
		return depth;
	}

	private void setDepth(HashMap<String, ArrayList<DeepRow>> depth) {
		this.depth = depth;
	}

	private HashMap<String, Boolean> getFlag() {
		return flag;
	}

	private void setFlag(HashMap<String, Boolean> flag) {
		this.flag = flag;
	}

	private HashMap<String, List<Long>> getTimes() {
		return times;
	}

	private void setTimes(HashMap<String, List<Long>> times) {
		this.times = times;
	}

	private Lookup getLookup() {
		return lookup;
	}

	private void setLookup(Lookup lookup) {
		this.lookup = lookup;
	}

	private TSDB getDB() {
		return DB;
	}

	private void setDB(TSDB dB) {
		DB = dB;
	}

	
}
