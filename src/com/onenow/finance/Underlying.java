package com.onenow.finance;

public class Underlying {

	private String ticker;

	public Underlying(String ticker) {
		setTicker(ticker);
	}
	
	public String toString() {
		String string = getTicker();
		// System.out.println("Underlying: " + string);
		return string;
	}
	
	public String getTicker() {
		return ticker;
	}
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
}
