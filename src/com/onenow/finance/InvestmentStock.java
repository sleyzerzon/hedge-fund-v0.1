package com.onenow.finance;

public class InvestmentStock extends Investment {

	public InvestmentStock (Underlying underlying) {
		super(underlying, InvType.STOCK);
	}
	
	public String toString() {
		String string = super.toString();
		System.out.println("Investment Stock: " + string);
		return string;
	}
}
