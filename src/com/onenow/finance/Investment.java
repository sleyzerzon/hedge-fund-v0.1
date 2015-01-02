package com.onenow.finance;

public class Investment {

	private Underlying under;
	private Enum invType;
	
	// CONSTRUCTOR
	public Investment() {
		// does not set underlying
	}
	public Investment(Underlying underlying, Enum invType) {
		setUnder(underlying);
		setInvType(invType);
	}	
	
	// PUBLIC
	public boolean equals(Investment inv) {
		boolean equal = false;
		if(inv.getUnder().getTicker().equals(getUnder().getTicker())) {
			// TODO add cases for options etc
			equal = true;
		}
		return equal;
	}
		
	// PRINT
	public String toString() {
		String string = getUnder().toString();
		// System.out.println("Investment: " + string);
		return string;
	}
	
	// SET GET
	private Underlying getUnder() {
		return under;
	}
	private void setUnder(Underlying under) {
		this.under = under;
	}
	public Enum getInvType() {
		return invType;
	}
	private void setInvType(Enum invTuype) {
		this.invType = invTuype;
	}
		
}
