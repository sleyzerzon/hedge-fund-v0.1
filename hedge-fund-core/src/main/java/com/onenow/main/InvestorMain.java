package com.onenow.main;

import com.onenow.constant.BrokerMode;
import com.onenow.constant.Topology;
import com.onenow.data.InitMarket;
import com.onenow.data.InvestmentList;
import com.onenow.execution.BrokerInteractive;
import com.onenow.execution.BusWallSt;
import com.onenow.portfolio.Portfolio;
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
		
		BrokerMode mode = getModeArgument(args);
		FlexibleLogger.setup(mode.toString());

	    // choose relevant time frame
	    String toDashedDate = TimeParser.getDashedDatePlus(TimeParser.getDashedToday(), 1);

	    BrokerInteractive broker = new BrokerInteractive(	mode, 
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

		if(mode.equals(BrokerMode.HISTORIAN)) {
			// Do historical queries from SQS
			broker.procesHistoricalQuotesRequests();
		}
		
//		PortfolioFactory portfolioFactory = new PortfolioFactory(broker, marketPortfolio);
//		portfolioFactory.launch();							

	}
	
	private static BrokerMode getModeArgument(String[] args) {
		BrokerMode mode = BrokerMode.STANDBY;
		if(args.length>0) {
			if(args[0]!=null) {
				String s0 = args[0];
				if(s0.equals("PRIMARY")) {
					mode = BrokerMode.PRIMARY;
				}
				if(s0.equals("HISTORIAN")) {
					mode = BrokerMode.HISTORIAN;
				}
			} else {
				System.out.println("ERROR: mode is a required argument");
			}
		}
		return mode;
	}
	
}
