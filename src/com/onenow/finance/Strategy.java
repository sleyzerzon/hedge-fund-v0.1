package com.onenow.finance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Strategy {
	
	private List<Transaction> transactions;
	private List<Double> checkpoints;	
		
	// CONSTRUCTOR
	public Strategy() {
		setTransactions(new ArrayList<Transaction>());
		setCheckpoints(new ArrayList<Double>());		
	}
	
	// PUBLIC	
	public Transaction addTransaction() {
		Transaction trans = new Transaction();
		getTransactions().add(trans);
		return trans;
	}
		
	public Double getMargin() {
		Double margin=0.0;
		for(Transaction trans:getTransactions()) {
			margin+=trans.getMargin();
		}
		if(margin<0.0) {
			margin=0.0;  
		} 
		return(margin);
	}
	
	public Double getNetMargin() {
		return(getMargin() - getNetPremium());
	}
	
	public Double getNetValue(Double marketPrice) {
		Double net = getNetPremium();  
		for(Transaction trans:getTransactions()) {
			for(Trade trade:trans.getTrades()) {
				net += trade.getValue(marketPrice); 
			}
		}
		return net;
	}
	
	public Double getNetPremium() {
		Double netPremium = 0.0;
		
		for(Transaction trans:getTransactions()) {		
			for (Trade trade:trans.getTrades()) {
				netPremium += trade.getNetPremium();
			}
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
	public Double getBiddingOrder(Enum rewardAlgo) {
		Double order=0.0;
		// TODO setBiddingOrder();
//		order = getReward().getAlgoOrder(rewardAlgo);
		return order;
	}
	
//	private Reward getReward() {
//
//	}
	


	// PRIVATE
	private void getBiddingOrder(){
		// TODO
	}

	private void setStrikes() {
		Double strike=0.0;
		for(Transaction trans:getTransactions()) {
			for (Trade trade:trans.getTrades()) {
				strike = trade.getStrike();
				setStrikeVariants(strike);
			}
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
		s = s + getTransactions().toString() + "\n";
		s = s + "Max Profit: $" + getMaxProfit() + ". " + 
				"Max Loss: $" + getMaxLoss() + "\n";
		s = s + "Margin Required: $" + getMargin().intValue() + ". " + 
				"Buying Power (net margin after credit): $" + getNetMargin().intValue() + "\n";
		s = s + "Maximum Profit: " + getMaxROI().intValue() + "%" + ". " + 
				"Risk/Reward: " + getRiskReward().intValue() + "%" + "\n";
		s = s + checkpointValueToString();
		s = s + bidOrderToString();
		return(s);
	}

	private String checkpointValueToString() {
		String s = "";
		setStrikes();
		Collections.sort(getCheckpoints());
		for(int i=0; i<getCheckpoints().size(); i++) {
			Double checkpoint = getCheckpoints().get(i);
			s = s + "Profit($" + checkpoint.intValue() + "): $" + getNetValue(checkpoint).intValue() + "\n";
		}
		return s;
	}
	
	private String bidOrderToString() {
		String s = ""; // intValue
		s = s + "BIDDING SCORE ";
		s = s + RewardAlgo.Linear + ": " + getBiddingOrder(RewardAlgo.Linear).intValue() + ". " +
				RewardAlgo.Success + ": " + getBiddingOrder(RewardAlgo.Success).intValue() + ". " +
				RewardAlgo.ROI + ": " + getBiddingOrder(RewardAlgo.ROI).intValue() + "\n";
		return s;
	}
	
	// TEST 
	public boolean testMaxProfit(Double num) {
		if(!getMaxProfit().equals(num)) {
			System.out.println("ERROR: (t0) max profit $" + getMaxProfit());
			return false;
		}
		return true;
	}
	public boolean testMaxLoss(Double num) {
		if(!getMaxLoss().equals(num)) {
			System.out.println("ERROR: (te) max loss $" + getMaxLoss());
			return false;
		}
		return true;
	}	
	public boolean testNetValue(Double price, Double value) {
		if(!getNetValue(price).equals(value)) {
			System.out.println("ERROR net $" + getNetValue(375.0));
			return false;
		}		
		return true;
	}
	public boolean testMargin(Double num) {
		if(!getMargin().equals(num)) {
			System.out.println("ERROR: margin $" + getMargin());
			return false;
		}
		return true;
	}
	public boolean testNetMargin(Double num) {
		if(!getNetMargin().equals(num)) {
			System.out.println("ERROR: net margin $" + getNetMargin());
			return false;
		}
		return true;
	}
	public boolean testMaxROI(Double num) {
		if(!getMaxROI().equals(num)) {
			System.out.println("ERROR: max ROI %" + getMaxROI()); 
			return false;
		}
		return true;
	}
	public boolean testRiskReward(Double num) {
		if(!getRiskReward().equals(num)) {
			System.out.println("ERROR: (te) max loss $" + getRiskReward());
			return false;
		}	
		return true;
	}
	public boolean testBiddingOrder(Double num, Enum rewardAlgo) {
//		if(!getBiddingOrder(Enum rewardAlgo).equals(num)) {
//			System.out.println("ERROR: (te) max loss $" + getBiddingOrder(rewardAlgo));
//			return false;
//		}		
		return true;
	}


	// SET GET
	public List<Transaction> getTransactions() {
		return transactions;
	}

	private void setTransactions(ArrayList<Transaction> transactions) {
		this.transactions = transactions;
		}

	private List<Double> getCheckpoints() {
		return checkpoints;
	}

	private void setCheckpoints(List<Double> checkpoints) {
		this.checkpoints = checkpoints;
	}

}
