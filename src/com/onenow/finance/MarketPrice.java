package com.onenow.finance;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Serie;

import com.onenow.database.Lookup;
import com.onenow.investor.DataType;
import com.onenow.investor.QuoteDepth.DeepRow;

public class MarketPrice {

	Lookup lookup;

	HashMap<String, Double> 				prices; // $
	HashMap<String, Integer> 				size; 	// volume
	HashMap<String, ArrayList<DeepRow>>		depth;	// market depth
	HashMap<String, Boolean>				flag;	// flag
	HashMap<String, List<Long>>				times;

	
	public MarketPrice() {
		setLookup(new Lookup());
		setPrices(new HashMap<String, Double>());
		setSize(new HashMap<String, Integer>());
		setDepth(new HashMap<String, ArrayList<DeepRow>>());
		setTimes(new HashMap<String, List<Long>>());
	}

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
		String type="";
		
		type = TradeType.LAST.toString();
		String key = getLookup().getTimedKey(lastTradeTime, inv, type);
		if(lastSize>0) { // TODO: ignore busts with negative size
			getPrices().put(getLookup().getTimedKey(lastTradeTime, inv, type), lastPrice);
			getSize().put(getLookup().getTimedKey(lastTradeTime, inv, type), lastSize);		
			type = DataType.VOLUME.toString();
			getSize().put(getLookup().getTimedKey(lastTradeTime, inv, type), volume);		
			type = DataType.VWAP.toString();
			getPrices().put(getLookup().getTimedKey(lastTradeTime, inv, type), VWAP);
			type = DataType.TRADEFLAG.toString();
	//		getFlag().put(getTimedLookupKey(lastTradeTime, inv, type), splitFlag); // TODO
			
//			System.out.println(getRealTime(lastTradeTime, inv).toString()); // see what written
		}
	}
	
	

	// influxDB.createDatabase("aTimeSeries");
//	for (int i = -10; i < 10; i++) {
//		Serie serie1 = new Serie.Builder("serie2Name")
//				.columns("column1", "column2")
//				.values(System.currentTimeMillis() + 3600000 * i, 1)
//				.values(System.currentTimeMillis() + 3600000 * i, 2)
//				.build();
//		Serie serie2 = new Serie.Builder("serie2Name")
//				.columns("column1", "column2")
//				.values(System.currentTimeMillis() + 3600000 * i, 1)
//				.values(System.currentTimeMillis() + 3600000 * i, 2)
//				.build();
//		influxDB.write("aTimeSeries", TimeUnit.MILLISECONDS, serie1, serie2);
//	}
//	List<Serie> sers = influxDB
//			.query("aTimeSeries",
//					"select * from serie2Name where time > now() - 3h",
//					TimeUnit.MILLISECONDS);
//	
//	
//	for (Serie ser : sers) {
//		for (String col : ser.getColumns()) {
//			System.out.print(col + "\t");
//		}
//		System.out.println();
//		for (Map<String, Object> row : ser.getRows()) {
//			for (String col : ser.getColumns()) {
//				System.out.print(row.get(col) + "\t");
//			}
//			System.out.println();
//		}
//	}
//
//	System.out.println(sers.size() + " entries");
//}

	
	public String getRealTime(Long tradeTime, Investment inv) {
		
		Integer size = getTimedSize(tradeTime, inv, TradeType.LAST.toString());
		Integer volume = getTimedSize(tradeTime, inv, DataType.VOLUME.toString());
		
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
					"Price " + getTimedPrice(tradeTime, inv, TradeType.LAST.toString()) + " " +
					"Size " + sizeS + " " + 
					"Volume " + volumeS + " " +
					"VWAP " + getTimedPrice(tradeTime, inv, DataType.VWAP.toString()) + "\n\n" ; // +
	//				"Trade Flag " + getTimedFlag(tradeTime, inv, DataType.TRADEFLAG.toString()); // TODO
		}
		return s;
	}
	
	public void setTime(Investment inv, Long time) {
		getTime(inv).add(time);
		System.out.println("Last time " +  	getTime(inv).toString() + " " + inv.toString());
	}
	public List<Long> getTime(Investment inv) {
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

	public void setFlag(Investment inv, boolean flag) {
		getFlag().put(getLookup().getKey(inv, DataType.TRADEFLAG.toString()), flag);	
	}
	
	public boolean getTimedFlag(Long time, Investment inv, String dataType) {
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
	
	public void setDepth(Investment inv, ArrayList<DeepRow> depth) {
		getDepth().put(getLookup().getKey(inv, DataType.MARKETDEPTH.toString()), depth);
		System.out.println("Depth " +  	getDepth(inv).toString() + " " + inv.toString());
	}
	
	public ArrayList<DeepRow> getDepth(Investment inv) {
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
		
	public void setSize(Investment inv, Integer size, String dataType) {
		getSize().put(getLookup().getKey(inv, dataType), size);
		System.out.println(dataType.toString() + " " +	getSize(inv, dataType) + " " + inv.toString());
	}
	
	public Integer getTimedSize(Long time, Investment inv, String dataType) {
		String key = getLookup().getTimedKey(time, inv, dataType);
		Integer size=0;
		try {
			size = (Integer) (getSize().get(key)); 
		} catch (Exception e) {
			e.printStackTrace();
		} 		
		return size;		
	}
	
	public Integer getSize(Investment inv, String dataType) {
		String key = getLookup().getKey(inv, dataType);
		Integer size=0;
		try {
			size = (Integer) (getSize().get(key)); 
		} catch (Exception e) {
			e.printStackTrace();
		} 		
		return size;
	}
	
	public void setPrice(Investment inv, Double price, String dataType) {
		getPrices().put(getLookup().getKey(inv, dataType), price);
		System.out.println(dataType.toString() + " $" +  getPrice(inv, dataType)  + " " + inv.toString() + "\n");
	}
	
	public Double getTimedPrice(Long time, Investment inv, String dataType) {
		String key = getLookup().getTimedKey(time, inv, dataType);
		Double price=0.0;
		try {
			price = (Double) (getPrices().get(key)); // let price be null to know it's not set
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return price;		
	}
	public Double getPrice(Investment inv, String dataType) {
		String key = getLookup().getKey(inv, dataType);
		Double price=0.0;
		try {
			price = (Double) (getPrices().get(key)); // let price be null to know it's not set
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return price;
	}

	// PRINT
	public String toString() {
		String s="";
		s = prices.toString();
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

	
}
