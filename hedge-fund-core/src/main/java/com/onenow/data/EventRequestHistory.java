package com.onenow.data;

import com.onenow.instrument.Investment;

public class EventRequestHistory extends EventRequest {

	public HistorianConfig config;
	
	public EventRequestHistory() {
		
	}

	public EventRequestHistory(Investment inv, String fromDashedDate, String toDashedDate, HistorianConfig config) {

		this.fromDashedDate = fromDashedDate;
		this.toDashedDate = toDashedDate;
		this.investment = inv;

		this.config = config;

		this.source = config.source;
		this.timing = config.timing;
		this.sampling = config.sampling;
		this.tradeType = config.tradeType; 		

	}

}
