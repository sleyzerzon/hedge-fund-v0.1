package com.onenow.data;

import com.onenow.instrument.Investment;

/**
 * Single quote class
 *
 */
public class QuoteRealtimeNonOption extends QuoteHandler {
			
	public QuoteRealtimeNonOption () {
		
	}
	
	public QuoteRealtimeNonOption(Investment inv) {
		this.investment = inv;
	}
	
	// PRINT
	public String toString() {
		String s = "";
		s = s + super.toString();
		return s;
	}

}