package com.onenow.instrument;

import com.onenow.constant.InvType;

public class InvFuture extends Investment {

	
	String expirationDate;

	// CONSTRUCTOR
	public InvFuture() {
		
	}
	
	public InvFuture (Underlying underlying, String expDate) {
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
		
		s = s + super.toString() + " ";
		
		try {
			s = s + "-Exp "+ getExpirationDate().toString();
		} catch (Exception e) {
		}
		
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
