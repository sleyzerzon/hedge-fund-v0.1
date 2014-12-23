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
		Date expDate = new Date();
		expDate.setTime(1000000);
		Investment stock = marketPortfolio.getBest(apl, InvType.STOCK);
		Investment call = marketPortfolio.getBest(apl, InvType.CALL, expDate, 405.00);
		Investment put = marketPortfolio.getBest(apl, InvType.PUT, expDate, 390.00);
		
		System.out.println(call.toString() + " @$ " + marketPrices.getBuyPrice(call));
		put.toString();
		stock.toString();

		TradeTransaction transaction = new TradeTransaction();
		Trade trade = new Trade(call, TradeType.BUY, 100, marketPrices);
		transaction.addTrade(trade);
		System.out.println("1 call contract " + trade.getNetCost() + " " + transaction.getNetCost());
		
		// get buy/sell price and act
		
	}
	
	
	private static void loadMarket(Portfolio port, Underlying under, MarketPrice prices) {

		Date expDate = new Date();
		expDate.setTime(1000000);

		Investment stock = new InvestmentStock(under);
		Investment call1 = new InvestmentOption(under, InvType.CALL, expDate, 405.00);
		Investment call2 = new InvestmentOption(under, InvType.CALL, expDate, 400.00);
		Investment put1 = new InvestmentOption(under, InvType.PUT, expDate, 390.00);
		Investment put2 = new InvestmentOption(under, InvType.PUT, expDate, 385.00);

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
