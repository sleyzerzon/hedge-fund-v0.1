package com.onenow.database;

import java.util.ArrayList;
import java.util.List;

import com.onenow.data.Chartist;
import com.onenow.data.InitMarket;
import com.onenow.data.MarketPrice;
import com.onenow.instrument.Investment;
import com.onenow.research.Candle;

public class Orchestrator {

	MarketPrice marketPrice;
	Lookup lookup;
	TSDB TSDB;


	public Orchestrator() {
		
	}

	public Orchestrator(MarketPrice marketPrice) {
		setMarketPrice(marketPrice);
		setTSDB(new TSDB());
	}

	// SIZE	
	public void writeSize(Long time, Investment inv, String dataType, Integer size) {
		
		getTSDB().writeSize(time, inv, dataType, size);		// write
		// TODO: SNS
	}
	
	// READ
	public List<Candle> readPrice(	Investment inv, String dataType, String sampling, 
									String fromDate, String toDate) {

		// System.out.println("READ PRICE " + inv.toString() + " " + dataType + " " + sampling + " " + fromDate + " " + toDate);
		
		List<Candle> candles = new ArrayList<Candle>();
		try {
			candles = getTSDB().readPriceFromDB(inv, dataType, sampling, fromDate, toDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// System.out.println("PRICE CANDLES " + candles.toString());
		
		// convert to callback from event
		return candles;

	}

	public List<Integer> readSize(	Investment inv, String dataType, String sampling, 
									String fromDate, String toDate) {

		// System.out.println("READ SIZE " + inv.toString() + " " + dataType + " " + sampling + " " + fromDate + " " + toDate);
		
		List<Integer> sizes = new ArrayList<Integer>();
		
		try {
			sizes = getTSDB().readSizeFromDB(inv, dataType, sampling, fromDate, toDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//		System.out.println("SIZES " + sizes.toString());
		
		// convert to callback from event
		return sizes;
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

	public MarketPrice getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(MarketPrice marketPrice) {
		this.marketPrice = marketPrice;
	}

}
