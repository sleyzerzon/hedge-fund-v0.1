package com.onenow.finance;

import java.util.Date;

public class StrategyPutSpread extends StrategyOptions {

	private Trade putSell;
	private Trade putBuy;
		
	// CONSTRUCTOR
	public StrategyPutSpread() {
		
	}
		
	public StrategyPutSpread(Underlying under, int quantity, Date exp,
							  Double putSellStrike, Double putSellPrice, 
							  Double putBuyStrike, Double putBuyPrice) {
		super();
		
		setPutSell(new Trade(	new InvestmentOption(under, InvType.PUT, exp, putSellStrike), 
								TradeType.SELL, quantity, putSellPrice));
		setPutBuy(new Trade(	new InvestmentOption(under, InvType.PUT, exp, putBuyStrike), 
								TradeType.BUY, quantity, putBuyPrice));	
		getTransaction().addTrade(getPutSell());
		getTransaction().addTrade(getPutBuy());
	}
	
	// PUBLIC
//	public Double getMaxProfit() {
//		Double maxProfit = getPutNetPrice();
//		return maxProfit;
//	}
//	
//	public Double getMaxLoss() {
//		Double putLoss = getNetValue(getPutBuy().getStrike());
//		return putLoss;
//	}

	// PRIVATE

	// PRINT
	public String toString() {
		String s= getPutBuy().toString() + " " + getPutSell().toString();
		return(s);
	}

	
	// SET GET
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
