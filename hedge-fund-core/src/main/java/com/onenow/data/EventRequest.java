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

		super.source = source;
		super.timing = timing;		
		super.tradeType = tradeType;

		this.sampling = sampling;
		this.fromDashedDate = fromDashedDate;
		this.toDashedDate = toDashedDate;		

	}

	public EventRequest(	Investment inv, TradeType tradeType, SamplingRate sampling, 
							InvDataSource source, InvDataTiming timing,
							String timeGap, String endPoint) {

		setInvestment(inv);
		
		super.source = source;
		super.timing = timing;
		super.tradeType = tradeType;
		
		this.sampling = sampling;
		this.timeGap = timeGap;
		this.endPoint = endPoint;

	}

	public String toString() {
		String s = "";
		
		s = s + super.toString() + " ";
		
		try {
			s = s + "-sampling " + sampling + " ";
		} catch (Exception e) {
		}
		
		try {
			s = s + "-from " + fromDashedDate + " ";
		} catch (Exception e) {
		}
				
		try {
			s = s + "-to " + toDashedDate;
		} catch (Exception e) {
		}	

		try {
			if(timeGap!=null) {
				s = s + "-timeGap " + timeGap;
			}
		} catch (Exception e) {
		}	

		try {
			if(endPoint!=null) {
				s = s + "-endPoint " + endPoint;
			}
		} catch (Exception e) {
		}	

		return s;
	}

}
