package com.onenow.main;

import java.util.logging.Level;

import com.onenow.constant.BrokerMode;
import com.onenow.constant.Topology;
import com.onenow.data.Historian;
import com.onenow.data.InitMarket;
import com.onenow.data.InvestmentList;
import com.onenow.execution.BrokerInteractive;
import com.onenow.execution.BusWallSt;
import com.onenow.execution.HistorianService;
import com.onenow.portfolio.Portfolio;
import com.onenow.util.FlexibleLogger;
import com.onenow.util.TimeParser;
import com.onenow.util.WatchLog;

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

	public static void main(String[] args) {
		
		FlexibleLogger.setup(Level.INFO);

	    // choose relevant timeframe
	    String toDashedDate = TimeParser.getDashedDatePlus(TimeParser.getDashedToday(), 1);

	    BrokerInteractive broker = new BrokerInteractive(	BrokerMode.HISTORIAN, 
	    													marketPortfolio, 
	    													new BusWallSt(Topology.LOCAL)); // Topology.LOCAL
	    Historian historian = new Historian(broker, new HistorianService().size30sec);		
			    
	    // get ready to loop
		int count=0;
		while(true) {
			String log = "^^ HISTORIAN MAIN: " + toDashedDate;
			WatchLog.addToLog(Level.INFO, log);
	    	// update the market portfolio, broker, and historian every month
	    	if(count%30 == 0) {
					InitMarket initMarket = new InitMarket(	marketPortfolio, 
															InvestmentList.getUnderlying(InvestmentList.someStocks), 
															InvestmentList.getUnderlying(InvestmentList.someIndices),
															InvestmentList.getUnderlying(InvestmentList.futures), 
															InvestmentList.getUnderlying(InvestmentList.options),
			    											toDashedDate);
		    }	// TODO : 10000068 322 Error processing request:-'wd' : cause - Only 50 simultaneous API historical data requests allowed.


			// updates historical L1 from L2
			historian.run(toDashedDate);
			// go back further in time
			toDashedDate = TimeParser.getDashedDateMinus(toDashedDate, 1);
			count++;
		}
	}	
}
