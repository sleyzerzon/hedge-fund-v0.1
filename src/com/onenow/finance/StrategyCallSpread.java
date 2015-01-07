package com.onenow.finance;

import java.util.Date;

public class StrategyCallSpread extends StrategyOptions {

	private Trade callBuy;
	private Trade callSell;
		
	// CONSTRUCTOR
	public StrategyCallSpread() {
		
	}
		
	public StrategyCallSpread(Underlying under, int quantity, Date exp,
							  Double callBuyStrike, Double callBuyPrice, 
							  Double callSellStrike, Double callSellPrice) {
		super();
		
		setCallBuy(new Trade(new InvestmentOption(under, InvType.CALL, exp, callBuyStrike), 
							TradeType.BUY, quantity, callBuyPrice));
		setCallSell(new Trade(new InvestmentOption(under, InvType.CALL, exp, callSellStrike), 
							TradeType.SELL, quantity, callSellPrice));
		getTransaction().addTrade(getCallBuy());
		getTransaction().addTrade(getCallSell());

	}
	
	// PUBLIC
//	public Double getMaxProfit() {
//		Double maxProfit = getCallNetPrice();
//		return maxProfit;
//	}
//	
//	public Double getMaxLoss() {
//		Double callLoss = getNetValue(getCallBuy().getStrike());
//		return callLoss;
//	}

	
	// PRINT
	public String toString() {
		String s = getCallBuy().toString() + " " + getCallSell().toString();
		return(s);
	}	
	
	// PRIVATE
	private Trade getCallBuy() {
		return callBuy;
	}

	private void setCallBuy(Trade callBuy) {
		this.callBuy = callBuy;
	}

	private Trade getCallSell() {
		return callSell;
	}

	private void setCallSell(Trade callSell) {
		this.callSell = callSell;
	}
	
}
