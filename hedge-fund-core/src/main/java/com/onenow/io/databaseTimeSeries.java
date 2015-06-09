package com.onenow.io;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Serie;

import com.onenow.admin.NetworkConfig;
import com.onenow.admin.NetworkService;
import com.onenow.constant.DBname;
import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.SamplingRate;
import com.onenow.constant.TradeType;
import com.onenow.data.DataSampling;
import com.onenow.instrument.Investment;
import com.onenow.research.Candle;
import java.util.logging.Level;
import com.onenow.util.Watchr;

public class databaseTimeSeries {
	
	private static InfluxDB influxDB = dbConnect();
	
	/**
	 * Default constructor connects to database
	 */
	public databaseTimeSeries() {
	}
	
// INIT
private static InfluxDB dbConnect() { 
	InfluxDB db  = null;
	boolean tryToConnect = true;
	while(tryToConnect) {		
		try {	
			tryToConnect = false;
			Watchr.log(Level.INFO, "CONNECTING TO TSDB...", "\n", "");
			NetworkService tsdbService = NetworkConfig.getTSDB();
			db = InfluxDBFactory.connect(	tsdbService.protocol+"://"+tsdbService.URI+":"+tsdbService.port, 
													tsdbService.user, tsdbService.pass);
			dbCreate();	
		} catch (Exception e) {
			tryToConnect = true;
			Watchr.log(Level.SEVERE, "...COULD NOT CONNECT TO TSDB: ", "\n", "");
			e.printStackTrace();
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e1) {
				// nothing to do
			}
		}
	} 
	Watchr.log(Level.INFO, "CONNECTED TO TSDB!");
	return db;
}

private static void dbCreate() {
	try {
		influxDB.createDatabase(DBname.PRICE.toString());
		influxDB.createDatabase(DBname.SIZE.toString());
	} catch (Exception e) {
		// Throws exception if the DB already exists
		// e.printStackTrace();
	}
}

// PRICE
public static void writePrice(Long time, Investment inv, TradeType tradeType, Double price, InvDataSource source, InvDataTiming timing) {
	String name = Lookup.getInvestmentKey(inv, tradeType, source, timing);
	Serie serie = new Serie.Builder(name)
	.columns("time", "price")
	.values(time, price)
	.build();
	String log = "TSDB WRITE PRICE: " + DBname.PRICE.toString() + " " + serie;
	Watchr.log(Level.INFO, log, "\n", "");

	influxDB.write(DBname.PRICE.toString(), TimeUnit.MILLISECONDS, serie);
}

public static List<Candle> readPriceFromDB(	Investment inv, TradeType tradeType, SamplingRate samplingRate,
										String fromDate, String toDate,
										InvDataSource source, InvDataTiming timing) {
	
		List<Candle> candles = new ArrayList<Candle>();
		
		String key = Lookup.getInvestmentKey(inv, tradeType, source, timing);

		List<Serie> series = queryPrice(DBname.PRICE.toString(), key, samplingRate, fromDate, toDate);

		candles = priceSeriesToCandles(series); 
		
		String log = "TSDB Cache Chart/Price READ: L1 " + fromDate + " " + toDate + " " + " for " + key + " Prices: " + candles.toString();
		Watchr.log(Level.INFO, log, "\n", "");

		return candles;
	}

/**
 * Price queries per http://influxdb.com/docs/v0.7/api/aggregate_functions.html
 * @param dbName
 * @param serieName
 * @param sampling
 * @param fromDate
 * @param toDate
 * @return
 */
