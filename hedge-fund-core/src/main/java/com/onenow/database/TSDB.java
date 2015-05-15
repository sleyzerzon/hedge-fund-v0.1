package com.onenow.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Serie;

import com.onenow.constant.DBname;
import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.SamplingRate;
import com.onenow.constant.TradeType;
import com.onenow.data.Sampling;
import com.onenow.instrument.Investment;
import com.onenow.research.Candle;
import com.onenow.util.ParseDate;

public class TSDB {
	
	private InfluxDB DB;
	private Lookup lookup;
	private ParseDate parseDate;
	private Sampling sampling;
	
	/**
	 * Default constructor connects to database
	 */
	public TSDB() {
		setLookup(new Lookup());
		dbConnect();
		dbCreate();
		
		setParseDate(new ParseDate());
		setSampling(new Sampling());
	}

	
// INFLUX HOSTING
//	Hostname: calvinklein-fluxcapacitor-1.c.influxdb.com (45.55.169.140)
//	API Ports: 8086 (HTTP) and 8087 (HTTPS)
//	Admin User: root
//	Admin Password: b45547741dd1709b
//	Admin Interface: http://calvinklein-fluxcapacitor-1.c.influxdb.com:8083
//
//	And if you need some pointers on how to get started, check out our documentation:
//	http://influxdb.com/docs/v0.8/introduction/getting_started.html

	
// INIT
//	setDB(InfluxDBFactory.connect("http://calvinklein-fluxcapacitor-1.c.influxdb.com:8086", "root", "b45547741dd1709b"));
//	setDB(InfluxDBFactory.connect("http://tsdb.enremmeta.com:8086", "root", "root"));	
private void dbConnect() { 
	boolean tryToConnect = true;
	
	while(tryToConnect) {
		try {
			tryToConnect = false;
			System.out.println("\n" + "CONNECTING TO DB...");
			setDB(InfluxDBFactory.connect("http://calvinklein-fluxcapacitor-1.c.influxdb.com:8086", "root", "b45547741dd1709b"));
		} catch (Exception e) {
//			tryToConnect = true;
			System.out.println("\n" + "...COULD NOT CONNECT TO DB: ");
			e.printStackTrace();
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e1) {
				// nothing to do
			}
		}
	} 
}

// list series
// SELECT FIRST(price), LAST(price), MIN(price), MAX(price), SUM(price) FROM "BBY-STOCK-TRADED-IB-HISTORICAL" WHERE time > '2015-05-08' AND time < '2015-05-09' GROUP BY time(60m)
// count(), min(), max(), mean(), mode(), median(), distinct(), percentile(), histogram(), derivative(), sum(), stddev(), first(), last()
private void dbCreate() {
	try {
		getDB().createDatabase(DBname.PRICE.toString());
		getDB().createDatabase(DBname.SIZE.toString());
	} catch (Exception e) {
		// Throws exception if the DB already exists
		// e.printStackTrace();
	}
}

// PRICE
public void writePrice(Long time, Investment inv, TradeType tradeType, Double price, InvDataSource source, InvDataTiming timing) {
	String name = getLookup().getInvestmentKey(inv, tradeType, source, timing);
	Serie serie = new Serie.Builder(name)
	.columns("time", "price")
	.values(time, price)
	.build();
	System.out.println("WRITE " + DBname.PRICE.toString() + " " + serie);
	try {
		getDB().write(DBname.PRICE.toString(), TimeUnit.MILLISECONDS, serie);
	} catch (Exception e) {
		e.printStackTrace();
	}
}

public List<Candle> readPriceFromDB(	Investment inv, TradeType tradeType, SamplingRate sampling,
										String fromDate, String toDate,
										InvDataSource source, InvDataTiming timing) {
	
		List<Candle> candles = new ArrayList<Candle>();
		
		String key = getLookup().getInvestmentKey(inv, tradeType, source, timing);

		List<Serie> series = queryPrice(DBname.PRICE.toString(), key, sampling, fromDate, toDate);

		candles = priceSeriesToCandles(series); 

		System.out.println("Cache Chart/Price READ: L1 " + fromDate + " " + toDate + " " + " for " + key + " Prices: " + candles.toString());
		
		return candles;
	}

public List<Serie> queryPrice(String dbName, String serieName, SamplingRate sampling, String fromDate, String toDate) {
	List<Serie> series = new ArrayList<Serie>();
	
	String query = 	"SELECT " +
						"FIRST(price)" + ", " +
						"LAST(price)" + ", " +
						"MIN(price)" + ", " +
						"MAX(price)" + ", " + 
						"SUM(price) " +  
					"FROM " + "\"" + serieName + "\" " +
					"WHERE " +
						"time > " + "'" + fromDate + "' " + 
						"AND " +
						"time < " + "'" + toDate + "' " + 
					"GROUP BY " +
						"time" + "(" + getSampling().getGroupByTimeString(sampling) + ")";
					
	try {
		System.out.println("#PRICE# QUERY " + query);
		series = getDB().query(	dbName, query, TimeUnit.MILLISECONDS);
	} catch (Exception e) {
//		e.printStackTrace();  some time series don't exist or have data
	}
	return series;
}

