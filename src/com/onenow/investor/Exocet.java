package com.onenow.investor;

import java.util.Date;

import com.onenow.broker.BrokerActivityImpl;
import com.onenow.finance.InvProb;
import com.onenow.finance.InvType;
import com.onenow.finance.InvestmentIndex;
import com.onenow.finance.InvestmentOption;
import com.onenow.finance.Strategy;
import com.onenow.finance.StrategyCallSpread;
import com.onenow.finance.StrategyIronCondor;
import com.onenow.finance.StrategyPutSpread;
import com.onenow.finance.Trade;
import com.onenow.finance.TradeType;
import com.onenow.finance.Underlying;

public class Exocet {
	
	private Strategy exocet;
	
	private Integer quant;
	private Underlying under;
	private Double underPrice;
	private Date exp;
	
	private BrokerActivityImpl broker;
	private Double agression;
	private Double spread=5.0; // TODO: generalize
	
	private InvProb prob;
	private TradeRatio ratio;
		
	public Exocet() {
		
	}
	
	public Exocet(Integer quant, Underlying under, Date exp) {
		setQuant(quant);
		setUnder(under);
		setExp(exp);	
	}
	
	public StrategyCallSpread getCallSpread(	InvProb prob, TradeRatio ratio,
												BrokerActivityImpl broker, Double agression) {
		setProb(prob);
		setRatio(ratio);
		setBroker(broker);
		setAgression(agression);
		InvestmentIndex index = new InvestmentIndex(getUnder());
		Double price = getBroker().getPrice(index, TradeType.LAST); 
		setUnderPrice(price);
		setExocet(new StrategyCallSpread(getCallBuy(), getCallSell()));
		return (StrategyCallSpread) getExocet();
	}
	
	public StrategyPutSpread getPutSpread(	InvProb prob, TradeRatio ratio,
												BrokerActivityImpl broker, Double agression) {
		setProb(prob);
		setRatio(ratio);
		setBroker(broker);
		setAgression(agression);
		InvestmentIndex index = new InvestmentIndex(getUnder());
		Double price = getBroker().getPrice(index, TradeType.LAST); 
		setUnderPrice(price);
		setExocet(new StrategyPutSpread(getPutBuy(), getPutSell()));
		return (StrategyPutSpread) getExocet();	
	}
	
