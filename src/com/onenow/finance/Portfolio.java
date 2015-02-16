package com.onenow.finance;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

public class Portfolio {

	private List<Investment> investments = new ArrayList<Investment>();
	private Hashtable<Investment, Integer> quantity = new Hashtable<Investment, Integer>();

	// CONSTRUCT
	public Portfolio() {
		
	}

	public Portfolio(List<Investment> invs) { // investments with no quantity
		setInvestments(invs); 
	}

	public Portfolio(List<Investment> invs, Hashtable<Investment, Integer> quantity) {
		setInvestments(invs); 
		setQuantity(quantity);
	}

	// INIT
	public void enterTransaction(Transaction trans) {
		for (Trade trade : trans.getTrades()) {
			enterInvestment(trade);
		}
	}
	
	private void enterInvestment(Trade trade) {
		Investment invToEnter = trade.getInvestment();
		Integer quantity = trade.getQuantity();

		if(getQuantity().get(invToEnter) == null) { // not there
			getInvestments().add(invToEnter);
			getQuantity().put(invToEnter, quantity);
		} else { // increment
				Integer init = getQuantity().get(invToEnter);
				if (trade.getTradeType().equals(TradeType.BUY)) {
					getQuantity().put(invToEnter,  init + trade.getQuantity());								
				} else {
					getQuantity().put(invToEnter, init - trade.getQuantity());								
				}				
		}
	}
	
	public Integer getAbsQuantity() {
		Integer sum = 0;
			for(Investment inv:getInvestments()) {
				sum += Math.abs(getQuantity().get(inv));
			}
		return sum;
	}
	
	// SEARCH
	public List<Investment> getInvestments(InvType type) {
		List<Investment> foundInvs = new ArrayList<Investment>();
		for (Investment investment : getInvestments()) {
			if (investment.getInvType().equals(type)) {
				foundInvs.add(investment);
			}
		}
		return foundInvs;
	}

	public List<Investment> getInvestments(Underlying under) {
		List<Investment> foundInvs = new ArrayList<Investment>();
		for (Investment investment : getInvestments()) {
			if (investment.getUnder().equals(under)) {
				foundInvs.add(investment);
			}
		}
		return foundInvs;
	}

	public List<Investment> getInvestments(Underlying under, InvType type) {
		List<Investment> foundInvs = new ArrayList<Investment>();
		for (Investment investment : getInvestments(type)) {
			if (under.getTicker().equals(investment.getUnder().getTicker())) {
				foundInvs.add(investment);
			}
		}	
		return foundInvs;
	}

	public List<Investment> getInvestments(Underlying under, InvType type, InvTerm term) {
		List<Investment> foundInvs = new ArrayList<Investment>();
		for (Investment investment : getInvestments(under, type)) {
			if (investment instanceof InvestmentReserved) { }
				InvestmentReserved invRes= (InvestmentReserved) investment;
				if(invRes.getTerm().equals(term)) {
					foundInvs.add(investment);					
			}
		}	
		return foundInvs;
	}

	public List<Investment> getInvestments(Underlying under, InvType type, String exp) {
		List<Investment> foundInvs = new ArrayList<Investment>();
		for (Investment investment : getInvestments(under, type)) {
			if (type.equals(InvType.CALL) || type.equals(InvType.PUT)) {
				InvestmentOption opt = (InvestmentOption) investment;
				if(opt.getExpirationDate().equals(exp)) {
					foundInvs.add(investment);
				}
			}
		}	
		return foundInvs;		
	}
	
	public List<Investment> getInvestments(Underlying under, InvType type, String exp, Double strike) {
		List<Investment> foundInvs = new ArrayList<Investment>();
		for (Investment investment : getInvestments(under, type, exp)) {
			if (type.equals(InvType.CALL) || type.equals(InvType.PUT)) {
				InvestmentOption opt = (InvestmentOption) investment;
				if(opt.getStrikePrice().equals(strike)) {
					foundInvs.add(investment);
				}
			}
		}	
		return foundInvs;			
	}
	
	// CLOUD GET BEST
	public Investment getBestSpot(Underlying under) throws IndexOutOfBoundsException {
		List<Investment> invs = getInvestments(under, InvType.SPOT);
		return invs.get(0);  
	}
	
	public Investment getBestOnDemand(Underlying under) throws IndexOutOfBoundsException {
		List<Investment> invs = getInvestments(under, InvType.ONDEMAND);
		return invs.get(0);
	}
	
	public Investment getBestReserved(Underlying under, InvTerm term) throws IndexOutOfBoundsException {	
		List<Investment> invs = getInvestments(under, InvType.RESERVED, term); 
		return invs.get(0);
	}
	
	// WALL ST GET BEST
	public Investment getBestStock(Underlying under) throws IndexOutOfBoundsException {
		List<Investment> invs = getInvestments(under, InvType.STOCK);
		return invs.get(0);
	}
	
	public Investment getBestOption(Underlying under, InvType type, String exp, Double strike) 
		throws IndexOutOfBoundsException {
		List<Investment> invs = getInvestments(under, type, exp, strike); 
		return invs.get(0);
	}

	// PRINT
	public String toString() {
		String s = "";
		Integer i = 0;
		for(Investment inv:getInvestments()) {
			s = s + getQuantity().get(inv) + " " + getInvestments().get(i) + "\n";
			i++;
		}
		return s;
	}
	
	// TEST

	// GET SET
	public List<Investment> getInvestments() {
		return investments;
	}

	public void setInvestments(List<Investment> investments) {
		this.investments = investments;
	}

	public Hashtable<Investment, Integer> getQuantity() {
		return quantity;
	}

	public void setQuantity(Hashtable<Investment, Integer> quantity) {
		this.quantity = quantity;
	}

}