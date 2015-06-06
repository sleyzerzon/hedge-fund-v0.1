package com.onenow.execution;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.onenow.alpha.BrokerInterface;
import com.onenow.constant.BrokerMode;
import com.onenow.constant.InvType;
import com.onenow.constant.TradeType;
import com.onenow.data.HistorianConfig;
import com.onenow.data.MarketPrice;
import com.onenow.instrument.Investment;
import com.onenow.instrument.InvestmentIndex;
import com.onenow.instrument.InvestmentOption;
import com.onenow.instrument.InvestmentStock;
import com.onenow.instrument.Underlying;
import com.onenow.portfolio.Portfolio;
import com.onenow.portfolio.Trade;
import com.onenow.portfolio.Transaction;
import com.onenow.risk.MarketAnalytics;

public class BrokerEmulator implements BrokerInterface {

	private static List<Underlying> underList;
	private static Portfolio marketPortfolio;
	private static List<Trade> trades;
	private static Portfolio myPortfolio; 
	private static MarketPrice marketPrices;
	private static MarketAnalytics marketAnalytics;
		
	
	public BrokerEmulator() {
		setUnderList(new ArrayList<Underlying>());
		setMarketPortfolio(new Portfolio());
		
		setMyPortfolio(new Portfolio());
		setMarketPrices(new MarketPrice());
		setTrades(new ArrayList<Trade>());
		
		initMarket("aapl");
		initExocet("spx");
	}
	
	@Override
	public List<Underlying> getUnderlying() {
		return getUnderList();
	}
	
	@Override
	public void enterTransaction(Transaction trans) {
		getTrades().addAll(trans.getTrades());
		getMyPortfolio().enterTransaction(trans);		
	}

	@Override
	public Portfolio getMyPortfolio() {
		return myPortfolio;
	}
	
	@Override
	public List<Trade> getTrades() {
		return trades;	
	}

	@Override
	public Double getPrice(Investment inv, TradeType type) {
		// TODO Auto-generated method stub
		return null;
	}


