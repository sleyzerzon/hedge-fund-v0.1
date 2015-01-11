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

	public Double getNetPremium() {
		Double sum=0.0;
		for(Trade trade:getTrades()){
			sum+=trade.getNetPremium();
		}
		return sum;		
	}
	public Double getNetPremium(InvType invType) {
		Double sum=0.0;
		for(Trade trade:getTrades()){
			if(trade.getInvestment().getInvType().equals(invType)){
				sum+=trade.getNetPremium();
			}
		}
		return sum;		
	}
	
	public Double getMargin() { // assumes spreads are transacted
		Double margin=0.0;
		Double callSpread = getCallSpread();
		Double putSpread = getPutSpread();
		
		if(callSpread>putSpread) {
			margin=callSpread*getCallContracts();  
		} else {
			margin=putSpread*getPutContracts();
		}
		return margin;
	}

	public Double probabilityOfProfit() {
		Double prob=0.8;
		return prob;
	}
	
	// PRIVATE
	private Double getCallSpread() { // assumes up to two call
		Double callSpread=0.0;
		Double sellCallStrike=0.0;
		Double buyCallStrike=0.0;
		for(Trade trade:getTrades()) { // put
			if(trade.getInvestment().getInvType().equals(InvType.CALL)) {  
				if(trade.getTradeType().equals(TradeType.SELL)) {
					sellCallStrike = trade.getStrike();
				} else {
					buyCallStrike = trade.getStrike();
				}
			}
		}
		if(sellCallStrike<buyCallStrike) {
			callSpread = buyCallStrike - sellCallStrike;
		}
		return callSpread;
	}

	private Double getPutSpread() { // assumes up to two puts
		Double putSpread=0.0;
		Double sellPutStrike=0.0;
		Double buyPutStrike=0.0;
		for(Trade trade:getTrades()) { // call
			if(trade.getInvestment().getInvType().equals(InvType.PUT)) {  
				if(trade.getTradeType().equals(TradeType.SELL)) {
					sellPutStrike = trade.getStrike();
				} else {
					buyPutStrike = trade.getStrike();
				}
			}
		}
		if(sellPutStrike>buyPutStrike) {
			putSpread = sellPutStrike - buyPutStrike;
		}
		return putSpread;

	}
	
	private Integer getCallContracts() { 
		Integer contracts=0;
		for(Trade trade:getTrades()) {
			if(trade.getInvestment().getInvType().equals(InvType.CALL)) {
				contracts=trade.getQuantity();
			}
		}
		return contracts;
	}
	
	private Integer getPutContracts() {
		Integer contracts=0;
		for(Trade trade:getTrades()) {
			if(trade.getInvestment().getInvType().equals(InvType.PUT)) {
				contracts=trade.getQuantity();
			}			
		}		
		return contracts;		
	}
	
	// PRINT
	public String toString() {
		String s = "";
		for (Trade trade : getTrades()) {
			s = s + trade.toString() + "\n";
		}
		return s;
	}

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
