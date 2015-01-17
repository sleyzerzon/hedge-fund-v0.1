package com.onenow.finance;

import java.util.Date;

public class TestFinance {

	// MAIN
	public static void main(String[] args) {
		
		Underlying under = new Underlying("AAPL");
		Integer quantity = 100;
		Date exp = new Date();
		
		Double callBuyStrike = 405.00;
		Double callBuyPrice = 7.41;
		Double callSellStrike = 400.00;
		Double callSellPrice = 8.85;
		
		Double putSellStrike = 390.00;
		Double putSellPrice = 9.5;
		Double putBuyStrike = 385.00;
		Double putBuyPrice = 8.33;
				
		Trade callBuy = new Trade(new InvestmentOption(under, InvType.CALL, exp, callBuyStrike), 
				TradeType.BUY, quantity, callBuyPrice);
		Trade callSell = new Trade(new InvestmentOption(under, InvType.CALL, exp, callSellStrike), 
				TradeType.SELL, quantity, callSellPrice);

		Trade putSell = new Trade(	new InvestmentOption(under, InvType.PUT, exp, putSellStrike), 
							TradeType.SELL, quantity, putSellPrice);
		Trade putBuy = new Trade(	new InvestmentOption(under, InvType.PUT, exp, putBuyStrike), 
							TradeType.BUY, quantity, putBuyPrice);	

		testIronCondor(callBuy, callSell, putBuy, putSell);
		testCallSpread(callBuy, callSell);
		testPutSpread(putBuy, putSell);
		
		Sequence seq = new Sequence();
//		StrategyIronCondor condor = new StrategyIronCondor(callBuy, callSell, putBuy, putSell);
//		seq.getStrategies().add(condor);

	}
	
	// PRIVATE
	private static boolean testIronCondor(Trade callBuy, Trade callSell, Trade putBuy, Trade putSell) {
		StrategyIronCondor strat = new StrategyIronCondor(callBuy, callSell, putBuy, putSell);
		boolean success = strat.test();
		return success;
	}
	

	private static boolean testCallSpread(Trade callBuy, Trade callSell) {
		StrategyCallSpread strat = new StrategyCallSpread(callBuy, callSell);
		boolean success = strat.test();
		return success;	
		}
	
	private static boolean testPutSpread(Trade putBuy, Trade putSell) {	
		StrategyPutSpread strat = new StrategyPutSpread(putBuy, putSell);
		boolean success = strat.test();
		return success;
	}
}
