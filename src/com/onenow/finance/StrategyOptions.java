package com.onenow.finance;

import java.util.Date;

public class StrategyOptions extends Strategy {

	
	public StrategyOptions() {
		super();		
	}
	
	
	// PUBLIC
	public Double getCallNetPrice() {
		double net = 0.0;
		for(Trade trade:getTransaction()) {
			if(trade.getInvestment().getInvType().equals(InvType.CALL))
			net += trade.getNetCost();
		}
		return net;
	}
	
	public Double getPutNetPrice() {
		double net = 0.0;
		for(Trade trade:getTransaction()) {
			if(trade.getInvestment().getInvType().equals(InvType.PUT))
			net += trade.getNetCost();
		}
		return net;
	}
	
	public Double getBoughtNetPrice() {
		double net = 0.0;
		for(Trade trade:getTransaction()) {
			if(trade.getTradeType().equals(TradeType.BUY)) {
			net += trade.getNetCost();
			}
		}
		return net;		
	}
	
	public Double getSoldNetPrice() {
		double net = 0.0;
		for(Trade trade:getTransaction()) {
			if(trade.getTradeType().equals(TradeType.SELL)) {
			net += trade.getNetCost();
			}
		}
		return net;		
	}
	

	// PRIVATE 
	
	public String toString() {
//		String s0="Max Profit (t0): $".concat(getTransaction().getMaxProfit().toString());
//		String se="Max Loss (te): $".concat(getMaxLoss().toString());
		String sc="NET CALL (t0): $".concat(getCallNetPrice().toString());
		String sp="NET PUT (t0): $".concat(getPutNetPrice().toString());
		String sb="NET BOUGHT (t0): $".concat(getBoughtNetPrice().toString());
		String ss="NET SOLD (t0): $".concat(getSoldNetPrice().toString());
//		System.out.println(s0);
//		System.out.println(se);
		System.out.println(sc);
		System.out.println(sp);
		System.out.println(sb);
		System.out.println(ss);
//		String s=s0.concat(se).concat(sc).concat(sp).concat(sb).concat(ss);
		String s=sc.concat(sp).concat(sb).concat(ss);

		return s;
	}
	
	// SET GET
	

	
}
