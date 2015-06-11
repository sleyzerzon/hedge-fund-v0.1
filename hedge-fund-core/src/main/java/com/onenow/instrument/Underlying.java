package com.onenow.instrument;

public class Underlying {

	private String ticker;

	public Underlying() {

	}

	public Underlying(String ticker) {
		setTicker(ticker);
	}

	public String toString() {
		String s = "";
		s = s + getTicker();
		// System.out.println("Underlying: " + string);
		return s;
	}

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
}
