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
import com.onenow.research.Candle;
import com.onenow.util.ParseDate;

public class Historian {
	
	private Broker 								broker;
	private HashMap<String, QuoteHistory>		history;							// price history from L2

	private TSDB 								TSDB = new TSDB();			// database
	private Lookup 								lookup = new Lookup();			// key
	private ParseDate							parseDate = new ParseDate();

	
	public Historian(Broker broker) {
		
		this.broker = broker;
		this.history = new HashMap<String, QuoteHistory>();
	}

	public void updateHistory() {
		Investment inv = new Investment();
		
		// TODO: which investments
		updateL1HistoryFromL2(inv);
	}
	
/**
 * Continually augment L1 with data from L2 (3rd party DB)
 * @param inv
 */
	private void updateL1HistoryFromL2(Investment inv) {
		System.out.println("Cache Chart READ: L2 (augment data) "  + inv.toString());

		// go from...
		String dashedDate = parseDate.getDashedToday();
		String undashedDateClosing = parseDate.getClose(parseDate.getUndashedDate(dashedDate));
		int numDays = 2; // number of days to acquire at a time
		
		while(true) { 
			
			TradeType tradeType = TradeType.TRADED; 
			SamplingRate scalping = SamplingRate.SWING;
			InvDataSource source = InvDataSource.IB;
			InvDataTiming timing = InvDataTiming.HISTORICAL;

			// See if data already in L1
			List<Candle> prices = TSDB.readPriceFromDB(		inv, tradeType, scalping, 
															parseDate.getDashedDateMinus(dashedDate, 1), dashedDate,
															source, timing);

			if (prices.size()<10) {		
				// get the history reference for the specific investment 
				QuoteHistory invHist = lookupInvHistory(	inv, tradeType, 
															source, timing);
				
				broker.readHistoricalQuotes(inv, undashedDateClosing, invHist); 

				// put history in L1				
				writeHistoryL0ToL1(	inv, tradeType, 
									source, timing,
									invHist);					
//				numDays--;
			} else {
				System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& SUCCESS");
			}
			
			numDays--;
			
			// go back further in time?
			if(numDays>0) {	
				dashedDate = parseDate.getDashedDateMinus(dashedDate, 1);
				undashedDateClosing = parseDate.getClose(parseDate.getUndashedDate(dashedDate));;
			} else {
				return;
			}
		}
	}

	/**
	 * Every investment has it's own L2 history
	 * @param inv
	 * @param tradeType
	 * @param source
	 * @param timing
	 * @return
	 */
	private QuoteHistory lookupInvHistory(Investment inv, TradeType tradeType,
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

	private void writeHistoryL0ToL1(Investment inv,  
											TradeType dataType,
											InvDataSource source, InvDataTiming timing,
											QuoteHistory invHistory) {
		
		for(int i=0; i<invHistory.quoteRows.size(); i++) {
			QuoteRow row = invHistory.quoteRows.get(i);
			
			Long time = row.getM_time()*1000; // RT 143,104,098,2011 // Hist 143,096,400,0???
			Double price = row.getM_open(); 

//			System.out.println("Cache History WRITE: L1 (from L2 via L0) "  + inv.toString() + " " + invHistory.toString());
			TSDB.writePrice(	time, inv, dataType, price,
								source, timing);				

		}
		invHistory.init(); // reset to avoid writing same twice
	}

	private void paceHistoricalQuery() {
		System.out.println("...pacing historical query");
	    try {
			Thread.sleep(12000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}				

	
	// TEST
	
	// PRINT
}
