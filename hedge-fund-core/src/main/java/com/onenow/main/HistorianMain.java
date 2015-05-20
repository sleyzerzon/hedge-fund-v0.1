package com.onenow.main;

import java.util.List;

import com.onenow.constant.BrokerMode;
import com.onenow.data.Historian;
import com.onenow.data.InitMarket;
import com.onenow.data.InvestmentList;
import com.onenow.execution.BrokerInteractive;
import com.onenow.execution.BusWallSt;
import com.onenow.execution.HistorianService;
import com.onenow.portfolio.Portfolio;
import com.onenow.util.ParseDate;

/** 
 * Gather complete accurate historical market data
 * @param args
 */

public class HistorianMain {

	private static Portfolio marketPortfolio = new Portfolio();
	private static BusWallSt bus;
	private static BrokerInteractive broker;
	private static Historian historian;

	private static HistorianService service = new HistorianService();

	public static void main(String[] args) {
		
	    // choose relevant timeframe
	    String toDashedDate = ParseDate.getDashedDatePlus(ParseDate.getDashedToday(), 1);

	    bus = new BusWallSt();
		broker = new BrokerInteractive(BrokerMode.HISTORIAN, marketPortfolio, bus); 
		historian = new Historian(broker, service.size30sec);		
			    
	    // get ready to loop
		int count=0;
		while(true) {
			// TODO : 10000068 322 Error processing request:-'wd' : cause - Only 50 simultaneous API historical data requests allowed.

	    	// update the market portfolio, broker, and historian every month
	    	if(count%30 == 0) {
					InitMarket initMarket = new InitMarket(	marketPortfolio, 
															InvestmentList.getUnderlying(InvestmentList.someStocks), 
															InvestmentList.getUnderlying(InvestmentList.someIndices),
															InvestmentList.getUnderlying(InvestmentList.futures), 
															InvestmentList.getUnderlying(InvestmentList.options),
			    											toDashedDate);						
		    } 	    

			// updates historical L1 from L2
			historian.run(toDashedDate);
			// go back further in time
			toDashedDate = ParseDate.getDashedDateMinus(toDashedDate, 1);
			count++;
		}
	}
}
