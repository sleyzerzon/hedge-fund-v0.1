package com.onenow.io;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.influxdb.dto.Serie;

import com.onenow.constant.DBname;
import com.onenow.constant.SamplingRate;
import com.onenow.data.DataSampling;
import com.onenow.data.EventActivity;
import com.onenow.data.EventRequest;
import com.onenow.research.Candle;
import com.onenow.util.TimeParser;
import com.onenow.util.Watchr;

public class DBTimeSeriesSize {

	public DBTimeSeriesSize() {
		
	}
	
	static Serie getWriteSizeSerie(final EventActivity event, String name) {
		final Serie serie = new Serie.Builder(name)
		.columns("time", "size", "source", "timing", "tradeType", "underlying", "invType", "optionStrike", "optionExp", "futureExp")
		.values(event.time, event.size, 																		// basic columns
				"\""+ event.source + "\"", "\""+ event.timing + "\"", event.tradeType + "\"",					// event origination
				"\""+ event.getUnder() + "\"", "\""+ event.getInvType() + "\"", 								// investment
				"\""+ event.getOptionStrikePrice() + "\"", "\""+ event.getOptionExpirationDate() + "\"",		// option
				"\""+ event.getFutureExpirationDate() + "\""													// if future, expiration
				) 

		.build();
		return serie;
	}

	// SIZE
	public static void writeSize(final EventActivity event) {
		String name = Lookup.getEventKey(event);
		final Serie serie = getWriteSizeSerie(event, name);

		new Thread () {
			@Override public void run () {

			long before = TimeParser.getTimestampNow();
			DBTimeSeries.influxDB.write(DBname.SIZE_STAGING.toString(), TimeUnit.MILLISECONDS, serie);
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
		
//		String log = "TSDB Cache Chart/Size READ: " + MemoryLevel.L2TSDB + " SIZE " + " for " + request.toString() + " Returned Sizes: " + sizes.toString();
//		Watchr.log(Level.INFO, log, "\n", "");

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
		
		String query = 	"SELECT " + DBTimeSeries.getThoroughSelect("size") + " " +
						"FROM " + "\"" + serieName + "\" " +
						"GROUP BY " +
							"time" + "(" + DataSampling.getGroupByTimeString(sampling) + ") " +
						// "FILL(0) " +
						"WHERE " +
						"time > " + "'" + fromDate + "' " + 
						"AND " +
						"time < " + "'" + toDate + "' ";
						
		try {
			series = DBTimeSeries.influxDB.query(	dbName, query, TimeUnit.MILLISECONDS);
//			Watchr.log(Level.INFO, query + " RETURNED " + series.toString()); 
		} catch (Exception e) {
			e.printStackTrace(); // some time series don't exist or have data
		}
		
		return series;
	}

	private static List<Integer> sizeSeriesToInts(List<Serie> series) {
		List<Integer> sizes = new ArrayList<Integer>();
		
		String s="";
		for (Serie ser : series) {
			for (String col : ser.getColumns()) {
				s = s + col + "\t";
//				System.out.println("column " + col); column names
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
						Double num = new Double(DBTimeSeries.extractQueryString(row, col));
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
//		System.out.println("SIZES: " + s + "\n");	full series
		return sizes;
	}

}
