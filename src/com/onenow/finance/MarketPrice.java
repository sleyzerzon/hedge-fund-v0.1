package com.onenow.finance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.influxdb.dto.Serie;

import com.onenow.analyst.Candle;
import com.onenow.analyst.Chart;
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
		setDB(new TSDB());
	}

	
	// REAL-TIME
	public void setRealTime(Investment inv, String rtvolume) {
		String lastTradedPrice="";
		String lastTradeSize="";
		String lastTradeTime="";
		String totalVolume="";
		String VWAP="";
		String splitFlag="";
		
		int i=1;
		for(String split:rtvolume.split(";")) {
			if(i==1) { //	Last trade price
				lastTradedPrice = split;
				if(lastTradedPrice.equals("")) {
					return;
				}
			}
			if(i==2) { //	Last trade size
				lastTradeSize = split;
				if(lastTradeSize.equals("")) {
					return;
				}
			}
			if(i==3) { //	Last trade time
				lastTradeTime = split;
				if(lastTradeTime.equals("")) {
					return;
				}
			}
			if(i==4) { //	Total volume
				totalVolume = split;
				if(totalVolume.equals("")) {
					return;
				}
			}
			if(i==5) { //	VWAP
				VWAP = split;
				if(VWAP.equals("")) {
					return;
				}
			}
			if(i==6) { //	Single trade flag - True indicates the trade was filled by a single market maker; False indicates multiple market-makers helped fill the trade
				splitFlag = split;
				if(splitFlag.equals("")) {
					return;
				}
			}
			i++;
		}
		Long time = Long.parseLong(lastTradeTime); 	// TODO: *1000 ?
		fillRealTime(time, inv, Double.parseDouble(lastTradedPrice), Integer.parseInt(lastTradeSize),  
					Integer.parseInt(totalVolume), Double.parseDouble(VWAP), Boolean.parseBoolean(splitFlag));
		return;
	}
 	
	private void fillRealTime(	Long lastTradeTime, Investment inv, Double lastPrice, Integer lastSize, 
								Integer volume, Double VWAP, boolean splitFlag) {

		if(lastSize>0) { // TODO: ignore busts with negative size
			setSizeDB(lastTradeTime, inv, TradeType.TRADED.toString(), lastSize);		
			setPriceDB(lastTradeTime, inv, TradeType.TRADED.toString(), lastPrice);
			// setSizeDB(lastTradeTime, inv, DataType.VOLUME.toString(), volume);		
			// setPriceDB(lastTradeTime, inv, DataType.VWAP.toString(), VWAP);
			// writeFlag(lastTradeTime, inv, DataType.TRADEFLAG.toString(), splitFlag);
			
//			System.out.println(realTimeMapToString(lastTradeTime, inv)); // see what written
		}
	}
	
	// CANDLES
	public Chart queryChart(Investment inv, String dataType, 
			String fromDate, String toDate, String sampling) {
		
		Chart chart = new Chart();
		List<Candle> prices = getPriceFromDB(inv, dataType, fromDate, toDate, sampling);
		List<Integer> sizes = getSizeFromDB(inv, dataType, fromDate, toDate, sampling);
		chart.setPrices(prices);
		chart.setSizes(sizes);

		return chart;
	}
		
	public List<Candle> getPriceFromDB(	Investment inv, String dataType, 
		String fromDate, String toDate, String sampling) {
		List<Serie> series = getDB().readPrice(	inv, dataType,
								fromDate, toDate, sampling);
		List<Candle> candles = priceSeriesToCandles(series); 
		return candles;
	}
	
	private List<Candle> priceSeriesToCandles(List<Serie> series) {
		List<Candle> candles = new ArrayList<Candle>();
				
		String s="";
		for (Serie ser : series) {
			for (String col : ser.getColumns()) {
				s = s + col + "\t";
//				System.out.println("column " + col);
			}
			s = s + "\n";
			for (Map<String, Object> row : ser.getRows()) {
				Candle candle = new Candle();
				Integer i=0;
				for (String col : ser.getColumns()) {
					s = s + row.get(col) + "\t";
//					System.out.println("row " + row + " " + row.get(col));
					if(i.equals(1)) {
						candle.setOpenPrice(new Double(row.get(col).toString()));
					}
					if(i.equals(2)) {
						candle.setClosePrice(new Double(row.get(col).toString()));
					}
					if(i.equals(3)) {
						candle.setLowPrice(new Double(row.get(col).toString()));
					}
					if(i.equals(4)) {
						candle.setHighPrice(new Double(row.get(col).toString()));
					}
					if(i.equals(5)) {
						//	sum
					}
					i++;
				}
				s = s + "\n";
				candles.add(candle);
			}
		}
		System.out.println("CANDLE: " + s + "\n");	
		return candles;
	}
	
	public List<Integer> getSizeFromDB(	Investment inv, String dataType, 
			String fromDate, String toDate, String sampling) {
		
		List<Integer> sizes = new ArrayList<Integer>();
		List<Serie> series = getDB().readSize(	inv, dataType, fromDate, toDate, sampling);
		sizes = sizeSeriesToInts(series); 
		return sizes;
		
	}

	public List<Integer> sizeSeriesToInts(List<Serie> series) {
		List<Integer> sizes = new ArrayList<Integer>();
		
		String s="";
		for (Serie ser : series) {
			for (String col : ser.getColumns()) {
				s = s + col + "\t";
//				System.out.println("column " + col);
			}
			s = s + "\n";
			for (Map<String, Object> row : ser.getRows()) {
				Candle candle = new Candle();
				Integer i=0;
				for (String col : ser.getColumns()) {
					s = s + row.get(col) + "\t";
//					System.out.println("row " + row + " " + row.get(col));
					if(i.equals(1)) {
						// open 
					}
					if(i.equals(2)) {
						// close
					}
					if(i.equals(3)) {
						// low
					}
					if(i.equals(4)) {
						// high
					}
					if(i.equals(5)) {
						Double num = new Double(row.get(col).toString());
						sizes.add((int) Math.round(num));
					}
					i++;
				}
				s = s + "\n";
			}
		}
		System.out.println("SIZES: " + s + "\n");	
		return sizes;
	}
	public String queryToString(List<Serie> series) {
		String s = "";
		
		for (Serie ser : series) {
			for (String col : ser.getColumns()) {
				System.out.print(col + "\t");
			}
			System.out.println();
			for (Map<String, Object> row : ser.getRows()) {
				for (String col : ser.getColumns()) {
					System.out.print(row.get(col) + "\t");
				}
				System.out.println();
			}
		}
//		System.out.println(series.size() + " entries");
		return s;
	}

				
	// SIZE
	private void setSizeDB(Long lastTradeTime, Investment inv, String type, Integer lastSize) {
		// getSize().put(getLookup().getTimedKey(lastTradeTime, inv, type), lastSize);
		getDB().writeSize(lastTradeTime, inv, type, lastSize);
	}

	public void setSizeMap(Investment inv, Integer size, String dataType) {
		getSize().put(getLookup().getKey(inv, dataType), size);
//		System.out.println(dataType.toString() + " " +	getSizeFromMap(inv, dataType) + " " + inv.toString()); // log
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
	private void setPriceDB(Long lastTradeTime, Investment inv, String type, Double lastPrice) {
		//getPrices().put(getLookup().getTimedKey(lastTradeTime, inv, type), lastPrice);
		getDB().writePrice(lastTradeTime, inv, type, lastPrice);
	}

	public void setPriceMap(Investment inv, Double price, String dataType) {
		getPrices().put(getLookup().getKey(inv, dataType), price);
//		System.out.println(dataType.toString() + " $" +  getPriceFromMap(inv, dataType)  + " " + inv.toString() + "\n"); // log
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
	
	public String realTimeMapToString(Long tradeTime, Investment inv) {
		
		Integer size = getSizeFromTimedMap(tradeTime, inv, TradeType.TRADED.toString());
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
					"Price " + getPriceFromTimedMap(tradeTime, inv, TradeType.TRADED.toString()) + " " +
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
