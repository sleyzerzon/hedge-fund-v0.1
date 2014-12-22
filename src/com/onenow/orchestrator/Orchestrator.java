package com.onenow.orchestrator;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class Orchestrator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Portfolio marketPortfolio = new Portfolio();
		MarketPrice marketPrices = new MarketPrice();
		Portfolio myPortfolio = new Portfolio();
		
		Underlying apl = new Underlying("APL");
		loadMarket(marketPortfolio, apl, marketPrices);		
		
		// Trade trade = new Trade(
		List<Investment> calltsToTrade = marketPortfolio.searchCalls(apl, new Date(), 405.00);
		List<Investment> putsToTrade = marketPortfolio.searchPuts(apl, new Date(), 405.00);
		List<Investment> stockToTrade = marketPortfolio.searchStock(apl);
		calltsToTrade.toString();
		putsToTrade.toString();
		stockToTrade.toString();
		
		// get buy/sell price and act
		
	}
	
	
	private static void loadMarket(Portfolio port, Underlying under, MarketPrice prices) {
		
		Investment stock = new InvestmentStock(under);
		Investment call1 = new InvestmentOption(under, InvestmentTypeEnum.CALL, new Date(), 405.00);
		Investment call2 = new InvestmentOption(under, InvestmentTypeEnum.CALL, new Date(), 400.00);
		Investment put1 = new InvestmentOption(under, InvestmentTypeEnum.PUT, new Date(), 390.00);
		Investment put2 = new InvestmentOption(under, InvestmentTypeEnum.PUT, new Date(), 385.00);

		port.addInvestment(stock);
		port.addInvestment(call1);
		port.addInvestment(call2);
		port.addInvestment(put1);
		port.addInvestment(put2);
		
		prices.setInvestmentPrice(stock, 396.00, 395.00);
		prices.setInvestmentPrice(call1, 7.41, 7.40);
		prices.setInvestmentPrice(call2, 8.85, 8.84);
		prices.setInvestmentPrice(put1, 9.50, 9.49);
		prices.setInvestmentPrice(put2, 8.33, 8.32);
		
		port.toString();
		
	}


}
