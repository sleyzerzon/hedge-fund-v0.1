package com.onenow.io;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.influxdb.dto.Serie;

import com.onenow.constant.ColumnName;
import com.onenow.constant.DBQuery;
import com.onenow.constant.DBname;
import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.InvType;
import com.onenow.constant.PriceType;
import com.onenow.data.EventActivity;
import com.onenow.data.EventActivityPriceSizeRealtime;
import com.onenow.data.EventActivityPriceHistory;
import com.onenow.data.EventRequest;
import com.onenow.data.EventRequestRaw;
import com.onenow.instrument.Underlying;
import com.onenow.research.Candle;
import com.onenow.util.Watchr;

public class DBTimeSeriesPrice {

	public DBTimeSeriesPrice() {
		
	}
	
	static List<Serie> getWriteSerie(final EventActivity event, String serieName) {
		
		List<Serie> series = new ArrayList<Serie>();
		
		InvDataSource source = event.source;
		InvDataTiming timing = event.timing;
		PriceType tradeType = event.priceType;
		Underlying under = event.getUnder();
		InvType invType = event.getInvType();
		Double strikePrice = event.getOptionStrikePrice();
		String optionExpDate = event.getOptionExpirationDate(); 
		String futureExpDate = event.getFutureExpirationDate();
		
		if(event instanceof EventActivityPriceSizeRealtime) {
			Serie serie = getWriteSingleSerie(		serieName, 
													event.time, event.price,
													source, timing, tradeType,
													under, invType,
													strikePrice, optionExpDate,
													futureExpDate);
			series.add(serie);
		} 
		
		if(event instanceof EventActivityPriceHistory) {
			// TODO: use the actual interval size to place the four data points in time
			Serie serieOpen = getWriteSingleSerie(	serieName, 
													event.time, ((EventActivityPriceHistory) event).open,
													source, timing, tradeType,
													under, invType,
													strikePrice, optionExpDate,
													futureExpDate);
			Serie serieHigh = getWriteSingleSerie(	serieName, 
													event.time, ((EventActivityPriceHistory) event).high,
													source, timing, tradeType,
													under, invType,
													strikePrice, optionExpDate,
													futureExpDate);
			Serie serieLow = getWriteSingleSerie(	serieName, 
													event.time, ((EventActivityPriceHistory) event).low,
													source, timing, tradeType,
													under, invType,
													strikePrice, optionExpDate,
													futureExpDate);
			Serie serieClose = getWriteSingleSerie(	serieName, 
													event.time, ((EventActivityPriceHistory) event).close,
													source, timing, tradeType,
													under, invType,
													strikePrice, optionExpDate,
													futureExpDate);

			series.add(serieOpen);
			series.add(serieHigh);
			series.add(serieLow);
			series.add(serieClose);
		}
		
		return series;
	}

	private static Serie getWriteSingleSerie(	String serieName, 
												Long time, Double price,
												InvDataSource source, InvDataTiming timing, PriceType tradeType,
												Underlying under, InvType invType,
												Double strikePrice, String optionExpDate, 
												String futureExpDate) {
		
		final Serie serie = new Serie.Builder(serieName)
		.columns(	ColumnName.TIME.toString(), ColumnName.PRICE.toString(), 
					ColumnName.SOURCE.toString(), ColumnName.TIMING.toString(), ColumnName.TRADETYPE.toString(), 
					ColumnName.UNDERLYING.toString(), ColumnName.INVTYPE.toString(), 
					ColumnName.OPTIONSTRIKE.toString(), ColumnName.OPTIONEXP.toString(), 
					ColumnName.FUTUREEXP.toString())
		.values(time, price, 																		// basic columns
				"\""+ source + "\"", "\""+ timing +"\"", "\""+ tradeType +"\"",					// event origination
				"\""+ under + "\"", "\""+ invType +"\"", 								// investment
				"\""+ strikePrice +"\"", "\""+ optionExpDate +"\"",		// option
				"\""+ futureExpDate +"\""													// if future, expiration
				) 

		.build();
		return serie;
	}

	/**
	 * Write each price point.  In the case of real-time it's one, in the case of historic it is high/low/open/close for the interval
	 * @param event
	 */
	public static void write(EventActivity event) {
		
		String name = Lookup.getEventKey(event);		
		
		for(Serie serie : getWriteSerie(event, name)) {
			writeThread(event, serie);			
		}
	}

	public static void writeThread(EventActivity event, Serie serie) {
		DBTimeSeries.writeThread(event, serie, DBTimeSeries.getPriceDatabaseName());
	}


		
		public static List<Candle> read(EventRequestRaw request) throws Exception {
			
			List<Candle> candles = new ArrayList<Candle>();
					
			List<Serie> series = readSeries(request);

			candles = seriesToCandles(series); 
			 
//			String log = "TSDB Cache Chart/Price READ: " + MemoryLevel.L2TSDB + "HISTORY " + request.toString() + " " + " for " + key + " Prices: " + candles.toString();
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
