package com.onenow.main;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.onenow.constant.BrokerMode;
import com.onenow.constant.Topology;
import com.onenow.constant.InvApproach;
import com.onenow.data.InitMarket;
import com.onenow.data.InvestmentList;
import com.onenow.execution.BrokerActivityImpl;
import com.onenow.execution.BrokerInteractive;
import com.onenow.execution.BusWallSt;
import com.onenow.instrument.InvestmentIndex;
import com.onenow.instrument.Underlying;
import com.onenow.portfolio.Portfolio;
import com.onenow.portfolio.PortfolioFactory;
import com.onenow.util.FlexibleLogger;
import com.onenow.util.TimeParser;

public class InvestorMainOLD {
		
	private static Portfolio marketPortfolio = new Portfolio();

	public static void main(String[] args) throws ParseException, InterruptedException {

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

		broker.getLiveQuotes(); 
		PortfolioFactory portfolioFactory = new PortfolioFactory(broker, marketPortfolio);
		portfolioFactory.launch();							
	}
	
}
