package com.onenow.execution;

import java.util.ArrayList;
import java.util.List;

import com.onenow.constant.TradeType;
import com.onenow.instrument.Investment;
import com.onenow.instrument.InvestmentIndex;
import com.onenow.instrument.InvestmentOption;
import com.onenow.instrument.Underlying;
import com.onenow.portfolio.BrokerController;
import com.onenow.portfolio.Portfolio;
import com.onenow.portfolio.Trade;
import com.onenow.portfolio.Transaction;

/**
 * Implement the BrokerActivity interface
 */
public class BrokerActivityImpl implements BrokerActivity { 

	private static BrokerEmulator brokerEmulator;
	private static BrokerInteractive brokerInteractive;
	private static BrokerAWS brokerAWS;
	private static BrokerGoogle brokerGoogle;

	/**
	 * Default constructor creates an emulator
	 */
	public BrokerActivityImpl() {
		setBrokerEmulator(new BrokerEmulator());
	}

	/**
	 * This advanced constructor takes in an Interactive Brokers broker
	 * @param ib  
	 */
	public BrokerActivityImpl(BrokerInteractive ib) {
		setBrokerEmulator(null);
		setBrokerInteractive(ib);
		setBrokerAWS(null);
		setBrokerGoogle(null);
	}
	
	/**
	 * A broker is selected to operate with
	 * @return
	 */
	private Broker getBroker() {
		Broker broker = null;
		if(getBrokerEmulator()!=null) {
			broker = brokerEmulator;
		}
		if(getBrokerInteractive()!=null) {
			broker = brokerInteractive;
		}
		if(getBrokerAWS()!=null) {
			broker = brokerAWS;
		}
		return broker;
	}

	@Override
	public List<Underlying> getUnderlying() {
		List<Underlying> list = new ArrayList<Underlying>();
		list.add(new Underlying("aapl"));
		return list;
	}

	@Override
	public Portfolio getMyPortfolio() {
		return getBroker().getMyPortfolio();
	}

	@Override
	public List<Trade> getTrades() {
		List<Trade > trades= getBroker().getTrades();
		return trades;
	}

	@Override
	public void enterTransaction(Transaction trans) {
		getBroker().enterTransaction(trans);
	}
	
	// PRINT
	public String toString() {
		String s = "\n";
		s = s + getBroker().toString();
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
	public Double getBestBid (String type, Investment inv, Double aggression) {
		
		Double price = 0.0;
		
		price = getBroker().getPrice(inv, type);
				
		return price;

//		// market: bid<ask
//		Double askPrice = getBroker().getPrice(inv, TradeType.SELL);
//		Double bidPrice = getBroker().getPrice(inv, TradeType.BUY);
//		Double spread = askPrice-bidPrice;
//		Double deltaSell = spread * (1-aggression);
//		Double deltaBuy = spread * aggression;
//		Double price = 0.0;
//		if(type.equals(TradeType.BUY)) {
//			price = bidPrice + deltaBuy;
//		} else {
//			price = askPrice - deltaSell; 
//		}
//			
//		System.out.println("****** IMPL " + askPrice + " " + bidPrice + " " + price);
//		return price;
	}

	@Override
	public Double getPrice(Investment inv, String type) {
		Double price = 0.0;
		
		price = getBroker().getPrice(inv, type);
				
		return price;
	}
	
}
