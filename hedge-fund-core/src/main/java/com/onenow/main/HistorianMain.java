package com.onenow.main;

import java.util.logging.Level;
import java.util.ArrayList;
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
				
				updateL2HistoryFromL3(inv, toDashedDate);							
			}
									
			// go back further in time
			toDashedDate = TimeParser.getDateMinusDashed(toDashedDate, 1);
		}
	}

	
/**
 * Continually augment L2 (TSDB) with data from L3 (3rd party DB)
 * @param inv
 */
	// cat  com.onenow.main.InvestorMainHISTORY-Log.txt | grep -i "into stream" | grep -i "under es" | grep -i "call"
	private static void updateL2HistoryFromL3(Investment inv, String toDashedDate) {
				
		Watchr.log(Level.INFO, 	"LOOKING FOR " + MemoryLevel.L2TSDB + " incomplete information for " + inv.toString() + " TIL " + toDashedDate);

		EventRequestHistory request = new EventRequestHistory(inv, toDashedDate, new HistorianService().size5min);
			
		List<Candle> storedPrices = getL2TSDBStoredPrice(request);

		requestL3PartnerDataIfL2Incomplete(inv, toDashedDate, request, storedPrices);		

	}


private static void requestL3PartnerDataIfL2Incomplete(Investment inv, String toDashedDate, EventRequestHistory request, List<Candle> storedPrices) {
	// query L3 only if L2 data is incomplete
	int minPrices = 75;
	if ( storedPrices.size()<minPrices ) {	
		// NOTE: gets today's data by requesting 'by end of today'
		requestL3PartnerPrice(toDashedDate, request);
	} else {
		Watchr.log(Level.INFO, "HISTORIC HIT: " + MemoryLevel.L2TSDB + " for " + inv.toString()); // " found "  + storedPrices.size()
	}
}


private static void requestL3PartnerPrice(String toDashedDate, EventRequestHistory request) {
	
	try { // TODO: remove after exceptions identified
		TimeParser.paceHistoricalQuery(lastQueryTime);

		Watchr.log(Level.INFO, 	"Found incomplete information, thus will request: " + request.toString() + 
								" FROM " + MemoryLevel.L3PARTNER + " VIA InvestorMain SQS, THROUGH " + toDashedDate);
		
		// Send SQS request to broker
		String message = Piping.serialize((Object) request);
		sqs.sendMessage(message, SQS.getHistoryQueueURL());				

		lastQueryTime = TimeParser.getTimestampNow();
		
	} catch (Exception e) {
		e.printStackTrace();
	}
}


private static List<Candle> getL2TSDBStoredPrice(EventRequestHistory request) {
	// NOTE: readPriceFromDB gets today data by requesting 'by tomorrow'
	List<Candle> storedPrices = new ArrayList<Candle>();
	try {
		storedPrices = DBTimeSeriesPrice.read(request);
	} catch (Exception e) {
		e.printStackTrace();
		// some time series just don't exist or have data
	}
	return storedPrices;
}

}
