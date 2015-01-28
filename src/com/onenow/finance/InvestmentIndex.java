package com.onenow.finance;

public class InvestmentIndex extends Investment {

	// CONSTRUCTOR
	public InvestmentIndex() {
		
	}
	
	public InvestmentIndex (Underlying underlying) {
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
//		System.out.println("Investment Stock: " + s);
		return s;
	}
	
}
