package com.onenow.finance;

public class InvestmentFuture extends Investment {

	
	String expirationDate;

	// CONSTRUCTOR
	public InvestmentFuture() {
		
	}
	
	public InvestmentFuture (Underlying underlying, String expDate) {
		super(underlying, InvType.FUTURE);
		setExpirationDate(expDate);
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

	// SET GET
	public String getExpirationDate() {
		return expirationDate;
	}

	private void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}
	
}
