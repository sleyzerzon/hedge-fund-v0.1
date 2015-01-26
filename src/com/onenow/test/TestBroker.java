package com.onenow.test;

import java.util.Date;
import java.util.List;

import com.onenow.broker.BrokerActivityImpl;
import com.onenow.database.DatabaseSystemActivityImpl;
import com.onenow.finance.InvType;
import com.onenow.finance.Investment;
import com.onenow.finance.Portfolio;
import com.onenow.finance.StrategyIronCondor;
import com.onenow.finance.Trade;
import com.onenow.finance.TradeType;
import com.onenow.finance.Transaction;
import com.onenow.finance.Underlying;
import com.onenow.investor.Exocet;
import com.sforce.ws.ConnectionException;

public class TestBroker {

	private DatabaseSystemActivityImpl logDB;
	private BrokerActivityImpl broker = new BrokerActivityImpl();
	private List<Underlying> unders = broker.getUnderlying();
	private Portfolio market = broker.getMarketPortfolio();

	Investment stock = new Investment();
	Investment call1 = new Investment();
	Investment call2 = new Investment();
	Investment put1 = new Investment();
	Investment put2 = new Investment();

	private Date expDate = new Date(1000000);
	
	private Trade tradeCall1; 
	private Trade tradeCall2; 
	private Trade tradePut1; 
	private Trade tradePut2;
	
	// CONSTRUCTOR
	public TestBroker() {
		
	}
	
	public TestBroker (DatabaseSystemActivityImpl logDB) {
		setLogDB(logDB);
		setUnders(getBroker().getUnderlying());
		setMarket(getBroker().getMarketPortfolio());
		setUnders(getBroker().getUnderlying());
		setMarket(getBroker().getMarketPortfolio());
	}

	// PUBLIC
	public boolean test() {
		boolean result = 	testBuy();  // TODO: && with other tests
		testExocet();	
		handleResult(result);	
		return result;
	}

	private void handleResult(boolean success) {
		String s="";
		if(success==true) {
			s = s + "NO ERRORS FOUND==AT-ALL==: " + "TestBroker" + "\n\n";
		} else {
			s = s + "ERROR " + "TestBroker"  + "\n\n";
		}
		System.out.println(s);
		try {
			getLogDB().newLog("TestBroker", s);
		} catch (ConnectionException e) {
			e.printStackTrace();
		}
	}
	
	private boolean testExocet() {
		Exocet ex = new Exocet(100, new Underlying("spx"), getExpDate());
		StrategyIronCondor ic = ex.getStrategy(getBroker(), 0.50);
		String s= ""  + "\n\n";
		s = s + "EXOCET" + "\n";
		s = s + ic.toString();
		System.out.println(s);
		
		if(!ic.getPutNetPremium().equals(122.5)) {
			System.out.println("ERROR put net premium " + ic.getPutNetPremium());
			return false;
		} 
		if(!ic.getCallNetPremium().equals(87.5)) {
			System.out.println("ERROR call net premium " + ic.getCallNetPremium());
			return false;
		} 
		if(!ic.getMaxProfit().equals(210.0)) {
			System.out.println("ERROR max profit " + ic.getMaxProfit());
			return false;
		} 
		if(!ic.getMaxLoss().equals(-290.0)) {
			System.out.println("ERROR max loss " + ic.getMaxLoss());
			return false;
		} 
		if(!ic.getBoughtNetPremium().equals(-37.5)) {
			System.out.println("ERROR bought net " + ic.getBoughtNetPremium());
			return false;
		} 
		if(!ic.getSoldNetPremium().equals(247.5)) {
			System.out.println("ERROR sold net " + ic.getSoldNetPremium());
			return false;
		} 

		return true;
	}
	
	private boolean testBuy() {

		// chose underlying
		Underlying theUnder = getUnders().get(0);
		// construct all possible investments
		setAllInv(theUnder);
		// then get ready to trade them
		setAllTrade();
		
//		Transaction tx = new Transaction(tradeStock1, tradeStock2, tradeCall1, tradeCall2, tradePut1, tradePut2);
		Transaction tx = new Transaction(getTradeCall1(), getTradeCall2(), getTradePut1(), getTradePut2());
		getBroker().enterTransaction(tx);
						
		Portfolio myPortfolio = getBroker().getMyPortfolio();		
		getBroker().toString();

		
		if(!myPortfolio.getAbsQuantity().equals(500)) {
			System.out.println("ERROR total shares " + myPortfolio.getAbsQuantity());
			return false;
		}
		
		if(!tx.getNetPremium().equals(39761.0)) {
			System.out.println("ERROR premium " + tx.getNetPremium());
			return false;
		}
		
		return true;	
	}

