package com.onenow.main;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.ib.client.Types.BarSize;
import com.ib.client.Types.WhatToShow;
import com.onenow.constant.ColumnName;
import com.onenow.constant.DBQuery;
import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.MemoryLevel;
import com.onenow.constant.PriceType;
import com.onenow.constant.SamplingRate;
import com.onenow.data.EventRequest;
import com.onenow.data.EventRequestHistory;
import com.onenow.data.EventRequestRaw;
import com.onenow.data.InitMarket;
import com.onenow.execution.HistorianService;
import com.onenow.instrument.Investment;
import com.onenow.instrument.InvestmentFuture;
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
	
	private static SQS sqs = new SQS();
	
	private static long lastQueryTime;

	public static void main(String[] args) {
		
		InitLogger.run("");
		
		String toDashedDate = getToDate(args);
		
		while(true) {

			updatePortfolioL2HistoryFromL3(toDashedDate);
									
			// go back further in time
			toDashedDate = TimeParser.getDateMinusDashed(toDashedDate, 1);

		}
	}

	private static String getToDate(String[] args) {
		String toDashedDate = getThroughToday();
		try {
			if(args[0]!=null) {
				toDashedDate = args[0]; // for testability
			}
		} catch (Exception e) {
			// it's ok if args is empty
		}
		return toDashedDate;
	}
	
	// TODO: it's weird to ask for today + 1 to get a date historian thinks is today
	private static String getThroughToday() {
		return TimeParser.getDatePlusDashed(TimeParser.getTodayDashed(), 1);
	}

	/**
	 * Updates history for previous day, and for today
	 * Runs week days; except for Futures it runs every day
	 * @param toDashedDate
	 */
	private static void updatePortfolioL2HistoryFromL3(String toDashedDate) {
		Watchr.log(Level.WARNING, "HISTORIAN through: " + toDashedDate);
		marketPortfolio = InitMarket.getHistoryPortfolio(toDashedDate);	

		// updates historical L1 from L2
		for(Investment inv:marketPortfolio.investments) {
			
			updateBackDataL2HistoryFromL3(toDashedDate, inv);			
			updateTodayDataL2HistoryFromL3(inv);
		}
	}

	private static void updateTodayDataL2HistoryFromL3(Investment inv) {
		
		String todayDateOf = TimeParser.getDateMinusDashed(getThroughToday(), 1);
		
		if(TimeParser.isWeekDay(todayDateOf) || (inv instanceof InvestmentFuture)) {
			updateDataL2HistoryFromL3(getThroughToday(), inv);
		} else {
			Watchr.log("Skipping today's data " + todayDateOf + " FOR " + inv.toString());
		}
	}

	private static void updateBackDataL2HistoryFromL3(String toDashedDate, Investment inv) {
		
		String backDayOf = TimeParser.getDateMinusDashed(toDashedDate, 1);	
		
		if(TimeParser.isWeekDay(backDayOf) || (inv instanceof InvestmentFuture)) {
			updateDataL2HistoryFromL3(toDashedDate, inv);
		} else {
			Watchr.log("Skipping back data " + backDayOf + " FOR " + inv.toString());
		}
	}

	private static void updateDataL2HistoryFromL3(String toDashedDate, Investment inv) {

		updatePricesL2HistoryFromL3(toDashedDate, inv);
		updateGreeksL2HistoryFromL3(toDashedDate, inv);
		
	}

	private static void updatePricesL2HistoryFromL3(String toDashedDate, Investment inv) {
		// To be saved to prices database
		updateL2HistoryFromL3(inv, toDashedDate, WhatToShow.TRADES);							
		updateL2HistoryFromL3(inv, toDashedDate, WhatToShow.ASK);							
		updateL2HistoryFromL3(inv, toDashedDate, WhatToShow.BID);
	}

	private static void updateGreeksL2HistoryFromL3(String toDashedDate, Investment inv) {
		// To be saved to Greeks database
//		updateL2HistoryFromL3(inv, toDashedDate, WhatToShow.MIDPOINT);							
//		updateL2HistoryFromL3(inv, toDashedDate, WhatToShow.HISTORICAL_VOLATILITY);							
//		updateL2HistoryFromL3(inv, toDashedDate, WhatToShow.OPTION_IMPLIED_VOLATILITY);													
	}
	
	
