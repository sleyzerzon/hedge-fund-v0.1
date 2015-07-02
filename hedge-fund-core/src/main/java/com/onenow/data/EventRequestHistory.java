package com.onenow.data;

import com.ib.client.Types.BarSize;
import com.onenow.constant.ColumnName;
import com.onenow.constant.DBQuery;
import com.onenow.constant.TradeType;
import com.onenow.execution.HistorianService;
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

		// Indices don't trade themselves, are calculated from basket of equities
		super.tradeType = TradeType.TRADED; 		
		if(inv instanceof InvestmentIndex) {
			super.tradeType = TradeType.CALCULATED;
		} 

	}

	/**
	 * Requests toDashedDate, from an earlier time determined by timeGap
	 * @param event
	 * @param toDashedDate
	 * @param timeGap
	 */
	public EventRequestHistory(EventActivity event, String timeGap, String endPoint) {

		this.config = HistorianService.getConfig(BarSize._30_secs, event);

		setInvestment(event.getInvestment());
		this.tradeType = event.tradeType;
		
		super.timeGap = timeGap;
		super.endPoint = endPoint;
		
		super.source = config.source;
		super.timing = config.timing;
		super.sampling = config.sampling;				
	}

	/**
	 * Requests toDashedDate, from one day before
	 * @param event
	 * @param toDashedDate
	 */
	public EventRequestHistory(EventActivity event, String toDashedDate) {

		this.config = HistorianService.getConfig(BarSize._30_secs, event);

		setInvestment(event.getInvestment());

		super.fromDashedDate = TimeParser.getDateMinusDashed(toDashedDate, 1);
		super.toDashedDate = toDashedDate;

		super.source = config.source;
		super.timing = config.timing;
		super.sampling = config.sampling;
		super.tradeType = event.tradeType;							
	}
	
	/**
	 * Requests for one specific point in time
	 * @param event
	 * @param requestTime
	 */
	public EventRequestHistory(EventActivity event, Long requestTime) {

		this.config = HistorianService.getConfig(BarSize._30_secs, event);

		setInvestment(event.getInvestment());
		
		super.time = requestTime;		

		super.source = config.source;
		super.timing = config.timing;
		super.sampling = config.sampling;
		super.tradeType = event.tradeType;

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
