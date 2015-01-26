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

	public void setPrice(Investment investment, Double lastPrice) {
		String key = getLookupKey(investment, TradeType.LAST);
		getPrices().put(key, lastPrice);
	}

	public Double getPrice(Investment investment, TradeType tradeType) {
		String key = getLookupKey(investment, tradeType);
		Double price=0.0;
		try {
			price = (Double) (getPrices().get(key));
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return price;
	}

	private String getLookupKey(Investment investment, TradeType tradeType) {
		Underlying under = investment.getUnder();
		String lookup = under.getTicker() + "-" + 
		                investment.getInvType() + "-" +
		                tradeType;		
		if (investment instanceof InvestmentOption) {
			Double strike = ((InvestmentOption) investment).getStrikePrice();
			Date exp = (Date) ((InvestmentOption) investment).getExpirationDate();
			lookup = lookup + "-" + strike + "-" + exp; 
		}
		return (lookup);
	}

	// PRINT
	public String toString() {
		String s="";
		s = prices.toString();
		return s;
	}
	
	// TEST
	
	// SET GET
	public HashMap getPrices() {
		return prices;
	}

	public void setPrices(HashMap prices) {
		this.prices = prices;
	}

}
