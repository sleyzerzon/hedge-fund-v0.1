package com.onenow.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.onenow.constant.DataType;
import com.onenow.constant.TradeType;
import com.onenow.execution.QuoteDepth.DeepRow;
import com.onenow.instrument.Investment;
import com.onenow.research.Candle;

public class Cache {
	
	Lookup lookup;
	HashMap<String, Double> 				prices; // $
	HashMap<String, Integer> 				size; 	// volume
	HashMap<String, ArrayList<DeepRow>>		depth;	// market depth
	HashMap<String, Boolean>				flag;	// flag
	HashMap<String, List<Long>>				times;

	TSDB DB;	

	Ring ring;
	
	public Cache() {
		setLookup(new Lookup());
		setPrices(new HashMap<String, Double>());
		setSize(new HashMap<String, Integer>());
		setDepth(new HashMap<String, ArrayList<DeepRow>>());
		setTimes(new HashMap<String, List<Long>>());
		setDB(new TSDB());
		
	}
	
	// PUBLIC
	// TODO: continuous queries http://influxdb.com/docs/v0.8/api/continuous_queries.html
	
	// PRICE
	public List<Candle> readPrice(	Investment inv, String dataType, 
			String fromDate, String toDate, String sampling) {
		
		return getDB().readPriceFromDB(inv, dataType, fromDate, toDate, sampling);
	}
	

	// SIZE
	public List<Integer> readSize(	Investment inv, String dataType, 
			String fromDate, String toDate, String sampling) {
	
		return getDB().readSizeFromDB(inv, dataType, fromDate, toDate, sampling);
	}

	public void writeSize(Long lastTradeTime, Investment inv, String type, Integer lastSize) {
		// getSize().put(getLookup().getTimedKey(lastTradeTime, inv, type), lastSize);
		getDB().writeSize(lastTradeTime, inv, type, lastSize);	
	}

//	public void writeSizeToMap(Investment inv, Integer size, String dataType) {
//		getSize().put(getLookup().getKey(inv, dataType), size);
////		System.out.println(dataType.toString() + " " +	getSizeFromMap(inv, dataType) + " " + inv.toString()); // log
//	}
//	public Integer readSizeFromTimedMap(Long time, Investment inv, String dataType) {
//		String key = getLookup().getTimedKey(time, inv, dataType);
//		Integer size=0;
//		try {
//			size = (Integer) (getSize().get(key)); 
//		} catch (Exception e) {
//			e.printStackTrace();
//		} 		
//		return size;		
//	}
//	public Integer readSizeFromMap(Investment inv, String dataType) {
//		String key = getLookup().getKey(inv, dataType);
//		Integer size=0;
//		try {
//			size = (Integer) (getSize().get(key)); 
//		} catch (Exception e) {
//			e.printStackTrace();
//		} 		
//		return size;
//	}
	
	
	// PRICE

	public void writePrice(Long lastTradeTime, Investment inv, String type, Double lastPrice) {
		//getPrices().put(getLookup().getTimedKey(lastTradeTime, inv, type), lastPrice);
		getDB().writePrice(lastTradeTime, inv, type, lastPrice);
	}

	
//	public void writePriceToMap(Investment inv, Double price, String dataType) {
//		getPrices().put(getLookup().getKey(inv, dataType), price);
////		System.out.println(dataType.toString() + " $" +  getPriceFromMap(inv, dataType)  + " " + inv.toString() + "\n"); // log
//	}
//	
//	public Double readPriceFromTimedMap(Long time, Investment inv, String dataType) {
//		String key = getLookup().getTimedKey(time, inv, dataType);
//		Double price=0.0;
//		try {
//			price = (Double) (getPrices().get(key)); // let price be null to know it's not set
//		} catch (Exception e) {
//			e.printStackTrace();
//		} 
//		return price;		
//	}
//	public Double readPriceFromMap(Investment inv, String dataType) {
//		String key = getLookup().getKey(inv, dataType);
//		Double price=0.0;
//		try {
//			price = (Double) (getPrices().get(key)); // let price be null to know it's not set
//		} catch (Exception e) {
//			e.printStackTrace();
//		} 
//		return price;
//	}
	
	// FLAG
	
