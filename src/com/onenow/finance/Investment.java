package com.onenow.finance;

public class Investment {

	private Underlying underlying;
	Enum investmentType;
	
	public Investment() {
		// does not set underlying
	}
	public Investment(Underlying underlying, Enum invType) {
		setunderlying(underlying);
		setInvestmentType(invType);
	}	
	public String toString() {
		String string = this.underlying.toString();
		// System.out.println("Investment: " + string);
		return string;
	}

	public Underlying getUnderlying() {
		return underlying;
	}
	public void setUnderlying(Underlying underlying) {
		this.underlying = underlying;
	}
	public Enum getInvestmentType() {
		return investmentType;
	}
	public void setInvestmentType(Enum investmentType) {
		this.investmentType = investmentType;
	}
	public Underlying getunderlying() {
		return underlying;
	}

	public void setunderlying(Underlying underlying) {
		this.underlying = underlying;
	}
}
