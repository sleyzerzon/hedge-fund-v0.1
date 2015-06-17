package com.onenow.execution;

import java.util.ArrayList;
import java.util.List;

import com.onenow.alpha.BrokerInterface;
import com.onenow.constant.StreamName;
import com.onenow.constant.TradeType;
import com.onenow.data.HistorianConfig;
import com.onenow.data.QuoteHistory;
import com.onenow.instrument.Investment;
import com.onenow.instrument.Underlying;
import com.onenow.portfolio.Portfolio;
import com.onenow.portfolio.Trade;
import com.onenow.portfolio.Transaction;

/**
 * Implement the BrokerActivity interface
 */
public class BrokerActivityImpl implements BrokerActivity { 

	private static BrokerEmulator brokerEmulator = null;
	private static BrokerInteractive brokerInteractive = null;
	private static BrokerAWS brokerAWS = null;
	private static BrokerGoogle brokerGoogle = null;

	/**
	 * Default constructor creates an emulator
	 */
	public BrokerActivityImpl(BrokerEmulator be) {
		this.brokerEmulator = be;
	}

	/**
	 * This advanced constructor takes in an Interactive Brokers broker
	 * @param ib  
	 */
	public BrokerActivityImpl(BrokerInteractive ib) {
		this.brokerInteractive = ib;
	}
	
	/**
	 * A broker is selected to operate with
	 * @return
	 */
	private BrokerInterface getBroker() {
		BrokerInterface broker = null;
		if(brokerEmulator!=null) {
			broker = brokerEmulator;
		}
		if(brokerInteractive!=null) {
			broker = brokerInteractive;
		}
		if(brokerAWS!=null) {
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
	public int hashCode() {
		return brokerEmulator.hashCode();
	}

	public boolean equals(Object obj) {
		return brokerEmulator.equals(obj);
	}

	@Override
	public Portfolio getMarketPortfolio() {
		return getBroker().getMarketPortfolio();
	}

	@Override
	public Double getBestBid (TradeType type, Investment inv, Double aggression) {
		
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
	public Double getPrice(Investment inv, TradeType type) {
		Double price = 0.0;
		
		price = getBroker().getPrice(inv, type);
				
		return price;
	}

	@Override
	public Integer readHistoricalQuotes(Investment inv, String end,
			HistorianConfig config, QuoteHistory history) {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public StreamName getStream() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