	public void writeFlag(Long lastTradeTime, Investment inv, String type, boolean splitFlag) { // TODO: DB write
		//getFlag().put(getLookup().getTimedKey(lastTradeTime, inv, type), splitFlag); // TODO
	}
	
	
//	public void setFlagMap(Investment inv, boolean flag) {
//		getFlag().put(getLookup().getKey(inv, DataType.TRADEFLAG.toString()), flag);	
//	}	
//	public boolean getFlagFromTimedMap(Long time, Investment inv, String dataType) {
//		String key = getLookup().getTimedKey(time, inv, dataType);
//		boolean flag=false;
//		try {
//			flag = (boolean) (getFlag().get(key)); 
//		} catch (Exception e) {
//			e.printStackTrace();
//		} 		
//		return flag;			
//	}
//	public boolean getFlag(Investment inv) {
//		String key = getLookup().getKey(inv, DataType.TRADEFLAG.toString());
//		boolean flag = false;
//		try {
//			flag = (boolean) (getFlag().get(key)); // let price be null to know it's not set
//		} catch (Exception e) {
//			e.printStackTrace();
//		} 	
//		return flag;		
//	}
	
	
	// DEPTH
//	public void setDepth(Investment inv, ArrayList<DeepRow> depth) {
//		getDepth().put(getLookup().getKey(inv, DataType.MARKETDEPTH.toString()), depth);
//		System.out.println("Depth " +  	getDepthFromMap(inv).toString() + " " + inv.toString());
//	}
//	public ArrayList<DeepRow> getDepthFromMap(Investment inv) {
//		String key = getLookup().getKey(inv, DataType.MARKETDEPTH.toString());
//		ArrayList<DeepRow> depth = new ArrayList<DeepRow>();
//		try {
//			depth = (ArrayList<DeepRow>) (getDepth().get(key)); // let price be null to know it's not set
//		} catch (Exception e) {
//			e.printStackTrace();
//		} 	
//		if(depth==null) {
//			depth=new ArrayList<DeepRow>(); // return empty
//		}
//		return depth;
//	}
		

	// TIME
//	public void setTimeMap(Investment inv, Long time) {
//		getTimeFromMap(inv).add(time);
//		System.out.println("Last time " +  	getTimeFromMap(inv).toString() + " " + inv.toString()); // log
//	}
//	public List<Long> getTimeFromMap(Investment inv) {
//		String dataType = DataType.LASTTIME.toString();
//		String key = getLookup().getKey(inv, dataType);
//		List<Long> timeList=new ArrayList<Long>();
//		try {
//			timeList = (ArrayList<Long>) (getTimes().get(key)); // let price be null to know it's not set
//		} catch (Exception e) {
//			e.printStackTrace();
//		} 
//		return timeList;
//	}

	
	// TEST
	
	// PRINT
	
	// TODO: print all the data in memory, not just prices, from Maps/Ring/etc
	public String toString() {
		String s="";
		s = getPrices().toString();
		return s;
	}

	
// TODO: convert to print from whatever source what Real-time information was just written
//	public String realTimeMapToString(Long tradeTime, Investment inv) {
//		
//		Integer size = getCache().readSizeFromTimedMap(tradeTime, inv, TradeType.TRADED.toString());
//		Integer volume = readSizeFromTimedMap(tradeTime, inv, DataType.VOLUME.toString());
//		
//		String sizeS = size.toString();
//		String volumeS = volume.toString();
//		
//		if(size<0) {
//			sizeS = "(" + sizeS + ")";
//		}
//		
//		boolean print = true;
//		if(size>500) {
//			sizeS = "***" + sizeS;
//			print = true;
//		}
//		if(volume>5000) {
//			volumeS = "***" + volumeS;
//			print = true;
//		}
//		
//		String s = "";
//		if(print) {
//			s = "\n" + inv.toString() + "\n";
//			s = s +	"REAL TIME " +
//					"Price " + readPriceFromTimedMap(tradeTime, inv, TradeType.TRADED.toString()) + " " +
//					"Size " + sizeS + " " + 
//					"Volume " + volumeS + " " +
//					"VWAP " + readPriceFromTimedMap(tradeTime, inv, DataType.VWAP.toString()) + "\n\n" ; // +
//	//				"Trade Flag " + getTimedFlag(tradeTime, inv, DataType.TRADEFLAG.toString()); // TODO
//		}
//		return s;
//	}
	
	
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
	
	private TSDB getDB() {
		return DB;
	}

	private void setDB(TSDB dB) {
		DB = dB;
	}

	public Lookup getLookup() {
		return lookup;
	}

	public void setLookup(Lookup lookup) {
		this.lookup = lookup;
	}

}
