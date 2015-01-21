package com.onenow.finance;

import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;

public class MarketPrice {

	HashMap prices;

	public MarketPrice() {
		setPrices(new HashMap());
	}

	public void setPrice(Investment investment, Double bidPrice, Double askPrice) {
		getPrices().put(getLookupKey(investment, TradeType.BUY), bidPrice);
		getPrices().put(getLookupKey(investment, TradeType.SELL), askPrice);
	}

	public Double getPrice(Investment investment, TradeType type) {
		String key = getLookupKey(investment, type);
		Double price = (Double) (getPrices().get(key)); 
		return price;
	}

	private String getLookupKey(Investment investment, TradeType type) {
		Underlying under = investment.getUnder();
		String lookup = under.getTicker() + investment.getInvType();		
		if (investment instanceof InvestmentOption) {
			Date exp = (Date) ((InvestmentOption) investment).getExpirationDate();
			Double strike = ((InvestmentOption) investment).getStrikePrice();
			lookup = lookup + exp + strike; 
		}
		return (lookup);
	}

	public HashMap getPrices() {
		return prices;
	}

	public void setPrices(HashMap prices) {
		this.prices = prices;
	}

}
