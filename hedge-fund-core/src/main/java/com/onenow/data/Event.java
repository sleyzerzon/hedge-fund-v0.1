package com.onenow.data;

import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.SamplingRate;
import com.onenow.constant.TradeType;
import com.onenow.instrument.Investment;

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
		
		s = super.toString();
		s = s + investment.toString() + " " + source + " " + timing + " " + tradeType;
		
		return s;
	}
}
