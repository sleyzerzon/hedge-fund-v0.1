package com.onenow.data;

import javax.swing.table.AbstractTableModel;

import com.onenow.instrument.Investment;

/**
 * Single quote class
 *
 */
public class QuoteRealtimeNonOption extends QuoteRealtimeHandler {
			
	public QuoteRealtimeNonOption () {
		
	}
	
	public QuoteRealtimeNonOption(Investment inv, AbstractTableModel chainTable) {
		this.investment = inv;
		this.chainTable = chainTable;
	}
	
	// PRINT
	public String toString() {
		String s = "";
		s = s + super.toString();
		return s;
	}

}