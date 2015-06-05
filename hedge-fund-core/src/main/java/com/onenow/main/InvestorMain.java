package com.onenow.main;

import java.util.logging.Level;

import com.onenow.constant.BrokerMode;
import com.onenow.constant.Topology;
import com.onenow.data.InitMarket;
import com.onenow.data.InvestmentList;
import com.onenow.execution.BrokerInteractive;
import com.onenow.execution.BusWallSt;
import com.onenow.portfolio.Portfolio;
import com.onenow.util.FlexibleLogger;
import com.onenow.util.TimeParser;
import com.onenow.util.Watchr;

/** 
 * Makes investment choices in real-time
 * Also provides real-time and historical data as a service to the back-end
 * It operates in several different BrokerMode
 * - In PRIMARY / STANDBY modes, the application works as a database, only the master effecting investment decisions
 * - In HISTORIAN / HISTORIANRT modes, the application only provides data-gathering services to the back-end 
 * @param args
 */

public class InvestorMain {

	private static Portfolio marketPortfolio = new Portfolio();

	public static void main(String[] args) {
		
		BrokerMode mode = getModeArgument(args);
		String log = "Starting in MODE: " + mode;
		Watchr.log(Level.INFO, log);

		FlexibleLogger.setup(mode.toString());

	    BrokerInteractive broker = new BrokerInteractive(	mode, 
	    													marketPortfolio, 
	    													new BusWallSt(mode, Topology.LOCAL)); 

		
		if(	mode.equals(BrokerMode.PRIMARY) || mode.equals(BrokerMode.STANDBY)) {
			// register once: get all market real-time quotes
		    setPortfolioForInvestment();						
			broker.getLiveQuotes(); 			
		}
		
		if(mode.equals(BrokerMode.STANDBY)) {
			// register once: get all market real-time quotes
//		    setPortfolioForInvestment();						
//			broker.getLiveQuotes(); 			
		}

		if(	mode.equals(BrokerMode.REALTIME)) {
			// Do realtime queries from SQS
			 // broker.getLiveQuotes(); 			
		}

		if(mode.equals(BrokerMode.HISTORIAN)) {
			// Do historical queries from SQS
			// broker.procesHistoricalQuotesRequests();
		}

		if(	mode.equals(BrokerMode.STREAMING)) {
			// TODO: Do straming queries from SQS
		}

//		PortfolioFactory portfolioFactory = new PortfolioFactory(broker, marketPortfolio);
//		portfolioFactory.launch();							

	}

	private static void setPortfolioForInvestment() {
		// choose relevant time frame
	    String toDashedDate = TimeParser.getDashedDatePlus(TimeParser.getDashedToday(), 1);

	    // choose what to hedge on
		InitMarket initMarket = new InitMarket(	marketPortfolio, 
												InvestmentList.getUnderlying(InvestmentList.someStocks), 
												InvestmentList.getUnderlying(InvestmentList.someIndices),
												InvestmentList.getUnderlying(InvestmentList.futures), 
												InvestmentList.getUnderlying(InvestmentList.options),
    											toDashedDate);
	}
	
	private static BrokerMode getModeArgument(String[] args) {
		BrokerMode mode = BrokerMode.REALTIME;
		if(args.length>0) {
			if(args[0]!=null) {
				String s0 = args[0];
				if(s0.equals("PRIMARY")) {
					mode = BrokerMode.PRIMARY;
				}
				if(s0.equals("STANDBY")) {
					mode = BrokerMode.STANDBY;
				}
				if(s0.equals("HISTORIAN")) {
					mode = BrokerMode.HISTORIAN;
				}
				if(s0.equals("STREAMING")) {
					mode = BrokerMode.STREAMING;
				}
			} else {
				String log = "ERROR: mode is a required argument";
		    	Watchr.log(Level.SEVERE, log);
			}
		}
		return mode;
	}
	
}
