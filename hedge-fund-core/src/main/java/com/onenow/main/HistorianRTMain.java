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
import com.onenow.execution.HistorianService;
import com.onenow.instrument.Underlying;
import com.onenow.portfolio.Portfolio;
import com.onenow.util.ParseDate;

/** 
 * Gather complete accurate historical market data
 * @param args
 */

public class HistorianRTMain {

	private static Portfolio marketPortfolio = new Portfolio();
	private static BrokerInteractive brokerInteractive;

	private static InvestmentList invList = new InvestmentList();
	private static ParseDate parseDate = new ParseDate();

	public static void main(String[] args) {
		
	    // choose relevant timeframe
	    String toDashedDate = parseDate.getDashedDatePlus(parseDate.getDashedToday(), 1);

		brokerInteractive = new BrokerInteractive(BrokerMode.REALTIME, marketPortfolio); 
			    
		InitMarket initMarket = new InitMarket(	marketPortfolio, 
												invList.getUnderlying(invList.someStocks), invList.getUnderlying(invList.someIndices),
												invList.getUnderlying(invList.futures), invList.getUnderlying(invList.options),
    											toDashedDate);						
		// register once: get all real-time quotes
		brokerInteractive.getLiveQuotes(); 
	}
}
