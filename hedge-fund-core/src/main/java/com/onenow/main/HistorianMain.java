package com.onenow.main;

import java.util.List;

import com.onenow.constant.BrokerMode;
import com.onenow.data.Historian;
import com.onenow.data.InitMarket;
import com.onenow.data.InvestmentList;
import com.onenow.execution.BrokerInteractive;
import com.onenow.instrument.Underlying;
import com.onenow.portfolio.Portfolio;
import com.onenow.util.ParseDate;

/** 
 * Gather complete accurate historical market data
 * @param args
 */

public class HistorianMain {

	private static BrokerInteractive IB;

	private static InvestmentList invList = new InvestmentList();
	private static ParseDate parseDate = new ParseDate();

	public static void main(String[] args) {
		
		// choose investments
		Portfolio marketPortfolio = new Portfolio();
	    List<Underlying> stocks = invList.getUnderlying(invList.someStocks);
	    List<Underlying> indices = invList.getUnderlying(invList.someIndices);
	    List<Underlying> futures = invList.getUnderlying(invList.futures);
	    List<Underlying> options = invList.getUnderlying(invList.options);
	    String fromDate = parseDate.getDashedToday();
	    String toDate = parseDate.getDashedToday();

	    // fill the market portfolio
	    InitMarket initMarket = new InitMarket(	marketPortfolio, 
	    										stocks, indices,
	    										futures, options,
	    										fromDate, toDate);

		try {			
			// updates real-time L1 from real=time events
			IB = new BrokerInteractive(BrokerMode.HISTORIAN, marketPortfolio); 
			
			// updates historical L1 from L2
			Historian hist = new Historian(IB);
			
		} catch (Exception e) {
			System.out.println("COULD NOT CREATE INTERACTIVE BROKER / HISTORIAN" + "\n");
			e.printStackTrace();
		}
		
	}
	
}
