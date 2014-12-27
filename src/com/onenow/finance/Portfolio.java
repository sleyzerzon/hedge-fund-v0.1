package com.onenow.finance;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class Portfolio {

	private List<Investment> investments;
	
	public Portfolio() {
		setInvestments(new ArrayList<Investment>());
	}
	
	public void addInvestment(Investment inv) {
		getInvestments().add(inv);
	}
	
	public Investment getBest(Underlying under, Enum invType) { // generic
		return(search(under, invType).get(0));
	}
	public Investment getBest(Underlying under, Enum invType, Date expiration, Double strike) { // generic
		return(search(under, invType, expiration, strike).get(0));
	}
			
	public List<Investment> search(Underlying under, Enum invType) { // generic
		List<Investment> foundInvestments = new ArrayList<Investment>();
		for(Investment investment : getInvestments()) {
			if(under.getTicker().equals(investment.getunderlying().getTicker()) &&
				investment.getInvestmentType().equals(invType)) {
				foundInvestments.add(investment);
			}
		}
		return foundInvestments;
	}
	
	public List<Investment> search(Underlying under, Enum invType, Date expiration, Double strike) {
		List<Investment> foundInvestments = new ArrayList<Investment>();
		for(Investment investment : getInvestments()) {
			if(investment.getInvestmentType().equals(InvType.CALL) || investment.getInvestmentType().equals(InvType.PUT)) {					
				InvestmentOption option = (InvestmentOption) investment;
				if (under.getTicker().equals(investment.getunderlying().getTicker()) &&
					option.getExpirationDate().equals(expiration) && 
					option.getStrikePrice().equals(strike)) {
						foundInvestments.add(investment);
				} 
			}
		}
		return foundInvestments;						
	}
	
	public String toString() {
		String string = "";
		for(Investment investment : getInvestments()) {
			string.concat(investment.toString());
		}
		return string;
	}

	public List<Investment> getInvestments() {
		return investments;
	}
	
	public void setInvestments(List<Investment> investments) {
		this.investments = investments;
	}

}