public static List<Serie> queryPrice(String dbName, String serieName, SamplingRate sampling, String fromDate, String toDate) {
	List<Serie> series = new ArrayList<Serie>();
	 
	String query = 	"SELECT " +
						"FIRST(price)" + ", " +			
						"LAST(price)" + ", " +			
						"DIFFERENCE(price)" + ", " +							
						"MIN(price)" + ", " +			
						"MAX(price)" + ", " +			
						"MEAN(price)" + ", " +			
						"MODE(price)" + ", " +			
						"MEDIAN(price)" + ", " +						
						"STDDEV(price)" + ", " +						
						"DISTINCT(price)" + ", " +					
						"COUNT(price)" + ", " +			
						"SUM(price)" + ", " + 
						"DERIVATIVE(price)" + " " + 						
					"FROM " + "\"" + serieName + "\" " +
					"GROUP BY " +
						"time" + "(" + DataSampling.getGroupByTimeString(sampling) + ") " + 
					// "FILL(0) " +
					"WHERE " +
					"time > " + "'" + fromDate + "' " + 
					"AND " +
					"time < " + "'" + toDate + "' "; 
					
	// TODO: SELECT PERCENTILE(column_name, N) FROM series_name group by time(10m) ...
	// TODO: SELECT HISTOGRAM(column_name) FROM series_name ...
	// TODO: SELECT TOP(column_name, N) FROM series_name ...
	// TODO: SELECT BOTTOM(column_name, N) FROM series_name ...
	
	try {
		Watchr.log(Level.INFO, query);
		series = influxDB.query(	dbName, query, TimeUnit.MILLISECONDS);
	} catch (Exception e) {
		// Watchr.log(Level.WARNING, e.toString());
		e.printStackTrace();
	}
	return series;
}

private static List<Candle> priceSeriesToCandles(List<Serie> series) {
	List<Candle> candles = new ArrayList<Candle>();
	
	Watchr.log(Level.INFO, "SERIES: " + series.toString());
			
	String s="";
	for (Serie ser : series) {
		for (String col : ser.getColumns()) {
			s = s + col + "\t";
			// System.out.println("column " + col); column names
		}
		s = s + "\n";
		for (Map<String, Object> row : ser.getRows()) {
			Candle candle = new Candle();
			Integer i=0;
			for (String col : ser.getColumns()) {	// iterate columns to create candle
				s = s + row.get(col) + "\t";
				// System.out.println("row " + row + " " + row.get(col)); // full row
				if(i.equals(1)) {
					candle.openPrice = new Double(extractQueryString(row, col));
				}
				if(i.equals(2)) {
					candle.closePrice = new Double(extractQueryString(row, col));
				}
				if(i.equals(3)) {
					candle.difference = new Double(extractQueryString(row, col));
				}				
				//				"FIRST(price)" + ", " +			
				//				"LAST(price)" + ", " +			
				//				"DIFFERENCE(price)" + ", " +							
				if(i.equals(4)) {
					candle.lowPrice = new Double(extractQueryString(row, col));
				}
				if(i.equals(5)) {
					candle.highPrice = new Double(extractQueryString(row, col));
				}
				//				"MIN(price)" + ", " +			
				//				"MAX(price)" + ", " +			
				if(i.equals(6)) {
					candle.meanPrice = new Double(extractQueryString(row, col));
				}
				if(i.equals(7)) {
					candle.modePrice = new Double(extractQueryString(row, col));
				}
				if(i.equals(8)) {
					candle.medianPrice = new Double(extractQueryString(row, col));
				}
				if(i.equals(9)) {
					candle.stddevPrice = new Double(extractQueryString(row, col));
				}
				//				"MEAN(price)" + ", " +			
				//				"MODE(price)" + ", " +			
				//				"MEDIAN(price)" + ", " +						
				//				"STDDEV(price)" + ", " +						
				if(i.equals(10)) {
					candle.distinctPrice = new Double(extractQueryString(row, col));
				}
				if(i.equals(11)) {
					candle.countPrice = new Double(extractQueryString(row, col));
				}
				if(i.equals(12)) {
					candle.sumPrice = new Double(extractQueryString(row, col));
				}
				if(i.equals(13)) {
					candle.derivativePrice = new Double(extractQueryString(row, col));
				}
				//				"DISTINCT(price)" + ", " +					
				//				"COUNT(price)" + ", " +			
				//				"SUM(price) " + ", " + 
				//				"DERIVATIVE(price)" + 						

				i++;
			}
			s = s + "\n";
			candles.add(candle);
		}
	}
//	System.out.println("CANDLE: " + s + "\n");	full candle
	return candles;
}

private static String extractQueryString(Map<String, Object> row, String col) {
	String s = "";
	try {
		s = row.get(col).toString();
	} catch (Exception e) {
		s = "-1.0";
		String log = "TSDB NULL query result" + "row " + row + " " + row.get(col);
		Watchr.log(Level.SEVERE, log);
		// TODO: something to do for defaults?
		// e.printStackTrace();
	}
	return s;
}


