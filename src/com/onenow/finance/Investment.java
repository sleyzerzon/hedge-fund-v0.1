package com.onenow.finance;

public class Investment {

	private Underlying underlying;
	Enum investmentType;
	
	public Investment() {
		// does not set underlying
	}
	public Investment(Underlying underlying, Enum invType) {
		setUnderlying(underlying);
		setInvestmentType(invType);
	}	
	public String toString() {
		String string = this.underlying.toString();
		// System.out.println("Investment: " + string);
		return string;
	}
	
	// SET GET

	public Underlying getUnderlying() {
		return underlying;
	}
	private void setUnderlying(Underlying underlying) {
		this.underlying = underlying;
	}
	public Enum getInvestmentType() {
		return investmentType;
	}
	private void setInvestmentType(Enum investmentType) {
		this.investmentType = investmentType;
	}
		
}
