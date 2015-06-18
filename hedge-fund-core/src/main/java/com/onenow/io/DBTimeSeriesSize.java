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

	/**
	 * A serie contains a list of increments
	 * @param series
	 * @return
	 */
	private static List<Integer> seriesToInts(List<Serie> series) {
		
		List<Integer> sizes = new ArrayList<Integer>();
		Watchr.log(Level.INFO, "SERIES: " + series.toString());

		String s="";
		
		for (Serie serie : series) {
			
			for (String col : serie.getColumns()) {
				s = s + col + "\t";
				System.out.println("column " + col); // column names
			}
			s = s + "\n";
			
			List<DBTimeIncrement> rows = DBTimeSeries.seriesToIncrements(serie, s);
			sizes.addAll(rowsToInts(rows));
			// sizes.add((int) Math.round(values.sum));
		}
		System.out.println("SIZES: " + s + "\n");	// full series
		return sizes;
	}
	
	/** 
	 * Each increment has an Integer "size"
	 * @param increments
	 * @return
	 */
	private static List<Integer> rowsToInts(List<DBTimeIncrement> rows) {
		
		List<Integer> integers = new ArrayList<Integer>();		

		for(DBTimeIncrement row:rows) {
			
			Integer sum = (int) Math.round(row.sum);
			
			integers.add(sum);
		}
		
		return integers;
	}
}
