package com.onenow.database;

import java.util.ArrayList;
import java.util.List;

import com.onenow.instrument.Investment;
import com.onenow.research.Candle;

public class Ring {
	
	Orchestrator orch = new Orchestrator();
	TSDB DB;	

	public Ring() {
		
	}
	
	// WRITE
	public void writeEventRT(EventRT event) {

		Long time = event.getTime(); 
		Investment inv = event.getInv(); 
		String dataType = event.getDataType(); 

		Double price = event.getPrice();
		int size = event.getSize();

		// TODO: INSERT RING AND EVENTS HERE.  Write to different rings instead (orchestrator, investor...)
		getOrch().writePrice(time, inv, dataType, price);	
		getOrch().writeSize(time, inv, dataType, size);		
	}
	
	// READ
	public List<Candle> readPrice(	Investment inv, String dataType, 
									String fromDate, String toDate, String sampling) {

//		System.out.println("READ PRICE " + inv.toString() + " " + dataType + " " + fromDate + " " + toDate + " " + sampling);
		
		List<Candle> candles = new ArrayList<Candle>();
		try {
			candles = getDB().readPriceFromDB(inv, dataType, fromDate, toDate, sampling);
		} catch (Exception e) {
//			e.printStackTrace();
		}
		
		System.out.println("PRICE CANDLES " + candles.toString());
		
		// convert to callback from event
		return candles;

	}

	public List<Integer> readSize(	Investment inv, String dataType, 
									String fromDate, String toDate, String sampling) {

//		System.out.println("READ SIZE " + inv.toString() + " " + dataType + " " + fromDate + " " + toDate + " " + sampling);
		
		List<Integer> sizes = new ArrayList<Integer>();
		
		try {
			sizes = getDB().readSizeFromDB(inv, dataType, fromDate, toDate, sampling);
		} catch (Exception e) {
//			e.printStackTrace();
		}
		
//		System.out.println("SIZES " + sizes.toString());
		
		// convert to callback from event
		return sizes;
	}


	
//	// PRICE
//	public void writePrice(EventPriceWrite event) {		
//		Long time = event.getTime(); 
//		Investment inv = event.getInv(); 
//		String dataType = event.getDataType(); 
//		Double price = event.getPrice();
//
//		// TODO: INSERT RING AND EVENTS HERE.  Write to different rings instead (orchestrator, investor...)
//		getOrch().writePrice(time, inv, dataType, price);	// write
//	}
//
//	public List<Candle> readPrice(	Investment inv, String dataType, 
//			String fromDate, String toDate, String sampling) {
//		return getDB().readPriceFromDB(inv, dataType, fromDate, toDate, sampling);
//	}
	
//	// SIZE
//	public void writeSize(EventSizeWrite event) {
//		Long time = event.getTime();
//		Investment inv = event.getInv();
//		String dataType = event.getDataType();
//		Integer size = event.getSize();
//		
//		// TODO: INSERT RING AND EVENTS HERE.  Write to different rings instead (orchestrator, investor...)
//		getOrch().writeSize(time, inv, dataType, size);		// write
//	}
//	
//	public List<Integer> readSize(	Investment inv, String dataType, 
//			String fromDate, String toDate, String sampling) {
//		
//		// convert to callback from event
//		return getDB().readSizeFromDB(inv, dataType, fromDate, toDate, sampling);
//	}


	// SET GET
	public Orchestrator getOrch() {
		return orch;
	}

	public void setOrch(Orchestrator orch) {
		this.orch = orch;
	}

	public TSDB getDB() {
		return DB;
	}

	public void setDB(TSDB dB) {
		DB = dB;
	}
	
}
