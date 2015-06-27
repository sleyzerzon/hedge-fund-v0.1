package com.onenow.main;

import java.util.logging.Level;
import java.util.List;

import com.onenow.constant.MemoryLevel;
import com.onenow.data.EventRequestHistory;
import com.onenow.data.HistorianConfig;
import com.onenow.data.InitMarket;
import com.onenow.execution.HistorianService;
import com.onenow.instrument.Investment;
import com.onenow.io.DBTimeSeriesPrice;
import com.onenow.io.SQS;
import com.onenow.portfolio.Portfolio;
import com.onenow.research.Candle;
import com.onenow.util.InitLogger;
import com.onenow.util.Piping;
import com.onenow.util.TimeParser;
import com.onenow.util.Watchr;

public class HistorianMain {

	private static Portfolio marketPortfolio = new Portfolio();
    private static String toDashedDate = TimeParser.getDatePlusDashed(TimeParser.getTodayDashed(), 1);
	private static HistorianConfig config = new HistorianService().size30sec;
	
	private static SQS sqs = new SQS();
	
	private static long lastQueryTime;

	public static void main(String[] args) {
		
		InitLogger.run("");

		sqs.listQueues();
		
		while(true) {
			
			Watchr.log(Level.WARNING, "HISTORIAN through: " + toDashedDate);

			marketPortfolio = InitMarket.getHistoryPortfolio(toDashedDate);	

			// updates historical L1 from L2
			for(Investment inv:marketPortfolio.investments) {
				
				TimeParser.paceHistoricalQuery(lastQueryTime);
				
				updateL2HistoryFromL3(inv, toDashedDate);
				
				lastQueryTime = TimeParser.getTimestampNow();
				
//				// request update
//				if(updateL2HistoryFromL3(inv, toDashedDate)) {		
//					// only if request sent reset the query time
//					lastQueryTime = TimeParser.getTimestampNow();	
//				}

			}
									
			// go back further in time
			toDashedDate = TimeParser.getDateMinusDashed(toDashedDate, 1);
		}
	}

	
/**
 * Continually augment L2 (TSDB) with data from L3 (3rd party DB)
 * @param inv
 */
	private static boolean updateL2HistoryFromL3(Investment inv, String toDashedDate) {
		
		boolean requestMade = false;
		
		// minimum number of price data points
		int minPrices = 50;
		
		Watchr.log(Level.INFO, "READ: " + MemoryLevel.L3PARTNER.toString() + " (augment data) "  + inv.toString());
		
		EventRequestHistory request = new EventRequestHistory(inv, toDashedDate, config);
			
//		// See if data already in L2
//		// NOTE: readPriceFromDB gets today data by requesting 'by tomorrow'
//		List<Candle> storedPrices = DBTimeSeriesPrice.read(request);
//		
//		// query L3 only if L2 data is incomplete
//		// NOTE: readHistoricalQuotes gets today's data by requesting 'by end of today'
//		if ( storedPrices.size()<minPrices ) {	

			Watchr.log(Level.INFO, 	MemoryLevel.L2TSDB + " information incomplete, thus will request: " + request.toString() + 
									" FROM " + MemoryLevel.L3PARTNER + " VIA InvestorMain SQS");
			
			// Send SQS request to broker
			String message = Piping.serialize((Object) request);
			sqs.sendMessage(message, SQS.getHistoryQueueURL());				

//			requestMade = true;
//			
//		} else {
//			Watchr.log(Level.INFO, "HISTORIC HIT: " + MemoryLevel.L2TSDB + " found "  + storedPrices.size() + " prices for " + inv.toString());
//		}
		
		return requestMade;
	}

}
