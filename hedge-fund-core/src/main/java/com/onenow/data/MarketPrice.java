package com.onenow.data;

import java.util.HashMap;
import java.util.logging.Level;

import org.apache.derby.iapi.types.DataType;

import com.onenow.alpha.BrokerInterface;
import com.onenow.constant.ColumnName;
import com.onenow.constant.GenericType;
import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.VolatilityType;
import com.onenow.constant.PriceType;
import com.onenow.constant.SizeType;
import com.onenow.execution.BrokerInteractive;
import com.onenow.instrument.Investment;
import com.onenow.io.CacheInProcess;
import com.onenow.portfolio.Portfolio;
import com.onenow.research.Chart;
import com.onenow.util.TimeParser;
import com.onenow.util.Watchr;

public class MarketPrice {

	private static CacheInProcess cache; 		// just storage	
	private Portfolio portfolio;
	private DataSampling sampling;
	private BrokerInteractive broker;
	
	public static HashMap<Investment, Long> lastTradeMilisecMap = new HashMap<Investment, Long>();
		
	public MarketPrice() {
		
	}
	
	public MarketPrice(Portfolio marketPortfolio, BrokerInteractive broker) {
		this.portfolio = marketPortfolio;
		this.broker = broker;
		this.cache = new CacheInProcess(broker);
		this.sampling = new DataSampling();
	}
	
	
	// WRITE REAL-TIME 
	// TODO: handle VWAP & VOLUME, SPLITFLAG
	private static void writePriceSizeRealtime(	Long timeInMilisec, Investment inv, Double lastPrice, Integer lastSize, 
												Integer volume, Double VWAP, boolean splitFlag,
												InvDataSource source) {

		if(lastSize>0) { 
			
			EventActivityPriceSizeRealtime event = new EventActivityPriceSizeRealtime(	timeInMilisec, inv, 
																						lastPrice, lastSize,
																						source);
			cache.writeEvent(event);
			
		} else {
			Watchr.info("RT Volume without size");	
		}
	}

	// PRICE
	public static void writePriceStreaming(Investment inv, Double price, PriceType priceType, InvDataSource source) {
		
		EventActivityPriceStreaming event = new EventActivityPriceStreaming(	TimeParser.getTimeMilisecondsNow(), inv,  
																				price, priceType, source);
				
		cache.writeEvent(event);
		
	}

	// SIZE
	public static void writeSizeStreaming(Investment inv, Integer size, SizeType sizeType, InvDataSource source) {
		
		EventActivitySizeStreaming event = new EventActivitySizeStreaming(	TimeParser.getTimeMilisecondsNow(), inv,  
																			size, sizeType, source);

		cache.writeEvent(event);
		
	}

	// GREEKS
	public static void writeGreekStreaming(Investment inv, Double greek, GreekType greekType, InvDataSource source) {
		
		ColumnName dataType = ColumnName.DELTA;
		
	}

	// VOLATILITY
	public static void writeVolatilityStreaming(Investment inv, Double computation, 
												VolatilityType volatilityType, InvDataSource source) {
		
		ColumnName dataType = ColumnName.DELTA;
		
	}
	
	// GENERIC
	public static void writeGenericStreaming(Investment inv, Double computation, GenericType generic, InvDataSource source) {
		
		ColumnName dataType = ColumnName.DELTA;
		
	}

	// STRINGS
	public static void parseAndWriteStrings(Investment inv, String value, InvDataSource source) {
		
		
	}
	
	// READ PRICE READ
	/**
	 * Get the latest price, based on the last time-stamp
	 * @param inv
	 * @param tradeType
	 */
	public double readPrice(Investment inv, PriceType tradeType) {
		return cache.readPrice(inv, tradeType);
	}
	

	// READ CHART
	public Chart readChart(	EventRequest request ) {
		
		Chart chart = new Chart();
		
		chart = cache.readChart(request);
		Watchr.log(Level.INFO, "READ CHART " + "\n" + chart);
		
		return chart;
	}		
		
	
	// PRIVATE
	public static void parseAndWriteRealTimePriceSize(Investment inv, String rtvolume) {
		String lastTradedPrice="";
		String lastTradeSize="";
		String lastTradeTime="";
		String totalVolume="";
		String VWAP="";
		String splitFlag="";
		
		int i=1;
		for(String split:rtvolume.split(";")) {
			if(i==1) { //	Last trade price
				lastTradedPrice = split;
				if(lastTradedPrice.equals("")) {
					return;
				}
			}
			if(i==2) { //	Last trade size
				lastTradeSize = split;
				if(lastTradeSize.equals("")) {
					return;
				}
			}
			if(i==3) { //	Last trade time
				lastTradeTime = split;
				if(lastTradeTime.equals("")) {
					return;
				}
			}
			if(i==4) { //	Total volume
				totalVolume = split;
				if(totalVolume.equals("")) {
					return;
				}
			}
			if(i==5) { //	VWAP
				VWAP = split;
				if(VWAP.equals("")) {
					return;
				}
			}
			if(i==6) { //	Single trade flag - True indicates the trade was filled by a single market maker; False indicates multiple market-makers helped fill the trade
				splitFlag = split;
				if(splitFlag.equals("")) {
					return;
				}
			}
			i++;
		}
		Long time = Long.parseLong(lastTradeTime); 	
		
		InvDataSource source = InvDataSource.IB;
		writePriceSizeRealtime(	time, inv, Double.parseDouble(lastTradedPrice), Integer.parseInt(lastTradeSize),  
									Integer.parseInt(totalVolume), Double.parseDouble(VWAP), Boolean.parseBoolean(splitFlag),
									source);
		return;
	}
	
	// PRINT
	public String toString() {
		String s="";
		s = cache.toString();
		return s;
	}

}
