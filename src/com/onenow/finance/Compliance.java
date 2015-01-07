package com.onenow.finance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Compliance {

	// High ROI right before deadline
	Transaction transaction;
	List<Double> checkpoints;

	// CONSTRUCTOR
	public Compliance(Transaction tx) {
		setTransaction(tx);
		setCheckpoints(new ArrayList<Double>());
		examineStrike();
	}
	
	// PUBLIC
	
	// PRIVATE
	private void examineStrike() {
		Double strike=0.0;
		Double highStrike=0.0;
		Double lowStrike=1000000000000.0;
		Double buffer=0.2;
		for (Trade trade:getTransaction().getTrades()) {
			strike = trade.getStrike();
			getCheckpoints().add(strike);
			if(strike>highStrike) {
				highStrike=strike;
			}
			if(strike<lowStrike){
				lowStrike=strike;
			}
		}
		getCheckpoints().add(highStrike*(1+buffer)); 
		getCheckpoints().add(lowStrike*(1-buffer));		
		getCheckpoints().add((lowStrike+highStrike)/2);		
	}

	// PRINT
	public String toString(Strategy strat) {
		Collections.sort(getCheckpoints());
		String s="COMPLIANCE\n";
		for(int i=0; i<getCheckpoints().size(); i++) {
			Double checkpoint = getCheckpoints().get(i);
			s=s + "Price ($" + checkpoint + "): $" + strat.getNetValue(checkpoint) + "\n";
		}
		System.out.println(""+s);
		return s;
	}

	
	// SET GET 
	private Transaction getTransaction() {
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
