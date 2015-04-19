package com.onenow.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.onenow.execution.QuoteDepth.DeepRow;
import com.onenow.instrument.Investment;
import com.onenow.research.Candle;

public class Cache {
	
	Lookup 									lookup;		// key
	HashMap<String, Double> 				prices; 	// $
	HashMap<String, Integer> 				size; 		// volume

	HashMap<String, ArrayList<DeepRow>>		depth;		// market depth
	HashMap<String, Boolean>				flag;		// flag

	TSDB DB;	

	Ring ring;
	
	public Cache() {
		setLookup(new Lookup());
		setPrices(new HashMap<String, Double>());
		setSize(new HashMap<String, Integer>());
		setDepth(new HashMap<String, ArrayList<DeepRow>>());
		setDB(new TSDB());
		
	}
	
	// PUBLIC
	// TODO: continuous queries http://influxdb.com/docs/v0.8/api/continuous_queries.html
	
	
	// PRICE
	public void writePrice(Long timeStamp, Investment inv, String type, Double price) {
		// keep last in memory
		String key = getLookup().getInvestmentKey(inv, type);
		getPrices().put(key, price);			
		// fast write to ring
		EventPriceWrite event = new EventPriceWrite(timeStamp, inv, type, price);
		getRing().writePrice(event);
	}

	/**
	 * Read price from a time range (from database)
	 * @param inv
	 * @param dataType
	 * @param fromDate
	 * @param toDate
	 * @param sampling
	 * @return
	 */
	public List<Candle> readPrice(	Investment inv, String dataType, 
			String fromDate, String toDate, String sampling) {
		
		return getDB().readPriceFromDB(inv, dataType, fromDate, toDate, sampling);
	}

	/**
	 * Read the latest price
	 * @param inv
	 * @param type
	 * @return
	 */
	public double readPrice(Investment inv, String type) {

		String key = getLookup().getInvestmentKey(inv, type);

		return getPrices().get(key);
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
	

	// SIZE
	public void writeSize(Long timeStamp, Investment inv, String type, Integer size) {
		// keep last in memory
		String key = getLookup().getInvestmentKey(inv, type);
		getSize().put(key, size);		
		// fast write to ring
		EventSizeWrite event = new EventSizeWrite(timeStamp, inv, type, size);
		getRing().writeSize(event);
	}

	public List<Integer> readSize(	Investment inv, String dataType, 
			String fromDate, String toDate, String sampling) {
		
		return getDB().readSizeFromDB(inv, dataType, fromDate, toDate, sampling);
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

	public Ring getRing() {
		return ring;
	}

	public void setRing(Ring ring) {
		this.ring = ring;
	}

}
