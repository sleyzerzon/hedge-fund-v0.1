package com.onenow.instrument;

import java.util.ArrayList;
import java.util.List;

public class Sequence {
	
	private List<Strategy> strategies;
	private Double riskTolerance;

	// TODO: act on strategy/transaction based on trigger
	private Trigger trigger; // determines when the transaction is executed
	
	public Sequence() {
		setStrategies(new ArrayList<Strategy>());
		setRiskTolerance(4.0);
	}
	
	// PRINT
	
	// TEST
	public void test() {
//		for(Strategy strat:getStrategies()){
//			strat.test();
//		}
	}
	
	// SET GET
	public List<Strategy> getStrategies() {
		return strategies;
	}

	private void setStrategies(List<Strategy> strategies) {
		this.strategies = strategies;
	}
	
	private Double getRiskTolerance() {
		return riskTolerance;
	}

	private void setRiskTolerance(Double riskTolerance) {
		this.riskTolerance = riskTolerance;
	}

}
