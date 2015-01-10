package com.onenow.finance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Compliance {

	// High ROI right before deadline
	private Strategy strategy;
	private List<Double> checkpoints;
	Double maxProfit;
	Double maxLoss;

	// CONSTRUCTOR
	public Compliance(Strategy strat) {
		setStrategy(strat);
		setCheckpoints(new ArrayList<Double>());
		Double largeNum = 99999999.99;
		setMaxProfit(-largeNum);
		setMaxLoss(largeNum);
		getStrikes();
	}
	
	// PUBLIC
	
	// PRIVATE
	private void getStrikes() {
		Double strike=0.0;
		for (Trade trade:getStrategy().getTransaction().getTrades()) {
			strike = trade.getStrike();
			setVariants(strike);
		}
	}

	private void setVariants(Double strike) { // explore Net around strikes
		Double buffer=0.025;
		getCheckpoints().add(strike);
		setMaxProfitLoss(strike);
		Double sPlus = strike*(1+buffer);
		getCheckpoints().add(sPlus);
		setMaxProfitLoss(sPlus);
		Double sMinus = strike*(1-buffer);
		getCheckpoints().add(sMinus);
		setMaxProfitLoss(sMinus);
	}
	
	private void setMaxProfitLoss(Double price) {
		Double net = getStrategy().getNetValue(price);
		if(net > getMaxProfit()) {
			setMaxProfit(net);
		}
		if(net < getMaxLoss()) {
			setMaxLoss(net);
		}
	}

	private Double getMaxROI() {
		return (Math.abs(getMaxProfit()/getMaxLoss())*100);
	}
	
	private Double getRiskReward() {
		return((1/(getMaxROI()/100))*100);
	}

	// PRINT
	public String toString() {
		Collections.sort(getCheckpoints());
		String s="COMPLIANCE\n";
		for(int i=0; i<getCheckpoints().size(); i++) {
			Double checkpoint = getCheckpoints().get(i);
			s = s + "Profit($" + checkpoint.intValue() + "): $" + getStrategy().getNetValue(checkpoint).intValue() + "\n";
		}
		s = s + "Max Profit: $" + getMaxProfit() + " " + 
				"Max Loss: $" + getMaxLoss() + "\n";
		s = s + "Margin Required: $" + getStrategy().getMargin().intValue() + " " + 
				"Buying Power (net margin after credit): $" + getStrategy().getNetMargin().intValue() + "\n";
		s = s + "Maximum Profit: " + getMaxROI().intValue() + "% " + 
				"Risk/Reward: " + getRiskReward().intValue() + "%" + "\n";
		System.out.println(s+"\n\n");
		return s;
	}

	
	// SET GET 
	private List<Double> getCheckpoints() {
		return checkpoints;
	}

	private void setCheckpoints(List<Double> checkpoints) {
		this.checkpoints = checkpoints;
	}

	private Strategy getStrategy() {
		return strategy;
	}

	private void setStrategy(Strategy strategy) {
		this.strategy = strategy;
	}

	private Double getMaxProfit() {
		return maxProfit;
	}

	private void setMaxProfit(Double maxProfit) {
		this.maxProfit = maxProfit;
	}

	private Double getMaxLoss() {
		return maxLoss;
	}

	private void setMaxLoss(Double maxLoss) {
		this.maxLoss = maxLoss;
	}
	
}