	public StrategyIronCondor getIronCondor(	InvProb prob, TradeRatio ratio, 
												BrokerActivityImpl broker, Double agression) {
		setProb(prob);
		setRatio(ratio);
		setBroker(broker);
		setAgression(agression);
		InvestmentIndex index = new InvestmentIndex(getUnder());
		Double price = getBroker().getPrice(index, TradeType.LAST); 
		setUnderPrice(price);
		setExocet(new StrategyIronCondor(	getCallBuy(), getCallSell(), 
											getPutBuy(), getPutSell()));
		return (StrategyIronCondor) getExocet();
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
		
		InvestmentOption inv = 	new InvestmentOption(getUnder(), invType, getExp(), 
								getStrike(invType, tradeType));
		Integer quant = getQuant();
		if(tradeType.equals(TradeType.BUY)) { // ratio protection
			if(getRatio().equals(TradeRatio.low)){
				quant = (int) Math.round(quant*1.25);				
			}
			if(getRatio().equals(TradeRatio.mid)){
				quant = (int) Math.round(quant*1.5);				
			}
			if(getRatio().equals(TradeRatio.high)){
				quant = (int) Math.round(quant*2);				
			}
			if(getRatio().equals(TradeRatio.vhigh)){
				quant = (int) Math.round(quant*3);				
			}
		}
		Trade trade = new Trade(inv, tradeType, quant, getBroker().getBestBid(tradeType, inv, getAgression()));
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
	
	// CALL SELL
	private Double getCallSellStrike() {
		Double strike=0.0;
		if(getProb().equals(InvProb.high)) { strike=getHPCallSellStrike(); }
		if(getProb().equals(InvProb.mid)) { strike=getMPCallSellStrike(); }
		if(getProb().equals(InvProb.low)) { strike=getLPCallSellStrike(); }
		if(getProb().equals(InvProb.strangle)) { strike=getSTCallSellStrike(); }
		return strike;
	}
	private Double getHPCallSellStrike() {
		Double strike = 0.0;
		strike = getStrikeAboveClosing() + 2*getSpread(); // TODO: do based on delta
		return strike;
	}
	private Double getMPCallSellStrike() {
		Double strike = getStrikeAboveClosing(); // TODO: no extra buffer?
		return strike;
	}
	private Double getLPCallSellStrike() {
		Double strike=0.0;
		strike = getStrikeAboveClosing();
		return strike;
	}
	private Double getSTCallSellStrike() {
		Double strike = 0.0;
		strike = getStrikeAboveClosing()-getSpread();
		return strike;
	}

	// PUT SELL
	private Double getPutSellStrike() {
		Double strike=0.0;
		if(getProb().equals(InvProb.high)) { strike=getHPPutSellStrike(); }
		if(getProb().equals(InvProb.mid)) { strike=getMPPutSellStrike(); }
		if(getProb().equals(InvProb.low)) { strike=getLPPutSellStrike(); }
		if(getProb().equals(InvProb.strangle)) { strike=getSTPutSellStrike(); }
		return strike;
	}
	private Double getHPPutSellStrike() {
		Double strike = 0.0;
		strike = getMPPutSellStrike()-getSpread(); // TODO: do based on delta
		return strike;
	}
	private Double getMPPutSellStrike() {
		Double strike = getLPPutSellStrike()-getSpread();
		return strike;
	}
	private Double getLPPutSellStrike() {
		Double strike=0.0;
		strike = getStrikeBelowClosing();
		return strike;
	}
	private Double getSTPutSellStrike() {
		Double strike = 0.0;
		strike = getStrikeBelowClosing()+getSpread();
		return strike;
	}

	private Double getPutBuyStrike(){
		return getPutSellStrike()-getSpread();
	}
	
	private Double getStrikeBelowClosing() {
		Double margin=getSpread()/2; // with down-side buffer: profit 25%
		Double mult = (double) Math.round((estClosing()-margin)/getSpread());
		Double strike = mult*spread; // OTM 
		return strike;
	}
	private Double getStrikeAboveClosing() {
		Double strike=getStrikeBelowClosing()+getSpread();
		return strike;
	}

	private Double estClosing() {
		// TODO: use actuals
		Double priceOpen=2063.15;
		Double priceNow=getUnderPrice(); // 2054.74
		Double delta=priceNow-priceOpen;
		Double hsOpen=6.5;
		Double velocity=delta/hsOpen;
		Double hsLeft=0.50;
		Double estClosing=priceNow+velocity*hsLeft; 
//		System.out.println(	"***EST CLOSING " + Math.round(estClosing) + 
//							"***BAKED CLOSING " + Math.round(bakedClosing()));		
		return estClosing;
	}
	private Double bakedClosing() { // based on ITM option mid value
		Integer separation=10; // TODO: increase
		Double callStrike = Math.round(getUnderPrice()/getSpread())*getSpread()-separation; 
		Double putStrike =  callStrike + 2*separation;
		// look at extremes
		InvestmentOption putExt = new InvestmentOption(getUnder(), InvType.put, getExp(), putStrike);
		Double putMid = getBroker().getBestBid(TradeType.BUY, putExt, 0.50);
		Double estClosingPut = putStrike-putMid;
		InvestmentOption callExt = new InvestmentOption(getUnder(), InvType.call, getExp(), callStrike);
		Double callMid = getBroker().getBestBid(TradeType.BUY, callExt, 0.50);	
		Double estClosingCall = callStrike+callMid;
		
		Double estClosing = (estClosingPut+estClosingCall)/2;
		return estClosing;
	}

	
	// PRINT
	public String toString() {
		String s = "\n\n";
		s = s + "***EXOCET" + "\n";
		s = s + "PROBABILITY " + getProb() + ", " +
				"RATIO " + getRatio() + ", " +
				"AGGRESSION " + getAgression() + "\n";
		s = s + getExocet().toString();
		return s;
	}
	
	// TEST
	public boolean test() {
		boolean success=true;
		
//		setUnderPrice(2051.82);
//		
//		Double sum = getPutBuyStrike() + getPutSellStrike() +  
//				   getCallSellStrike() + getCallBuyStrike(); 
//		if(!sum.equals(8205.0)) {
//			System.out.println("ERROR strikes " + sum);
//			return false;
//		} else {
//			System.out.println("...strikes PASS");
//		}
		
		return success;
	}
	
	// SET GET
	public Strategy getExocet() {
		return exocet;
	}

	public void setExocet(Strategy exocet) {
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

	public Double getAgression() {
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

	public InvProb getProb() {
		return prob;
	}

	public void setProb(InvProb prob) {
		this.prob = prob;
	}

	public TradeRatio getRatio() {
		return ratio;
	}

	public void setRatio(TradeRatio ratio) {
		this.ratio = ratio;
	}
	
	
}
