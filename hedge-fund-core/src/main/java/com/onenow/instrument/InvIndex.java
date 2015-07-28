package com.onenow.instrument;

import com.onenow.constant.InvType;

public class InvIndex extends Investment {

	// CONSTRUCTOR
	public InvIndex() {
		
	}
	
	public InvIndex (Underlying underlying) {
		super(underlying, InvType.INDEX);
	}
	
	// PUBLIC
	public Double getValue(Double marketPrice) { // TODO: set real value (i.e. vs strike)
		Double val=0.0;
		return val;
	}
	
	// PRINT
	public String toString() {
		String s = "";
		
		s = super.toString();
		
		return s;
	}
	
}
