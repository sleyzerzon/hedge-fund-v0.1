package com.onenow.database;

import com.onenow.finance.Investment;
import com.onenow.finance.InvestmentOption;
import com.onenow.finance.Underlying;

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
	String lookup = under.getTicker() + "-" + 
	                inv.getInvType() + "-" +
	                dataType;		
	if (inv instanceof InvestmentOption) {
		Double strike = ((InvestmentOption) inv).getStrikePrice();
		String exp = (String) ((InvestmentOption) inv).getExpirationDate();
		lookup = lookup + "-" + strike + "-" + exp; 
	}
	return (lookup);
}

}