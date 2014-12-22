package com.onenow.orchestrator;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class Portfolio {

	private List<Investment> investments;
	
	public Portfolio() {
		investments = new ArrayList<Investment>();

	}
	
	public void addInvestment(Investment inv) {
		investments.add(inv);
	}
		
	public List<Investment> searchStock(Underlying under) {
		List<Investment> foundInvestments = new ArrayList<Investment>();
		for(Investment investment : getStocks()) {
			if (under.getTicker().equals(investment.getunderlying().getTicker()) &&
				investment.getInvestmentType()==InvestmentTypeEnum.STOCK) {
				foundInvestments.add(investment);
			}
		}
		return foundInvestments;						
	}
	
	public List<Investment> searchCalls(Underlying under, Date expiration, Double strike) {
		List<Investment> foundInvestments = new ArrayList<Investment>();
		for(Investment investment : getCalls()) {
			InvestmentOption option = (InvestmentOption) investment;
			if (under.getTicker().equals(investment.getunderlying().getTicker()) &&
				option.getExpirationDate() == expiration && 
				option.getStrikePrice() == strike) {
					foundInvestments.add(investment);
			}
		}
		return foundInvestments;				
	}

	public List<Investment> searchPuts(Underlying under, Date expiration, Double strike) {
		List<Investment> foundInvestments = new ArrayList<Investment>();
		for(Investment investment : getPuts()) {
			InvestmentOption option = (InvestmentOption) investment;
			if (under.getTicker().equals(investment.getunderlying().getTicker()) &&
				option.getExpirationDate() == expiration && 
				option.getStrikePrice() == strike) {
					foundInvestments.add(investment);
			}
		}
		return foundInvestments;				
	}

	public String toString() {
		String string = "";
		for(Investment investment : investments) {
			investment.toString();
		}
		return string;
	}

	public List<Investment> getInvestments() {
		return investments;
	}
	
	public List<Investment> getStocks() {
		List<Investment> foundInvestments = new ArrayList<Investment>();
		for(Investment investment : getInvestments()) {
			if (investment.getInvestmentType()==InvestmentTypeEnum.STOCK) {
				foundInvestments.add(investment);
			}
		}
		return foundInvestments;							
	}
	
	public List<Investment> getCalls() {
		List<Investment> foundInvestments = new ArrayList<Investment>();
		for(Investment investment : getInvestments()) {
			if (investment.getInvestmentType()==InvestmentTypeEnum.CALL) {
				foundInvestments.add(investment);
			}
		}
		return foundInvestments;									
	}

	public List<Investment> getPuts() {
		List<Investment> foundInvestments = new ArrayList<Investment>();
		for(Investment investment : getInvestments()) {
			if (investment.getInvestmentType()==InvestmentTypeEnum.PUT) {
				foundInvestments.add(investment);
			}
		}
		return foundInvestments;									
	}

	public void setInvestments(List<Investment> investments) {
		this.investments = investments;
	}

}