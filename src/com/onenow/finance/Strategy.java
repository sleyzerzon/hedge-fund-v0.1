package com.onenow.finance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Strategy {
	
	private List<Transaction> transactions;
	private Greeks greeks; // TODO: aggregate only ?
	
	private List<Integer> checkpoints;	
	private Integer presentCheckpoint=0;
	private Integer futureCheckpoint=0;
		
	// CONSTRUCTOR
	public Strategy() {
		setTransactions(new ArrayList<Transaction>());
		setCheckpoints(new ArrayList<Integer>());		
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
		for(Transaction trans:getTransactions()) {		
			max+=trans.getMaxProfit();
		}		
		return max;
	}

	public Double getMaxLoss() { // loss
		Double max=0.0;
		setStrikes();
		for(Transaction trans:getTransactions()) {		
			max+=trans.getMaxLoss();
		}
		return max;
	}
	
	public Double getMaxROI() {
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
		Double bufferPercent=0.005;
		Double bufferAmount=1.0;
		addNewCheckpoint(strike);
		addNewCheckpoint(strike+bufferAmount);
		addNewCheckpoint(strike+2*bufferAmount);
		addNewCheckpoint(strike-bufferAmount);
		addNewCheckpoint(strike-2*bufferAmount);
		addNewCheckpoint(strike*(1+bufferPercent));
		addNewCheckpoint(strike*(1+2*bufferPercent));
		addNewCheckpoint(strike*(1-bufferPercent));
		addNewCheckpoint(strike*(1-2*bufferPercent));
	}
	
	public void addNewCheckpoint(Double num) {
		Integer point = num.intValue();
		if(!getCheckpoints().contains(point)){
			getCheckpoints().add(point);
		}
	}

	
	// PRINT
	public String toString(){
		String s = "";
		s = s + getTransactions().toString() + "\n";
		s = s + "Max Profit: $" + Math.round(getMaxProfit()) + ". " + 
				"Max Loss: $" + Math.round(getMaxLoss()) + "\n";
		s = s + "Margin Required: $" + Math.round(getMargin()) + ". " + 
				"Buying Power (net margin after credit): $" + Math.round(getNetMargin()) + "\n";
		s = s + "Maximum Profit: " + Math.round(getMaxROI()) + "%" + ". " + 
				"Risk/Reward: " + Math.round(getRiskReward()) + "%" + "\n";
		s = s + "Break even price: " + getBreakEven() + "\n";
		s = s + checkpointValueToString();
		s = s + bidOrderToString();
		return(s);
	}

	private String getBreakEven() {
		List<Integer> breakEven = new ArrayList<Integer>();
		Collections.sort(getCheckpoints());
		String s="";
		for(Integer price=getCheckpoints().get(0); 
			price<getCheckpoints().get(getCheckpoints().size()-1); 
			price=price+1) {
			Double val = getNetValue(price*1.0); // convert to double
			if(Math.abs(val)<10) { 
				breakEven.add(price); // multiple for same b/e
			}
		}	
		for(int i=0; i<breakEven.size(); i++) { // reduce multiple to 1
			Integer be = breakEven.get(i);
			if(i==0) {
				s = s + "$" + Math.round(be);
			}
			if(i>0) {
				if(!((be-2.0)<breakEven.get(i-1))) {
					s = s + ", $" + Math.round(be);				
				}
			}
		}
		return s;
	}

	private String checkpointValueToString() {
		String s = "";
		setStrikes();
		Collections.sort(getCheckpoints());
		for(int i=0; i<getCheckpoints().size(); i++) {
			Integer checkpoint = getCheckpoints().get(i);
			Double netVal = getNetValue(checkpoint*1.0);
			s = s + "Profit($" + checkpoint + "): $" + Math.round(netVal);
			Integer range=3;
			Integer max=getPresentCheckpoint()+range;
			Integer min=getPresentCheckpoint()-range;
			if((checkpoint <= max) &&
			   (checkpoint >= min)) {
				if(checkpoint.equals(getFutureCheckpoint())) {
					s = s + "  *";
				}
				s = s + "\t" + "|";
				if(checkpoint.equals(getPresentCheckpoint())){
					s = s + ">";
				}
				Double roi=netVal/getMaxLoss()*100;
				s = s + "\t" + Math.round(-roi) + "% ROI";
			}
			s = s + "\n";
		}
		return s;
	}
	
	private String bidOrderToString() {
		String s = ""; 
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

	private List<Integer> getCheckpoints() {
		return checkpoints;
	}

	private void setCheckpoints(List<Integer> checkpoints) {
		this.checkpoints = checkpoints;
	}

	public Integer getPresentCheckpoint() {
		return presentCheckpoint;
	}

	public void setPresentCheckpoint(Integer keyCheckpoint) {
		this.presentCheckpoint = keyCheckpoint;
	}

	public Integer getFutureCheckpoint() {
		return futureCheckpoint;
	}

	public void setFutureCheckpoint(Integer futureCheckpoint) {
		this.futureCheckpoint = futureCheckpoint;
	}

}
