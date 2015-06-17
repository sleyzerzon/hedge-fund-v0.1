package com.onenow.data;

import com.onenow.instrument.Investment;
import com.onenow.util.TimeParser;

// https://www.interactivebrokers.com/en/software/api/apiguide/tables/historical_data_limitations.htm
public class EventRequestHistory extends EventRequest {

	public HistorianConfig config;
	
	public EventRequestHistory() {
		
	}

	public EventRequestHistory(Investment inv, String toDashedDate, HistorianConfig config) {

		setInvestment(inv);

		this.fromDashedDate = getFromDate(config, toDashedDate);		
		this.toDashedDate = toDashedDate;
		
		this.config = config;

		this.source = config.source;
		this.timing = config.timing;
		this.sampling = config.sampling;
		this.tradeType = config.tradeType; 		

	}
	
	/** 
	 * Calculate the from based on the extent of the query in the config
	 * @param config
	 * @return
	 */
	private String getFromDate(HistorianConfig config, String toDashedDate) {
		String fromDashedDate = "";
		
		fromDashedDate = TimeParser.getDateMinusDashed(toDashedDate, 1);
		
		return fromDashedDate;
	}

	public String toString() {
		String s = "";
		s = 	super.toString() + 
				"-config " + config.toString(); 
		return s;
	}
	
}
