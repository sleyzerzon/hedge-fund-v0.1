package com.onenow.finance;

import java.util.Date;

public class TestFinance {

	// TEST
	public static void main(String[] args) {
		StrategyOptions so = new StrategyOptions();
		
		testIronCondor();
	}
	
	// TEST
	private static void testIronCondor() {
		StrategyIronCondor ic = new StrategyIronCondor(new Underlying("AAPL"), 100, new Date(),
				405.00, 7.41,
				400.00, 8.85,
				390.00, 9.5,
				385.00, 8.33);
		
		if(!ic.getCallNetPrice().equals(144.0)) {
			System.out.println("ERROR: call net.");			
		}
		if(!ic.getPutNetPrice().equals(117.0)){
			System.out.println("ERROR: put net.");						
		}
		if(!ic.getBoughtNetPrice().equals(-1574.0)) {
			System.out.println("ERROR: buy net.");			
		}
		if(!ic.getSoldNetPrice().equals(1835.0)){
			System.out.println("ERROR: sell net.");		
		}
		
		if(!ic.getNetValue(375.0).equals(-239.0)) {
			System.out.println("ERROR: put net.");					
		}
		if(!ic.getNetValue(395.0).equals(261.0)) {
			System.out.println("ERROR: in money net.");		
		}
		if(!ic.getNetValue(415.0).equals(-239.0)) {
			System.out.println("ERROR: call net.");		
		}
	}

}
