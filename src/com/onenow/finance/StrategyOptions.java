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
				if(trade.getInvestment().getInvType().equals(InvType.call))
				net += trade.getNetPremium();
			}
		}
		return net;
	}
	
	public Double getPutNetPremium() {
		double net = 0.0;
		for(Transaction trans:getTransactions()) {
			for(Trade trade:trans.getTrades()) {
				if(trade.getInvestment().getInvType().equals(InvType.put))
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
	// RULE: INCOME STRATEGY
	public boolean rulesPass() { // if Fail, then adjust or liquidate the position
		return isIncomeStrategy() && isDeltaNeutral() && 
			   isNotExpirationLoss() && isPricingRiskManaged() &&
			   isMarketRiskManaged();
	}
	
	// RULE 1: INCOME STRATEGY 
	public boolean isIncomeStrategy() {
		return isThetaPositive() && isGammaPositive();
	}
	
	public boolean isThetaPositive() { 
		boolean callsThetaPostive = isSpreadThetaPositive(InvType.call);
		boolean putsThetaPositive = isSpreadThetaPositive(InvType.put);
		return callsThetaPostive && putsThetaPositive;
	}
	
	private boolean isSpreadThetaPositive(Enum invType) {
		boolean isPositive=false;
		// TODO: traverse each spread, with theta += opt.getGreeks().getTheta();
		return isPositive;
	}
	
	public boolean isGammaPositive() {  // across each SPREAD
		boolean callsGammaPositive = isSpreadGammaPositive(InvType.call);
		boolean putsGammaPositive = isSpreadGammaPositive(InvType.put);
		return callsGammaPositive && putsGammaPositive; // both
	}
	
	private boolean isSpreadGammaPositive(Enum invType) {
		boolean isPositive=false;
		// TODO: traverse each spread, with theta += opt.getGreeks().getGamma();		
		return isPositive;
	}
	
	// RULE 2: DELTA NEUTRALITY
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
				// TODO: go get from broker
				// Double delta = opt.getGreeks().getDelta(); 
				Double delta = 0.0;
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
				// TODO: go get from broker
				// Double delta = opt.getGreeks().getDelta();
				Double delta = 0.0;
				avgAbsDelta += Math.abs(delta);
				counter++;
			}
		}		
		return avgAbsDelta/counter;
	}

	// RULE 3: GET OUT OF LOSS
	public boolean isNotExpirationLoss () { 
		Double buffer=0.0;  // could be buffer in time or price
		Double underPrice = 0.0; // TODO: go get the price now
		Double triggerPrice = underPrice*(1+buffer);
		return triggerPrice<0;
	}
	
	// RULE 4: ELIMINATION OF RISK IN TIME
	public boolean isPricingRiskManaged() {
		boolean pass = true;  // TODO: sum of delta/theta ratios across the strategy
		Double delta = 0.0; // opt.getGreeks().getDelta()
		Double theta = 0.0; // opt.getGreeks().getTheta();
		Double ratio = delta / theta;
		return ratio < 0.5; // what's the right number?
	}
	
	// RULE 5: 
	public boolean isMarketRiskManaged() {
		boolean pass = true;
		Double vega = 0.0; // TODO: get
		Double theta = 0.0; // get
		Double ratio = vega / theta;
		return ratio < 0.5; // what's the right number?
	}

	// PRIVATE 
	
	// PRINT
	public String toString() {
		String s = "";
		s = s + super.toString();
		s = s + "Net Call (t0): $" + getCallNetPremium().intValue() + ". " +
				"Net Put (t0): $" + getPutNetPremium().intValue() + "\n";
		s = s +	"Net Bought (t0): $" + getBoughtNetPremium().intValue() + ". " +
				"Net Sold (t0): $" + getSoldNetPremium().intValue() + "\n";
		
		s = s + "BASIC RULES" + "\n";
		s = s + "1. income strategy <" + isIncomeStrategy() + ">\n";
		s = s + "2. delta neutral <" + isDeltaNeutral()  + ">\n";
		s = s + "3. is not expiration loss <" + isNotExpirationLoss () + ">";
		s = s + "4. delta option price risk vs. theta time decay <" + isPricingRiskManaged() + ">\n"; 
		s = s + "5. vega market risk vs. theta time decal <" + isMarketRiskManaged() + ">\n";

		return s;
	}
	
	// TEST
	public boolean testCallNetPremium(Double num){
		if(!getCallNetPremium().equals(num)) {
			System.out.println("ERROR call net $" + getCallNetPremium());
			return false;
		}
		return true;
	
	}
	public boolean testPutNetPremium(Double num) {
		if(!getPutNetPremium().equals(num)){
			System.out.println("ERROR put net $" + getPutNetPremium());
			return false;
		}
		return true;
	
	}
	public boolean testBoughtNetPremium(Double num) {
		if(!getBoughtNetPremium().equals(num)) {
			System.out.println("ERROR buy net $" + getBoughtNetPremium());
			return false;
		}
		return true;
		
	}
	public boolean testSoldNetPremium(Double num) {
		if(!getSoldNetPremium().equals(num)){
			System.out.println("ERROR sell net $" + getSoldNetPremium());
			return false;
		}
		return true;
	}
	
	
	
	// SET GET
	

	
}
