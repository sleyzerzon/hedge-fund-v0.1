package com.onenow.main;

import java.util.List;

import com.ib.client.Types.BarSize;
import com.ib.client.Types.DurationUnit;
import com.ib.client.Types.WhatToShow;
import com.onenow.constant.BrokerMode;
import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.SamplingRate;
import com.onenow.constant.TradeType;
import com.onenow.data.Historian;
import com.onenow.data.HistorianConfig;
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

	private static Portfolio marketPortfolio = new Portfolio();
	private static BrokerInteractive brokerInteractive = new BrokerInteractive();
	private static Historian historian = new Historian();

	private static InvestmentList invList = new InvestmentList();
	private static ParseDate parseDate = new ParseDate();

	public static void main(String[] args) {
		
		// choose investments
	    List<Underlying> stocks = invList.getUnderlying(invList.someStocks);
	    List<Underlying> indices = invList.getUnderlying(invList.someIndices);
	    List<Underlying> futures = invList.getUnderlying(invList.futures);
	    List<Underlying> options = invList.getUnderlying(invList.options);
	    
	    // choose relevant timeframe
	    String toDashedDate = parseDate.getDashedDatePlus(parseDate.getDashedToday(), 1);

	    HistorianConfig config = new HistorianConfig(	InvDataSource.IB, InvDataTiming.HISTORICAL,
				1, DurationUnit.DAY, BarSize._1_hour, WhatToShow.TRADES,
				TradeType.TRADED, SamplingRate.SWING);   	    	
	    

		brokerInteractive = new BrokerInteractive(BrokerMode.HISTORIAN, marketPortfolio); 
		historian = new Historian(brokerInteractive, config);		
			    
	    // get ready to loop
		int count=0;
		while(true) {
			// TODO : 10000068 322 Error processing request:-'wd' : cause - Only 50 simultaneous API historical data requests allowed.

	    	// update the market portfolio, broker, and historian every month
	    	if(count%30 == 0) {
					InitMarket initMarket = new InitMarket(	marketPortfolio, 
			    											stocks, indices,
			    											futures, options,
			    											toDashedDate);						
			    	// register once: get all real-time quotes
					brokerInteractive.getLiveQuotes(); 
		    } 	    

			// updates historical L1 from L2
			historian.run(toDashedDate);
			// go back further in time
			toDashedDate = parseDate.getDashedDateMinus(toDashedDate, 1);
			count++;
		}
	}
}
