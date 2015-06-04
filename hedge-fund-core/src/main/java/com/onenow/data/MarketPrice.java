package com.onenow.data;

import java.util.List;

import com.onenow.alpha.Broker;
import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.SamplingRate;
import com.onenow.constant.TradeType;
import com.onenow.instrument.Investment;
import com.onenow.io.PriceSizeCache;
import com.onenow.portfolio.Portfolio;
import com.onenow.research.Chart;

public class MarketPrice {

	private PriceSizeCache cache; 		// just storage	
	private Portfolio portfolio;
	private DataSampling sampling;
	private Broker broker;
	
	
	public MarketPrice() {
		
	}
	
	public MarketPrice(Portfolio marketPortfolio, Broker broker) {
		this.portfolio = marketPortfolio;
		this.broker = broker;
		this.cache = new PriceSizeCache(broker);
		this.sampling = new DataSampling();
	}
	
	
	// WRITE REAL-TIME 
	public void writeRealTime(	Long timeStamp, Investment inv, Double lastPrice, Integer lastSize, 
								Integer volume, Double VWAP, boolean splitFlag,
								InvDataSource source, InvDataTiming timing) {

		if(lastSize>0) { 
			
			EventRealTime event = new EventRealTime(	timeStamp, inv, TradeType.TRADED, 
														lastPrice, lastSize,
														source, timing);
			// System.out.println("MarketPrice WRITE " + event.toString());
			cache.writeEventRT(event);
			
			
//			// TODO: ignore busts with negative size
//			// TODO: IMPORTANT write both size and price or none
//			cache.writeSize(timeStamp, inv, TradeType.TRADED.toString(), lastSize);		
//			cache.writePrice(timeStamp, inv, TradeType.TRADED.toString(), lastPrice);
//			
//			// TODO: create these time series: VWAP & VOLUME
//			// writeSizeDB(lastTradeTime, inv, DataType.VOLUME.toString(), volume);		
//			// writePriceDB(lastTradeTime, inv, DataType.VWAP.toString(), VWAP);
//			
//			 cache.writeFlag(timeStamp, inv, DataType.TRADEFLAG.toString(), splitFlag);			
		}
	}
	
	
	// READ PRICE READ
	/**
	 * Get the latest price, based on the last time-stamp
	 * @param inv
	 * @param tradeType
	 */
	public double readPrice(Investment inv, TradeType tradeType) {
		return cache.readPrice(inv, tradeType);
	}
	

	// READ CHART
	public Chart readChart(	Investment inv, TradeType tradeType, SamplingRate sampling,
							String fromDate, String toDate,
							InvDataSource source, InvDataTiming timing) {
		
		Chart chart = new Chart();
		
		chart = cache.readChart(	inv, tradeType, sampling, fromDate, toDate,
											source, timing);
		System.out.println("READ CHART " + "\n" + chart);
		
		return chart;
	}		
			
	
	// PRINT
	public String toString() {
		String s="";
		s = cache.toString();
		return s;
	}

}
