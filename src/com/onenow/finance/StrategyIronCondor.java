package com.onenow.finance;

public class StrategyIronCondor extends StrategyOptions {

	private StrategyCallSpread callSpread;
	private StrategyPutSpread putSpread;
	
	// CONSTRUCTOR
	public StrategyIronCondor() {
		
	}
	
	public StrategyIronCondor(	Trade callBuy, Trade callSell,
								Trade putBuy, Trade putSell) {
		super();
		Transaction trans = new Transaction();
		getTransactions().add(trans);
		setCallSpread(new StrategyCallSpread(trans, callBuy, callSell));
		setPutSpread(new StrategyPutSpread(trans, putBuy, putSell));
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
		System.out.println("\n\n" + toString());
		
		return (
			// profit
			testMaxProfit(261.0) &&
			testMaxLoss(-239.0) &&
			// premium
			testCallNetPremium(144.0) &&
			testPutNetPremium(117.0) &&
			testBoughtNetPremium(-1574.0) &&
			testSoldNetPremium(1835.0) &&
			// value
			testNetValue(375.0, -239.0) &&
			testNetValue(395.0, 261.0) &&
			testNetValue(415.0, -239.0) &&	
			// margin
			testMargin(500.0) &&
			testNetMargin(239.0) &&
			// ROI
			testMaxROI(109.20502092050208) &&
			testRiskReward(91.57088122605364)
			// strat.testBiddingOrder(83.55079579755636);
		);		
	}
	
	
	// SET GET
	private StrategyCallSpread getCallSpread() {
		return callSpread;
	}

	private void setCallSpread(StrategyCallSpread callSpread) {
		this.callSpread = callSpread;
	}

	private StrategyPutSpread getPutSpread() {
		return putSpread;
	}

	private void setPutSpread(StrategyPutSpread putSpread) {
		this.putSpread = putSpread;
	}

}
