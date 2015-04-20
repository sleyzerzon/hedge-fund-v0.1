package com.onenow.database;

import com.onenow.instrument.Investment;

public class Orchestrator {
	
	TSDB TSDB = new TSDB();
	Lookup lookup;

	public Orchestrator() {
		
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

}
