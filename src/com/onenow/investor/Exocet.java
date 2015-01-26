package com.onenow.investor;

import java.util.Date;

import com.onenow.broker.BrokerActivityImpl;
import com.onenow.finance.InvType;
import com.onenow.finance.InvestmentIndex;
import com.onenow.finance.InvestmentOption;
import com.onenow.finance.Strategy;
import com.onenow.finance.StrategyIronCondor;
import com.onenow.finance.Trade;
import com.onenow.finance.TradeType;
import com.onenow.finance.Underlying;

public class Exocet {
	
	private StrategyIronCondor exocet;
	
	private Integer quant;
	private Underlying under;
	private Double underPrice;
	private Date exp;
	
	private BrokerActivityImpl broker;
	private Double agression;
	private Double spread=5.0; // TODO: generalize
		
	public Exocet() {
		
	}

	public Exocet(Integer quant, Underlying under, Date exp) {
		setQuant(quant);
		setUnder(under);
		setExp(exp);	
	}
	
	public StrategyIronCondor getStrategy(BrokerActivityImpl broker, Double agression) {
		setBroker(broker);
		setAgression(agression);
		InvestmentIndex index = new InvestmentIndex(getUnder());
		Double price = getBroker().getPrice(index, TradeType.LAST); 
		setUnderPrice(price);
		setExocet(new StrategyIronCondor(getCallBuy(), getCallSell(), getPutBuy(), getPutSell()));
		return getExocet();
	}
	
	// PRIVATE
	private Trade getCallBuy() {
		Trade trade = getInv(InvType.call, TradeType.BUY); 	
		return trade;
	}
	private Trade getCallSell() {
		Trade trade = getInv(InvType.call, TradeType.SELL); 			
		return trade;
	}

	private Trade getPutBuy() {
		Trade trade = getInv(InvType.put, TradeType.BUY); 	
		return trade;
	}

	private Trade getPutSell() {
		Trade trade = getInv(InvType.put, TradeType.SELL); 	
		return trade;
	}
	
	private Trade getInv(InvType invType, TradeType tradeType) {
		InvestmentOption inv = new InvestmentOption(getUnder(), invType, getExp(), getStrike(invType, tradeType));
		Trade trade = new Trade(inv, tradeType, getQuant(), getBroker().getBestBid(inv, getAgression()));
		return trade;
	}
	
	private Double getStrike(InvType invType, TradeType tradeType) {
		Double strike=0.0;
		if(invType.equals(InvType.call) && tradeType.equals(TradeType.BUY)) {
			strike = getCallBuyStrike(); 
		}
		if(invType.equals(InvType.call) && tradeType.equals(TradeType.SELL)) {
			strike = getCallSellStrike(); 
		}
		if(invType.equals(InvType.put) && tradeType.equals(TradeType.BUY)) {
			strike = getPutBuyStrike(); 
		}
		if(invType.equals(InvType.put) && tradeType.equals(TradeType.SELL)) {
			strike = getPutSellStrike(); 
		}
		return strike;
	}

	private Double getCallBuyStrike() {
		return getCallSellStrike()+getSpread();
	}
	private Double getCallSellStrike() {
		return getPutSellStrike()+getSpread();
	}

	
	private Double getPutSellStrike() {
		Double strike=0.0;
		Double underp = getUnderPrice();
		Double spr = getSpread();
		Double multDouble = getUnderPrice()/getSpread();
		Integer multInt = multDouble.intValue();
		strike = multInt*spread;
		return strike;
	}
	private Double getPutBuyStrike(){
		return getPutSellStrike()-getSpread();
	}

	
	// PRINT
	public String toString() {
		String s = "";
		s = getExocet().toString();
		return s;
	}
	
	// TEST
	public boolean test() {
		boolean success=true;
		
		setUnderPrice(2051.82);
		
		Double sum = getPutBuyStrike() + getPutSellStrike() +  
				   getCallSellStrike() + getCallBuyStrike(); 
		if(!sum.equals(8205.0)) {
			System.out.println("ERROR strikes " + sum);
			return false;
		} else {
			System.out.println("...strikes PASS");
		}
		
		return success;
	}
	
	// SET GET
	public StrategyIronCondor getExocet() {
		return exocet;
	}

	public void setExocet(StrategyIronCondor exocet) {
		this.exocet = exocet;
	}

	public Integer getQuant() {
		return quant;
	}

	public void setQuant(Integer quant) {
		this.quant = quant;
	}

	public Date getExp() {
		return exp;
	}

	public void setExp(Date exp) {
		this.exp = exp;
	}

	private Underlying getUnder() {
		return under;
	}

	private void setUnder(Underlying under) {
		this.under = under;
	}

	private Double getAgression() {
		return agression;
	}

	private void setAgression(Double agression) {
		this.agression = agression;
	}

	private BrokerActivityImpl getBroker() {
		return broker;
	}

	private void setBroker(BrokerActivityImpl broker) {
		this.broker = broker;
	}

	private Double getUnderPrice() {
		return underPrice;
	}

	private void setUnderPrice(Double underPrice) {
		this.underPrice = underPrice;
	}

	public Double getSpread() {
		return spread;
	}

	public void setSpread(Double spread) {
		this.spread = spread;
	}
	
	
}
