package com.onenow.main;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.onenow.constant.InvApproach;
import com.onenow.data.InitMarket;
import com.onenow.data.InvestmentList;
import com.onenow.execution.BrokerActivityImpl;
import com.onenow.execution.BrokerInteractive;
import com.onenow.instrument.InvestmentIndex;
import com.onenow.instrument.Underlying;
import com.onenow.portfolio.Portfolio;
import com.onenow.portfolio.PortfolioFactory;
import com.onenow.util.ParseDate;

public class InvestorMain {
		
	private static InvestmentList invList = new InvestmentList();
	private static ParseDate parseDate = new ParseDate();

	public static void main(String[] args) throws ParseException, InterruptedException {

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

		PortfolioFactory portfolioFactory;
		
		try {
			System.out.println("TRYING TO CREATE INVESTOR");
			portfolioFactory = new PortfolioFactory(marketPortfolio);		// create it
		} catch (Exception e) {
			System.out.println("COULD NOT CREATE INVESTOR\n");
			e.printStackTrace();
			return;
		}
		

		try {
			System.out.println("TRYING TO LAUNCH INVESTOR");
			portfolioFactory.launch();							// launch it
		} catch (Exception e) {
			System.out.println("COULD NOT EXECUTE INVESTOR\n");
			e.printStackTrace();
		}

	}
	
}
