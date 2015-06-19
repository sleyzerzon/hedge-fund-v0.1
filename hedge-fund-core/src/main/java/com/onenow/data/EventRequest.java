package com.onenow.data;

import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.SamplingRate;
import com.onenow.constant.TradeType;
import com.onenow.instrument.Investment;

public class EventRequest extends Event {
	
	public SamplingRate sampling;

	public String toDashedDate;
	public String fromDashedDate;
	
	// where time > now() - 1h limit 1000
	public String timeGap;
	public String endPoint;
	

	public EventRequest() {
		
	}
	
	public EventRequest(	Investment inv, TradeType tradeType, SamplingRate sampling, 
							String fromDashedDate, String toDashedDate,
							InvDataSource source, InvDataTiming timing) {
		
		setInvestment(inv);
		this.tradeType = tradeType;
		this.sampling = sampling;
		this.fromDashedDate = fromDashedDate;
		this.toDashedDate = toDashedDate;
		this.source = source;
		this.timing = timing;		
	}

	public EventRequest(	Investment inv, TradeType tradeType, SamplingRate sampling, 
							InvDataSource source, InvDataTiming timing,
							String timeGap, String endPoint) {

		setInvestment(inv);
		this.tradeType = tradeType;
		this.sampling = sampling;
		this.source = source;
		this.timing = timing;
		
		this.timeGap = timeGap;
		this.endPoint = endPoint;

	}

	public String toString() {
		String s = "";
		s = 	super.toString() + " " +
				"-sampling " + sampling + " " +
				"-from " + fromDashedDate + " " +
				"-to " + toDashedDate;
		return s;
	}

}
