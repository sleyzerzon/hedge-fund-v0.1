package com.onenow.main;

import java.util.List;
import java.util.logging.Level;

import com.onenow.data.EventHistoryRequest;
import com.onenow.data.HistorianConfig;
import com.onenow.data.InitMarket;
import com.onenow.data.InvestmentList;
import com.onenow.execution.HistorianService;
import com.onenow.instrument.Investment;
import com.onenow.io.TSDB;
import com.onenow.portfolio.Portfolio;
import com.onenow.research.Candle;
import com.onenow.util.FlexibleLogger;
import com.onenow.util.TimeParser;
import com.onenow.util.Watchr;

/** 
 * Gather complete accurate historical market data
 * L0: investor application memory
 * L1: ElastiCache
 * L2: Time Series Database
 * L3: 3rd party database via API
 * @param args
 */
public class HistorianMain {

	private static Portfolio marketPortfolio = new Portfolio();

	// relevant time frame
    private static String toDashedDate = TimeParser.getDashedDatePlus(TimeParser.getDashedToday(), 1);
    
	private static HistorianConfig config = new HistorianService().size30sec;
	
	
	public static void main(String[] args) {
		
		FlexibleLogger.setup();

	    // get ready to loop
		int count=0;
		while(true) {
			Watchr.log(Level.INFO, "^^ HISTORIAN MAIN: " + toDashedDate);
	    	// update the market portfolio, broker, and historian every month
	    	if(count%30 == 0) {
	    		marketPortfolio = InitMarket.getPortfolio(	InvestmentList.getUnderlying(InvestmentList.someStocks), 
															InvestmentList.getUnderlying(InvestmentList.someIndices),
															InvestmentList.getUnderlying(InvestmentList.futures), 
															InvestmentList.getUnderlying(InvestmentList.options),
			    											toDashedDate);
		    }	

			// updates historical L1 from L2
			run();
			
			// go back further in time
			toDashedDate = TimeParser.getDashedDateMinus(toDashedDate, 1);
			count++;
		}
	}
	
	public static void run() {
			// iterate through investments
			for(Investment inv:marketPortfolio.investments) {
				updateL2HistoryFromL3(inv, toDashedDate);
			}
	}
	
/**
 * Continually augment L2 (TSDB) with data from L3 (3rd party DB)
 * @param inv
 */
	private static void updateL2HistoryFromL3(Investment inv, String toDashedDate) {
		
		System.out.println("Cache Chart READ: L3 (augment data) "  + inv.toString());
		
			
		// See if data already in L2
		// NOTE: readPriceFromDB gets today data by requesting 'by tomorrow'
		List<Candle> prices = TSDB.readPriceFromDB(		inv, config.tradeType, config.sampling, 
														TimeParser.getDashedDateMinus(toDashedDate, 1), toDashedDate, 
														config.source, config.timing);

		// query L3 only if L2 data is incomplete
		// NOTE: readHistoricalQuotes gets today's data by requesting 'by end of today'
		if (prices.size()<50) {	

			EventHistoryRequest req = new EventHistoryRequest(inv, toDashedDate, config);
			
			// TODO: SQS instead of call to broker

			TimeParser.wait(5);
			
		} else {
			// System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& HISTORIC L2 HIT:" + inv.toString() + "\n\n");
		}
	}

}