	private void setAllTrade() {
		// get ready to buy something
//		Trade tradeStock1 = new Trade(stock, TradeType.BUY, 50, broker.getPrice(stock, TradeType.BUY));
//		Trade tradeStock2 = new Trade(stock, TradeType.SELL, 150, broker.getPrice(stock, TradeType.SELL));
		setTradeCall1(new Trade(getCall1(), TradeType.BUY, 100, getBroker().getPrice(getCall1(), TradeType.BUY)));
		setTradeCall2(new Trade(getCall2(), TradeType.SELL, 100, getBroker().getPrice(getCall2(), TradeType.SELL)));
		setTradePut1(new Trade(getPut1(), TradeType.SELL, 100, getBroker().getPrice(getPut1(), TradeType.SELL)));
		setTradePut2(new Trade(getPut2(), TradeType.BUY, 100, getBroker().getPrice(getPut2(), TradeType.BUY)));
	}

	private void setAllInv(Underlying theUnder) {
		try {
			// find investment
//			stock = market.getBestStock(theUnder);
			// TODO: get best
			setCall1(getMarket().getInvestments(theUnder, InvType.call, expDate, 405.00).get(0));
			// to test exception:
			// call1 = market.getInvestments(theUnder, InvType.call, expDate, 407.00).get(0); 
			setCall2(getMarket().getInvestments(theUnder, InvType.call, expDate, 400.00).get(0));
			setPut1(getMarket().getInvestments(theUnder, InvType.put, expDate, 390.00).get(0));
			setPut2(getMarket().getInvestments(theUnder, InvType.put, expDate, 385.00).get(0));
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}

	private DatabaseSystemActivityImpl getLogDB() {
		return logDB;
	}

	private void setLogDB(DatabaseSystemActivityImpl logDB) {
		this.logDB = logDB;
	}

	private BrokerActivityImpl getBroker() {
		return broker;
	}

	private void setBroker(BrokerActivityImpl broker) {
		this.broker = broker;
	}

	private List<Underlying> getUnders() {
		return unders;
	}

	private void setUnders(List<Underlying> unders) {
		this.unders = unders;
	}

	private Portfolio getMarket() {
		return market;
	}

	private void setMarket(Portfolio market) {
		this.market = market;
	}

	private Investment getStock() {
		return stock;
	}

	private void setStock(Investment stock) {
		this.stock = stock;
	}

	private Investment getCall1() {
		return call1;
	}

	private void setCall1(Investment call1) {
		this.call1 = call1;
	}

	private Investment getCall2() {
		return call2;
	}

	private void setCall2(Investment call2) {
		this.call2 = call2;
	}

	private Investment getPut1() {
		return put1;
	}

	private void setPut1(Investment put1) {
		this.put1 = put1;
	}

	private Investment getPut2() {
		return put2;
	}

	private void setPut2(Investment put2) {
		this.put2 = put2;
	}

	private Date getExpDate() {
		return expDate;
	}

	private void setExpDate(Date expDate) {
		this.expDate = expDate;
	}

	private Trade getTradeCall1() {
		return tradeCall1;
	}

	private void setTradeCall1(Trade tradeCall1) {
		this.tradeCall1 = tradeCall1;
	}

	private Trade getTradeCall2() {
		return tradeCall2;
	}

	private void setTradeCall2(Trade tradeCall2) {
		this.tradeCall2 = tradeCall2;
	}

	private Trade getTradePut1() {
		return tradePut1;
	}

	private void setTradePut1(Trade tradePut1) {
		this.tradePut1 = tradePut1;
	}

	private Trade getTradePut2() {
		return tradePut2;
	}

	private void setTradePut2(Trade tradePut2) {
		this.tradePut2 = tradePut2;
	}
}
