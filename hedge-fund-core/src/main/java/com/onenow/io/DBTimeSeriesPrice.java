package com.onenow.io;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.influxdb.dto.Serie;

import com.onenow.constant.ColumnName;
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
	
	static Serie getWriteSerie(final EventActivity event, String serieName) {
		final Serie serie = new Serie.Builder(serieName)
		.columns(	ColumnName.TIME.toString(), ColumnName.PRICE.toString(), 
					ColumnName.SOURCE.toString(), ColumnName.TIMING.toString(), ColumnName.TRADETYPE.toString(), 
					ColumnName.UNDERLYING.toString(), ColumnName.INVTYPE.toString(), 
					ColumnName.OPTIONSTRIKE.toString(), ColumnName.OPTIONEXP.toString(), 
					ColumnName.FUTUREEXP.toString())
		.values(event.time, event.price, 																		// basic columns
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

	static void writeThread(final EventActivity event, final Serie serie) {
		
		new Thread () {
			@Override public void run () {

			Long before = TimeParser.getTimestampNow();
			try {
				DBTimeSeries.influxDB.write(DBTimeSeries.getPriceDatabaseName().toString(), TimeUnit.MILLISECONDS, serie);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Long after = TimeParser.getTimestampNow();
		
			Watchr.log(Level.INFO, 	"TSDB WRITE: " + DBTimeSeries.getPriceDatabaseName() + " " + 
									event.toString() + " " +  
									"ELAPSED WRITE " + (after-before) + "ms ",
									// "ELAPSED TOTAL " + (after-event.origin.start) + "ms ", // TODO: CloudWatch
									"\n", "");
			}
		}.start();
	}
		
		public static List<Candle> read(EventRequest request) {
			
			List<Candle> candles = new ArrayList<Candle>();
					
			List<Serie> series = readSeries(request);

			candles = seriesToCandles(series); 
			 
//			String log = "TSDB Cache Chart/Price READ: " + MemoryLevel.L2TSDB + "HISTORY " + request.toString() + " " + " for " + key + " Prices: " + candles.toString();
//			Watchr.log(Level.INFO, log, "\n", "");

			return candles;
		}

	public static List<Serie> readSeries(EventRequest request) {
		// Watchr.log(Level.INFO, "REQUESTING " + request.toString());
		List<Serie> series = DBTimeSeries.query(ColumnName.PRICE, DBTimeSeries.getPriceDatabaseName().toString(), request);
		return series;
	}

	private static List<Candle> seriesToCandles(List<Serie> series) {
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
