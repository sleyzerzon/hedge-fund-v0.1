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
import com.onenow.constant.MemoryLevel;
import com.onenow.constant.SamplingRate;
import com.onenow.constant.TradeType;
import com.onenow.data.DataSampling;
import com.onenow.data.EventActivity;
import com.onenow.data.EventRequestHistory;
import com.onenow.data.EventRequestRealtime;
import com.onenow.data.EventRequest;
import com.onenow.instrument.Investment;
import com.onenow.research.Candle;

import java.util.logging.Level;

import com.onenow.util.TimeParser;
import com.onenow.util.Watchr;

public class databaseTimeSeries {
	
	private static InfluxDB influxDB = dbConnect();
	
	/**
	 * Default constructor connects to database
	 */
	public databaseTimeSeries() {
	}
	
// INIT
public static InfluxDB dbConnect() { 
	InfluxDB db  = null;
	boolean tryToConnect = true;
	while(tryToConnect) {		
		try {	
			tryToConnect = false;
			Watchr.log(Level.INFO, "CONNECTING TO TSDB...", "\n", "");
			NetworkService tsdbService = NetworkConfig.getTSDB();
			db = InfluxDBFactory.connect(	tsdbService.protocol+"://"+tsdbService.URI+":"+tsdbService.port, 
											tsdbService.user, tsdbService.pass);	
			dbCreateAndConnect();	
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

private static void dbCreateAndConnect() {
	try {
		influxDB.createDatabase(DBname.PRICE_DEVELOPMENT.toString());
		influxDB.createDatabase(DBname.PRICE_STAGING.toString());
		influxDB.createDatabase(DBname.PRICE_PRODUCTION.toString());

		influxDB.createDatabase(DBname.SIZE_DEVELOPMENT.toString());
		influxDB.createDatabase(DBname.SIZE_STAGING.toString());
		influxDB.createDatabase(DBname.SIZE_PRODUCTION.toString());
		
	} catch (Exception e) {
		// Throws exception if the DB already exists
		// e.printStackTrace();
	}
}

// PRICE
public static void writePrice(final EventActivity event) {
	String name = Lookup.getEventKey(event);
	final Serie serie = new Serie.Builder(name)
	.columns("time", "price")
	// iclient.write_points(json_body, time_precision='ms')
	// time*1000
	.values(event.time*1000, event.price)  // precision in seconds
	.build();

	new Thread () {
		@Override public void run () {

		Long before = TimeParser.getTimestampNow();
		influxDB.write(DBname.PRICE_STAGING.toString(), TimeUnit.MILLISECONDS, serie);
		// TODO: time_precision='ms'
		Long after = TimeParser.getTimestampNow();
	
		Watchr.log(Level.INFO, 	"TSDB WRITE: " + DBname.PRICE_STAGING.toString() + " " + 
								event.toString() + " " +  
								"ELAPSED WRITE " + (after-before) + "ms ",
								// "ELAPSED TOTAL " + (after-event.origin.start) + "ms ", // TODO: CloudWatch
								"\n", "");
		}
	}.start();
}

	
	public static List<Candle> readPriceFromDB(EventRequest request) {
		
		List<Candle> candles = new ArrayList<Candle>();
				
		String key = Lookup.getEventKey(request);

		List<Serie> series = readPriceSeriesFromDB(request);

		candles = priceSeriesToCandles(series); 
		 
		String log = "TSDB Cache Chart/Price READ: " + MemoryLevel.L2TSDB + "HISTORY " + request.toString() + " " + " for " + key + " Prices: " + candles.toString();
		Watchr.log(Level.INFO, log, "\n", "");

		return candles;
	}

public static List<Serie> readPriceSeriesFromDB(EventRequest request) {
	// Watchr.log(Level.INFO, "REQUESTING " + request.toString());
	List<Serie> series = queryPrice(DBname.PRICE_STAGING.toString(), request);
	return series;
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
public static List<Serie> queryPrice(String dbName, EventRequest request) {
	
	String serieName = Lookup.getEventKey(request);

	List<Serie> series = new ArrayList<Serie>();
	 
	String query = 	"SELECT " + getThoroughSelect("price") + " " + 						
					"FROM " + "\"" + serieName + "\" " +
					"GROUP BY " +
						"time" + "(" + DataSampling.getGroupByTimeString(request.sampling) + ") " + 
					// "FILL(0) " +
					"WHERE " +
					"time > " + "'" + request.fromDashedDate + "' " + 
					"AND " +
					"time < " + "'" + request.toDashedDate + "' "; 
					
	// TODO: SELECT PERCENTILE(column_name, N) FROM series_name group by time(10m) ...
	// TODO: SELECT HISTOGRAM(column_name) FROM series_name ...
	// TODO: SELECT TOP(column_name, N) FROM series_name ...
	// TODO: SELECT BOTTOM(column_name, N) FROM series_name ...
	
	try {
		series = influxDB.query(dbName, query, TimeUnit.MILLISECONDS);
		Watchr.log(Level.INFO, query + " RETURNED " + series.toString());  
	} catch (Exception e) {
		// e.printStackTrace(); // some series don't exist or have data 
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
		Watchr.log(Level.FINE, "TSDB NULL query result for ROW " + row + " " + row.get(col));
	}
	return s;
}


// SIZE
public static void writeSize(final EventActivity event) {
	String name = Lookup.getEventKey(event);
	final Serie serie = new Serie.Builder(name)
	.columns("time", "size")
	.values(event.time*1000, event.size) // precision in seconds
	.build();

	new Thread () {
		@Override public void run () {

		long before = TimeParser.getTimestampNow();
		influxDB.write(DBname.SIZE_STAGING.toString(), TimeUnit.MILLISECONDS, serie);
		long after = TimeParser.getTimestampNow();
	
		Watchr.log(	Level.INFO, "TSDB WRITE: " + DBname.SIZE_STAGING.toString() + " " + 
					event.toString() + " " +  
					"ELAPSED WRITE " + (after-before) + "ms ",
					// "ELAPSED TOTAL " + (after-event.origin.start) + "ms ",
					"\n", "");
		}
	}.start();

}

public static List<Integer> readSizeFromDB(	EventRequest request) {
	
	List<Integer> sizes = new ArrayList<Integer>();
	
	String key = Lookup.getEventKey(request);
	
	String fromDashedDate = TimeParser.getDateMinusDashed(request.toDashedDate, 1);		
	List<Serie> series = readSizeSeriesFromDB(key, request.sampling, fromDashedDate, request.toDashedDate);
	
	sizes = sizeSeriesToInts(series); 
	
	String log = "TSDB Cache Chart/Size READ: " + MemoryLevel.L2TSDB + " SIZE " + " for " + request.toString() + " Returned Sizes: " + sizes.toString();
	Watchr.log(Level.INFO, log, "\n", "");

	return sizes;
}

private static List<Serie> readSizeSeriesFromDB(String key,
		SamplingRate sampling, String fromDate, String toDate) {
	List<Serie> series = querySize(	DBname.SIZE_STAGING.toString(), key,  sampling, fromDate, toDate);
	return series;
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
	
	String query = 	"SELECT " + getThoroughSelect("size") + " " +
					"FROM " + "\"" + serieName + "\" " +
					"GROUP BY " +
						"time" + "(" + DataSampling.getGroupByTimeString(sampling) + ") " +
					// "FILL(0) " +
					"WHERE " +
					"time > " + "'" + fromDate + "' " + 
					"AND " +
					"time < " + "'" + toDate + "' ";
					
	try {
		series = influxDB.query(	dbName, query, TimeUnit.MILLISECONDS);
		Watchr.log(Level.INFO, query + " RETURNED " + series.toString()); 
	} catch (Exception e) {
		e.printStackTrace(); // some time series don't exist or have data
	}
	
	return series;
}

private static String getThoroughSelect(String table) {
	String s = "";
	s = 	"FIRST(" + table + ")" + ", " +			
			"LAST("  + table + ")" + ", " +			
			"DIFFERENCE(" + table + ")" + ", " +							
			"MIN("  + table + ")" + ", " +			
			"MAX(" +  table + ")" + ", " +			
			"MEAN(" + table + ")" + ", " +			
			"MODE(" + table + ")" + ", " +			
			"MEDIAN(" +  table + ")" + ", " +						
			"STDDEV(" +  table + ")" + ", " +						
			"DISTINCT(" + table + ")" + ", " +					
			"COUNT("  + table + ")" + ", " +			
			"SUM("  + table + ")" + ", " +
			"DERIVATIVE(" + table + ")" + " ";						  

	return s;
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