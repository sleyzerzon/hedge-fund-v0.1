package com.onenow.finance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Strategy {
	
	private Transaction transaction;
	private List<Double> checkpoints;

	// APPROACH: Iron Condor
	// 1) LOOK FOR THE STRIKE SPREAD TO BE SMALLER THAN THE PRICING GAP
	// 2) FOR MAX NETom: Pcs+Pcb >> Pcs+Ppb
	// P: price
	// S: strike
	// c: call
	// p: put
	// b: bought
	// s: sold
	// om: out of money
	// im: in the money
	//
	// NETom = 100x(Pcs+Pps-Pcb-Ppb)  ... @start, out of money
	//
	// out of money--
	// NETcom = 100x(Pcs-Pcb) ... P<Scs
	// NETpom = 100x(Pps-Ppb) ... P>Sps
	//
	// in the money, calls--
	// NETcb = -100xPcb + 100x(P-Scb)
	// NETcs = 100xPcs + 100x(Scs-P)
	//
	// to avoid loss--
	// NETcb+NETcs > 0
	// thus,
	// 100x(Pcs-Pcb+Scs-Scb) > 0
	// in other words,
	// Pcs-Pcb > Scb-Scs	****** SEARCH FOR THIS: calls ******
	// Pps-Ppb > Sps-Spb   ****** SEARCH FOR THIS: puts ******
	
	
	// CONSTRUCTOR
	public Strategy() {
		setTransaction(new Transaction());
		
		setCheckpoints(new ArrayList<Double>());
//		Double largeNum = 99999999.99;
//		setMaxProfit(-largeNum);
//		setMaxLoss(largeNum);
	}
	
	// PUBLIC	
	public Double getMargin() {
		return(getTransaction().getMargin());
	}
	
	public Double getNetMargin() {
		return(getMargin() - getNetPremium());
	}
	
	public Double getNetValue(Double marketPrice) {
		Double net = getNetPremium();
		for(Trade trade:getTransaction().getTrades()) {
			net += trade.getValue(marketPrice); 
		}
		return net;
	}
	
	public Double getNetPremium() {
		Double netPremium = 0.0;
		for (Trade trade:getTransaction().getTrades()) {
			netPremium += trade.getNetPremium();
		}
		return  netPremium;
	}
	
	public boolean isCreditStrategy() {
		if(getNetPremium()>0.0) { 
			return true;
		}
		return false;
	}
	
	public boolean isDebitStrategy() {
		return (!isCreditStrategy());
	}

	
	public Double getMaxProfit() { // profit
		Double max=0.0;
		setStrikes();
		for(Double price:getCheckpoints()) {
			Double net=getNetValue(price);
			if(net>max) {
				max=net;
			}
		}
		return max;
	}

	public Double getMaxLoss() { // loss
		Double max=0.0;
		setStrikes();
		for(Double price:getCheckpoints()) {
			Double net=getNetValue(price);
			if(net<max) {
				max=net;
			}
		}		
		return max;
	}
	
	private Double getMaxROI() {
		return (Math.abs(getMaxProfit()/getMaxLoss())*100);
	}
	
	private Double getRiskReward() {
		return((1/(getMaxROI()/100))*100);
	}

	// Use bidding algorithm to determine order of execution of this strategy
	public Double biddingOrder(){
		Reward rew = new Reward();
		Double order = rew.successBias(getTransaction().probabilityOfProfit(), getMaxROI());
		return order;
	}

	// PRIVATE
	private void setStrikes() {
		Double strike=0.0;
		for (Trade trade:getTransaction().getTrades()) {
			strike = trade.getStrike();
			setStrikeVariants(strike);
		}
	}

	private void setStrikeVariants(Double strike) { // explore Net around strikes
		Double buffer=0.025;
		addNewCheckpoint(strike);
		addNewCheckpoint(strike*(1+buffer));
		addNewCheckpoint(strike*(1-buffer));
	}
	
	private void addNewCheckpoint(Double num) {
		if(!getCheckpoints().contains(num)){
			getCheckpoints().add(num);
		}
	}

	
	// PRINT
	public String toString(){
		String s = "";
		s = s + getTransaction().toString();
		s = s + "Max Profit: $" + getMaxProfit() + ". " + 
				"Max Loss: $" + getMaxLoss() + "\n";
		s = s + "Margin Required: $" + getMargin().intValue() + ". " + 
				"Buying Power (net margin after credit): $" + getNetMargin().intValue() + "\n";
		s = s + "Maximum Profit: " + getMaxROI().intValue() + "%" + ". " + 
				"Risk/Reward: " + getRiskReward().intValue() + "%" + "\n";
		s = s + "Bidding Order: " + biddingOrder().intValue() + "\n";
		s = s + getCheckpointValue();
		return(s);
	}

	private String getCheckpointValue() {
		String s = "";
		setStrikes();
		Collections.sort(getCheckpoints());
		for(int i=0; i<getCheckpoints().size(); i++) {
			Double checkpoint = getCheckpoints().get(i);
			s = s + "Profit($" + checkpoint.intValue() + "): $" + getNetValue(checkpoint).intValue() + "\n";
		}
		return s;
	}
	
	// TEST 
	public void testMaxProfit(Double num) {
		if(!getMaxProfit().equals(num)) {
			System.out.println("ERROR: (t0) max profit $" + getMaxProfit());
		}
	}
	public void testMaxLoss(Double num) {
		if(!getMaxLoss().equals(num)) {
			System.out.println("ERROR: (te) max loss $" + getMaxLoss());
		}
	}	
	public void testNetValue(Double price, Double value) {
		if(!getNetValue(price).equals(value)) {
			System.out.println("ERROR net $" + getNetValue(375.0));					
		}		
	}
	public void testMargin(Double num) {
		if(!getMargin().equals(num)) {
			System.out.println("ERROR: margin $" + getMargin());
		}
	}
	public void testNetMargin(Double num) {
		if(!getNetMargin().equals(num)) {
			System.out.println("ERROR: net margin $" + getNetMargin());
		}
	}
	public void testMaxROI(Double num) {
		if(!getMaxROI().equals(num)) {
			System.out.println("ERROR: max ROI %" + getMaxROI());
		}
	}
	public void testRiskReward(Double num) {
		if(!getRiskReward().equals(num)) {
			System.out.println("ERROR: (te) max loss $" + getRiskReward());
		}	
	}
	public void testBiddingOrder(Double num) {
		if(!biddingOrder().equals(num)) {
			System.out.println("ERROR: (te) max loss $" + biddingOrder());
		}		
	}


	// SET GET
	public Transaction getTransaction() {
		return transaction;
	}

	private void setTransaction(Transaction transaction) {
		this.transaction = transaction;
		}

	private List<Double> getCheckpoints() {
		return checkpoints;
	}

	private void setCheckpoints(List<Double> checkpoints) {
		this.checkpoints = checkpoints;
	}

}
