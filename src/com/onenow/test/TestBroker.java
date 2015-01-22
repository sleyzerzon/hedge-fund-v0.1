package com.onenow.test;

import java.util.Date;
import java.util.List;

import com.onenow.broker.BrokerActivityImpl;
import com.onenow.finance.InvType;
import com.onenow.finance.Investment;
import com.onenow.finance.Portfolio;
import com.onenow.finance.Trade;
import com.onenow.finance.TradeType;
import com.onenow.finance.Transaction;
import com.onenow.finance.Underlying;

public class TestBroker {
	
	public TestBroker() {
		
	}

	public boolean test() {
		
		boolean success = testBuy();
		
		if(success==true) {
			System.out.println("NO ERRORS FOUND==AT-ALL==: " + "TestBroker");
		}

		return success;
		
	}
	
	private boolean testBuy() {
		
		Date expDate = new Date();
		expDate.setTime(1000000); // if date not set, getBest returns null

		
		BrokerActivityImpl broker = new BrokerActivityImpl();
				
		List<Underlying> unders = broker.getUnderlying();
		
		Portfolio market = broker.getMarketPortfolio();
		
		
		// find investment: that should be present
		Underlying theUnder = unders.get(0);
		
		Investment stock = new Investment();
		Investment call1 = new Investment();
		Investment call2 = new Investment();
		Investment put1 = new Investment();
		Investment put2 = new Investment();
		try {
			// find investment
			stock = market.getBestStock(theUnder);
			call1 = market.getInvestments(theUnder, InvType.call, expDate, 405.00).get(0);
			// to test exception:
			// call1 = market.getInvestments(theUnder, InvType.call, expDate, 407.00).get(0); 
			call2 = market.getInvestments(theUnder, InvType.call, expDate, 400.00).get(0);
			put1 = market.getInvestments(theUnder, InvType.put, expDate, 390.00).get(0);
			put2 = market.getInvestments(theUnder, InvType.put, expDate, 385.00).get(0);
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		}

		// get ready to buy something
		Trade tradeStock1 = new Trade(stock, TradeType.BUY, 50, broker.getPrice(stock, TradeType.BUY));
		Trade tradeStock2 = new Trade(stock, TradeType.SELL, 150, broker.getPrice(stock, TradeType.SELL));
		Trade tradeCall1 = new Trade(call1, TradeType.BUY, 100, broker.getPrice(call1, TradeType.BUY));
		Trade tradeCall2 = new Trade(call2, TradeType.SELL, 100, broker.getPrice(call2, TradeType.SELL));
		Trade tradePut1 = new Trade(put1, TradeType.SELL, 100, broker.getPrice(put1, TradeType.SELL));
		Trade tradePut2 = new Trade(put2, TradeType.BUY, 100, broker.getPrice(put2, TradeType.BUY));
		
		Transaction tx = new Transaction(tradeStock1, tradeStock2, tradeCall1, tradeCall2, tradePut1, tradePut2);
		broker.enterTransaction(tx);
						
		Portfolio myPortfolio = broker.getMyPortfolio();		
		broker.toString();

		
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
}
