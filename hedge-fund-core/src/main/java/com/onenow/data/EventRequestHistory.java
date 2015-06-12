package com.onenow.data;

import com.onenow.instrument.Investment;

public class EventRequestHistory extends EventRequest {

	public EventRequestHistory() {
		
	}

	public EventRequestHistory(Investment inv, String toDashedDate, HistorianConfig config) {

		this.toDashedDate = toDashedDate;
		this.investment = inv;

		this.source = config.source;
		this.timing = config.timing;
		this.sampling = config.sampling;
		this.tradeType = config.tradeType; 

	}

}
