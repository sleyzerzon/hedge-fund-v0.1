package com.onenow.database;

import com.onenow.instrument.Investment;
import com.onenow.instrument.InvestmentOption;
import com.onenow.instrument.Underlying;

public class Lookup {
	
	public Lookup() {
		
	}

public String getTimedKey(Long time, Investment inv, String dataType) {
	String s="";
	s = time.toString() + "-";
	s = s + getKey(inv, dataType);
	return s;
}
public String getKey(Investment inv, String dataType) {
	Underlying under = inv.getUnder();
	String lookup = ""; 
	lookup = under.getTicker() + "-" + inv.getInvType();		
	if (inv instanceof InvestmentOption) {
		Double strike = ((InvestmentOption) inv).getStrikePrice();
		String exp = (String) ((InvestmentOption) inv).getExpirationDate();
		lookup = lookup + "-" + strike + "-" + exp; 
	}
	lookup = lookup + "-" + dataType;
	
	return (lookup);
}

}