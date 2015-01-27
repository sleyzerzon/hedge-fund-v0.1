package com.onenow.broker;

import java.util.ArrayList;
import java.util.List;

import com.onenow.finance.Investment;
import com.onenow.finance.Portfolio;
import com.onenow.finance.Trade;
import com.onenow.finance.TradeType;
import com.onenow.finance.Transaction;
import com.onenow.finance.Underlying;

public class BrokerActivityImpl implements BrokerActivity { 

	private static BrokerEmulator brokerEmulator;
	private static BrokerInteractive brokerInteractive;
	private static BrokerAWS brokerAWS;
	private static BrokerGoogle brokerGoogle;

	public BrokerActivityImpl() {
		setBrokerEmulator(new BrokerEmulator());
		setBrokerInteractive(new BrokerInteractive());
		setBrokerAWS(new BrokerAWS());
		setBrokerGoogle(new BrokerGoogle());
	}

	@Override
	public List<Underlying> getUnderlying() {
		List<Underlying> list = new ArrayList<Underlying>();
		list.add(new Underlying("aapl"));
		return list;
	}

	@Override
	public Portfolio getMyPortfolio() {
		return getBrokerEmulator().getMyPortfolio();
	}

	@Override
	public List<Trade> getTrades() {
		List<Trade > trades= getBrokerEmulator().getTrades();
		return trades;
	}

	@Override
	public void enterTransaction(Transaction trans) {
		getBrokerEmulator().enterTransaction(trans);
	}
	
	// PRINT
	public String toString() {
		String s = "\n";
		s = s + getBrokerEmulator().toString();
		System.out.println(s);
		return s;
	}
	
	// TEST
	
	

	// SET GET
	private static BrokerEmulator getBrokerEmulator() {
		return brokerEmulator;
	}

	private static void setBrokerEmulator(BrokerEmulator brokerEmulator) {
		BrokerActivityImpl.brokerEmulator = brokerEmulator;
	}

	public int hashCode() {
		return brokerEmulator.hashCode();
	}

	public boolean equals(Object obj) {
		return brokerEmulator.equals(obj);
	}

	private static BrokerInteractive getBrokerInteractive() {
		return brokerInteractive;
	}

	private static void setBrokerInteractive(BrokerInteractive brokerInteractive) {
		BrokerActivityImpl.brokerInteractive = brokerInteractive;
	}

	private static BrokerAWS getBrokerAWS() {
		return brokerAWS;
	}

	private static void setBrokerAWS(BrokerAWS brokerAWS) {
		BrokerActivityImpl.brokerAWS = brokerAWS;
	}

	private static BrokerGoogle getBrokerGoogle() {
		return brokerGoogle;
	}

	private static void setBrokerGoogle(BrokerGoogle brokerGoogle) {
		BrokerActivityImpl.brokerGoogle = brokerGoogle;
	}

	@Override
	public Portfolio getMarketPortfolio() {
		return getBrokerEmulator().getMarketPortfolio();
	}

	@Override
	public Double getBestBid (TradeType type, Investment inv, Double aggression) {
		// market: bid<ask
		Double askPrice = getBrokerEmulator().getPrice(inv, TradeType.SELL);
		Double bidPrice = getBrokerEmulator().getPrice(inv, TradeType.BUY);
		Double spread = askPrice-bidPrice;
		Double deltaSell = spread * (1-aggression);
		Double deltaBuy = spread * aggression;
		Double price = 0.0;
		if(type.equals(TradeType.BUY)) {
			price = bidPrice + deltaBuy;
		} else {
			price = askPrice - deltaSell; 
		}
			
		return price;
	}

	@Override
	public Double getPrice(Investment inv, TradeType type) {
		Double price = getBrokerEmulator().getPrice(inv, type); 
		return price;
	}
}
