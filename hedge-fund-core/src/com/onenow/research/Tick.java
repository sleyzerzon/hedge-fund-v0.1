package com.onenow.research;

import com.onenow.instrument.Investment;

public class Tick {

	public Tick() {
		// TODO Auto-generated constructor stub
	}
	
	private long timestamp;
	
	private Investment investment;
	
	double price;
	
	private String source;

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public Investment getInvestment() {
		return investment;
	}

	public void setInvestment(Investment investment) {
		this.investment = investment;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
