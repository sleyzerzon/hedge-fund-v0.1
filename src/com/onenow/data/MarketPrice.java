package com.onenow.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.influxdb.dto.Serie;

import com.onenow.constant.DBname;
import com.onenow.constant.DataType;
import com.onenow.constant.TradeType;
import com.onenow.database.Cache;
import com.onenow.database.Lookup;
import com.onenow.database.TSDB;
import com.onenow.execution.QuoteDepth.DeepRow;
import com.onenow.instrument.Investment;
import com.onenow.research.Candle;
import com.onenow.research.Chart;

public class MarketPrice {

	Cache cache;
	
	public MarketPrice() {
		setCache(new Cache());
	}
	
	// WRITE REAL-TIME 
	public void writeRealTime(	Long timeStamp, Investment inv, Double lastPrice, Integer lastSize, 
								Integer volume, Double VWAP, boolean splitFlag) {

		if(lastSize>0) { 
			// TODO: ignore busts with negative size
			// TODO: IMPORTANT write both size and price or none
			getCache().writeSize(timeStamp, inv, TradeType.TRADED.toString(), lastSize);		
			getCache().writePrice(timeStamp, inv, TradeType.TRADED.toString(), lastPrice);
			
			// TODO: create these time series: VWAP & VOLUME
			// writeSizeDB(lastTradeTime, inv, DataType.VOLUME.toString(), volume);		
			// writePriceDB(lastTradeTime, inv, DataType.VWAP.toString(), VWAP);
			
			 getCache().writeFlag(timeStamp, inv, DataType.TRADEFLAG.toString(), splitFlag);			
		}
	}
	
	// WRITE NOT REAL-TIME
	public void writeSizeNotRealTime(Investment inv, int size, String tupe) {
		// TODO: ignore
	}

	public void writePriceNotRealTime(Investment inv, double price, String tupe) {
		// TODO: ignore
	}
	
	
	// CANDLES READ
	public Chart getChart(Investment inv, String dataType, 
			String fromDate, String toDate, String sampling) {
		
		Chart chart = new Chart();
		List<Candle> prices = getCache().readPrice(inv, dataType, fromDate, toDate, sampling);
		List<Integer> sizes = getCache().readSize(inv, dataType, fromDate, toDate, sampling);
		chart.setPrices(prices);
		chart.setSizes(sizes);

		return chart;
	}		
	
	// PRICE READ
	/**
	 * Get the latest price, based on the last time-stamp
	 * @param inv
	 * @param dataType
	 */
	public double readPrice(Investment inv, String dataType) {

		return getCache().readPrice(inv, dataType);
	}

	/**
	 * Get the price during a time window
	 * @param inv
	 * @param dataType
	 * @param fromDate
	 * @param toDate
	 * @param sampling
	 * @return
	 */
	public List<Candle> readPrice(	Investment inv, String dataType, 
			String fromDate, String toDate, String sampling) {
		
		return getCache().readPrice(inv, dataType, fromDate, toDate, sampling);

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
	
}
