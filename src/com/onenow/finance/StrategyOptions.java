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
	
	public String toString() {
		String s = "";
		s = s + super.toString();
		s = s + "Net Call (t0): $" + getCallNetPremium().intValue() + ". " +
				"Net Put (t0): $" + getPutNetPremium().intValue() + "\n";
		s = s +	"Net Bought (t0): $" + getBoughtNetPremium().intValue() + ". " +
				"Net Sold (t0): $" + getSoldNetPremium().intValue();
		return s;
	}
	
	// SET GET
	

	
}
