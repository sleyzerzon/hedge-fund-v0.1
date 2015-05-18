package com.onenow.data;

import java.util.HashMap;
import java.util.List;

import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.SamplingRate;
import com.onenow.constant.TradeType;
import com.onenow.database.Lookup;
import com.onenow.database.TSDB;
import com.onenow.execution.Broker;
import com.onenow.execution.QuoteHistory;
import com.onenow.execution.QuoteRow;
import com.onenow.instrument.Investment;
import com.onenow.portfolio.Portfolio;
import com.onenow.research.Candle;
import com.onenow.util.ParseDate;

public class Historian {
	
	private Broker 								broker;
	private HashMap<String, QuoteHistory>		history;							// price history from L2

	private TSDB 								TSDB = new TSDB();			// database
	private Lookup 								lookup = new Lookup();			// key
	private ParseDate							parseDate = new ParseDate();

	private HistorianConfig config;
	private String toDashedDate;
	
	private long lastHistQuery;

	public Historian() {
		
	}
	
	public Historian(Broker broker, HistorianConfig config, String toDashedDate) {
		
		this.broker = broker;
		this.history = new HashMap<String, QuoteHistory>();
		this.config = config;
		this.toDashedDate = toDashedDate;
		
		updateHistory();
	}

	public void updateHistory() {
		
		Portfolio portfolio = broker.getMarketPortfolio();

		String startDate = toDashedDate;		
		while(true) {	
			
			// when date changes, start over from that today
			String today = parseDate.getDashedToday();
			if(!today.equals(startDate)) {
				toDashedDate = today;
			}
			
			// iterate through investments
			for(Investment inv:portfolio.investments) {
				updateL1HistoryFromL2(inv, toDashedDate);
			}
			
			// go back further in time
			toDashedDate = parseDate.getDashedDateMinus(toDashedDate, 1);
		}
	}
	
/**
 * Continually augment L1 with data from L2 (3rd party DB)
 * @param inv
 */
	private void updateL1HistoryFromL2(Investment inv, String toDashedDate) {
		System.out.println("Cache Chart READ: L2 (augment data) "  + inv.toString());
		
			
		// See if data already in L1
		List<Candle> prices = TSDB.readPriceFromDB(		inv, config.tradeType, config.sampling, 
														parseDate.getDashedDateMinus(toDashedDate, 1), toDashedDate,
														config.source, config.timing);

		// query L2 only if L1 data is incomplete
		if (prices.size()<10) {		
			// get the history reference for the specific investment 
			QuoteHistory invHist = lookupInvHistory(	inv, config.tradeType, 
														config.source, config.timing);
			
			paceHistoricalQuery(); 
			broker.readHistoricalQuotes(inv, parseDate.getClose(parseDate.getUndashedDate(toDashedDate)), invHist); 
			lastHistQuery = parseDate.getNow();
			
			// put history in L1				
			writeHistoryL0ToL1(	inv, config.tradeType, 
								config.source, config.timing,
								invHist);					
		} else {
			System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& SUCCESS");
		}
						
	}

	/**
	 * Every investment has it's own history from L2
	 * @param inv
	 * @param tradeType
	 * @param source
	 * @param timing
	 * @return
	 */
	private QuoteHistory lookupInvHistory(	Investment inv, TradeType tradeType,
											InvDataSource source, InvDataTiming timing) {
		
		String key = lookup.getInvestmentKey(	inv, tradeType,
												source, timing);

		QuoteHistory invHist = history.get(key);
		if(invHist==null) {
			invHist = new QuoteHistory();
			history.put(key, invHist);			
		}
		return invHist;
	}

	private void writeHistoryL0ToL1(	Investment inv,  
										TradeType dataType,
										InvDataSource source, InvDataTiming timing,
										QuoteHistory invHistory) {
		
		for(int i=0; i<invHistory.quoteRows.size(); i++) {
			QuoteRow row = invHistory.quoteRows.get(i);
			
			Long time = row.time()*1000; // processed RT 143,104,098,2011 vs. native History 143,096,400,0xxx
			Double price = row.open(); 

			// System.out.println("Cache History WRITE: L1 (from L2 via L0) "  + inv.toString() + " " + invHistory.toString());
			TSDB.writePrice(	time, inv, dataType, price,
								source, timing);				

		}
		// reset to avoid writing same twice
		// TODO: synchronize access rather than nullifying?
		invHistory = null; 
	}

	private void paceHistoricalQuery() {
		System.out.println("...pacing historical query: " + getSleepTime()/1000 + "s");
	    try {
			Thread.sleep(getSleepTime());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}	
	
	private long getSleepTime() {
		long sleepTime = 0;

		long elapsed = parseDate.getElapsedStamps(lastHistQuery);
		sleepTime = 12000-elapsed; // 12s target
		if(sleepTime<0) {
			sleepTime = 0;
		}
		
		return sleepTime;
	}

	
	// TEST
	
	// PRINT
}
