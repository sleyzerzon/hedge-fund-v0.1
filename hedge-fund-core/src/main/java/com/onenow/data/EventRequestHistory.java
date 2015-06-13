package com.onenow.data;

import java.util.logging.Level;

import com.onenow.instrument.Investment;
import com.onenow.util.TimeParser;
import com.onenow.util.Watchr;

public class EventRequestHistory extends EventRequest {

	public HistorianConfig config;
	
	public EventRequestHistory() {
		
	}

	public EventRequestHistory(Investment inv, String toDashedDate, HistorianConfig config) {

		this.fromDashedDate = getFromDate(config, toDashedDate);
		
		this.toDashedDate = toDashedDate;
		this.investment = inv;

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
		try {
			s = super.toString();
		} catch (Exception e) {
			Watchr.log(Level.SEVERE, e.toString());
		}
		return s;
	}
	
}
