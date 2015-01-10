package com.onenow.finance;

public class StrategyOptions extends Strategy {

	
	public StrategyOptions() {
		super();		
	}
	
	
	// PUBLIC
	public Double getCallNetPrice() {
		double net = 0.0;
		for(Trade trade:getTransaction().getTrades()) {
			if(trade.getInvestment().getInvType().equals(InvType.CALL))
			net += trade.getNetPremium();
		}
		return net;
	}
	
	public Double getPutNetPrice() {
		double net = 0.0;
		for(Trade trade:getTransaction().getTrades()) {
			if(trade.getInvestment().getInvType().equals(InvType.PUT))
			net += trade.getNetPremium();
		}
		return net;
	}
	
	public Double getBoughtNetPrice() {
		double net = 0.0;
		for(Trade trade:getTransaction().getTrades()) {
			if(trade.getTradeType().equals(TradeType.BUY)) {
			net += trade.getNetPremium();
			}
		}
		return net;		
	}
	
	public Double getSoldNetPrice() {
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
		String sc = "Net Call (t0): $" + getCallNetPrice().toString();
		String sp = "Net Put (t0): $" + getPutNetPrice().toString();
		String sb = "Net Bought (t0): $" + getBoughtNetPrice().toString();
		String ss = "Net Sold (t0): $" + getSoldNetPrice().toString();
		String sx = super.toString();
		System.out.println(sc);
		System.out.println(sp);
		System.out.println(sb);
		System.out.println(ss);
		System.out.println(sx);

		String s = sc + " " + sp + " " + sb + " " + ss + " " + sx;

		return s;
	}
	
	// SET GET
	

	
}
