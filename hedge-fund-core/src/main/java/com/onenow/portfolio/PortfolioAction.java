package com.onenow.portfolio;

import com.onenow.alpha.Broker;
import com.onenow.constant.InvApproach;
import com.onenow.constant.InvType;
import com.onenow.constant.TradeType;
import com.onenow.execution.BrokerActivityImpl;
import com.onenow.execution.Contract;
import com.onenow.instrument.InvestmentIndex;
import com.onenow.instrument.InvestmentOption;
import com.onenow.instrument.Underlying;

public class PortfolioAction {
	
	private Strategy strategy;
	
	private Integer quant;
	private Underlying under;
	private Double underPrice;
	private String exp;
	
	private Contract contract;
	
	private Broker broker;
	private Double agression;
	private Double spread=5.0; // TODO: generalize
	
	private InvApproach approach;
	private TradeRatio ratio;
			
	public PortfolioAction() {
		
	}
	
	public PortfolioAction(Integer quant, Underlying under, String exp, Broker broker) {
		setQuant(quant);
		setUnder(under);
		setExp(exp);	
		setBroker(broker);
	}

	public StrategyCallBuy getCall(				InvApproach approach, TradeRatio ratio, Double agression) {
		setApproach(approach);
		setRatio(ratio);
		setAgression(agression);
		
		lookupIndexPrice();		
		setStrategy(new StrategyCallBuy(getCallBuy())); // no put

		setCheckpoints();
		return (StrategyCallBuy) getStrategy();
	}
	
	public StrategyPutBuy getPut(					InvApproach approach, TradeRatio ratio, Double agression) {
		setApproach(approach);
		setRatio(ratio);
		setAgression(agression);
		
		lookupIndexPrice();		
		setStrategy(new StrategyPutBuy(getPutBuy())); // no call

		setCheckpoints();
		return (StrategyPutBuy) getStrategy();
	}
	
	public StrategyCallSpread getCallSpread(	InvApproach approach, TradeRatio ratio, Double agression) {
		setApproach(approach);
		setRatio(ratio);
		setAgression(agression);
		
		lookupIndexPrice();		
		setStrategy(new StrategyCallSpread(getCallBuy(), getCallSell()));
		
		setCheckpoints();
		return (StrategyCallSpread) getStrategy();
	}
	
	public StrategyPutSpread getPutSpread(		InvApproach approach, TradeRatio ratio, Double agression) {
		setApproach(approach);
		setRatio(ratio);
		setAgression(agression);
		
		lookupIndexPrice();
		setStrategy(new StrategyPutSpread(getPutBuy(), getPutSell()));
		setCheckpoints();
		return (StrategyPutSpread) getStrategy();	
	}
	
	public StrategyIronCondor getIronCondor(	InvApproach approach, TradeRatio ratio, Double agression) {
		setApproach(approach);
		setRatio(ratio);
		setAgression(agression);
		
		lookupIndexPrice();
		setStrategy(new StrategyIronCondor(	getCallBuy(), getCallSell(), 
											getPutBuy(), getPutSell()));
		setCheckpoints();
		return (StrategyIronCondor) getStrategy();
	}
		
	// PRIVATE
	private void lookupIndexPrice() {
		InvestmentIndex index = new InvestmentIndex(getUnder());
		Double price = broker.getPrice(index, TradeType.TRADED);
		setUnderPrice(price);
	}

	private void setCheckpoints() {
		Double referencePrice = getUnderPrice(); 
		
		getStrategy().setPastCheckpoint((referencePrice).intValue()); 
		getStrategy().setFutureCheckpoint(estClosing().intValue());
		Integer num = (int) Math.round(getUnderPrice());
		
		getStrategy().setPresentCheckpoint(num);
		getStrategy().addNewCheckpoint(num+1.0);
		getStrategy().addNewCheckpoint(num+2.0);
		getStrategy().addNewCheckpoint(num+3.0);
		getStrategy().addNewCheckpoint(num+4.0);
		getStrategy().addNewCheckpoint(num+5.0);
		getStrategy().addNewCheckpoint(num-1.0);
		getStrategy().addNewCheckpoint(num-2.0);
		getStrategy().addNewCheckpoint(num-3.0);
		getStrategy().addNewCheckpoint(num-4.0);
		getStrategy().addNewCheckpoint(num-5.0);
	}
	
