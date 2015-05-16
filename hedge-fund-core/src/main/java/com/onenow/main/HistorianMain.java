package com.onenow.main;

import java.util.ArrayList;
import java.util.List;

import com.onenow.data.Historian;
import com.onenow.data.InitMarket;
import com.onenow.execution.BrokerInteractive;
import com.onenow.execution.Contract;
import com.onenow.execution.ContractFactory;
import com.onenow.instrument.InvestmentIndex;
import com.onenow.instrument.Underlying;
import com.onenow.portfolio.Portfolio;
import com.onenow.util.ParseDate;

/** 
 * Gather complete accurate historical market data
 * @param args
 */

public class HistorianMain {

	private static BrokerInteractive IB;

	public static void main(String[] args) {
		
		// choose investments
		Portfolio marketPortfolio = new Portfolio();
	    Underlying index = new Underlying("SPX");
	    Underlying stocks = new Underlying("SPX");
	    Underlying options = new Underlying("SPX");
	    Underlying futures = new Underlying("ES");

	    InitMarket initMarket = new InitMarket(	marketPortfolio, 
	    										index, stocks,
	    										options, futures);

		try {
			
			// updates real-time L1 from real=time events
			IB = new BrokerInteractive(marketPortfolio); 
			
			// updates historical L1 from L2
			Historian hist = new Historian(IB);
			
		} catch (Exception e) {
			System.out.println("COULD NOT CREATE INTERACTIVE BROKER / HISTORIAN" + "\n");
			e.printStackTrace();
		}
		
	}
	
}
