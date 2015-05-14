package com.onenow.risk;

import java.util.Hashtable;

public class MarketAnalytics {
	
	private Hashtable<String, Greeks> greeks; // TODO: one for each price for ever expiration at every time

	public MarketAnalytics() {
		// TODO: get values from broker
		setGreeks(new Hashtable<String, Greeks>()); 
		
	}


	// PRINT
	
	// TEST
	public boolean test() {
		Greeks greeks = new Greeks();
		boolean success = greeks.test();
		return success;
	}	
	
	// SET GET
	private Hashtable<String, Greeks> getGreeks() {
		return greeks;
	}

	private void setGreeks(Hashtable<String, Greeks> greeks) {
		this.greeks = greeks;
	}

}
