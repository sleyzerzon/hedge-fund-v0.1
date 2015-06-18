package com.onenow.io;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.influxdb.dto.Serie;

import com.onenow.constant.DBname;
import com.onenow.data.DataSampling;
import com.onenow.data.EventActivity;
import com.onenow.data.EventRequest;
import com.onenow.research.Candle;
import com.onenow.util.TimeParser;
import com.onenow.util.Watchr;

public class DBTimeSeriesPrice {

	public DBTimeSeriesPrice() {
		
	}
	
	// PRICE
	public static boolean writePrice(final EventActivity event) {
		final boolean success = false;
		String name = Lookup.getEventKey(event);
		
		final Serie serie = getWritePriceSerie(event, name);
		
		writeThreadedPrice(event, serie);

		return success;
	}


	static Serie getWritePriceSerie(final EventActivity event, String name) {
		final Serie serie = new Serie.Builder(name)
		.columns("time", "price", "source", "timing", "tradeType", "underlying", "invType", "optionStrike", "optionExp", "futureExp")
		.values(event.time, event.price, 																		// basic columns
				"\""+ event.source + "\"", "\""+ event.timing + "\"", event.tradeType + "\"",					// event origination
				"\""+ event.getUnder() + "\"", "\""+ event.getInvType() + "\"", 								// investment
				"\""+ event.getOptionStrikePrice() + "\"", "\""+ event.getOptionExpirationDate() + "\"",		// option
				"\""+ event.getFutureExpirationDate() + "\""													// if future, expiration
				) 

		.build();
		return serie;
	}

	static void writeThreadedPrice(final EventActivity event, final Serie serie) {
		
		new Thread () {
			@Override public void run () {

			Long before = TimeParser.getTimestampNow();
			try {
				DBTimeSeries.influxDB.write(getPriceDatabaseName().toString(), TimeUnit.MILLISECONDS, serie);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Long after = TimeParser.getTimestampNow();
		
			Watchr.log(Level.INFO, 	"TSDB WRITE: " + getPriceDatabaseName() + " " + 
									event.toString() + " " +  
									"ELAPSED WRITE " + (after-before) + "ms ",
									// "ELAPSED TOTAL " + (after-event.origin.start) + "ms ", // TODO: CloudWatch
									"\n", "");
			}
		}.start();
	}

	/** 
	 * Each environment has its own database
	 * @return
	 */
	private static DBname getPriceDatabaseName() {
		return DBname.PRICE_STAGING;
	}

		
		public static List<Candle> readPriceFromDB(EventRequest request) {
			
			List<Candle> candles = new ArrayList<Candle>();
					
			String key = Lookup.getEventKey(request);

			List<Serie> series = readPriceSeriesFromDB(request);

			candles = priceSeriesToCandles(series); 
			 
//			String log = "TSDB Cache Chart/Price READ: " + MemoryLevel.L2TSDB + "HISTORY " + request.toString() + " " + " for " + key + " Prices: " + candles.toString();
//			Watchr.log(Level.INFO, log, "\n", "");

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
		 
		String query = 	"SELECT " + DBTimeSeries.getThoroughSelect("price") + " " + 						
						"FROM " + "\"" + serieName + "\" " +
						"GROUP BY " +
							"time" + "(" + DataSampling.getGroupByTimeString(request.sampling) + ") " + 
						// "FILL(0) " +
						"WHERE " +
						"time > " + "'" + request.fromDashedDate + "' " + 
						"AND " +
						"time < " + "'" + request.toDashedDate + "' "; 
						
		
		// TODO The where clause supports comparisons against regexes, strings, booleans, floats, integers, and the times listed before. 
		// Comparators include = equal to, > greater than, < less than, <> not equal to, =~ matches against, !~ doesn’t match against. 
		// You can chain logic together using and and or and you can separate using ( and )
		
		// TODO: SELECT PERCENTILE(column_name, N) FROM series_name group by time(10m) ...
		// TODO: SELECT HISTOGRAM(column_name) FROM series_name ...
		// TODO: SELECT TOP(column_name, N) FROM series_name ...
		// TODO: SELECT BOTTOM(column_name, N) FROM series_name ...
		
		try {
			series = DBTimeSeries.influxDB.query(dbName, query, TimeUnit.MILLISECONDS);
			// Watchr.log(Level.INFO, query + " RETURNED " + series.toString());  
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
						candle.openPrice = new Double(DBTimeSeries.extractQueryString(row, col));
					}
					if(i.equals(2)) {
						candle.closePrice = new Double(DBTimeSeries.extractQueryString(row, col));
					}
					if(i.equals(3)) {
						candle.difference = new Double(DBTimeSeries.extractQueryString(row, col));
					}				
					//				"FIRST(price)" + ", " +			
					//				"LAST(price)" + ", " +			
					//				"DIFFERENCE(price)" + ", " +							
					if(i.equals(4)) {
						candle.lowPrice = new Double(DBTimeSeries.extractQueryString(row, col));
					}
					if(i.equals(5)) {
						candle.highPrice = new Double(DBTimeSeries.extractQueryString(row, col));
					}
					//				"MIN(price)" + ", " +			
					//				"MAX(price)" + ", " +			
					if(i.equals(6)) {
						candle.meanPrice = new Double(DBTimeSeries.extractQueryString(row, col));
					}
					if(i.equals(7)) {
						candle.modePrice = new Double(DBTimeSeries.extractQueryString(row, col));
					}
					if(i.equals(8)) {
						candle.medianPrice = new Double(DBTimeSeries.extractQueryString(row, col));
					}
					if(i.equals(9)) {
						candle.stddevPrice = new Double(DBTimeSeries.extractQueryString(row, col));
					}
					//				"MEAN(price)" + ", " +			
					//				"MODE(price)" + ", " +			
					//				"MEDIAN(price)" + ", " +						
					//				"STDDEV(price)" + ", " +						
					if(i.equals(10)) {
						candle.distinctPrice = new Double(DBTimeSeries.extractQueryString(row, col));
					}
					if(i.equals(11)) {
						candle.countPrice = new Double(DBTimeSeries.extractQueryString(row, col));
					}
					if(i.equals(12)) {
						candle.sumPrice = new Double(DBTimeSeries.extractQueryString(row, col));
					}
					if(i.equals(13)) {
						candle.derivativePrice = new Double(DBTimeSeries.extractQueryString(row, col));
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
//		System.out.println("CANDLE: " + s + "\n");	full candle
		return candles;
	}

}
