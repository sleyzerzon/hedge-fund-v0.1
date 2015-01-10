package com.onenow.finance;

import java.util.Date;

public class StrategyIronCondor extends StrategyOptions {
		
	private Trade callBuy;
	private Trade callSell;
	private Trade putSell;
	private Trade putBuy;
	
	// CONSTRUCTOR
	public StrategyIronCondor() {
		
	}
		
	public StrategyIronCondor(Underlying under, int quantity, Date exp,
							  Double callBuyStrike, Double callBuyPrice, 
							  Double callSellStrike, Double callSellPrice,
							  Double putSellStrike, Double putSellPrice, 
							  Double putBuyStrike, Double putBuyPrice) {
		super();
		
		setCallBuy(	new Trade(new InvestmentOption(under, InvType.CALL, exp, callBuyStrike), 
					TradeType.BUY, quantity, callBuyPrice));
		setCallSell(new Trade(new InvestmentOption(under, InvType.CALL, exp, callSellStrike), 
					TradeType.SELL, quantity, callSellPrice));
		getTransaction().addTrade(getCallBuy());
		getTransaction().addTrade(getCallSell());

		setPutSell(new Trade(	new InvestmentOption(under, InvType.PUT, exp, putSellStrike), 
								TradeType.SELL, quantity, putSellPrice));
		setPutBuy(new Trade(	new InvestmentOption(under, InvType.PUT, exp, putBuyStrike), 
								TradeType.BUY, quantity, putBuyPrice));	
		getTransaction().addTrade(getPutSell());
		getTransaction().addTrade(getPutBuy());
		
	}
	
	// PUBLIC
//	public Double getMaxProfit() {
//		Double maxProfit = getCallNetPrice() + getPutNetPrice();
//		return maxProfit;
//	}
//	
//	public Double getMaxLoss() {
//		Double callLoss = getNetValue(getCallBuy().getStrike());
//		Double putLoss = getNetValue(getPutBuy().getStrike());
//		Double maxLoss;
//		if(Math.abs(callLoss) > Math.abs(putLoss)) {
//			maxLoss=callLoss;
//		} else {
//			maxLoss=putLoss;
//		}
//		return maxLoss;
//	}

	// PRIVATE

	
	// PRINT
	public String toString() {
		String s =	getCallBuy().toString() + getCallSell().toString() + 
					getPutSell().toString() + getPutBuy().toString() +
					super.toString();
		return s;
	}

	// SET GET
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

	private Trade getPutSell() {
		return putSell;
	}

	private void setPutSell(Trade putSell) {
		this.putSell = putSell;
	}

	private Trade getPutBuy() {
		return putBuy;
	}

	private void setPutBuy(Trade putBuy) {
		this.putBuy = putBuy;
	}
	


}
