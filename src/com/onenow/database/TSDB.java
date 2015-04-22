package com.onenow.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Serie;

import com.onenow.constant.DBname;
import com.onenow.constant.SamplingRate;
import com.onenow.instrument.Investment;
import com.onenow.research.Candle;

public class TSDB {
	
	private InfluxDB DB;
	private Lookup lookup;
	
	/**
	 * Default constructor connects to database
	 */
	public TSDB() {
		setLookup(new Lookup());
		dbConnect();
//		dbCreate();
		
	}

// INIT
private void dbConnect() { 
	try {
		System.out.println("CONNECTING TO DB");
		setDB(InfluxDBFactory.connect("http://tsdb.enremmeta.com:8086", "root", "root"));
	} catch (Exception e) {
		System.out.println("COULD NOT CONNECT TO DB\n");
		e.printStackTrace();
		return;
	}
}

private void dbCreate() {
	getDB().createDatabase(DBname.PRICE.toString());
	getDB().createDatabase(DBname.SIZE.toString());
}

// PRICE
public void writePrice(Long time, Investment inv, String dataType, Double price) {
	String name = getLookup().getInvestmentKey(inv, dataType);
	Serie serie = new Serie.Builder(name)
	.columns("time", "price")
	.values(time, price)
	.build();
	System.out.println("WRITE " + DBname.PRICE.toString() + " " + serie + "\n");
	getDB().write(DBname.PRICE.toString(), TimeUnit.MILLISECONDS, serie);
}

public List<Candle> readPriceFromDB(	Investment inv, String dataType, String sampling,
										String fromDate, String toDate) {
	
		List<Candle> candles = new ArrayList<Candle>();
		
		String name = getLookup().getInvestmentKey(inv, dataType);

		List<Serie> series = queryPrice(DBname.PRICE.toString(), name, fromDate, toDate, sampling);

		candles = priceSeriesToCandles(series); 
		
		return candles;
	}

public List<Serie> queryPrice(String dbName, String serieName, String sampling, String fromDate, String toDate) {
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
						"time" + "(" + getDBSamplingString(sampling) + ")";
					
	try {
//		System.out.println("QUERY " + query);
		series = getDB().query(	dbName, query, TimeUnit.MILLISECONDS);
	} catch (Exception e) {
//		e.printStackTrace(); some time series don't exist or have data
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
public void writeSize(Long time, Investment inv, String dataType, Integer size) {
	String name = getLookup().getInvestmentKey(inv, dataType);
	Serie serie = new Serie.Builder(name)
	.columns("time", "size")
	.values(time, size)
	.build();
	System.out.println("WRITE " + DBname.SIZE.toString() + " " + serie);
	getDB().write(DBname.SIZE.toString(), TimeUnit.MILLISECONDS, serie);
}

public List<Integer> readSizeFromDB(	Investment inv, String dataType, 
		String fromDate, String toDate, String sampling) {
	
	List<Integer> sizes = new ArrayList<Integer>();
	
	String name = getLookup().getInvestmentKey(inv, dataType);
	
	List<Serie> series = querySize(	DBname.SIZE.toString(), name,  sampling, fromDate, toDate);
	
	sizes = sizeSeriesToInts(series); 
	
	return sizes;
}

public List<Serie> querySize(String dbName, String serieName, String sampling, String fromDate, String toDate) {
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
						"time" + "(" + getDBSamplingString(sampling) + ")";
					
	try {
//		System.out.println("QUERY " + query);
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

// CONFIG
private String getDBSamplingString(String samplingRate) {
	String dbSamplingRate="";
	if(samplingRate.equals("SCALPSHORT")) {		//SCALPING 5min, 15min, 60min
		return "5m";
	}
	if(samplingRate.equals("SCALPMEDIUM")) {
		return "15m";
	}
	if(samplingRate.equals("SCALPLONG")) {
		return "60m";
	}
	if(samplingRate.equals("SWINGSHORT")) {		//SWINGING 60min, 240min, daily
		return "60m";
	}
	if(samplingRate.equals("SWINGMEDIUM")) {
		return "4h";
	}
	if(samplingRate.equals("SWINGLONG")) {
		return "1d";
	}
	if(samplingRate.equals("TRENDSHORT")) {		//TREND 4hr, daily, weekly
		return "4h";
	}
	if(samplingRate.equals("TRENDMEDIUM")) {
		return "1d";
	}
	if(samplingRate.equals("TRENDLONG")) {
		return "1w";
	}
	return dbSamplingRate;
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
	

}