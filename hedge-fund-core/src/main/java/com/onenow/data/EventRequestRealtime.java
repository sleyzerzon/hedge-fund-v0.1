package com.onenow.data;

import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.SamplingRate;
import com.onenow.constant.PriceType;
import com.onenow.instrument.Investment;

public class EventRequestRealtime extends EventRequest {
	
	
	public EventRequestRealtime() {
		super();
	}

	public EventRequestRealtime(	Investment inv,
									InvDataSource source, InvDataTiming timing, 
									SamplingRate sampling, PriceType tradeType,
									String fromDashedDate, String toDashedDate) {
		
		super();
		
		setInvestment(inv);

		super.source = source;
		super.timing = timing;
		super.sampling = sampling;
		super.priceType = tradeType;
		
		super.fromDashedDate = fromDashedDate;
		super.toDashedDate = toDashedDate;
	}

	public String toString() {
		String s = "";
		
		s = s +	super.toString() + " ";
		
		return s;
	}
}