/**
 * Continually augment L2 (TSDB) with data from L3 (3rd party DB)
 * @param inv
 */
	// cat  com.onenow.main.InvestorMainHISTORY-Log.txt | grep -i "into stream" | grep -i "under es" | grep -i "call"
	private static void updateL2HistoryFromL3(Investment inv, String toDashedDate, WhatToShow whatToShow) {
				
		Watchr.log(Level.INFO, 	"CHECKING FOR " + MemoryLevel.L2TSDB + " complete information for " + inv.toString() + " TIL " + toDashedDate);

		// TODO: get full range of WhatToShow
		EventRequestHistory request = new EventRequestHistory(	inv, toDashedDate,  
																HistorianService.getConfig(InvDataSource.IB, BarSize._5_mins, whatToShow));
			
		
		List<Candle> storedPriceSample = getL2TSDBStoredPrice(request);
				
		requestL3PartnerDataIfL2Incomplete(request, storedPriceSample, toDashedDate);
			
	}
	
	// select MEAN(PRICE) FROM "AAPL-STOCK-TRADED-IB-REALTIME" group by time(30m) where time > '2015-06-23' and time < '2015-06-24'
	private static List<Candle> getL2TSDBStoredPrice(EventRequest request) {
		// NOTE: readPriceFromDB gets today data by requesting 'by tomorrow'
		List<Candle> storedPrices = new ArrayList<Candle>();
		try {
			// TODO: convert request type
			String fromDashedDate = TimeParser.getDateMinusDashed(request.toDashedDate, 1);
			EventRequestRaw rawReq = new EventRequestRaw(	DBQuery.MEAN, ColumnName.PRICE,
															request.getInvestment(), PriceType.ASK, SamplingRate.HIFREQ,
															fromDashedDate, request.toDashedDate,
															InvDataSource.IB, InvDataTiming.HISTORICAL);
			storedPrices = DBTimeSeriesPrice.read(rawReq);
		} catch (Exception e) {
			e.printStackTrace();
			// some time series just don't exist or have data
		}
		return storedPrices;
	}


private static void requestL3PartnerDataIfL2Incomplete(EventRequest request, List<Candle> storedPrices, String toDashedDate) {

	// query L3 only if L2 data is incomplete
	int minPrices = 1;
	if ( storedPrices.size()<minPrices ) {
					
		Watchr.log(Level.INFO, "HISTORIC MISS" + "(" + storedPrices.size() + ")" + " " + MemoryLevel.L2TSDB + " for " + request.toString()); // 

		// NOTE: gets today's data by requesting 'by end of today'
		requestL3PartnerPrice(request, toDashedDate);
		
	} else {
		Watchr.log(Level.INFO, "HISTORIC HIT" + "(" + storedPrices.size() + ")" + " " + MemoryLevel.L2TSDB + " for " + request.toString()); // 
	}
}


private static void requestL3PartnerPrice(EventRequest request, String toDashedDate) {
	
	TimeParser.paceHistoricalQuery(lastQueryTime);

	Watchr.log(Level.INFO, 	"Found incomplete information, thus will request: " + request.toString() + 
							" FROM " + MemoryLevel.L3PARTNER + " VIA InvestorMain SQS, THROUGH " + toDashedDate);
	
	// Send SQS request to broker
	String message = Piping.serialize((Object) request);
	sqs.sendMessage(message, SQS.getHistoryQueueURL());				

	lastQueryTime = TimeParser.getTimeMilisecondsNow();
		
}



}
