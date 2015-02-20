package com.onenow.finance;

public class StrategyCallBuy extends StrategyOptions {
	
	private Trade callBuy;
	
	public StrategyCallBuy() {
		
	}
	
	public StrategyCallBuy(Trade callBuy) {
		super();
		setCallBuy(callBuy);
		Transaction trans = addTransaction();
		trans.addTrade(getCallBuy()); // add to existing on transaction list
	}
	
	public StrategyCallBuy(Transaction trans, Trade callBuy) {
		setCallBuy(callBuy);
		trans.addTrade(getCallBuy()); 
	}

	
	// PRINT
	public String toString() {
		String s = "";
		s = s + super.toString();
		return s;
	}

	private Trade getCallBuy() {
		return callBuy;
	}

	private void setCallBuy(Trade callBuy) {
		this.callBuy = callBuy;
	}
	

	// SET GET

}
