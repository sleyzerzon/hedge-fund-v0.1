package com.onenow.finance;

public class InvestmentReserved extends Investment {

	private InvTerm term;
	
	public InvestmentReserved() {
		
	}

	public InvTerm getTerm() {
		return term;
	}

	private void setTerm(InvTerm term) {
		this.term = term;
	}
	
}
