package com.onenow.finance;

import java.util.Date;

public class TestFinance {

	// MAIN
	public static void main(String[] args) {
		
		Underlying under = new Underlying("AAPL");
		Integer quantity = 100;
		Date exp = new Date();
		
		Trade callBuy = getCallBuy(under, quantity, exp);
		Trade callSell = getCallSell(under, quantity, exp);
		Trade putSell = getPutSell(under, quantity, exp);
		Trade putBuy = getPutBuy(under, quantity, exp);	

		boolean success = 	testIronCondor(callBuy, callSell, putBuy, putSell) &&
							testCallSpread(callBuy, callSell) &&
							testPutSpread(putBuy, putSell) && 
							testMarketAnalytics();
		
		if(success==true) {
			System.out.println("NO ERRORS FOUND AT ALL: " + "TestFinance");
		}

		Sequence seq = new Sequence();
//		StrategyIronCondor condor = new StrategyIronCondor(callBuy, callSell, putBuy, putSell);
//		seq.getStrategies().add(condor);

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
		Trade putBuy = new Trade(	new InvestmentOption(under, InvType.PUT, exp, putBuyStrike), 
							TradeType.BUY, quantity, putBuyPrice);
		return putBuy;
	}

	private static Trade getPutSell(Underlying under, Integer quantity, Date exp) {
		Double putSellStrike = 390.00;
		Double putSellPrice = 9.5;
		Trade putSell = new Trade(	new InvestmentOption(under, InvType.PUT, exp, putSellStrike), 
							TradeType.SELL, quantity, putSellPrice);
		return putSell;
	}

	private static Trade getCallSell(Underlying under, Integer quantity,
			Date exp) {
		Double callSellStrike = 400.00;
		Double callSellPrice = 8.85;
		Trade callSell = new Trade(new InvestmentOption(under, InvType.CALL, exp, callSellStrike), 
				TradeType.SELL, quantity, callSellPrice);
		return callSell;
	}

	private static Trade getCallBuy(Underlying under, Integer quantity, Date exp) {
		Double callBuyStrike = 405.00;
		Double callBuyPrice = 7.41;
		Trade callBuy = new Trade(new InvestmentOption(under, InvType.CALL, exp, callBuyStrike), 
				TradeType.BUY, quantity, callBuyPrice);
		return callBuy;
	}

	
}
