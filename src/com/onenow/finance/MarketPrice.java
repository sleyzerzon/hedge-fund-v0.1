package com.onenow.finance;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import com.onenow.investor.DataType;

public class MarketPrice {

	HashMap<String, Double> prices; 	// $
	HashMap<String, Long> times; 		// when
	HashMap<String, Integer> size; 		// volume
	

	public MarketPrice() {
		setPrices(new HashMap<String, Double>());
		setTimes(new HashMap<String, Long>());
		setSize(new HashMap<String, Integer>());
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
	
	public void setSize(Investment inv, Integer size, String dataType) {
		getSize().put(getLookupKey(inv, dataType), size);
	}
	public Integer getSize(Investment inv, String dataType) {
		String key = getLookupKey(inv, dataType);
		Integer size=0;
		try {
			size = (Integer) (getSize().get(key)); 
		} catch (Exception e) {
			e.printStackTrace();
		} 		
		return size;
	}
	
	public void setPrice(Investment inv, Double price, String dataType) {
		getPrices().put(getLookupKey(inv, dataType), price);
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
	private HashMap<String, Double> getPrices() {
		return prices;
	}

	private void setPrices(HashMap<String, Double> prices) {
		this.prices = prices;
	}

	private HashMap<String, Long> getTimes() {
		return times;
	}

	private void setTimes(HashMap<String, Long> times) {
		this.times = times;
	}

	private HashMap<String, Integer> getSize() {
		return size;
	}

	private void setSize(HashMap<String, Integer> size) {
		this.size = size;
	}
	


}
