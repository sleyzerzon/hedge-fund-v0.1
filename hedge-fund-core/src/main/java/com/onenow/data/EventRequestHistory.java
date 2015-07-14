package com.onenow.data;

import com.ib.client.Types.WhatToShow;
import com.onenow.constant.PriceType;
import com.onenow.instrument.Investment;
import com.onenow.util.TimeParser;

// https://www.interactivebrokers.com/en/software/api/apiguide/tables/historical_data_limitations.htm
public class EventRequestHistory extends EventRequest {

	public HistorianConfig config;
	
	public EventRequestHistory() {
		super();
	}

	public EventRequestHistory(Investment inv, String toDashedDate, HistorianConfig config) {

		super();
		
		this.config = config;

		setInvestment(inv);

		super.toDashedDate = toDashedDate;
		super.fromDashedDate = TimeParser.getDateMinusDashed(toDashedDate, 1);		
		
		super.source = config.source;
		super.timing = config.timing;
		super.sampling = config.sampling;

		super.priceType = getPriceType(config.whatToShow); 		

	}
	
	private PriceType getPriceType(WhatToShow whatToShow) {

		if(whatToShow.equals(WhatToShow.ASK)) {
			return PriceType.ASK;
		}
		
		if(whatToShow.equals(WhatToShow.BID)) {
			return PriceType.BID;
		}

		if(whatToShow.equals(WhatToShow.TRADES)) {
			return PriceType.TRADED;
		}
		// default
		return PriceType.CALCULATED;
	}	

	public String toString() {
		String s = "";
		
		s = s + super.toString() + " ";
				
		try {
			s = s + "-config " + config.toString();
		} catch (Exception e) {
		}
		
		return s;
	}
	
}
