package com.onenow.finance;

public class StrategyCallSpread extends StrategyOptions {

	private Trade callBuy;
	private Trade callSell;

	// CONSTRUCTOR
	public StrategyCallSpread() {
		
	}
	public StrategyCallSpread(Trade callBuy, Trade callSell) {
		super();
		setCallBuy(callBuy);
		setCallSell(callSell);
		Transaction trans = addTransaction();
		trans.addTrade(getCallBuy()); // add to existing on transaction list
		trans.addTrade(getCallSell());
	}
	
	public StrategyCallSpread(Transaction trans, Trade callBuy, Trade callSell) {
		setCallBuy(callBuy);
		setCallSell(callSell);
		trans.addTrade(getCallBuy()); // add to other transaction list
		trans.addTrade(getCallSell());
	}
	
				
	// PUBLIC
	
	// PRINT
	public String toString() {
		String s = "";
		s = s + super.toString();
		return s;
	}	
	
	// TEST
	public boolean test() {
		System.out.println("\n\n" + toString());
		return(
			// profit
			testMaxProfit(144.0) && 
			testMaxLoss(-356.0) &&
			// premium
			testCallNetPremium(144.0) &&
			testPutNetPremium(0.0) &&
			testBoughtNetPremium(-741.0) &&
			testSoldNetPremium(885.0) &&
			// value
			testNetValue(375.0, 144.0) &&
			testNetValue(395.0, 144.0) &&
			testNetValue(415.0, -356.0) &&
			// margin
			testMargin(500.0) &&
			testNetMargin(356.0) &&
			// ROI
			testMaxROI(40.44943820224719) &&
			testRiskReward(247.22222222222229)
	//		// strat.testBiddingOrder(30.947137071857373);
				);
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