private List<Candle> priceSeriesToCandles(List<Serie> series) {
	List<Candle> candles = new ArrayList<Candle>();
			
	String s="";
	for (Serie ser : series) {
		for (String col : ser.getColumns()) {
			s = s + col + "\t";
//			System.out.println("column " + col); column names
		}
		s = s + "\n";
		for (Map<String, Object> row : ser.getRows()) {
			Candle candle = new Candle();
			Integer i=0;
			for (String col : ser.getColumns()) {	// iterate columns to create candle
				s = s + row.get(col) + "\t";
//				System.out.println("row " + row + " " + row.get(col)); full row
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
//	System.out.println("CANDLE: " + s + "\n");	full candle
	return candles;
}
// SIZE
public void writeSize(Long time, Investment inv, TradeType tradeType, Integer size, InvDataSource source, InvDataTiming timing) {
	String name = getLookup().getInvestmentKey(inv, tradeType, source, timing);
	Serie serie = new Serie.Builder(name)
	.columns("time", "size")
	.values(time, size)
	.build();
	System.out.println("WRITE " + DBname.SIZE.toString() + " " + serie);
	try {
		getDB().write(DBname.SIZE.toString(), TimeUnit.MILLISECONDS, serie);
	} catch (Exception e) {
		e.printStackTrace();
	}
}

public List<Integer> readSizeFromDB(	Investment inv, TradeType tradeType, SamplingRate sampling,
										String fromDate, String toDate,
										InvDataSource source, InvDataTiming timing) {
	
	List<Integer> sizes = new ArrayList<Integer>();
	
	String key = getLookup().getInvestmentKey(inv, tradeType, source, timing);
	
	List<Serie> series = querySize(	DBname.SIZE.toString(), key,  sampling, fromDate, toDate);
	
	sizes = sizeSeriesToInts(series); 
	
	System.out.println("Cache Chart/Size READ: L1 " + fromDate + " " + toDate + " " + " for " + key + " Sizes: " + sizes.toString());

	return sizes;
}

public List<Serie> querySize(String dbName, String serieName, SamplingRate sampling, String fromDate, String toDate) {
	List<Serie> series = new ArrayList<Serie>();
	
	String query = 	"SELECT " +
						"FIRST(size)" + ", " +
						"LAST(size)" + ", " +
						"MIN(size)" + ", " +
						"MAX(size)" + ", " + 
						"SUM(size) " +  
					"FROM " + "\"" + serieName + "\" " +
					"WHERE " +
						"time > " + "'" + fromDate + "' " + 
						"AND " +
						"time < " + "'" + toDate + "' " + 
					"GROUP BY " +
						"time" + "(" + getSampling().getGroupByTimeString(sampling) + ")";
					
	try {
		System.out.println("#SIZE# QUERY " + query);
		series = getDB().query(	dbName, query, TimeUnit.MILLISECONDS);
	} catch (Exception e) {
//		e.printStackTrace(); some time series don't exist or have data
	}
	
	return series;
}

private List<Integer> sizeSeriesToInts(List<Serie> series) {
	List<Integer> sizes = new ArrayList<Integer>();
	
	String s="";
	for (Serie ser : series) {
		for (String col : ser.getColumns()) {
			s = s + col + "\t";
//			System.out.println("column " + col); column names
		}
		s = s + "\n";
		for (Map<String, Object> row : ser.getRows()) {
			Candle candle = new Candle();
			Integer i=0;
			for (String col : ser.getColumns()) {	// iterate columsn to get ints
				s = s + row.get(col) + "\t";
//				System.out.println("row " + row + " " + row.get(col)); full row
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
//	System.out.println("SIZES: " + s + "\n");	full series
	return sizes;
}


	// PRIVATE


	// TEST
	
	
	// PRINT
	
	// SET GET
	private InfluxDB getDB() {
		return DB;
	}
	
	private void setDB(InfluxDB dB) {
		DB = dB;
	}	
	
	private Lookup getLookup() {
		return lookup;
	}
	
	
	private void setLookup(Lookup lookup) {
		this.lookup = lookup;
	}

	public ParseDate getParseDate() {
		return parseDate;
	}

	public void setParseDate(ParseDate parseDate) {
		this.parseDate = parseDate;
	}

	public Sampling getSampling() {
		return sampling;
	}

	public void setSampling(Sampling sampling) {
		this.sampling = sampling;
	}
	

}