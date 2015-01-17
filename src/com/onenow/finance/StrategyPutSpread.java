package com.onenow.finance;

import java.util.Date;
import java.util.List;

public class StrategyPutSpread  extends StrategyOptions {

	private Trade putSell;
	private Trade putBuy;

	private Strategy strat; 

	
	// CONSTRUCTOR
	public StrategyPutSpread() {
		
	}
		
	public StrategyPutSpread(Trade putBuy, Trade putSell) {
		super();
		setPutSell(putSell);
		setPutBuy(putBuy);
		Transaction trans = addTransaction();
		trans.addTrade(getPutSell()); // add to existing on transaction list
		trans.addTrade(getPutBuy());
	}

	public StrategyPutSpread(Transaction trans, Trade putBuy, Trade putSell) {
		setPutSell(putSell);
		setPutBuy(putBuy);
		trans.addTrade(getPutSell()); // add to other transaction list
		trans.addTrade(getPutBuy());
	}

	
	// PUBLIC
	
	
	// PRIVATE

	// PRINT
	public String toString() {
		String s = "";
		s = s + super.toString();
		return s;
	}
	
	// TEST
	public boolean test() {
		boolean success = true;
		System.out.println("\n\n" + toString());
		// profit
		testMaxProfit(117.0);
		testMaxLoss(-383.0);
		// premium
		testCallNetPremium(0.0);
		testPutNetPremium(117.0);
		testBoughtNetPremium(-833.0);
		testSoldNetPremium(950.0);
		// value
		testNetValue(375.0, -383.0);
		testNetValue(395.0, 117.0);
		testNetValue(415.0, 117.0);
		// margin
		testMargin(500.0);
		testNetMargin(383.0);
		// ROI
		testMaxROI(30.548302872062667);
		testRiskReward(327.35042735042737);
		// strat.testBiddingOrder(23.37195665283224);
		return success;
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
