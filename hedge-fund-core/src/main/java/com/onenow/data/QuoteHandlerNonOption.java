package com.onenow.data;

import com.onenow.instrument.Investment;

/**
 * Single quote class
 *
 */
public class QuoteHandlerNonOption extends QuoteHandler {
			
	public QuoteHandlerNonOption () {
		
	}
	
	QuoteHandlerNonOption(Investment inv) {
		this.investment = inv;
	}
	
	// PRINT
	public String toString() {
		String s="\n\n";
		s = s + "QUOTE" + "\n";
		s = s + "Contract " + getContract().description() + "\n";
		s = s + "Bid " + m_bid + "\n";
		s = s + "Ask " + m_ask + "\n";
		s = s + "Last " + m_last + "\n";
		s = s + "Last time " + m_lastTime + "\n";
		s = s + "Bid size " + m_bidSize + "\n";
		s = s + "Ask size " + m_askSize + "\n";
		s = s + "Close " + m_close + "\n";
		s = s + "Frozen " + m_frozen + "\n";
		return s;
	}

}