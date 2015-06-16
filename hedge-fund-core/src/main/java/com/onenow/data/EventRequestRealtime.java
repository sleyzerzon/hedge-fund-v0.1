package com.onenow.data;

import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.SamplingRate;
import com.onenow.constant.TradeType;
import com.onenow.instrument.Investment;

public class EventRequestRealtime extends EventRequest {
	
	public InvDataSource source;
	public InvDataTiming timing;
	
	public SamplingRate sampling;
	public TradeType tradeType; 

	public EventRequestRealtime() {
		
	}

	public EventRequestRealtime(	Investment inv,
									InvDataSource source, InvDataTiming timing, 
									SamplingRate sampling, TradeType tradeType,
									String fromDashedDate, String toDashedDate) {
		
		setInvestment(inv);

		this.source = source;
		this.timing = timing;
		this.sampling = sampling;
		this.tradeType = tradeType;
		this.fromDashedDate = fromDashedDate;
		this.toDashedDate = toDashedDate;
	}

	public String toString() {
		String s = "";
		s = 	super.toString() + " " +
				"-source " + source + " " + 
				"-timing " + timing + " " +
				"-sampling " + sampling + " " +
				"-tradeType " + tradeType;
		
		return s;
	}
}
