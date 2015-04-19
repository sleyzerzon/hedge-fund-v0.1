package com.onenow.database;

import java.util.List;

import com.onenow.instrument.Investment;
import com.onenow.research.Candle;

public class Ring {
	
	Orchestrator orch = new Orchestrator();
	TSDB DB;	

	public Ring() {
		
	}
	
	// PRICE
	public void writePrice(EventPriceWrite event) {		
		Long time = event.getTime(); 
		Investment inv = event.getInv(); 
		String dataType = event.getDataType(); 
		Double price = event.getPrice();

		// TODO: INSERT RING AND EVENTS HERE.  Write to different rings instead (orchestrator, investor...)
		getOrch().writePrice(time, inv, dataType, price);	// write
	}

	public List<Candle> readPrice(	Investment inv, String dataType, 
			String fromDate, String toDate, String sampling) {
		return getDB().readPriceFromDB(inv, dataType, fromDate, toDate, sampling);
	}
	
	// SIZE
	public void writeSize(EventSizeWrite event) {
		Long time = event.getTime();
		Investment inv = event.getInv();
		String dataType = event.getDataType();
		Integer size = event.getSize();
		
		// TODO: INSERT RING AND EVENTS HERE.  Write to different rings instead (orchestrator, investor...)
		getOrch().writeSize(time, inv, dataType, size);		// write
	}
	
	public List<Integer> readSize(	Investment inv, String dataType, 
			String fromDate, String toDate, String sampling) {
		
		// convert to callback from event
		return getDB().readSizeFromDB(inv, dataType, fromDate, toDate, sampling);
	}


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
