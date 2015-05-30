package com.onenow.data;

import java.util.List;

import com.onenow.alpha.Broker;
import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.SamplingRate;
import com.onenow.constant.TradeType;
import com.onenow.instrument.Investment;
import com.onenow.io.EventRT;
import com.onenow.io.PriceSizeCache;
import com.onenow.portfolio.Portfolio;
import com.onenow.research.Candle;
import com.onenow.research.Chart;

public class MarketPrice {

	private PriceSizeCache cache; 		// just storage	
	private Portfolio portfolio;
	private DataSampling sampling;
	private Broker broker;
	
	
	public MarketPrice() {
		
	}
	
	public MarketPrice(Portfolio marketPortfolio, Broker broker) {
		setPortfolio(marketPortfolio);
		setBroker(broker);
		setCache(new PriceSizeCache(getBroker()));
		setSampling(new DataSampling());
	}
	
	
	// WRITE REAL-TIME 
	public void writeRealTime(	Long timeStamp, Investment inv, Double lastPrice, Integer lastSize, 
								Integer volume, Double VWAP, boolean splitFlag,
								InvDataSource source, InvDataTiming timing) {

		if(lastSize>0) { 
			
			EventRT event = new EventRT(	timeStamp, inv, TradeType.TRADED, 
											lastPrice, lastSize,
											source, timing);
			getCache().writeEventRT(event);
			
			
//			// TODO: ignore busts with negative size
//			// TODO: IMPORTANT write both size and price or none
//			getCache().writeSize(timeStamp, inv, TradeType.TRADED.toString(), lastSize);		
//			getCache().writePrice(timeStamp, inv, TradeType.TRADED.toString(), lastPrice);
//			
//			// TODO: create these time series: VWAP & VOLUME
//			// writeSizeDB(lastTradeTime, inv, DataType.VOLUME.toString(), volume);		
//			// writePriceDB(lastTradeTime, inv, DataType.VWAP.toString(), VWAP);
//			
//			 getCache().writeFlag(timeStamp, inv, DataType.TRADEFLAG.toString(), splitFlag);			
		}
	}
	
	// WRITE NOT REAL-TIME
	public void writeSizeNotRealTime(Investment inv, int size, String tupe) {
		// TODO: ignore
	}
	public void writePriceNotRealTime(Investment inv, double price, String tupe) {
		// TODO: ignore
	}
	
	// READ PRICE READ
	/**
	 * Get the latest price, based on the last time-stamp
	 * @param inv
	 * @param tradeType
	 */
	public double readPrice(Investment inv, TradeType tradeType) {
		return getCache().readPrice(inv, tradeType);
	}
	
//	public List<Candle> readPrice(	Investment inv, String dataType, String sampling, 
//									String fromDate, String toDate) {
//		
//		return getCache().readPrice(inv, dataType, sampling, fromDate, toDate);
//	}


	// READ CHART
	public Chart readChart(	Investment inv, TradeType tradeType, SamplingRate sampling,
							String fromDate, String toDate,
							InvDataSource source, InvDataTiming timing) {
		
		Chart chart = new Chart();
		
		chart = getCache().readChart(	inv, tradeType, sampling, fromDate, toDate,
											source, timing);
		System.out.println("READ CHART " + "\n" + chart);
		
		return chart;
	}		
			
	
	// PRINT
	public String toString() {
		String s="";
		s = getCache().toString();
		return s;
	}

	
	// TEST
	
	
	// SET GET
	public PriceSizeCache getCache() {
		return cache;
	}

	public void setCache(PriceSizeCache cache) {
		this.cache = cache;
	}

	public Portfolio getPortfolio() {
		return portfolio;
	}

	public void setPortfolio(Portfolio portfolio) {
		this.portfolio = portfolio;
	}

	public DataSampling getSampling() {
		return sampling;
	}

	public void setSampling(DataSampling sampling) {
		this.sampling = sampling;
	}

	public Broker getBroker() {
		return broker;
	}

	public void setBroker(Broker broker) {
		this.broker = broker;
	}
	
}
