package com.onenow.database;

import com.onenow.data.Chartist;
import com.onenow.data.InitMarket;
import com.onenow.data.MarketPrice;
import com.onenow.instrument.Investment;

public class Orchestrator {

	Lookup lookup;
	TSDB TSDB = new TSDB();
	
	MarketPrice marketPrice;
	Cache cache;


	public Orchestrator() {
		
	}

	public Orchestrator(MarketPrice marketPrice) {
		setMarketPrice(marketPrice);
		setCache(getMarketPrice().getCache());
		setTSDB(new TSDB());
	}

	// SIZE	
	public void writeSize(Long time, Investment inv, String dataType, Integer size) {
		
		getTSDB().writeSize(time, inv, dataType, size);		// write
		// TODO: SNS
	}
	
	// TODO: READ! BUT WHY RETURN SERIES???
//	public List<Serie> readSize(	Investment inv, String dataType,
//			String fromDate, String toDate, String sampling) {
//
//	List<Serie> series = new ArrayList<Serie>();
//	series = getTSDB().readSize(inv, dataType, fromDate, toDate, sampling);
//	
//	return series;
//	}

	// PRICE
	public void writePrice(Long time, Investment inv, String dataType, Double price) {
		
		getTSDB().writePrice(time, inv, dataType, price);	// write
		// TODO: SNS
	}

	// TODO: READ!  BUT WHY RETURN SERIES???
//	public List<Serie> readPrice(	Investment inv, String dataType,
//									String fromDate, String toDate, String sampling) {
//
//		List<Serie> series = new ArrayList<Serie>();
//		series = getTSDB().readPrice(inv, dataType, fromDate, toDate, sampling);
//
//		return series;
//	}

	
	// TEST

	// PRINT
	
	// SET GET
	public Lookup getLookup() {
		return lookup;
	}

	public void setLookup(Lookup lookup) {
		this.lookup = lookup;
	}

	public TSDB getTSDB() {
		return TSDB;
	}

	public void setTSDB(TSDB tSDB) {
		TSDB = tSDB;
	}

	public Cache getCache() {
		return cache;
	}

	public void setCache(Cache cache) {
		this.cache = cache;
	}

	public MarketPrice getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(MarketPrice marketPrice) {
		this.marketPrice = marketPrice;
	}

}
