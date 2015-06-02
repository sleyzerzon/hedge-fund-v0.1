package com.onenow.data;

import com.onenow.instrument.Investment;

public class EventHistoryRequest {

	public static HistorianConfig config;
	public static Investment investment;
	public static String toDashedDate;
	
	public EventHistoryRequest() {
		
	}
	
	public EventHistoryRequest(Investment inv, String toDashedDate, HistorianConfig config) {
		this.config = config;
		this.toDashedDate = toDashedDate;
		this.investment = inv;
	}

}
