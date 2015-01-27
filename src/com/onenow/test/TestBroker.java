package com.onenow.test;

import java.util.Date;
import java.util.List;

import com.onenow.broker.BrokerActivityImpl;
import com.onenow.database.DatabaseSystemActivityImpl;
import com.onenow.finance.InvType;
import com.onenow.finance.Investment;
import com.onenow.finance.Portfolio;
import com.onenow.finance.Strategy;
import com.onenow.finance.StrategyCallSpread;
import com.onenow.finance.StrategyIronCondor;
import com.onenow.finance.StrategyPutSpread;
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
	
	private Trade tradeStock1;
	private Trade tradeStock2;
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
		boolean result = 	testBuy() &&
							testExocet();  // TODO: && with other tests
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
		
		String s= ""  + "\n\n";
		s = s + "EXOCET" + "\n";
		System.out.println(s);

		testIronCondor(ex); 
				
		testCallSpread(ex); 
		
		testPutSpread(ex); 

		return true;
	}

	private boolean testPutSpread(Exocet ex) {
		StrategyPutSpread ps = ex.getPutSpread(getBroker(), 0.6);
		String s="";
		s = s + ps.toString();
		System.out.println(s);

		if(!ps.getMaxProfit().equals(245.0)) {
			System.out.println("ERROR ps max profit " + ps.getMaxProfit());
			return false;
		} 
		if(!ps.getMaxLoss().equals(-255.0)) {
			System.out.println("ERROR ps max loss " + ps.getMaxLoss());
			return false;
		}
		return true;
	}

	private boolean testCallSpread(Exocet ex) {
		StrategyCallSpread cs = ex.getCallSpread(getBroker(), 0.7);
		String s="";
		s = s + cs.toString();
		System.out.println(s);

		if(!cs.getMaxProfit().equals(245.0)) {
			System.out.println("ERROR cs max profit " + cs.getMaxProfit());
			return false;
		} 
		if(!cs.getMaxLoss().equals(-255.0)) {
			System.out.println("ERROR cs max loss " + cs.getMaxLoss());
			return false;
		}
		return true;
	}

	private boolean testIronCondor(Exocet ex) {
		StrategyIronCondor ic = ex.getIronCondor(getBroker(), 0.50);
		String s="";
		s = s + ic.toString();
		System.out.println(s);

		if(!ic.getPutNetPremium().equals(122.5)) {
			System.out.println("ERROR ic put net premium " + ic.getPutNetPremium());
			return false;
		} 
		if(!ic.getCallNetPremium().equals(87.5)) {
			System.out.println("ERROR ic call net premium " + ic.getCallNetPremium());
			return false;
		} 
		if(!ic.getMaxProfit().equals(210.0)) {
			System.out.println("ERROR ic max profit " + ic.getMaxProfit());
			return false;
		} 
		if(!ic.getMaxLoss().equals(-290.0)) {
			System.out.println("ERROR ic max loss " + ic.getMaxLoss());
			return false;
		} 
		if(!ic.getBoughtNetPremium().equals(-37.5)) {
			System.out.println("ERROR ic bought net " + ic.getBoughtNetPremium());
			return false;
		} 
		if(!ic.getSoldNetPremium().equals(247.5)) {
			System.out.println("ERROR ic sold net " + ic.getSoldNetPremium());
			return false;
		}
		// now more aggressive
		ic = ex.getIronCondor(getBroker(), 0.75); 
		if(!ic.getMaxProfit().equals(245.0)) {
			System.out.println("ERROR ic+ max profit " + ic.getMaxProfit());
			return false;
		} 
		if(!ic.getMaxLoss().equals(-255.0)) {
			System.out.println("ERROR ic+ max loss " + ic.getMaxLoss());
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
		
		Transaction tx = new Transaction(tradeStock1, tradeStock2, tradeCall1, tradeCall2, tradePut1, tradePut2);
		getBroker().enterTransaction(tx);
						
		Portfolio myPortfolio = getBroker().getMyPortfolio();		
		getBroker().toString();

		
		if(!myPortfolio.getAbsQuantity().equals(500)) {
			System.out.println("ERROR total shares " + myPortfolio.getAbsQuantity());
			return false;
		}
		
		if(!tx.getNetPremium().equals(39709.0)) {
			System.out.println("ERROR net premium " + tx.getNetPremium());
			return false;
		}
		
		return true;	
	}

	private void setAllTrade() {
		// get ready to buy something
		setTradeStock1(new Trade(getStock(), TradeType.BUY, 50, getBroker().getPrice(stock, TradeType.BUY)));
		setTradeStock2(new Trade(getStock(), TradeType.SELL, 150, getBroker().getPrice(stock, TradeType.SELL)));
		setTradeCall1(new Trade(getCall1(), TradeType.BUY, 100, getBroker().getPrice(getCall1(), TradeType.BUY)));
		setTradeCall2(new Trade(getCall2(), TradeType.SELL, 100, getBroker().getPrice(getCall2(), TradeType.SELL)));
		setTradePut1(new Trade(getPut1(), TradeType.SELL, 100, getBroker().getPrice(getPut1(), TradeType.SELL)));
		setTradePut2(new Trade(getPut2(), TradeType.BUY, 100, getBroker().getPrice(getPut2(), TradeType.BUY)));
	}

	private void setAllInv(Underlying theUnder) {
		try {
			setStock(getMarket().getBestStock(theUnder));
			// TODO: get best
			// call1 = market.getInvestments(theUnder, InvType.call, expDate, 407.00).get(0); 
			setCall1(getMarket().getInvestments(theUnder, InvType.call, expDate, 405.00).get(0));
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

	public Trade getTradeStock1() {
		return tradeStock1;
	}

	public void setTradeStock1(Trade tradeStock1) {
		this.tradeStock1 = tradeStock1;
	}

	public Trade getTradeStock2() {
		return tradeStock2;
	}

	public void setTradeStock2(Trade tradeStock2) {
		this.tradeStock2 = tradeStock2;
	}
}
