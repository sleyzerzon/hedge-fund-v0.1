package com.onenow.finance;

import java.util.HashMap;

public class MarketPrice {

	HashMap prices = new HashMap();
	
	public MarketPrice() {
	}
	
	public void setInvestmentPrice(Investment investment, Double buyPrice, Double sellPrice) {
		getPrices().put(getLookupKey(investment, TradeType.BUY), buyPrice);
		getPrices().put(getLookupKey(investment, TradeType.SELL), sellPrice);
	}
	
	public Double getBuyPrice(Investment investment) {
		return (Double) (getPrices().get(getLookupKey(investment, TradeType.BUY)));
	}

	public Double getSellPrice(Investment investment, Enum InvestmentTradeType) {
		return (Double) (getPrices().get(getLookupKey(investment, TradeType.SELL)));
	}

	private String getLookupKey (Investment investment, Enum InvestmentTradeType) {
		String lookup = investment.getunderlying().getTicker() + investment.getInvestmentType() + InvestmentTradeType;
		return(lookup);
	}
	
	public HashMap getPrices() {
		return prices;
	}

	public void setPrices(HashMap prices) {
		this.prices = prices;
	}
	
}
