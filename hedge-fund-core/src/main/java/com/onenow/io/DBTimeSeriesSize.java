package com.onenow.io;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.influxdb.dto.Serie;

import com.onenow.constant.ColumnName;
import com.onenow.constant.DBname;
import com.onenow.data.EventActivity;
import com.onenow.data.EventRequestRaw;
import com.onenow.util.Watchr;

public class DBTimeSeriesSize {

	public DBTimeSeriesSize() {
		
	}
	
	public static void write(EventActivity event) {
		
		String name = Lookup.getEventKey(event);
		
		final Serie serie = DBTimeSeries.getWriteSerie(event, name, ColumnName.SIZE);

		DBTimeSeries.writeThread(event, serie, DBTimeSeries.getSizeDatabaseName());

	}
	
	public static List<Integer> read(EventRequestRaw request) throws Exception {
		
		List<Integer> sizes = new ArrayList<Integer>();
		
		List<Serie> series = readSeries(request);
		
		sizes = seriesToSizes(series); 
		
//		String log = "TSDB Cache Chart/Size READ: " + MemoryLevel.L2TSDB + " SIZE " + " for " + request.toString() + " Returned Sizes: " + sizes.toString();
//		Watchr.log(Level.INFO, log, "\n", "");

		return sizes;
	}

	private static List<Serie> readSeries(EventRequestRaw request) {
		
		DBname dbName = DBTimeSeries.getSizeDatabaseName(); 
		Watchr.log(Level.FINEST, "REQUEST " + request.toString());
		List<Serie> series = DBTimeSeries.query(dbName, request);

		return series;
	}

	/**
	 * A serie contains a list of increments
	 * @param series
	 * @return
	 */
	private static List<Integer> seriesToSizes(List<Serie> series) {
		
		List<Integer> sizes = new ArrayList<Integer>();
		// Watchr.log(Level.INFO, "SERIES: " + series.toString());

		String s="";
		
		for (Serie serie : series) {
			
			for (String col : serie.getColumns()) {
				s = s + col + "\t";
				// System.out.println("column " + col); // column names
			}
			s = s + "\n";
			
			DBTimeIncrement increment = DBTimeSeries.thoroughSeriesToIncrements(serie, s);
			sizes.add(incrementToSize(increment));
		}
		// System.out.println("SIZES: " + s + "\n");	// full series
		return sizes;
	}
	
	/** 
	 * Each increment has an Integer "size"
	 * @param increments
	 * @return
	 */
	private static Integer incrementToSize(DBTimeIncrement row) {
		
		Integer sum = (int) Math.round(row.sum);
			
		return sum;
	}
}
