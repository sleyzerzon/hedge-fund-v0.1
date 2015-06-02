package com.onenow.main;

import com.onenow.constant.BrokerMode;
import com.onenow.data.InitMarket;
import com.onenow.data.InvestmentList;
import com.onenow.execution.BrokerInteractive;
import com.onenow.execution.BusWallSt;
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
	    													new BusWallSt()); 
			    
		InitMarket initMarket = new InitMarket(	marketPortfolio, 
												InvestmentList.getUnderlying(InvestmentList.someStocks), 
												InvestmentList.getUnderlying(InvestmentList.someIndices),
												InvestmentList.getUnderlying(InvestmentList.futures), 
												InvestmentList.getUnderlying(InvestmentList.options),
    											toDashedDate);						
		// register once: get all real-time quotes
		broker.getLiveQuotes(); 
		
//		PortfolioFactory portfolioFactory = new PortfolioFactory(broker, marketPortfolio);
//		portfolioFactory.launch();							

	}
}
