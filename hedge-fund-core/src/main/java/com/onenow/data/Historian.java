package com.onenow.data;

import java.util.HashMap;
import java.util.List;

import com.onenow.alpha.Broker;
import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.TradeType;
import com.onenow.execution.QuoteHistory;
import com.onenow.instrument.Investment;
import com.onenow.io.Lookup;
import com.onenow.io.EventHistory;
import com.onenow.io.TSDB;
import com.onenow.portfolio.Portfolio;
import com.onenow.research.Candle;

import java.util.logging.Level;

import com.onenow.util.TimeParser;
import com.onenow.util.WatchLog;

public class Historian {
	
	private Broker 								broker;
	private HashMap<String, QuoteHistory>		history;						// price history from L3

	private TSDB 								TSDB = new TSDB();				// database

	private HistorianConfig config;
	
	private long lastHistQuery;

/**
 * L0: investor application memory
 * L1: ElastiCache
 * L2: Time Series Database
 * L3: 3rd party database via API
 */
	public Historian() {
		
	}
	
	public Historian(Broker broker, HistorianConfig config) {
		
		this.broker = broker;
		this.history = new HashMap<String, QuoteHistory>();
		this.config = config;		
	}

	public void run(String toDashedDate) {
		
		Portfolio portfolio = broker.getMarketPortfolio();

			// iterate through investments
			for(Investment inv:portfolio.investments) {
				updateL2HistoryFromL3(inv, toDashedDate);
			}
	}
	
/**
 * Continually augment L2 (TSDB) with data from L3 (3rd party DB)
 * @param inv
 */
	private void updateL2HistoryFromL3(Investment inv, String toDashedDate) {
		System.out.println("Cache Chart READ: L3 (augment data) "  + inv.toString());
		
			
		// See if data already in L2
		// readPriceFromDB gets today data by requesting 'by tomorrow'
		List<Candle> prices = TSDB.readPriceFromDB(		inv, config.tradeType, config.sampling, 
														TimeParser.getDashedDateMinus(toDashedDate, 1), toDashedDate, 
														config.source, config.timing);

		// get the history reference for the specific investment 
		QuoteHistory invHist = lookupInvHistory(	inv, config.tradeType, 
													config.source, config.timing);

		// put any already-received history in L2
		// TODO: synchronize access rather than nullifying
		writeHistoryL0ToL2(	inv, config.tradeType, 
							config.source, config.timing,
							invHist);					

		// query L3 only if L2 data is incomplete
		// readHistoricalQuotes gets today's data by requesting 'by end of today'
		if (prices.size()<50) {					
			paceHistoricalQuery(); 
			
			Integer reqId = broker.readHistoricalQuotes(	inv, TimeParser.getClose(TimeParser.getUndashedDate(TimeParser.getDashedDateMinus(toDashedDate, 1))), 
															config, invHist);
			
			lastHistQuery = TimeParser.getTimestampNow();	
		} else {
			// System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& HISTORIC L2 HIT:" + inv.toString() + "\n\n");
		}
	}

	/**
	 * Every investment has it's own history from L3
	 * @param inv
	 * @param tradeType
	 * @param source
	 * @param timing
	 * @return
	 */
	private QuoteHistory lookupInvHistory(	Investment inv, TradeType tradeType,
											InvDataSource source, InvDataTiming timing) {
		
		String key = Lookup.getInvestmentKey(	inv, tradeType,
												source, timing);

		QuoteHistory invHist = history.get(key);
		if(invHist==null) {
			invHist = new QuoteHistory(inv, tradeType, source, timing);
			history.put(key, invHist);			
		}
		return invHist;
	}

	private void writeHistoryL0ToL2(	Investment inv,  
										TradeType dataType,
										InvDataSource source, InvDataTiming timing,
										QuoteHistory invHistory) {
		
		for(int i=0; i<invHistory.quoteRows.size(); i++) {
			EventHistory row = invHistory.quoteRows.get(i);
			
			Long time = row.time*1000; // processed RT 143,104,098,2011 vs. native History 143,096,400,0xxx
			Double price = row.open; 

			// System.out.println("Cache History WRITE: L2 (from L3 via L0) "  + inv.toString() + " " + invHistory.toString());
			boolean success = false;
			boolean retry = false;
			while (!success) {
				try {
					success = true;
					TSDB.writePrice(	time, inv, dataType, price,
										source, timing);
				} catch (Exception e) {
					success = false;
					retry = true;
					String log = "TSDB HISTORY WRITE FAILED: " + inv.toString();
					WatchLog.addToLog(Level.SEVERE, log);
					// e.printStackTrace();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {}
				}				
			}
			if(retry) {
				String log = "> TSDB HISTORY WRITE *RE-TRY* SUCCESS: " + inv.toString();
				WatchLog.addToLog(Level.INFO, log);
			}
		}
		// reset to avoid writing same twice
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

		long elapsed = TimeParser.getElapsedStamps(lastHistQuery);
		sleepTime = 12000-elapsed; // 12s target
		if(sleepTime<0) {
			sleepTime = 0;
		}
		
		return sleepTime;
	}


}
