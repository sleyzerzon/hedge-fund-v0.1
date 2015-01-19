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
	
	private Date expDate = new Date();
//	private expDate.setTime(1000000);

	public TestBroker() {
		
	}

	public boolean test() {
		
		boolean success = testBuy();
		
		if(success==true) {
			System.out.println("\n" + "NO ERRORS FOUND==AT-ALL==: " + "TestBroker");
		}

		return success;
		
	}
	
	private boolean testBuy() {
		
		BrokerActivityImpl broker = new BrokerActivityImpl();
				
		List<Underlying> unders = broker.getUnderlying();
		
		List<Investment> mktInv = broker.getInvestments();
		
		// find suitable investment
		Investment stock = broker.getBest(unders.get(0), InvType.stock);
//		Investment call1 = broker.getBest(unders.get(0), InvType.CALL, expDate, 405.00);
//		Investment call2 = broker.getBest(unders.get(0), InvType.CALL, expDate, 400.00);
//		Investment put1 = broker.getBest(unders.get(0), InvType.PUT, expDate, 390.00);
//		Investment put2 = broker.getBest(unders.get(0), InvType.PUT, expDate, 385.00);

		// get ready to buy something
		Trade tradeStock1 = new Trade(stock, TradeType.BUY, 50, broker.getPriceAsk(stock));
		Trade tradeStock2 = new Trade(stock, TradeType.SELL, 150, broker.getPriceAsk(stock));

//		Trade tradeCall1 = new Trade(call1, TradeType.BUY, 50, getPriceAsk(call1));
//		Trade tradeCall2 = new Trade(call2, TradeType.BUY, 50, getPriceAsk(call2));
//		Trade tradePut1 = new Trade(put1, TradeType.BUY, 50, getPriceAsk(put1));
//		Trade tradePut2 = new Trade(put2, TradeType.BUY, 50, getPriceAsk(put2));


		Transaction tx = new Transaction(tradeStock1, tradeStock2);
		broker.enterTransaction(tx);
				
		List<Trade> trades = broker.getTrades();  // verify it's what I entered

		Portfolio myInv = broker.getMyPortfolio();		

		broker.toString();
		
		if(!myInv.getTotalQuantity().equals(100)) {
			System.out.println("ERROR total shares " + myInv.getTotalQuantity());
			return false;
		}

		return true;
		
	}
	

//	System.out.println("Transaction Net:" + tx.getNetCost());
//	System.out.println("Transaction Call Net:" + tx.getNetCost(InvType.CALL));
//	System.out.println("Transaction Put Net:" + tx.getNetCost(InvType.PUT));

}
