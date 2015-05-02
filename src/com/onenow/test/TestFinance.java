package com.onenow.test;

import java.util.Date;

import com.onenow.admin.DatabaseSystemActivityImpl;
import com.onenow.constant.InvType;
import com.onenow.constant.TradeType;
import com.onenow.instrument.InvestmentOption;
import com.onenow.instrument.Underlying;
import com.onenow.portfolio.Sequence;
import com.onenow.portfolio.StrategyCallSpread;
import com.onenow.portfolio.StrategyIronCondor;
import com.onenow.portfolio.StrategyPutSpread;
import com.onenow.portfolio.Trade;
import com.onenow.risk.MarketAnalytics;
import com.sforce.ws.ConnectionException;

public class TestFinance {

	private Underlying under = new Underlying("AAPL");
	private Integer quantity = 100;
	private Date exp = new Date();
	
	private Trade callBuy = getCallBuy(under, quantity, exp);
	private Trade callSell = getCallSell(under, quantity, exp);
	private Trade putSell = getPutSell(under, quantity, exp);
	private Trade putBuy = getPutBuy(under, quantity, exp);	

	private DatabaseSystemActivityImpl logDB;

	public TestFinance() {

	}
	
	public TestFinance(DatabaseSystemActivityImpl logDB) {
		setLogDB(logDB);
	}

	public boolean test() {

		boolean result;
		try {
			result = testIronCondor(callBuy, callSell, putBuy, putSell) &&
								testCallSpread(callBuy, callSell) &&
								testPutSpread(putBuy, putSell) && 
								testMarketAnalytics();
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
		}
		handleResult(result);
		
//		Sequence seq = new Sequence();
//		StrategyIronCondor condor = new StrategyIronCondor(callBuy, callSell, putBuy, putSell);
//		seq.getStrategies().add(condor);
		
		return result;
	}

	private void handleResult(boolean result) {
		String s = "";
		if(result==true) {
			s = s + "\n" + "NO ERRORS FOUND==AT-ALL==: " + "TestFinance";
		} else {
			s = s + "\n" + "TEST ERROR: " + "TestFinance";
		}
		// print to screen
		System.out.println(s);
		// save to db
		if(getLogDB().isDBLive()) {
			try {
				getLogDB().newLog("TestFinance", s);
			} catch (ConnectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	// PRIVATE
	private static boolean testIronCondor(Trade callBuy, Trade callSell, Trade putBuy, Trade putSell) {
		StrategyIronCondor strat = new StrategyIronCondor(callBuy, callSell, putBuy, putSell);
		boolean success = strat.test();
		if(success==true) {
			System.out.println("NO ERRORS FOUND: " + "testIronCondor");
		}
		return success;
	}
	

	private static boolean testCallSpread(Trade callBuy, Trade callSell) {
		StrategyCallSpread strat = new StrategyCallSpread(callBuy, callSell);
		boolean success = strat.test();
		if(success==true) {
			System.out.println("NO ERRORS FOUND: " + "testCallSpread");
		}
		return success;	
	}
	
	private static boolean testPutSpread(Trade putBuy, Trade putSell) {	
		StrategyPutSpread strat = new StrategyPutSpread(putBuy, putSell);
		boolean success = strat.test();
		if(success==true) {
			System.out.println("NO ERRORS FOUND: " + "testCallSpread");
		}
		return success;
	}
	
	public static boolean testMarketAnalytics() {
		MarketAnalytics analytics = new MarketAnalytics();
		boolean success = analytics.test();
		if(success==true) {
			System.out.println("NO ERRORS FOUND: " + "testMarketAnalytics");
		}
		return success;
	}
	
	// SETUP INPUT DATA 
	private static Trade getPutBuy(Underlying under, Integer quantity, Date exp) {
		Double putBuyStrike = 385.00;
		Double putBuyPrice = 8.33;		
		Trade putBuy = null;
//		new Trade(	new InvestmentOption(under, InvType.PUT, exp, putBuyStrike), 
//							TradeType.BUY, quantity, putBuyPrice);
		return putBuy;
	}

	private static Trade getPutSell(Underlying under, Integer quantity, Date exp) {
		Double putSellStrike = 390.00;
		Double putSellPrice = 9.5;
		Trade putSell = null;
//		new Trade(	new InvestmentOption(under, InvType.PUT, exp, putSellStrike), 
//							TradeType.SELL, quantity, putSellPrice);
		return putSell;
	}

	private static Trade getCallSell(Underlying under, Integer quantity,
			Date exp) {
		Double callSellStrike = 400.00;
		Double callSellPrice = 8.85;
		Trade callSell =null;
//		new Trade(new InvestmentOption(under, InvType.CALL, exp, callSellStrike), 
//				TradeType.SELL, quantity, callSellPrice);
		return callSell;
	}

	private static Trade getCallBuy(Underlying under, Integer quantity, Date exp) {
		Double callBuyStrike = 405.00;
		Double callBuyPrice = 7.41;
		Trade callBuy =null;
//				new Trade(new InvestmentOption(under, InvType.CALL, exp, callBuyStrike), 
//				TradeType.BUY, quantity, callBuyPrice);
		return callBuy;
	}

	private DatabaseSystemActivityImpl getLogDB() {
		return logDB;
	}

	private void setLogDB(DatabaseSystemActivityImpl logDB) {
		this.logDB = logDB;
	}

	
}
