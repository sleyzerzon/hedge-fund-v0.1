package com.onenow.finance;

import java.util.Date;

public class StrategyIronCondor extends StrategyOptions {
	
	Trade callBuy;
	Trade callSell;
	Trade putSell;
	Trade putBuy;
	
	public StrategyIronCondor() {
		
	}
		
	public StrategyIronCondor(Underlying under, int quantity, Date exp,
							  Double callBuyStrike, Double callBuyPrice, 
							  Double callSellStrike, Double callSellPrice,
							  Double putSellStrike, Double putSellPrice, 
							  Double putBuyStrike, Double putBuyPrice) {
		super();
		
		InvestmentOption callBuy = new InvestmentOption(under, InvType.CALL, exp, callBuyStrike);
		InvestmentOption callSell = new InvestmentOption(under, InvType.CALL, exp, callSellStrike);
		InvestmentOption putSell = new InvestmentOption(under, InvType.PUT, exp, putSellStrike);
		InvestmentOption putBuy = new InvestmentOption(under, InvType.PUT, exp, putBuyStrike);
		
		setTrades(	new Trade(callBuy, TradeType.BUY, quantity, callBuyPrice), 
					new Trade(callSell, TradeType.SELL, quantity, callSellPrice),
					new Trade(putSell, TradeType.SELL, quantity, putSellPrice),
					new Trade(putBuy, TradeType.BUY, quantity, putBuyPrice));
		
		testConstructor();
	}

	// PRIVATE
	private void setTrades(	Trade callBuy, Trade callSell,
			  				Trade putSell, Trade putBuy) {   
		setCallBuy(callBuy);
		setCallSell(callSell);
		setPutSell(putSell);
		setPutBuy(putBuy);	
		getTransaction().add(getCallBuy());
		getTransaction().add(getCallSell());
		getTransaction().add(getPutSell());
		getTransaction().add(getPutBuy());
	}
	
	private void testConstructor() {
		
		if( getCallBuy().getStrike() < getCallSell().getStrike() ||
			getPutBuy().getStrike() > getPutSell().getStrike() ){
				System.out.println("ERROR: pricing");
			}
		
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
