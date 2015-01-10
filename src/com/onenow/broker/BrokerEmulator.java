package com.onenow.broker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.onenow.finance.InvType;
import com.onenow.finance.Investment;
import com.onenow.finance.InvestmentOption;
import com.onenow.finance.InvestmentStock;
import com.onenow.finance.MarketPrice;
import com.onenow.finance.Portfolio;
import com.onenow.finance.Trade;
import com.onenow.finance.Transaction;
import com.onenow.finance.Underlying;

public class BrokerEmulator implements BrokerCloud, BrokerWallSt {

	private static List<Underlying> underList;
	private static Portfolio marketPortfolio;
	private static Portfolio myPortfolio; // todo
	private static MarketPrice marketPrices;
	private static List<Trade> trades;
	
	public BrokerEmulator() {
		System.out.println("Created BrokerEmulator.");
		setUnderList(new ArrayList<Underlying>());
		setMarketPortfolio(new Portfolio());
		setMyPortfolio(new Portfolio());
		setMarketPrices(new MarketPrice());
		setTrades(new ArrayList<Trade>());
		
		setUnderlying();
		setInvestments();
	}
	
	@Override
	public List<Underlying> getUnderlying() {
		return getUnderList();
	}
	
	@Override
	public List<Investment> getInvestments(boolean myPortfolio) {
		if(myPortfolio) {
			return getMyPortfolio().getInvestments();
		}
		return getMarketPortfolio().getInvestments();
	}
	
	@Override
	public Double getPriceAsk(Investment inv) {
		return getMarketPrices().getPriceAsk(inv);			
	}

	@Override
	public Double getPriceBid(Investment inv) {
		return getMarketPrices().getPriceBid(inv);			
	}
	
	@Override
	public Investment getBest(Underlying under, Enum invType) { 
		return getMarketPortfolio().getBest(under, invType);
	}
	
//	@Override
//	public Investment getBest(Underlying under, Enum invType, Date expiration, Double strike) { 
//		return getMarketPortfolio().getBest(under, invType, expiration, strike);
//	}
//
//	@Override
//	public Investment getBest(Underlying under, Enum invType, Enum InvTerm) {
//		return getMarketPortfolio().getBest(under, invType, InvTerm);		
//	}

	@Override
	public List<Trade> getTrades() {
		return trades;	
	}

	@Override
	public void addTrade(Transaction trans) {
		List<Trade> trades =getTrades();
		List<Trade> trades2 = trans.getTrades();
		trades.addAll(trades2);
		Portfolio p =getMyPortfolio();
		p.addTrade(trans);
		System.out.println("In addTrade(): " + this.trades);
	}
	
	// PRIVATE
	private void setUnderlying() {
		
		Underlying apl = new Underlying("APL");
		Underlying intc = new Underlying("INTC");
		Underlying rus = new Underlying("RUS");
		Underlying amzn = new Underlying("AMZN");

		getUnderList().add(apl);
		getUnderList().add(intc);
		getUnderList().add(rus);
		getUnderList().add(amzn);
		
		System.out.println("Underlying: " + getUnderList().toString());		
	}
	
	private void setInvestments() {
		Date expDate = new Date();
		expDate.setTime(1000000);
		for(Underlying under:getUnderlying()){
			setMarketInvestmentPrice(under);
		}		
		System.out.println(getMarketPortfolio().toString());		
	}
	
	private static void setMarketInvestmentPrice(Underlying under) {

		Date expDate = new Date();
		expDate.setTime(1000000);

		Investment stock = new InvestmentStock(under);
		Investment call1 = new InvestmentOption(under, InvType.CALL, expDate, 405.00);
		Investment call2 = new InvestmentOption(under, InvType.CALL, expDate, 400.00);
		Investment put1 = new InvestmentOption(under, InvType.PUT, expDate, 390.00);
		Investment put2 = new InvestmentOption(under, InvType.PUT, expDate, 385.00);

		getMarketPortfolio().addInvestment(stock);
		getMarketPortfolio().addInvestment(call1);
		getMarketPortfolio().addInvestment(call2);
		getMarketPortfolio().addInvestment(put1);
		getMarketPortfolio().addInvestment(put2);
		
		getMarketPrices().setPrice(stock, 396.00, 395.00);
		getMarketPrices().setPrice(call1, 7.41, 7.40);
		getMarketPrices().setPrice(call2, 8.85, 8.84);
		getMarketPrices().setPrice(put1, 9.50, 9.49);
		getMarketPrices().setPrice(put2, 8.33, 8.32);
			
	}


	// SET GET

	private static List<Underlying> getUnderList() {
		return underList;
	}

	private static void setUnderList(List<Underlying> underList) {
		BrokerEmulator.underList = underList;
	}

	private static Portfolio getMarketPortfolio() {
		return marketPortfolio;
	}

	private static void setMarketPortfolio(Portfolio marketPortfolio) {
		BrokerEmulator.marketPortfolio = marketPortfolio;
	}

	private static Portfolio getMyPortfolio() {
		return myPortfolio;
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


}
