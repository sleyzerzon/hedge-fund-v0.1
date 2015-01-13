package com.onenow.broker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.onenow.finance.Investment;
import com.onenow.finance.Trade;
import com.onenow.finance.Transaction;
import com.onenow.finance.Underlying;

public class BrokerActivityImpl implements BrokerWallSt, BrokerCloud,
		BrokerActivity {

	private static BrokerEmulator brokerEmulator;
	private static BrokerWallStIntBro brokerIntBro;

	public BrokerActivityImpl() {
		System.out.println("Created BrokerActivityImpl.");
		setBrokerEmulator(new BrokerEmulator());
		setBrokerIntBro(new BrokerWallStIntBro());
	}

	@Override
	public List<Underlying> getUnderlying() {
		List<Underlying> list = new ArrayList<Underlying>();
		list.add(new Underlying("APL"));

		// List<Underlying> list = getBrokerEmulator().getUnderlying();

		System.out.println("Returning emulatorList");
		return list;
	}

	@Override
	public List<Investment> getInvestments(boolean myPortfolio) {
		return getBrokerEmulator().getInvestments(myPortfolio);
	}

	@Override
	public Double getPriceAsk(Investment inv) {
		return getBrokerEmulator().getPriceAsk(inv);
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

		System.out.println("In getTrades(): " + trades);
		return trades;
	}

	@Override
	public void addTrade(Transaction transaction) {
		getBrokerEmulator().addTrade(transaction);
	}

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
