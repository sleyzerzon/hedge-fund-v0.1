package com.onenow.finance;

import java.util.HashMap;

public class MarketPrice {

	HashMap prices = new HashMap();
	
	public MarketPrice() {
	}
	
	public void setPrice(Investment investment, Double bidPrice, Double askPrice) {
		getPrices().put(getLookupKey(investment, TradeType.BUY), bidPrice);
		getPrices().put(getLookupKey(investment, TradeType.SELL), askPrice);
	}
	
	public Double getPriceBid(Investment investment) {
		return (Double) (getPrices().get(getLookupKey(investment, TradeType.BUY)));
	}

	public Double getPriceAsk(Investment investment) {
		return (Double) (getPrices().get(getLookupKey(investment, TradeType.SELL)));
	}

	private String getLookupKey (Investment investment, Enum InvestmentTradeType) {
		String lookup = investment.getUnderlying().getTicker() + investment.getInvestmentType() + InvestmentTradeType;
		return(lookup);
	}
	
	public HashMap getPrices() {
		return prices;
	}

	public void setPrices(HashMap prices) {
		this.prices = prices;
	}
	
}
