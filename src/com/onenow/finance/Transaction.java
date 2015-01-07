package com.onenow.finance;
import java.util.ArrayList;
import java.util.List;

public class Transaction {
		
	private Counterparty counterParty; // Cloud?
	
	private List<Trade> trades;
	
	// CONSTRUCTOR
	public Transaction() {
		this.trades = new ArrayList<Trade>();
	}
	
	public Transaction(Trade... trades) {
		for(int i=0; i<trades.length; i++) {
			addTrade(trades[i]);
		}
	}
	
	// PUBLIC
	public void addTrade(Trade... trades) {
		for(int i=0; i<trades.length; i++) {
			getTrades().add(trades[i]);
		}
	}
	
	public void delTrade(Trade trade) {
		// implement 
	}

	public String toString() {
		String string = "";
		for (Trade trade : getTrades()) {
			trade.toString();
			string.concat(trade.toString());
		}
		return string;
	}
	
	public Double getNetCost() {
		Double sum=0.0;
		for(Trade trade:getTrades()){
			sum+=trade.getNetCost();
		}
		return sum;		
	}
	public Double getNetCost(InvType invType) {
		Double sum=0.0;
		for(Trade trade:getTrades()){
			if(trade.getInvestment().getInvType().equals(invType)){
				sum+=trade.getNetCost();
			}
		}
		return sum;		
	}
	
//	public Double getMaxProfit() { // TODO: other investment types
//		Double sum=0.0;
//		for(Trade trade:getTrades()){
//			sum += trade.getMaxProfit();
//		}
//		return sum;		
//	}
//
//	public Double getMaxLoss() { // TODO: other investment types
//		Double sum=0.0;
//		for (Trade trade:getTrades()) {
//			sum += trade.getMaxLoss();
//		}
//		return sum;		
//	}

	// PRIVATE
	
	// PRINT
	
	// SET GET
	public List<Trade> getTrades() {
		return this.trades;
	}
	
	public void setTrades(List<Trade> trades) {
		this.trades = trades;
	}

	public Counterparty getCounterParty() {
		return counterParty;
	}

	public void setCounterParty(Counterparty counterParty) {
		this.counterParty = counterParty;
	}


}
