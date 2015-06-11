package com.onenow.data;

import java.util.UUID;

import com.onenow.instrument.Investment;

public class EventHistoryRequest {

	public static Investment investment;
	public static String toDashedDate;
	public static HistorianConfig config;
	
	public final String id = String.valueOf(UUID.randomUUID());

	public EventHistoryRequest() {
		
	}
	
	public EventHistoryRequest(Investment inv, String toDashedDate, HistorianConfig config) {
		this.config = config;
		this.toDashedDate = toDashedDate;
		this.investment = inv;
	}

}
