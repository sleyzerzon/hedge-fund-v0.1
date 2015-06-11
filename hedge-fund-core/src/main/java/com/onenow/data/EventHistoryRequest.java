package com.onenow.data;

import java.util.UUID;

import com.onenow.instrument.Investment;
import com.onenow.util.TimeParser;

public class EventHistoryRequest extends Event {

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