	private Trade getCallBuy() {
		Trade trade = getInv(InvType.CALL, TradeType.BUY); 	
		return trade;
	}
	private Trade getCallSell() {
		Trade trade = getInv(InvType.CALL, TradeType.SELL); 			
		return trade;
	}

	private Trade getPutBuy() {
		Trade trade = getInv(InvType.PUT, TradeType.BUY); 	
		return trade;
	}

	private Trade getPutSell() {
		Trade trade = getInv(InvType.PUT, TradeType.SELL); 	
		return trade;
	}
	
	private Trade getInv(InvType invType, TradeType tradeType) {
		
		InvestmentOption inv = 	new InvestmentOption(getUnder(), invType, getExp(), getStrike(invType, tradeType));
		
		Integer mQuant=getQuant();
				
//		if(tradeType.equals(TradeType.BUY)) { // ratio protection
//			mQuant=getMQuant();		
//			boolean onlyUpper = getProb().equals(InvApproach.LOWER_STRANGLE) || getProb().equals(InvApproach.LEFT);
//			if(invType.equals(InvType.PUT) && onlyUpper) { 
//			   mQuant=getQuant(); // protect only upside on strangles
//			} 
//		} 
		
		Trade trade = new Trade(inv, tradeType, mQuant, ((BrokerActivityImpl) broker).getBestBid(tradeType, inv, getAgression()));
		return trade;
	}
	
	private Integer getMQuant() {
		Integer mQuant=getQuant()*1; // default "none" ratio
		
		Double lowR=1.16;
		Double midR=1.33;
		Double highR=1.66;
		Double vhighR=2.0;

		if(getRatio().equals(TradeRatio.LOW)){
			mQuant = (int) Math.round(getQuant()*lowR);				
		}
		if(getRatio().equals(TradeRatio.MID)){
			mQuant = (int) Math.round(getQuant()*midR);				
		}
		if(getRatio().equals(TradeRatio.HIGH)){
			mQuant = (int) Math.round(getQuant()*highR);				
		}
		if(getRatio().equals(TradeRatio.VHIGH)){
			mQuant = (int) Math.round(getQuant()*vhighR);				
		}

		return mQuant;
	}

