package com.onenow.database;

import com.onenow.instrument.Investment;

public class Ring {
	
	Orchestrator orch = new Orchestrator();

	public Ring() {
		
	}
	
	// WRITE
	public void writePrice(EventPriceWrite event) {
		
		Long time = event.getTime(); 
		Investment inv = event.getInv(); 
		String dataType = event.getDataType(); 
		Double price = event.getPrice();

		// TODO: Write to different rings instead (orchestrator, investor...)
		getOrch().writePrice(time, inv, dataType, price);	// write
	}

	public void writeSize(EventSizeWrite event) {

		Long time = event.getTime();
		Investment inv = event.getInv();
		String dataType = event.getDataType();
		Integer size = event.getSize();
		
		// TODO: Write to different rings instead (orchestrator, investor...)
		getOrch().writeSize(time, inv, dataType, size);		// write
	}

	// SET GET
	public Orchestrator getOrch() {
		return orch;
	}

	public void setOrch(Orchestrator orch) {
		this.orch = orch;
	}
	
}
