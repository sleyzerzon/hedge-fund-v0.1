package com.onenow.io;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.influxdb.dto.Serie;

import com.onenow.constant.ColumnName;
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
	
	static Serie getWriteSerie(final EventActivity event, String name) {
		final Serie serie = new Serie.Builder(name)
		.columns(	ColumnName.TIME.toString(), ColumnName.SIZE.toString(), 
					ColumnName.SOURCE.toString(), ColumnName.TIMING.toString(), ColumnName.TRADETYPE.toString(), 
					ColumnName.UNDERLYING.toString(), ColumnName.INVTYPE.toString(), 
					ColumnName.OPTIONSTRIKE.toString(), ColumnName.OPTIONEXP.toString(), 
					ColumnName.FUTUREEXP.toString())
		.values(event.time, event.size, 																		// basic columns
				"\""+ event.source + "\"", "\""+ event.timing + "\"", event.tradeType + "\"",					// event origination
				"\""+ event.getUnder() + "\"", "\""+ event.getInvType() + "\"", 								// investment
				"\""+ event.getOptionStrikePrice() + "\"", "\""+ event.getOptionExpirationDate() + "\"",		// option
				"\""+ event.getFutureExpirationDate() + "\""													// if future, expiration
				) 

		.build();
		return serie;
	}

	public static void write(final EventActivity event) {
		String name = Lookup.getEventKey(event);
		final Serie serie = getWriteSerie(event, name);

		writeThread(event, serie);

	}

	private static void writeThread(final EventActivity event, final Serie serie) {
		new Thread () {
			@Override public void run () {

			long before = TimeParser.getTimestampNow();
			DBTimeSeries.influxDB.write(DBTimeSeries.getSizeDatabaseName().toString(), TimeUnit.MILLISECONDS, serie);
			long after = TimeParser.getTimestampNow();
		
			Watchr.log(	Level.INFO, "TSDB WRITE: " + DBTimeSeries.getSizeDatabaseName().toString() + " " + 
						event.toString() + " " +  
						"ELAPSED WRITE " + (after-before) + "ms ",
						// "ELAPSED TOTAL " + (after-event.origin.start) + "ms ",
						"\n", "");
			}
		}.start();
	}

	public static List<Integer> read(EventRequest request) {
		
		List<Integer> sizes = new ArrayList<Integer>();
		
		List<Serie> series = readSeries(request);
		
		sizes = seriesToInts(series); 
		
//		String log = "TSDB Cache Chart/Size READ: " + MemoryLevel.L2TSDB + " SIZE " + " for " + request.toString() + " Returned Sizes: " + sizes.toString();
//		Watchr.log(Level.INFO, log, "\n", "");

		return sizes;
	}

	private static List<Serie> readSeries(EventRequest request) {
		
		List<Serie> series = DBTimeSeries.query(ColumnName.SIZE, DBTimeSeries.getSizeDatabaseName().toString(), request);
		return series;
	}


	private static List<Integer> seriesToInts(List<Serie> series) {
		List<Integer> sizes = new ArrayList<Integer>();
		
		String s="";
		for (Serie ser : series) {
			for (String col : ser.getColumns()) {
				s = s + col + "\t";
//				System.out.println("column " + col); column names
			}
			s = s + "\n";
			for (Map<String, Object> row : ser.getRows()) {
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
