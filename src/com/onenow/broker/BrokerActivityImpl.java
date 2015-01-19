package com.onenow.broker;

import java.util.ArrayList;
import java.util.List;

import com.onenow.finance.Investment;
import com.onenow.finance.Portfolio;
import com.onenow.finance.Trade;
import com.onenow.finance.Transaction;
import com.onenow.finance.Underlying;

public class BrokerActivityImpl implements BrokerWallSt, BrokerCloud,
		BrokerActivity {

	private static BrokerEmulator brokerEmulator;
	private static BrokerWallStIntBro brokerIntBro;

	public BrokerActivityImpl() {
//		System.out.println("Created BrokerActivityImpl");
		setBrokerEmulator(new BrokerEmulator());
		setBrokerIntBro(new BrokerWallStIntBro());
	}

	@Override
	public List<Underlying> getUnderlying() {
		List<Underlying> list = new ArrayList<Underlying>();
		list.add(new Underlying("aapl"));

//		List<Underlying> list = getBrokerEmulator().getUnderlying();
//		System.out.println("RETURNING UNDERLYING: " + list.toString());
		return list;
	}

	@Override
	public List<Investment> getInvestments() {
		return getBrokerEmulator().getInvestments();
	}

	@Override
	public Portfolio getMyPortfolio() {
		return getBrokerEmulator().getMyPortfolio();
	}

	@Override
	public Double getPriceAsk(Investment inv) {
		Double price = getBrokerEmulator().getPriceAsk(inv);
//		System.out.println("RETURNING PRICE: " + inv.toString() + " $"+ price);
		return price;
	}

	@Override
	public Double getPriceBid(Investment inv) {
		return getBrokerEmulator().getPriceBid(inv);
	}

	@Override
	public Investment getBest(Underlying under, Enum invType) { // stock &
																// on-demmand &
																// spot
		return getBrokerEmulator().getBest(under, invType);
	}
//
//	@Override
//	public Investment getBest(Underlying under, Enum invType, Date expiration,
//			Double strike) { // options
//		return getBrokerEmulator().getBest(under, invType, expiration, strike);
//	}
//
//	@Override
//	public Investment getBest(Underlying under, Enum invType, Enum InvTerm) { // Reserved
//		return getBrokerEmulator().getBest(under, invType, InvTerm);
//	}

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

	private static BrokerWallStIntBro getBrokerIntBro() {
		return brokerIntBro;
	}

	private static void setBrokerIntBro(BrokerWallStIntBro brokerIntBro) {
		BrokerActivityImpl.brokerIntBro = brokerIntBro;
	}

//	@Override
//	public Investment getBest(Underlying under, Enum invType, Enum InvTerm) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Investment getBest(Underlying under, Enum invType, Date expiration,
//			Double strike) {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
	

}