	private static void initMarket(String name) { // create the investments
		Underlying under = new Underlying(name);
		
//		Date expDate = new Date(1000000);
		String expDate = "20150319";
		
		Investment stock = new InvestmentStock(under);
		Investment call1 = new InvestmentOption(under, InvType.CALL, expDate, 405.00);
		Investment call2 = new InvestmentOption(under, InvType.CALL, expDate, 400.00);
		Investment put1 = new InvestmentOption(under, InvType.PUT, expDate, 390.00);
		Investment put2 = new InvestmentOption(under, InvType.PUT, expDate, 385.00);		
		// set their prices
//		getMarketPrices().setPrice(stock, 396.00, 395.00);
//		getMarketPrices().setPrice(call1, 7.41, 7.40);
//		getMarketPrices().setPrice(call2, 8.85, 8.84);
//		getMarketPrices().setPrice(put1, 9.50, 9.49);
//		getMarketPrices().setPrice(put2, 8.33, 8.32);
		// create trades based on market price
		Trade stockTrade = new Trade(stock, TradeType.BUY, 75, getMarketPrices().readPrice(stock, TradeType.BUY));
		Trade stockCall1 = new Trade(call1, TradeType.BUY, 75, getMarketPrices().readPrice(call1, TradeType.BUY));
		Trade stockCall2 = new Trade(call2, TradeType.BUY, 75, getMarketPrices().readPrice(call2, TradeType.BUY));
		Trade stockPut1 = new Trade(put1, TradeType.BUY, 75, getMarketPrices().readPrice(put1, TradeType.BUY));
		Trade stockPut2 = new Trade(put2, TradeType.BUY, 75, getMarketPrices().readPrice(put2, TradeType.BUY));
		// transact it
		Transaction trans = new Transaction(stockTrade, stockCall1, stockCall2, stockPut1, stockPut2);
		marketPortfolio.enterTransaction(trans);
	}
	private static void initExocet(String name) { 		// create the investments
		Underlying under = new Underlying(name);
//		Date expDate = new Date(1000000);
		String expDate = "20150319";
		Investment index = new InvestmentIndex(under);
		Investment put0 = new InvestmentOption(under, InvType.PUT, expDate, 2040.00);		
		Investment put1 = new InvestmentOption(under, InvType.PUT, expDate, 2045.00);		
		Investment put2 = new InvestmentOption(under, InvType.PUT, expDate, 2050.00);
		Investment put3 = new InvestmentOption(under, InvType.PUT, expDate, 2055.00);
		Investment put4 = new InvestmentOption(under, InvType.PUT, expDate, 2060.00);
		Investment put5 = new InvestmentOption(under, InvType.PUT, expDate, 2065.00);
		Investment put6 = new InvestmentOption(under, InvType.PUT, expDate, 2070.00);
		
		Investment call5 = new InvestmentOption(under, InvType.CALL, expDate, 2040.00);
		Investment call4 = new InvestmentOption(under, InvType.CALL, expDate, 2045.00);
		Investment call3 = new InvestmentOption(under, InvType.CALL, expDate, 2050.00);
		Investment call2 = new InvestmentOption(under, InvType.CALL, expDate, 2055.00);
		Investment call1 = new InvestmentOption(under, InvType.CALL, expDate, 2060.00);
		Investment call0 = new InvestmentOption(under, InvType.CALL, expDate, 2065.00);
		// set their prices
//		getMarketPrices().setPrice(index, 2054.74);
//		getMarketPrices().setPrice(put0, 0.05, 0.05);
//		getMarketPrices().setPrice(put1, 0.05, 0.05);
//		getMarketPrices().setPrice(put2, 0.15, 0.20);
//		getMarketPrices().setPrice(put3, 1.10, 1.70);
//		getMarketPrices().setPrice(put4, 4.70, 5.90);
//		getMarketPrices().setPrice(put5, 8.50, 12.00);
//		getMarketPrices().setPrice(put6, 13.50, 16.70);
//		
//		getMarketPrices().setPrice(call5, 13.30, 16.50);
//		getMarketPrices().setPrice(call4, 8.30, 11.50);
//		getMarketPrices().setPrice(call3, 4.0, 5.50);
//		getMarketPrices().setPrice(call2, 0.50, 1.65);
//		getMarketPrices().setPrice(call1, 0.05, 0.35);
//		getMarketPrices().setPrice(call0, 0.05, 0.05);
		
		// create trades based on market price
		Trade indexTrade = new Trade(index, TradeType.BUY, 75, getMarketPrices().readPrice(index, TradeType.TRADED));
		Trade indexPut0 = new Trade(put0, TradeType.BUY, 75, getMarketPrices().readPrice(put0, TradeType.BUY));
		Trade indexPut1 = new Trade(put1, TradeType.BUY, 75, getMarketPrices().readPrice(put1, TradeType.BUY));
		Trade indexPut2 = new Trade(put2, TradeType.BUY, 75, getMarketPrices().readPrice(put2, TradeType.BUY));
		Trade indexPut3 = new Trade(put3, TradeType.BUY, 75, getMarketPrices().readPrice(put3, TradeType.BUY));
		Trade indexPut4 = new Trade(put4, TradeType.BUY, 75, getMarketPrices().readPrice(put4, TradeType.BUY));
		Trade indexPut5 = new Trade(put5, TradeType.BUY, 75, getMarketPrices().readPrice(put5, TradeType.BUY));
		Trade indexPut6 = new Trade(put6, TradeType.BUY, 75, getMarketPrices().readPrice(put6, TradeType.BUY));

		Trade indexCall0 = new Trade(call0, TradeType.BUY, 75, getMarketPrices().readPrice(call0, TradeType.BUY));
		Trade indexCall1 = new Trade(call1, TradeType.BUY, 75, getMarketPrices().readPrice(call1, TradeType.BUY));
		Trade indexCall2 = new Trade(call2, TradeType.BUY, 75, getMarketPrices().readPrice(call2, TradeType.BUY));
		Trade indexCall3 = new Trade(call3, TradeType.BUY, 75, getMarketPrices().readPrice(call3, TradeType.BUY));
		Trade indexCall4 = new Trade(call4, TradeType.BUY, 75, getMarketPrices().readPrice(call4, TradeType.BUY));
		Trade indexCall5 = new Trade(call5, TradeType.BUY, 75, getMarketPrices().readPrice(call5, TradeType.BUY));

		
		// transact it
		Transaction trans = new Transaction(indexTrade, 
											indexPut0, indexPut1, indexPut2, indexPut3, indexPut4, indexPut5, indexPut6,
											indexCall0, indexCall1, indexCall2, indexCall3, indexCall4, indexCall5);
		marketPortfolio.enterTransaction(trans);		
	}
	
	
	// PRINT
	public String toString() {
		String s = "";
		s = s + "UNDERLYING: " + "\n" + getUnderList().toString() + "\n";		
		s = s + "MARKET PORTFOLIO: " + "\n" + getMarketPortfolio().toString() + "\n";
		s = s + "PRICES" + "\n" + marketPrices.toString() + "\n";
		s = s + "TRADES: " + "\n";
		for(Trade trade:getTrades()) {
			s = s + trade.toString() + "\n";
		}		
		s = s + "MY PORTFOLIO" + "\n" + getMyPortfolio() + "\n";
		return s;
	}
	
	// TEST
	
	


	// SET GET
	private static List<Underlying> getUnderList() {
		return underList;
	}

	private static void setUnderList(List<Underlying> underList) {
		BrokerEmulator.underList = underList;
	}

	private static void setMyPortfolio(Portfolio myPortfolio) {
		BrokerEmulator.myPortfolio = myPortfolio;
	}

	private static MarketPrice getMarketPrices() {
		return marketPrices;
	}

	private static void setMarketPrices(MarketPrice marketPrices) {
		BrokerEmulator.marketPrices = marketPrices;
	}

	private static void setTrades(List<Trade> trades) {
		BrokerEmulator.trades = trades;
	}

	private static MarketAnalytics getMarketAnalytics() {
		return marketAnalytics;
	}

	private static void setMarketAnalytics(MarketAnalytics marketAnalytics) {
		BrokerEmulator.marketAnalytics = marketAnalytics;
	}

	public Portfolio getMarketPortfolio() {
		return BrokerEmulator.marketPortfolio;
	}

	private static void setMarketPortfolio(Portfolio marketPortfolio) {
		BrokerEmulator.marketPortfolio = marketPortfolio;
	}

	@Override
	public BrokerMode getMode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer readHistoricalQuotes(Investment inv, String end,
			HistorianConfig config, QuoteHistory history) {
		// TODO Auto-generated method stub
		return 1;
	}


}
