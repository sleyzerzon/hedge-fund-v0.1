package com.onenow.finance;

public class StrategyOptions extends Strategy {

	
	public StrategyOptions() {
		super();		
	}
	
	
	// PUBLIC
	public Double getCallNetPremium() {
		double net = 0.0;
		for(Trade trade:getTransaction().getTrades()) {
			if(trade.getInvestment().getInvType().equals(InvType.CALL))
			net += trade.getNetPremium();
		}
		return net;
	}
	
	public Double getPutNetPremium() {
		double net = 0.0;
		for(Trade trade:getTransaction().getTrades()) {
			if(trade.getInvestment().getInvType().equals(InvType.PUT))
			net += trade.getNetPremium();
		}
		return net;
	}
	
	public Double getBoughtNetPremium() {
		double net = 0.0;
		for(Trade trade:getTransaction().getTrades()) {
			if(trade.getTradeType().equals(TradeType.BUY)) {
			net += trade.getNetPremium();
			}
		}
		return net;		
	}
	
	public Double getSoldNetPremium() {
		double net = 0.0;
		for(Trade trade:getTransaction().getTrades()) {
			if(trade.getTradeType().equals(TradeType.SELL)) {
			net += trade.getNetPremium();
			}
		}
		return net;		
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
