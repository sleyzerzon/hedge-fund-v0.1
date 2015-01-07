package com.onenow.finance;

import java.util.Date;

public class TestFinance {

	// MAIN
	public static void main(String[] args) {
		StrategyOptions so = new StrategyOptions();
		
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

		strat.toString();
		
//		if(!strat.getTransaction().getMaxProfit().equals(261.0)) {
//			System.out.println("ERROR: (t0) max profit.");
//		}
//		if(!strat.getMaxLoss().equals(-239.0)) {
//			System.out.println("ERROR: (te) max loss.");
//		}
		if(!strat.getCallNetPrice().equals(144.0)) {
			System.out.println("ERROR call net $" + strat.getCallNetPrice());			
		}
		if(!strat.getPutNetPrice().equals(117.0)){
			System.out.println("ERROR put net $" + strat.getPutNetPrice());						
		}
		if(!strat.getBoughtNetPrice().equals(-1574.0)) {
			System.out.println("ERROR buy net $" + strat.getBoughtNetPrice());			
		}
		if(!strat.getSoldNetPrice().equals(1835.0)){
			System.out.println("ERROR sell net $" + strat.getSoldNetPrice());		
		}
		
		if(!strat.getNetValue(375.0).equals(-239.0)) {
			System.out.println("ERROR net $" + strat.getNetValue(375.0));					
		}
		if(!strat.getNetValue(395.0).equals(261.0)) {
			System.out.println("ERROR in money net $" + strat.getNetValue(395.0));		
		}
		if(!strat.getNetValue(415.0).equals(-239.0)) {
			System.out.println("ERROR: net $" + strat.getNetValue(415.0));		
		}
		
		Compliance comp = new Compliance(strat.getTransaction());
		comp.toString(strat);
		
	}

	private static void testCallSpread() {
		StrategyCallSpread strat = new StrategyCallSpread(new Underlying("AAPL"), 100, new Date(),
				405.00, 7.41,
				400.00, 8.85);

		strat.toString();
		
//		if(!strat.getTransaction().getMaxProfit().equals(261.0)) {
//			System.out.println("ERROR: (t0) max profit.");
//		}
//		if(!strat.getMaxLoss().equals(-239.0)) {
//			System.out.println("ERROR: (te) max loss.");
//		}
		if(!strat.getCallNetPrice().equals(144.0)) {
			System.out.println("ERROR call net $" + strat.getCallNetPrice());			
		}
		if(!strat.getPutNetPrice().equals(0.0)){
			System.out.println("ERROR put net $" + strat.getPutNetPrice());						
		}
		if(!strat.getBoughtNetPrice().equals(-741.0)) {
			System.out.println("ERROR buy net $" + strat.getBoughtNetPrice());			
		}
		if(!strat.getSoldNetPrice().equals(885.0)){
			System.out.println("ERROR sell net $" + strat.getSoldNetPrice());		
		}
		
		if(!strat.getNetValue(375.0).equals(144.0)) {
			System.out.println("ERROR net $" + strat.getNetValue(375.0));					
		}
		if(!strat.getNetValue(395.0).equals(144.0)) {
			System.out.println("ERROR in money net $" + strat.getNetValue(395.0));		
		}
		if(!strat.getNetValue(415.0).equals(-356.0)) {
			System.out.println("ERROR: net $" + strat.getNetValue(415.0));		
		}

		Compliance comp = new Compliance(strat.getTransaction());
		comp.toString(strat);
		
	}
	
	private static void testPutSpread() {
		StrategyPutSpread strat = new StrategyPutSpread(new Underlying("AAPL"), 100, new Date(),
				390.00, 9.5,
				385.00, 8.33);

		strat.toString();
		
//		if(!strat.getTransaction().getMaxProfit().equals(261.0)) {
//			System.out.println("ERROR: (t0) max profit.");
//		}
//		if(!strat.getMaxLoss().equals(-239.0)) {
//			System.out.println("ERROR: (te) max loss.");
//		}
		if(!strat.getCallNetPrice().equals(0.0)) {
			System.out.println("ERROR call net $" + strat.getCallNetPrice());			
		}
		if(!strat.getPutNetPrice().equals(117.0)){
			System.out.println("ERROR put net $" + strat.getPutNetPrice());						
		}
		if(!strat.getBoughtNetPrice().equals(-833.0)) {
			System.out.println("ERROR buy net $" + strat.getBoughtNetPrice());			
		}
		if(!strat.getSoldNetPrice().equals(950.0)){
			System.out.println("ERROR sell net $" + strat.getSoldNetPrice());		
		}
		
		if(!strat.getNetValue(375.0).equals(-383.0)) {
			System.out.println("ERROR net $" + strat.getNetValue(375.0));					
		}
		if(!strat.getNetValue(395.0).equals(117.0)) {
			System.out.println("ERROR in money net $" + strat.getNetValue(395.0));		
		}
		if(!strat.getNetValue(415.0).equals(117.0)) {
			System.out.println("ERROR: net $" + strat.getNetValue(415.0));		
		}
		
		Compliance comp = new Compliance(strat.getTransaction());
		comp.toString(strat);

	}
}
