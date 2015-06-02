package com.onenow.main;

import java.util.HashMap;

import com.onenow.constant.BrokerMode;
import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.Topology;
import com.onenow.constant.TradeType;
import com.onenow.data.HistorianConfig;
import com.onenow.data.InitMarket;
import com.onenow.data.InvestmentList;
import com.onenow.execution.BrokerInteractive;
import com.onenow.execution.BusWallSt;
import com.onenow.execution.HistorianService;
import com.onenow.execution.QuoteHistory;
import com.onenow.instrument.Investment;
import com.onenow.io.Lookup;
import com.onenow.portfolio.Portfolio;
import com.onenow.portfolio.PortfolioFactory;
import com.onenow.util.FlexibleLogger;
import com.onenow.util.TimeParser;

/** 
 * Makes investment choices in real-time
 * Also provides real-time and historical data as a service to the back-end
 * @param args
 */

public class InvestorMain {

	private static Portfolio marketPortfolio = new Portfolio();

	public static void main(String[] args) {
		
		FlexibleLogger.setup();

	    // choose relevant timeframe
	    String toDashedDate = TimeParser.getDashedDatePlus(TimeParser.getDashedToday(), 1);

	    BrokerInteractive broker = new BrokerInteractive(	BrokerMode.PRIMARY, 
	    													marketPortfolio, 
	    													new BusWallSt(Topology.LOCAL)); 
	   
	    // choose what to hedge on
		InitMarket initMarket = new InitMarket(	marketPortfolio, 
												InvestmentList.getUnderlying(InvestmentList.someStocks), 
												InvestmentList.getUnderlying(InvestmentList.someIndices),
												InvestmentList.getUnderlying(InvestmentList.futures), 
												InvestmentList.getUnderlying(InvestmentList.options),
    											toDashedDate);						
		
		// register once: get all market real-time quotes
		broker.getLiveQuotes(); 

		// Do historical queries from SQS
		broker.getHistoricalQuotes();
		
//		PortfolioFactory portfolioFactory = new PortfolioFactory(broker, marketPortfolio);
//		portfolioFactory.launch();							

	}
	
	

}
