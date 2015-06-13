package com.onenow.data;

import java.util.logging.Level;

import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.SamplingRate;
import com.onenow.constant.TradeType;
import com.onenow.instrument.Investment;
import com.onenow.util.Watchr;


public class EventRequest extends Event {
	
	public SamplingRate sampling;

	public String toDashedDate;
	public String fromDashedDate;
		
	public EventRequest() {
		
	}
	
	public EventRequest(	Investment inv, TradeType tradeType, SamplingRate sampling, 
							String fromDashedDate, String toDashedDate,
							InvDataSource source, InvDataTiming timing) {
		
		this.investment = inv;
		this.tradeType = tradeType;
		this.sampling = sampling;
		this.fromDashedDate = fromDashedDate;
		this.toDashedDate = toDashedDate;
		this.source = source;
		this.timing = timing;
		
	}
	
	public String toString() {
		String s = "";
		try {
			s = super.toString();
			s = s + "-sampling " + sampling + "-from " + fromDashedDate + "-to " + toDashedDate;
		} catch (Exception e) {
			Watchr.log(Level.SEVERE, e.toString());
		}
		return s;
	}

}
