package com.onenow.finance;

public class StrategyPutBuy extends StrategyOptions {
	
	private Trade putBuy;
	
	public StrategyPutBuy(Trade putBuy) {
		super();
		setPutBuy(putBuy);
		Transaction trans = addTransaction();
		trans.addTrade(getPutBuy()); // add to existing on transaction list
	}
	
	public StrategyPutBuy(Transaction trans, Trade putBuy) {
		setPutBuy(putBuy);
		trans.addTrade(getPutBuy()); // add to other transaction list
	}

	// SET GET
	private Trade getPutBuy() {
		return putBuy;
	}

	private void setPutBuy(Trade putBuy) {
		this.putBuy = putBuy;
	}

}
