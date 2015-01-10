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
	Double callSpread;
	Double putSpread;
	Double margin;
	Double maxROI;

	// CONSTRUCTOR
	public Compliance(Strategy strat) {
		setStrategy(strat);
		setCheckpoints(new ArrayList<Double>());
		Double largeNum = 99999999.99;
		setMaxProfit(-largeNum);
		setMaxLoss(largeNum);
		getStrikes();
		setCallSpread(0.0);
		setPutSpread(0.0);
		setMargin(0.0);
		setMaxROI(0.0);
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

	private void setVariants(Double strike) {
		Double buffer=0.2;
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


	// PRINT
	public String toString() {
		Collections.sort(getCheckpoints());
		String s="COMPLIANCE\n";
		for(int i=0; i<getCheckpoints().size(); i++) {
			Double checkpoint = getCheckpoints().get(i);
			s = s + "Price ($" + checkpoint + "): $" + getStrategy().getNetValue(checkpoint) + "\n";
		}
		s = s + "MAX PROFIT: $" + getMaxProfit() + " " + "MAX LOSS: $" + getMaxLoss() + "\n";
		System.out.println(s);
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

	private Double getCallSpread() {
		return callSpread;
	}

	private void setCallSpread(Double callSpread) {
		this.callSpread = callSpread;
	}

	private Double getPutSpread() {
		return putSpread;
	}

	private void setPutSpread(Double putSpread) {
		this.putSpread = putSpread;
	}

	private Double getMargin() {
		return margin;
	}

	private void setMargin(Double margin) {
		this.margin = margin;
	}

	private Double getMaxROI() {
		return maxROI;
	}

	private void setMaxROI(Double maxROI) {
		this.maxROI = maxROI;
	}
	
}
