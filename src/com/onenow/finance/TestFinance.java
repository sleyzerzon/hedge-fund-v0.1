package com.onenow.finance;

import java.util.Date;

public class TestFinance {

	// MAIN
	public static void main(String[] args) {
		testIronCondor();
		testCallSpread();
		testPutSpread();
	}
	
	// PRIVATE
	private static void testIronCondor() {
		StrategyIronCondor strat = new StrategyIronCondor(new Underlying("AAPL"), 100, new Date(),
				405.00, 7.41,
				400.00, 8.85,
				390.00, 9.5,
				385.00, 8.33);

		System.out.println("\n\n" + strat.toString());
		// profit
		strat.testMaxProfit(261.0);
		strat.testMaxLoss(-239.0);
		// premium
		strat.testCallNetPremium(144.0);
		strat.testPutNetPremium(117.0);
		strat.testBoughtNetPremium(-1574.0);
		strat.testSoldNetPremium(1835.0);
		// value
		strat.testNetValue(375.0, -239.0);
		strat.testNetValue(395.0, 261.0);
		strat.testNetValue(415.0, -239.0);	
		// margin
		strat.testMargin(500.0);
		strat.testNetMargin(239.0);
		// ROI
		strat.testMaxROI(109.20502092050208);
		strat.testRiskReward(91.57088122605364);
		strat.testBiddingOrder(83.55079579755636);
	}
	

	private static void testCallSpread() {
		StrategyCallSpread strat = new StrategyCallSpread(new Underlying("AAPL"), 100, new Date(),
				405.00, 7.41,
				400.00, 8.85);

		System.out.println("\n\n" + strat.toString());
		// profit
		strat.testMaxProfit(144.0);
		strat.testMaxLoss(-356.0);
		// premium
		strat.testCallNetPremium(144.0);
		strat.testPutNetPremium(0.0);
		strat.testBoughtNetPremium(-741.0);
		strat.testSoldNetPremium(885.0);
		// value
		strat.testNetValue(375.0, 144.0);
		strat.testNetValue(395.0, 144.0);
		strat.testNetValue(415.0, -356.0);
		// margin
		strat.testMargin(500.0);
		strat.testNetMargin(356.0);
		// ROI
		strat.testMaxROI(40.44943820224719);
		strat.testRiskReward(247.22222222222229);
		strat.testBiddingOrder(30.947137071857373);
	}
	
	private static void testPutSpread() {
		StrategyPutSpread strat = new StrategyPutSpread(new Underlying("AAPL"), 100, new Date(),
				390.00, 9.5,
				385.00, 8.33);

		System.out.println("\n\n" + strat.toString());
		// profit
		strat.testMaxProfit(117.0);
		strat.testMaxLoss(-383.0);
		// premium
		strat.testCallNetPremium(0.0);
		strat.testPutNetPremium(117.0);
		strat.testBoughtNetPremium(-833.0);
		strat.testSoldNetPremium(950.0);
		// value
		strat.testNetValue(375.0, -383.0);
		strat.testNetValue(395.0, 117.0);
		strat.testNetValue(415.0, 117.0);
		// margin
		strat.testMargin(500.0);
		strat.testNetMargin(383.0);
		// ROI
		strat.testMaxROI(30.548302872062667);
		strat.testRiskReward(327.35042735042737);
		strat.testBiddingOrder(23.37195665283224);		
	}
}
