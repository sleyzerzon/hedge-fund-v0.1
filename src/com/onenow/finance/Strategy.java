package com.onenow.finance;

import java.util.ArrayList;
import java.util.List;

public class Strategy {
	
	private List<Trade> transaction;

	// CONSTRUCTOR
	public Strategy() {
		setTransaction(new ArrayList<Trade>());
	}
	
	// PUBLIC	
	public Double getNetValue(Double marketPrice) {
		Double net = 0.0;
		for(Trade trade:getTransaction()) {
			Double price = trade.getNetCost();
			Double value = trade.getValue(marketPrice);
			net += price+value;
		}
		System.out.println("NET VALUE ($" + marketPrice + "): $"+ net);
		return net;
	}
	
	// PRIVATE
	private Double getNetPrice() {
		Double net = 0.0;
		for(Trade trade:getTransaction()) {
			net += trade.getNetCost();
		}				
		return net;
	}


	// SET GET
	public List<Trade> getTransaction() {
		return transaction;
	}

	private void setTransaction(List<Trade> transaction) {
		this.transaction = transaction;
	}


}
