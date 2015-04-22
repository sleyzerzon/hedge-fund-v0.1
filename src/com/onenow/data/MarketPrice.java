package com.onenow.data;

import java.util.List;

import com.onenow.constant.TradeType;
import com.onenow.database.Cache;
import com.onenow.database.EventRT;
import com.onenow.database.Orchestrator;
import com.onenow.instrument.Investment;
import com.onenow.research.Candle;
import com.onenow.research.Chart;

public class MarketPrice {

	Cache cache; 		// just storage
	
	InitMarket initMarket;
	Orchestrator orchestrator;
	
	public MarketPrice(InitMarket initMarket) {
		setInitMarket(initMarket);
		setCache(new Cache(this));
		setOrchestrator( new Orchestrator(this) );
	}
	
	// WRITE REAL-TIME 
	public void writeRealTime(	Long timeStamp, Investment inv, Double lastPrice, Integer lastSize, 
								Integer volume, Double VWAP, boolean splitFlag) {

		if(lastSize>0) { 
			
			EventRT event = new EventRT(timeStamp, inv, TradeType.TRADED.toString(), lastPrice, lastSize);
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
	 * @param dataType
	 */
	public double readPrice(Investment inv, String dataType) {

		return getCache().readPrice(inv, dataType);
		
	}
	
	public List<Candle> readPrice(	Investment inv, String dataType, String sampling, 
									String fromDate, String toDate) {
		
		return getCache().readPrice(inv, dataType, sampling, fromDate, toDate);
	}


	// READ CHART
	public Chart getChart(	Investment inv, String dataType, String sampling,
							String fromDate, String toDate) {
		
		Chart chart = new Chart();
		
		chart = getCache().readChart(inv, dataType, sampling, fromDate, toDate);
		//		System.out.println("READ CHART " + chart);
		
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
	public Cache getCache() {
		return cache;
	}

	public void setCache(Cache cache) {
		this.cache = cache;
	}

	public InitMarket getInitMarket() {
		return initMarket;
	}

	public void setInitMarket(InitMarket initMarket) {
		this.initMarket = initMarket;
	}

	public Orchestrator getOrchestrator() {
		return orchestrator;
	}

	public void setOrchestrator(Orchestrator orchestrator) {
		this.orchestrator = orchestrator;
	}
	
}
