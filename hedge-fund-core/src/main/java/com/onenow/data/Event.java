package com.onenow.data;

import java.util.logging.Level;

import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.SamplingRate;
import com.onenow.constant.TradeType;
import com.onenow.instrument.Investment;
import com.onenow.util.Watchr;

public class Event {
	
	public Investment investment;

	public InvDataSource source;
	public InvDataTiming timing;
	public TradeType tradeType; 

	public ObjectOrigination origin = new ObjectOrigination();

	public Event() {
		
	}	
	
	public Event(Investment investment, InvDataSource source, InvDataTiming timing, TradeType tradeType) {
		this.investment = investment;
		this.source = source;
		this.timing = timing;
		this.tradeType = tradeType; 

	}

	public String toString() {
		String s = "";
		
		s = 	origin.toString("EVENT") + " " + 
				investment.toString() + " " +
				"source " + source + " " + 
				"-timing " + timing + " " + 
				"-tradeType " + tradeType;
		
		return s;
	}
}
