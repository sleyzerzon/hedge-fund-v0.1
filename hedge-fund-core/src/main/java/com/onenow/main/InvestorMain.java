package com.onenow.main;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.onenow.constant.BrokerMode;
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
import com.onenow.util.ParseDate;

public class InvestorMain {
		
	private static Portfolio marketPortfolio = new Portfolio();
	private static BusWallSt bus;
	private static BrokerInteractive broker;

	public static void main(String[] args) throws ParseException, InterruptedException {

	    // choose relevant timeframe
	    String toDashedDate = ParseDate.getDashedDatePlus(ParseDate.getDashedToday(), 1);

	    bus = new BusWallSt();
		broker = new BrokerInteractive(BrokerMode.PRIMARY, marketPortfolio, bus); 

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
