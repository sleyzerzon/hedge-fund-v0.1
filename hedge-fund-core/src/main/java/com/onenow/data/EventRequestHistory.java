package com.onenow.data;

import com.ib.client.Types.BarSize;
import com.onenow.constant.TradeType;
import com.onenow.execution.HistorianService;
import com.onenow.instrument.Investment;
import com.onenow.instrument.InvestmentIndex;
import com.onenow.util.TimeParser;

// https://www.interactivebrokers.com/en/software/api/apiguide/tables/historical_data_limitations.htm
public class EventRequestHistory extends EventRequest {

	public HistorianConfig config;
	
	public EventRequestHistory() {
		
	}

	public EventRequestHistory(Investment inv, String toDashedDate, HistorianConfig config) {

		setInvestment(inv);

		this.toDashedDate = toDashedDate;
		this.fromDashedDate = TimeParser.getDateMinusDashed(toDashedDate, 1);		
		
		this.config = config;

		this.source = config.source;
		this.timing = config.timing;
		this.sampling = config.sampling;

		// Indices don't trade themselves, are calculated from basket of equities
		this.tradeType = TradeType.TRADED; 		
		if(inv instanceof InvestmentIndex) {
			this.tradeType = TradeType.CALCULATED;
		} 

	}

	/**
	 * Requests toDashedDate, from an earlier time determined by timeGap
	 * @param event
	 * @param toDashedDate
	 * @param timeGap
	 */
	public EventRequestHistory(EventActivity event, String timeGap, String endPoint) {
		
		setInvestment(event.getInvestment());
		this.tradeType = event.tradeType;

		this.timeGap = timeGap;
		this.endPoint = endPoint;
		
		this.config = HistorianService.getConfig(BarSize._30_secs, event);

		this.source = config.source;
		this.timing = config.timing;
		this.sampling = config.sampling;
							
	}

	/**
	 * Requests toDashedDate, from one day before
	 * @param event
	 * @param toDashedDate
	 */
	public EventRequestHistory(EventActivity event, String toDashedDate) {
		
		setInvestment(event.getInvestment());
		this.tradeType = event.tradeType;

		this.fromDashedDate = TimeParser.getDateMinusDashed(toDashedDate, 1);
		this.toDashedDate = toDashedDate;

		this.config = HistorianService.getConfig(BarSize._30_secs, event);

		this.source = config.source;
		this.timing = config.timing;
		this.sampling = config.sampling;
							
	}
	
	/**
	 * Requests for one specific point in time
	 * @param event
	 * @param requestTime
	 */
	public EventRequestHistory(EventActivity event, Long requestTime) {
		
		setInvestment(event.getInvestment());
		this.tradeType = event.tradeType;
		
		this.time = requestTime;
		
		this.config = HistorianService.getConfig(BarSize._30_secs, event);

		this.source = config.source;
		this.timing = config.timing;
		this.sampling = config.sampling;
	}
	

	public String toString() {
		String s = "";
		s = 	super.toString() + 
				"-config " + config.toString(); 
		return s;
	}
	
}
