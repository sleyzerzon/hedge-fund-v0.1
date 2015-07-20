package com.onenow.data;

import com.ib.client.Types.WhatToShow;
import com.onenow.constant.PriceType;
import com.onenow.instrument.Investment;
import com.onenow.instrument.InvestmentIndex;
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

		super.priceType = getPriceTypeToSet(config.whatToShow); 		

	}
	
	private PriceType getPriceTypeToSet(WhatToShow whatToShow) {

		// default
		PriceType type = PriceType.CALCULATED;

		if(whatToShow.equals(WhatToShow.ASK)) {
			type = PriceType.ASK;
		}
		
		if(whatToShow.equals(WhatToShow.BID)) {
			type = PriceType.BID;
		}

		if(whatToShow.equals(WhatToShow.TRADES)) {
			type = PriceType.TRADED;
		}
		
		// override for indices that don't trade themselves
		if(getInvestment() instanceof InvestmentIndex) {
			type = PriceType.CALCULATED;
		}

		return type;
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
