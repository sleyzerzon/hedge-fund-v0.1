package com.onenow.data;

import java.util.UUID;

import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.TradeType;
import com.onenow.instrument.Investment;
import com.onenow.util.TimeParser;

public class Event {
	
	public long time;
	public Investment investment;
	
	public Double price;
	public Long size; 

	public InvDataSource source;
	public InvDataTiming timing;
	
	public TradeType tradeType; 	

	public final String id = String.valueOf(UUID.randomUUID());
	public final Long start = TimeParser.getTimestampNow();

	public Event() {
		
	}
	
	public String toString() {
		String s = "";
		s = s + "- id " + id.toString();
		s = s + "- start " + start.toString();
		return s;
	}

}
