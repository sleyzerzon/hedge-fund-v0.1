package com.onenow.portfolio;
import java.util.ArrayList;
import java.util.List;

import com.onenow.constant.InvType;
import com.onenow.constant.PriceType;
import com.onenow.instrument.Investment;
import com.onenow.instrument.InvOption;

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
			sum+=trade.netPremium;
		}
		return sum;		
	}
	public Double getNetPremium(InvType invType) {
		Double sum=0.0;
		for(Trade trade:getTrades()){
			if(trade.investment.getInvType().equals(invType)){
				sum+=trade.netPremium;
			}
		}
		return sum;		
	}
	
	public Double probabilityOfProfit() {
		Double prob=0.0;
		
		for(Trade trade:getTrades()) {
			if(	trade.investment.getInvType().equals(InvType.CALL) || 
				trade.investment.getInvType().equals(InvType.PUT)) {  
					if(trade.tradeType.equals(PriceType.ASK)) {
					
						Investment inv = trade.investment;
						prob += ((InvOption) inv).getProbabilityOfProfit();
					}	
			}
		}	
		return prob;
	}
	
	public Double getMargin() { // assumes spreads are transacted
		Double callMargin = getCallSpread()*getCallSoldContracts() ;
		Double putMargin = getPutSpread()*getPutSoldContracts();
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
	
	public Double getMaxProfit() {
		Double num = 0.0;
		num = getNetPremium();
		return num;
	}

	public Double getMaxLoss() {
		Double num=0.0;
		num = getMaxProfit()-getMargin();
		return num;
	}

	// PRIVATE
	private Double getCallSpread() { // assumes up to two call
		Double sellCallStrike=getStrike(InvType.CALL, PriceType.ASK);
		Double buyCallStrike=getStrike(InvType.CALL, PriceType.BID);
		Double spread = buyCallStrike - sellCallStrike;
		return spread;
	}

	private Double getPutSpread() { // assumes up to two puts
		Double sellPutStrike=getStrike(InvType.PUT, PriceType.ASK);
		Double buyPutStrike=getStrike(InvType.PUT, PriceType.BID);
		Double spread = sellPutStrike - buyPutStrike;
		return spread;
	}
	
	private Double getStrike(Enum invType, Enum tradeType) {
		Double strike=0.0;
		for(Trade trade:getTrades()) {
			if(trade.investment.getInvType().equals(invType) &&
			   trade.tradeType.equals(tradeType)) {
					strike = trade.getStrike();
					return strike;
				}
			}
		return strike;
	}
	
	private Integer getCallSoldContracts() { 
		Integer contracts=0;
		for(Trade trade:getTrades()) {
			Investment inv = trade.investment;
			if(trade.investment.getInvType().equals(InvType.CALL) &&
			   trade.tradeType.equals(PriceType.ASK)) {
				contracts=trade.quantity;
			}
		}
		return contracts;
	}
	
	private Integer getPutSoldContracts() {
		Integer contracts=0;
		for(Trade trade:getTrades()) {
			if(trade.investment.getInvType().equals(InvType.PUT) &&
			   trade.tradeType.equals(PriceType.ASK)) {
				contracts=trade.quantity;
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
