package com.onenow.orchestrator;
import java.util.ArrayList;
import java.util.List;

public class TradeTransaction {
		
	private Counterparty counterParty; // Cloud?
	
	private List<Trade> trades;
	
	public TradeTransaction() {
		this.trades = new ArrayList<Trade>();
	}
	
	public void addTrade(Trade trade) {
		getTrades().add(trade);
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
		return (getNetStock()+getNetCall()+getNetPut());
	}
	public Double getNetStock() {
		Double sum=0.0;
		for(Trade trade:getTrades()){
			if(trade.getInvestment().getInvestmentType().equals(InvestmentTypeEnum.STOCK)){
				sum+=trade.getNetStock();
			}
		}
		return sum;
	}
	public Double getNetCall() {
		Double sum=0.0;
		for(Trade trade:getTrades()){
			if(trade.getInvestment().getInvestmentType().equals(InvestmentTypeEnum.CALL)){
				sum+=trade.getNetCall();
				System.out.println("hit..." + trade.getNetCall());
			}
		}
		return sum;
	}
	public Double getNetPut() {
		Double sum=0.0;
		for(Trade trade:getTrades()){
			if(trade.getInvestment().getInvestmentType().equals(InvestmentTypeEnum.PUT)){
				sum+=trade.getNetPut();
			}
		}
		return sum;
	}
	
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
