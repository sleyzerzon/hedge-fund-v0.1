package com.onenow.main;

import java.util.List;
import java.util.logging.Level;

import com.onenow.admin.InitAmazon;
import com.onenow.constant.MemoryLevel;
import com.onenow.constant.QueueName;
import com.onenow.data.EventRequestHistory;
import com.onenow.data.HistorianConfig;
import com.onenow.data.InitMarket;
import com.onenow.data.InvestmentList;
import com.onenow.execution.HistorianService;
import com.onenow.instrument.Investment;
import com.onenow.io.SQS;
import com.onenow.io.databaseTimeSeries;
import com.onenow.portfolio.Portfolio;
import com.onenow.research.Candle;
import com.onenow.util.FlexibleLogger;
import com.onenow.util.SysProperties;
import com.onenow.util.TimeParser;
import com.onenow.util.Watchr;

public class HistorianMain {

	private static Portfolio marketPortfolio = new Portfolio();

	// relevant time frame
    private static String toDashedDate = TimeParser.getDatePlusDashed(TimeParser.getTodayDashed(), 1);
    
	private static HistorianConfig config = new HistorianService().size30sec;
	
	private static SQS sqs = new SQS();
	private static String queueURL;
	
	public static void main(String[] args) {
		
		SysProperties.setLogProperties();
		FlexibleLogger.setup();

		queueURL = sqs.createQueue(QueueName.HISTORY_STAGING);
		sqs.listQueues();
        // sqs.deleteQueue(queueURL);

		
		int count=0;
		while(true) {
			
			Watchr.log(Level.INFO, "HISTORIAN through: " + toDashedDate);

			getTimelyMarketPortfolio(count);	

			// updates historical L1 from L2
			for(Investment inv:marketPortfolio.investments) {
				updateL2HistoryFromL3(inv, toDashedDate);
			}
			
			TimeParser.wait(10); // pace
						
			// go back further in time
			toDashedDate = TimeParser.getDateMinusDashed(toDashedDate, 1);
			count++;
		}
	}

	private static void getTimelyMarketPortfolio(int count) {
		// update the market portfolio, broker, and historian every month
		if(count%30 == 0) {
			marketPortfolio = InitMarket.getPortfolio(	InvestmentList.getUnderlying(InvestmentList.someStocks), 
														InvestmentList.getUnderlying(InvestmentList.someIndices),
														InvestmentList.getUnderlying(InvestmentList.futures), 
														InvestmentList.getUnderlying(InvestmentList.options),
		    											toDashedDate);
		}
	}
	
/**
 * Continually augment L2 (TSDB) with data from L3 (3rd party DB)
 * @param inv
 */
	private static void updateL2HistoryFromL3(Investment inv, String toDashedDate) {
		
		// minimum number of price data points
		int minPrices = 50;
		
		Watchr.log(Level.INFO, "Cache Chart READ: " + MemoryLevel.L3PARTNER + " (augment data) "  + inv.toString());
		
		EventRequestHistory request = new EventRequestHistory(inv, toDashedDate, config);
			
		// See if data already in L2
		// NOTE: readPriceFromDB gets today data by requesting 'by tomorrow'
		List<Candle> storedPrices = databaseTimeSeries.readPriceFromDB(request);

		
		// query L3 only if L2 data is incomplete
		// NOTE: readHistoricalQuotes gets today's data by requesting 'by end of today'
		if ( storedPrices.size()<minPrices ) {	

			Watchr.log(Level.WARNING, "Request this! " + request.toString());
			sqs.sendMessage(request.toString(), queueURL);

			// TODO: send SQS request to broker

		} else {
			Watchr.log(Level.INFO, "HISTORIC HIT: " + MemoryLevel.L2TSDB + " found "  + storedPrices.size() + " prices for " + inv.toString());
		}
	}

}
