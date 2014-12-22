package com.onenow.orchestrator;

import java.util.HashMap;

public class MarketPrice {

	Portfolio market = new Portfolio();
	
	HashMap prices = new HashMap();
	
	public MarketPrice() {
	}
	
	public void setInvestmentPrice(Investment investment, Double buyPrice, Double sellPrice) {
		
		this.prices.put(getPriceLookup(investment, InvestmentTradeTypeEnum.BUY), buyPrice);
		this.prices.put(getPriceLookup(investment, InvestmentTradeTypeEnum.SELL), sellPrice);
	}
	
	public void getInvestmentPrice(Investment investment, Enum InvestmentTradeType) {
		this.prices.get(getPriceLookup(investment, InvestmentTradeType));
	}
	
	private String getPriceLookup (Investment investment, Enum InvestmentTradeType) {
		String lookup = investment.getunderlying().getTicker() + investment.getInvestmentType() + InvestmentTradeType;
		return(lookup);
	}
	
}
