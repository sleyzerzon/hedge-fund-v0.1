package com.onenow.io;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.influxdb.dto.Serie;

import com.onenow.constant.ColumnName;
import com.onenow.constant.DBname;
import com.onenow.data.EventActivity;
import com.onenow.data.EventRequestRaw;
import com.onenow.research.Candle;
import com.onenow.util.Watchr;

public class DBTimeSeriesPrice {

	public DBTimeSeriesPrice() {
		
	}
	

	/**
	 * Write each price point.  In the case of real-time it's one, in the case of historic it is high/low/open/close for the interval
	 * @param event
	 */
	public static void write(EventActivity event) {
		
		String name = Lookup.getPriceEventKey(event);		
		
		Serie serie = DBTimeSeries.getWriteSerie(	event, name, ColumnName.PRICE );

		DBTimeSeries.writeThread(event, serie, DBTimeSeries.getPriceDatabaseName());
		
	}
		
		public static List<Candle> read(EventRequestRaw request) throws Exception {
			
			List<Candle> candles = new ArrayList<Candle>();
					
			List<Serie> series = readSeries(request);

			candles = seriesToCandles(series); 
			 
//			String log = "TSDB Cache Chart/Price READ: " + MemoryLevel.L2TSDB + "HISTORY " + request.toString() + " " + " Prices: " + candles.toString();
//			Watchr.log(Level.INFO, log, "\n", "");

			return candles;
		}

	public static List<Serie> readSeries(EventRequestRaw request) {
		DBname dbName = DBTimeSeries.getPriceDatabaseName(); 
		Watchr.log(Level.FINEST, "REQUEST " + request.toString());
		List<Serie> series = DBTimeSeries.query(dbName, request);
		return series;
	}

	/**
	 * A serie contains a list of increments
	 * @param series
	 * @return
	 */
	private static List<Candle> seriesToCandles(List<Serie> series) {
		
		List<Candle> candles = new ArrayList<Candle>();		
		// Watchr.log(Level.INFO, "SERIES TO CANDLE: " + series.toString());
				
		String s="";
		
		for (Serie serie : series) {
			
			for (String col : serie.getColumns()) {
				s = s + col + "\t";
				// System.out.println("column " + col); // column names
			}
			s = s + "\n";

			DBTimeIncrement increment = DBTimeSeries.thoroughSeriesToIncrements(serie, s);
			candles.add(incrementsToCandle(increment));
		}
		// System.out.println("CANDLE FROM SERIES: " + s + "\n");	// full candle
		return candles;
	}
	
	/** 
	 * Each increment has a candle
	 * @param increments
	 * @return
	 */
	private static Candle incrementsToCandle(DBTimeIncrement increment) {
		
		Candle candle = new Candle();
		
		candle.openPrice = increment.first;
		candle.closePrice = increment.last;
		candle.difference = increment.difference;
		candle.lowPrice = increment.min;
		candle.highPrice = increment.max;
		candle.meanPrice = increment.mean;
		candle.modePrice = increment.mode;
		candle.medianPrice = increment.median;
		candle.stddevPrice = increment.stddev;
		candle.distinctPrice = increment.distinct;
		candle.countPrice = increment.count;
		candle.sumPrice = increment.sum;
		candle.derivativePrice = increment.derivative;
			
		// Watchr.log(Level.FINEST, "CANDLE FROM INCREMENT: " + candle.toString());
		return candle;
	}

}
