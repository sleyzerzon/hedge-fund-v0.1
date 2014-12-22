package com.onenow.orchestrator;

public class InvestmentStock extends Investment {

	public InvestmentStock (Underlying underlying) {
		super(underlying, InvestmentTypeEnum.STOCK);
	}
	
	public String toString() {
		String string = super.toString();
		System.out.println("Investment Stock: " + string);
		return string;
	}
}
