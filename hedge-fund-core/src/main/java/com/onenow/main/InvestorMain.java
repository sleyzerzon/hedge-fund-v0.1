package com.onenow.main;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.onenow.constant.InvApproach;
import com.onenow.data.InitMarket;
import com.onenow.execution.BrokerActivityImpl;
import com.onenow.execution.BrokerInteractive;
import com.onenow.instrument.InvestmentIndex;
import com.onenow.instrument.Underlying;
import com.onenow.portfolio.Portfolio;
import com.onenow.portfolio.PortfolioFactory;

public class InvestorMain {
		
	public static void main(String[] args) throws ParseException, InterruptedException {

		// choose investments
		Portfolio marketPortfolio = new Portfolio();
	    Underlying index = new Underlying("SPX");
	    Underlying stocks = new Underlying("SPX");
	    Underlying options = new Underlying("SPX");
	    Underlying futures = new Underlying("ES");

	    InitMarket initMarket = new InitMarket(	marketPortfolio, 
	    										index, stocks,
	    										options, futures);

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
