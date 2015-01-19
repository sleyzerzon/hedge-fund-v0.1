package com.onenow.finance;
import java.util.ArrayList;
import java.util.List;

public class Transaction {
		
	private Counterparty counterParty; // Cloud or Brokerage
	private List<Trade> trades = new ArrayList<Trade>();

	
	// CONSTRUCTOR
	public Transaction() {
		
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
	
	public Double probabilityOfProfit() {
		Double prob=0.0;
		
		for(Trade trade:getTrades()) {
			if(	trade.getInvestment().getInvType().equals(InvType.call) || 
				trade.getInvestment().getInvType().equals(InvType.put)) {  
					if(trade.getTradeType().equals(TradeType.SELL)) {
					
						Investment inv = trade.getInvestment();
						prob += ((InvestmentOption) inv).getProbabilityOfProfit();
					}	
			}
		}	
		return prob;
	}
	
	public Double getMargin() { // assumes spreads are transacted
		Double callMargin = getCallSpread()*getCallContracts() ;
		Double putMargin = getPutSpread()*getPutContracts();
		Double margin=0.0;
		if(callMargin>0.0 && putMargin>0.0) { // condor or other balanced wings
			// TODO: cases where call/put don't balance margin
			if(callMargin>putMargin) {
				margin = callMargin;
			} else {
				margin = putMargin;
			}			
		} else {
			margin = callMargin + putMargin;
		}
		return margin;
	}
	
	// PRIVATE
	private Double getCallSpread() { // assumes up to two call
		Double sellCallStrike=getStrike(InvType.call, TradeType.SELL);
		Double buyCallStrike=getStrike(InvType.call, TradeType.BUY);
		Double spread = buyCallStrike - sellCallStrike;
		return spread;
	}

	private Double getPutSpread() { // assumes up to two puts
		Double sellPutStrike=getStrike(InvType.put, TradeType.SELL);
		Double buyPutStrike=getStrike(InvType.put, TradeType.BUY);
		Double spread = sellPutStrike - buyPutStrike;
		return spread;
	}
	
	private Double getStrike(Enum invType, Enum tradeType) {
		Double strike=0.0;
		for(Trade trade:getTrades()) {
			if(trade.getInvestment().getInvType().equals(invType) &&
			   trade.getTradeType().equals(tradeType)) {
					strike = trade.getStrike();
					return strike;
				}
			}
		return strike;
	}
	
	private Integer getCallContracts() { 
		Integer contracts=0;
		for(Trade trade:getTrades()) {
			if(trade.getInvestment().getInvType().equals(InvType.call)) {
				contracts=trade.getQuantity();
			}
		}
		return contracts;
	}
	
	private Integer getPutContracts() {
		Integer contracts=0;
		for(Trade trade:getTrades()) {
			if(trade.getInvestment().getInvType().equals(InvType.put)) {
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