	private Double getStrike(InvType invType, TradeType tradeType) {
		Double strike=0.0;
		if(invType.equals(InvType.CALL) && tradeType.equals(TradeType.BUY)) { 
			strike = getCallBuyStrike(); 
		}
		if(invType.equals(InvType.CALL) && tradeType.equals(TradeType.SELL)) { 
			strike = getCallSellStrike(); 
		}
		if(invType.equals(InvType.PUT) && tradeType.equals(TradeType.BUY)) { 
			strike = getPutBuyStrike(); 
		}
		if(invType.equals(InvType.PUT) && tradeType.equals(TradeType.SELL)) { 
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
		if(getProb().equals(InvApproach.HIGH)) { strike=getHPCallSellStrike(); }
		if(getProb().equals(InvApproach.MID)) { strike=getMPCallSellStrike(); }
		if(getProb().equals(InvApproach.LOW)) { strike=getLPCallSellStrike(); }
		if(getProb().equals(InvApproach.LOWER_STRANGLE)) { strike=getLowerStrangleCallSellStrike(); }
		if(getProb().equals(InvApproach.UPPER_STRANGLE)) { strike=getUpperStrangleCallSellStrike(); }
		if(getProb().equals(InvApproach.LEFT)) { strike=getLeftCallSellStrike(); }
		//		if(getProb().equals(InvProb.RIGHT)) { strike=getRightCallSellStrike(); }
		if(getProb().equals(InvApproach.SWING)) { strike=getSwingCallSellStrike(); }
		return strike;
	}
	
	private Double getSwingCallSellStrike() {
		Double strike=0.0;
		strike = getStrikeBelowClosing();		
		return strike;
	}
	
	// for strikes from highest trading to: lowest, current price
	// get price of call sell & call buy
	// calculate spread at a strike
	// calculate gain % if price moves 2 spreads
	// choose that strike
	
	private Double getHPCallSellStrike() {
		Double strike = 0.0;
		strike = getStrikeAboveClosing() + getSpread(); // TODO: do based on delta
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
	private Double getLeftCallSellStrike() {
		Double strike = 0.0;
		strike = getLowerStrangleCallSellStrike()-getSpread();
		return strike;		
	}
	private Double getLowerStrangleCallSellStrike() { // call or put?
		Double strike = 0.0;
		strike = getStrikeAboveClosing()-getSpread();
		return strike;
	}
	private Double getUpperStrangleCallSellStrike() { // call or put?
		Double strike = 0.0;
		strike = getStrikeAboveClosing();
		return strike;
	}

	// PUT SELL
	private Double getPutSellStrike() {
		Double strike=0.0;
		if(getProb().equals(InvApproach.HIGH)) { strike=getHPPutSellStrike(); }
		if(getProb().equals(InvApproach.MID)) { strike=getMPPutSellStrike(); }
		if(getProb().equals(InvApproach.LOW)) { strike=getLPPutSellStrike(); }
		if(getProb().equals(InvApproach.LOWER_STRANGLE)) { strike=getUnderStranglePutSellStrike(); }
		if(getProb().equals(InvApproach.UPPER_STRANGLE)) { strike=getUpperStranglePutSellStrike(); }
		if(getProb().equals(InvApproach.LEFT)) { strike=getLeftPutSellStrike(); }
		//		if(getProb().equals(InvProb.RIGHT)) { strike=getRightPutSellStrike(); }
		if(getProb().equals(InvApproach.SWING)) { strike=getSwingPutSellStrike(); }
		return strike;
	}
	private Double getSwingPutSellStrike() {
		Double strike=0.0;
		
		return strike;
	}	
	private Double getHPPutSellStrike() {
		Double strike = 0.0;
		strike = getLPPutSellStrike()-getSpread(); // TODO: do based on delta
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
	private Double getLeftPutSellStrike() {
		Double strike=0.0;
		strike = getUnderStranglePutSellStrike()-getSpread();
		return strike;		
	}
	private Double getUnderStranglePutSellStrike() { // lower than estClosing
		Double strike = 0.0;
		strike = getStrikeBelowClosing(); // always call
		return strike;
	}
	private Double getUpperStranglePutSellStrike() { // lower than estClosing
		Double strike = 0.0;
		strike = getStrikeAboveClosing(); 
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

	
	
	// TODO: STUDY how long ago priceBefore is the best predictor of closing?
	public Double estClosing() {
		// TODO: use actuals
		Double priceBefore=2058.36; // TODO: get from broker & correlate interval in history
		Double hsSince=1.0; 

		Double priceNow=getUnderPrice(); // 2054.74 in emulator
		Double delta=priceNow-priceBefore;
		Double velocity=delta/hsSince;
		Double hsLeft=20.0/60;
		Double estClosing=priceNow+velocity*hsLeft; 
		return estClosing;
	}
	private Double bakedClosing() { // based on ITM option mid value
		Integer separation=10; // TODO: increase
		Double callStrike = Math.round(getUnderPrice()/getSpread())*getSpread()-separation; 
		Double putStrike =  callStrike + 2*separation;
		// look at extremes
		InvestmentOption putExt = new InvestmentOption(getUnder(), InvType.PUT, getExp(), putStrike);
		Double putMid = ((BrokerActivityImpl) broker).getBestBid(TradeType.BUY, putExt, 0.50);
		Double estClosingPut = putStrike-putMid;
		InvestmentOption callExt = new InvestmentOption(getUnder(), InvType.CALL, getExp(), callStrike);
		Double callMid = ((BrokerActivityImpl) broker).getBestBid(TradeType.BUY, callExt, 0.50);	
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
				"AGGRESSION " + getAgression() + ", " + 
				"EST CLOSING " + Math.round(estClosing()) + "\n";
		s = s + getStrategy().toString();
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
	public Strategy getStrategy() {
		return strategy;
	}

	public void setStrategy(Strategy strat) {
		this.strategy = strat;
	}

	public Integer getQuant() {
		return quant;
	}

	public void setQuant(Integer quant) {
		this.quant = quant;
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

	private Broker getBroker() {
		return broker;
	}

	private void setBroker(Broker broker) {
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

	public InvApproach getProb() {
		return approach;
	}

	public void setApproach(InvApproach approach) {
		this.approach = approach;
	}

	public TradeRatio getRatio() {
		return ratio;
	}

	public void setRatio(TradeRatio ratio) {
		this.ratio = ratio;
	}

	private String getExp() {
		return exp;
	}

	private void setExp(String exp) {
		this.exp = exp;
	}

	private Contract getContract() {
		return contract;
	}

	private void setContract(Contract contract) {
		this.contract = contract;
	}
	
	
}
