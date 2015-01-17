package com.onenow.finance;

public class StrategyOptions extends Strategy {

	
	public StrategyOptions() {
		super();	
	}
	
	
	// PUBLIC
	public Double getCallNetPremium() {
		double net = 0.0;
		for(Transaction trans:getTransactions()) {
			for(Trade trade:trans.getTrades()) {
				if(trade.getInvestment().getInvType().equals(InvType.CALL))
				net += trade.getNetPremium();
			}
		}
		return net;
	}
	
	public Double getPutNetPremium() {
		double net = 0.0;
		for(Transaction trans:getTransactions()) {
			for(Trade trade:trans.getTrades()) {
				if(trade.getInvestment().getInvType().equals(InvType.PUT))
				net += trade.getNetPremium();
			}
		}
		return net;
	}
	
	public Double getBoughtNetPremium() {
		double net = 0.0;
		for(Transaction trans:getTransactions()) {
			for(Trade trade:trans.getTrades()) {
				if(trade.getTradeType().equals(TradeType.BUY)) {
				net += trade.getNetPremium();
				}
			}
		}
		return net;		
	}
	
	public Double getSoldNetPremium() {
		double net = 0.0;
		for(Transaction trans:getTransactions()) {
			for(Trade trade:trans.getTrades()) {
				if(trade.getTradeType().equals(TradeType.SELL)) {
				net += trade.getNetPremium();
				}
			}
		}
		return net;		
	}
	
	//////////////////////////////
	// INCOME STRATEGY RULES pg 43
	// RULES
	//////////////////////////////
	public boolean rulesPass() { // if Fail, then adjust or liquidate the position
		return isIncomeStrategy();
	}
	
	// INCOME STRATEGY 
	public boolean isIncomeStrategy() {
		return isThetaPositive() && isGammaPositive();
	}
	
	public boolean isThetaPositive() { 
		boolean callsThetaPostive = isSpreadThetaPositive(InvType.CALL);
		boolean putsThetaPositive = isSpreadThetaPositive(InvType.PUT);
		return callsThetaPostive && putsThetaPositive;
	}
	
	private boolean isSpreadThetaPositive(Enum invType) {
		boolean isPositive=false;
		// TODO: traverse each spread, with theta += opt.getGreeks().getTheta();
		return isPositive;
	}
	
	public boolean isGammaPositive() {  // across each SPREAD
		boolean callsGammaPositive = isSpreadGammaPositive(InvType.CALL);
		boolean putsGammaPositive = isSpreadGammaPositive(InvType.PUT);
		return callsGammaPositive && putsGammaPositive; // both
	}
	
	private boolean isSpreadGammaPositive(Enum invType) {
		boolean isPositive=false;
		// TODO: traverse each spread, with theta += opt.getGreeks().getGamma();		
		return isPositive;
	}
	
	// DELTA NEUTRALITY
	public boolean isDeltaNeutral() { // across BUY TYPE
		boolean buyDeltaNeutral=isDeltaNeutral(TradeType.BUY);
		boolean sellDeltaNeutral=isDeltaNeutral(TradeType.SELL);
		return buyDeltaNeutral&&sellDeltaNeutral;
	}
	
	private boolean isDeltaNeutral(Enum tradeType) { 
		Double allowance=0.1;
		Double netDelta=getNetDelta(tradeType);
		Double avgAbsDelta=getAvgAbsDelta(tradeType);
		
		boolean deltaNeutral = Math.abs(netDelta)/avgAbsDelta < allowance;
		return deltaNeutral;
	}
	
	private Double getNetDelta(Enum tradeType) { 
		Double netDelta=0.0;
		Transaction trans=getTransactions().get(0); // first transaction only
		for(Trade trade:trans.getTrades()){
			if(trade.getTradeType().equals(tradeType)) {
				Investment inv = trade.getInvestment();		
				InvestmentOption opt = (InvestmentOption) inv;
				Double delta = opt.getGreeks().getDelta();
				netDelta += delta;
			}
		}		
		return netDelta;
	}
	
	private Double getAvgAbsDelta(Enum tradeType) { 
		Double avgAbsDelta=0.0;
		Integer counter=0;
		Transaction trans=getTransactions().get(0); // first transaction only
		for(Trade trade:trans.getTrades()) {
			if(trade.getTradeType().equals(tradeType)) {
				Investment inv = trade.getInvestment();
				InvestmentOption opt = (InvestmentOption) inv;
				Double delta = opt.getGreeks().getDelta();
				avgAbsDelta += Math.abs(delta);
				counter++;
			}
		}		
		return avgAbsDelta/counter;
	}

	// LOSS TRIGGER
	// trigger to adjust the strategy upon Underlying change
	public boolean isExpirationLoss (Double underPrice) { 
		Double buffer=0.0;  // could be buffer in time or price
		Double triggerPrice = underPrice*(1+buffer);
		return triggerPrice<0;
	}
	public boolean isPassDeltaThetaRule() {
		boolean pass = true;
		// TODO
		return pass;
	}
	public boolean isPassVegaThetaRule() {
		boolean pass = true;
		// TODO
		return pass;
	}

	// PRIVATE 
	
	// PRINT
	public String toString() {
		String s = "";
		s = s + super.toString();
		s = s + "Net Call (t0): $" + getCallNetPremium().intValue() + ". " +
				"Net Put (t0): $" + getPutNetPremium().intValue() + "\n";
		s = s +	"Net Bought (t0): $" + getBoughtNetPremium().intValue() + ". " +
				"Net Sold (t0): $" + getSoldNetPremium().intValue();
		return s;
	}
	
	// TEST
	public void testCallNetPremium(Double num){
		if(!getCallNetPremium().equals(num)) {
			System.out.println("ERROR call net $" + getCallNetPremium());			
		}
	
	}
	public void testPutNetPremium(Double num) {
		if(!getPutNetPremium().equals(num)){
			System.out.println("ERROR put net $" + getPutNetPremium());						
		}
	
	}
	public void testBoughtNetPremium(Double num) {
		if(!getBoughtNetPremium().equals(num)) {
			System.out.println("ERROR buy net $" + getBoughtNetPremium());			
		}
		
	}
	public void testSoldNetPremium(Double num) {
		if(!getSoldNetPremium().equals(num)){
			System.out.println("ERROR sell net $" + getSoldNetPremium());		
		}
	}
	
	// SET GET
	

	
}