// SIZE
public static void writeSize(Long time, Investment inv, TradeType tradeType, Integer size, InvDataSource source, InvDataTiming timing) {
	String name = Lookup.getInvestmentKey(inv, tradeType, source, timing);
	Serie serie = new Serie.Builder(name)
	.columns("time", "size")
	.values(time, size)
	.build();
	String log = "TSDB WRITE SIZE: " + DBname.SIZE.toString() + " " + serie;
	Watchr.log(Level.INFO, log, "\n", "");

	influxDB.write(DBname.SIZE.toString(), TimeUnit.MILLISECONDS, serie);
}

public static List<Integer> readSizeFromDB(	Investment inv, TradeType tradeType, SamplingRate sampling,
										String fromDate, String toDate,
										InvDataSource source, InvDataTiming timing) {
	
	List<Integer> sizes = new ArrayList<Integer>();
	
	String key = Lookup.getInvestmentKey(inv, tradeType, source, timing);
	
	List<Serie> series = querySize(	DBname.SIZE.toString(), key,  sampling, fromDate, toDate);
	
	sizes = sizeSeriesToInts(series); 
	
	String log = "TSDB Cache Chart/Size READ: L1 " + fromDate + " " + toDate + " " + " for " + key + " Sizes: " + sizes.toString();
	Watchr.log(Level.INFO, log, "\n", "");

	return sizes;
}

/**
 * Size/volume queries per http://influxdb.com/docs/v0.7/api/aggregate_functions.html
 * @param dbName
 * @param serieName
 * @param sampling
 * @param fromDate
 * @param toDate
 * @return
 */
public static List<Serie> querySize(String dbName, String serieName, SamplingRate sampling, String fromDate, String toDate) {
	List<Serie> series = new ArrayList<Serie>();
	
	String query = 	"SELECT " +
						"FIRST(size)" + ", " +			
						"LAST(size)" + ", " +			
						"DIFFERENCE(size)" + ", " +							
						"MIN(size)" + ", " +			
						"MAX(size)" + ", " +			
						"MEAN(size)" + ", " +			
						"MODE(size)" + ", " +			
						"MEDIAN(size)" + ", " +						
						"STDDEV(size)" + ", " +						
						"DISTINCT(size)" + ", " +					
						"COUNT(size)" + ", " +			
						"SUM(size)" + ", " +
						"DERIVATIVE(size)" + " " +						  
					"FROM " + "\"" + serieName + "\" " +
					"GROUP BY " +
						"time" + "(" + DataSampling.getGroupByTimeString(sampling) + ") " +
					// "FILL(0) " +
					"WHERE " +
					"time > " + "'" + fromDate + "' " + 
					"AND " +
					"time < " + "'" + toDate + "' ";
					
	try {
		Watchr.log(Level.INFO, query);
		series = influxDB.query(	dbName, query, TimeUnit.MILLISECONDS);
	} catch (Exception e) {
//		e.printStackTrace(); some time series don't exist or have data
	}
	
	return series;
}

private static List<Integer> sizeSeriesToInts(List<Serie> series) {
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
				// System.out.println("row " + row + " " + row.get(col)); full row
				if(i.equals(1)) {
				}
				if(i.equals(2)) {
				}
				if(i.equals(3)) {
				}
				//				"FIRST(price)" + ", " +			
				//				"LAST(price)" + ", " +			
				//				"DIFFERENCE(price)" + ", " +							
				if(i.equals(4)) {
				}
				if(i.equals(5)) {
				}
				//				"MIN(price)" + ", " +			
				//				"MAX(price)" + ", " +	
				if(i.equals(6)) {
				}
				if(i.equals(7)) {
				}
				if(i.equals(8)) {
				}
				if(i.equals(9)) {
				}
				//				"MEAN(price)" + ", " +			
				//				"MODE(price)" + ", " +			
				//				"MEDIAN(price)" + ", " +						
				//				"STDDEV(price)" + ", " +						
				if(i.equals(10)) {
				}
				if(i.equals(11)) {
				}
				if(i.equals(12)) {
					Double num = new Double(extractQueryString(row, col));
					sizes.add((int) Math.round(num));
				}
				if(i.equals(13)) {
				}
				//				"DISTINCT(price)" + ", " +					
				//				"COUNT(price)" + ", " +			
				//				"SUM(price) " + ", " + 
				//				"DERIVATIVE(price)" + 	
				i++;
			}
			s = s + "\n";
		}
	}
//	System.out.println("SIZES: " + s + "\n");	full series
	return sizes;
}

	
}