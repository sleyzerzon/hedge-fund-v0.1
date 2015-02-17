package com.onenow.finance;

import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;

public class MarketPrice {

	HashMap prices;

	public MarketPrice() {
		setPrices(new HashMap());
	}

	public void setAskPrice(Investment investment, Double askPrice) {
		getPrices().put(getLookupKey(investment, TradeType.BUY), askPrice);
	}
	
	public void setBidPrice(Investment investment, Double bidPrice) {
		getPrices().put(getLookupKey(investment, TradeType.SELL), bidPrice);
	}

	public void setLastPrice(Investment investment, Double lastPrice) {
		getPrices().put(getLookupKey(investment, TradeType.LAST), lastPrice);
	}

	public void setClosePrice(Investment investment, Double closePrice) {
		getPrices().put(getLookupKey(investment, TradeType.CLOSE), closePrice);
	}

	public Double getPrice(Investment investment, TradeType tradeType) {
		String key = getLookupKey(investment, tradeType);
		Double price=0.0;
		try {
			price = (Double) (getPrices().get(key));
		} catch (Exception e) {
			e.printStackTrace();
		} 
// let price be null to know it's not set
//		if(price==null) {
//			return 0.0;
//		}
		return price;
	}

	private String getLookupKey(Investment investment, TradeType tradeType) {
		Underlying under = investment.getUnder();
		String lookup = under.getTicker() + "-" + 
		                investment.getInvType() + "-" +
		                tradeType;		
		if (investment instanceof InvestmentOption) {
			Double strike = ((InvestmentOption) investment).getStrikePrice();
			String exp = (String) ((InvestmentOption) investment).getExpirationDate();
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
