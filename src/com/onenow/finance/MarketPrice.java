package com.onenow.finance;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import com.onenow.investor.DataType;

public class MarketPrice {

	HashMap<String, Double> prices;
	HashMap<String, Long> times;
	

	public MarketPrice() {
		setPrices(new HashMap<String, Double>());
		setTimes(new HashMap<String, Long>());
	}

	public void setLastTime(Investment inv, Long time) {
		getTimes().put(getLookupKey(inv, DataType.LASTTIME.toString()), time);
	}
	public Long getLastTime(Investment inv, String dataType) {
		String key = getLookupKey(inv, dataType);
		Long lastTime=(long) 0;
		try {
			lastTime = (Long) (getTimes().get(key)); // let price be null to know it's not set
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return lastTime;
	}
	
	public void setAskPrice(Investment inv, Double askPrice) {
		getPrices().put(getLookupKey(inv, TradeType.BUY.toString()), askPrice);
	}
	
	public void setBidPrice(Investment inv, Double bidPrice) {
		getPrices().put(getLookupKey(inv, TradeType.SELL.toString()), bidPrice);
	}

	public void setLastPrice(Investment inv, Double lastPrice) {
		getPrices().put(getLookupKey(inv, TradeType.LAST.toString()), lastPrice);
	}

	public void setClosePrice(Investment inv, Double closePrice) {
		getPrices().put(getLookupKey(inv, TradeType.CLOSE.toString()), closePrice);
	}

	public Double getPrice(Investment inv, String dataType) {
		String key = getLookupKey(inv, dataType);
		Double price=0.0;
		try {
			price = (Double) (getPrices().get(key)); // let price be null to know it's not set
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return price;
	}

	private String getLookupKey(Investment inv, String dataType) {
		Underlying under = inv.getUnder();
		String lookup = under.getTicker() + "-" + 
		                inv.getInvType() + "-" +
		                dataType;		
		if (inv instanceof InvestmentOption) {
			Double strike = ((InvestmentOption) inv).getStrikePrice();
			String exp = (String) ((InvestmentOption) inv).getExpirationDate();
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
	public HashMap<String, Double> getPrices() {
		return prices;
	}

	public void setPrices(HashMap prices) {
		this.prices = prices;
	}

	private HashMap<String, Long> getTimes() {
		return times;
	}

	private void setTimes(HashMap times) {
		this.times = times;
	}


}
