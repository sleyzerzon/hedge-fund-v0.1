package com.onenow.data;

import com.onenow.instrument.Investment;

public class EventHistoryRequest {

	public static Investment investment;
	public static String toDashedDate;
	public static HistorianConfig config;
	
	public EventHistoryRequest() {
		
	}
	
	public EventHistoryRequest(Investment inv, String toDashedDate, HistorianConfig config) {
		this.config = config;
		this.toDashedDate = toDashedDate;
		this.investment = inv;
	}

}
