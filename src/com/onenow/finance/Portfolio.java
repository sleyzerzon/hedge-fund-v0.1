package com.onenow.finance;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

public class Portfolio {

	private List<Investment> investments = new ArrayList<Investment>();
	private Hashtable<Investment, Integer> quantity = new Hashtable<Investment, Integer>();

	public Portfolio() {
	
	}

	public void enterTransaction(Transaction trans) {
		for (Trade trade : trans.getTrades()) {
			enterInvestment(trade);
		}
	}

	private void enterInvestment(Trade trade) {
		Investment invToEnter = trade.getInvestment();
		Underlying under = invToEnter.getUnder();
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
	
	public Integer getTotalQuantity() {
		Integer sum = 0;
			for(Investment inv:getInvestments()) {
				sum += Math.abs(getQuantity().get(inv));
			}
		return sum;
	}

	public Investment getBest(Underlying under, Enum invType) { // generic
		List<Investment> found = search(under, invType);
		if (found.size() == 0) {
			return null;
		}
		return found.get(0);
	}

	public Investment getBest(Underlying under, Enum invType, Date expiration,
			Double strike) { // generic
		List<Investment> found = search(under, invType, expiration, strike);
		if (found.size() == 0) {
			return null;
		}
		return found.get(0);
	}

	public Investment getBest(Underlying under, Enum invType, Enum InvTerm) {
		Investment inv = new Investment();
		// TODO
		return inv;
	}

	public List<Investment> search(Underlying under, Enum invType) { // generic
		List<Investment> foundInvestments = new ArrayList<Investment>();
		for (Investment investment : getInvestments()) {
			if (under.getTicker().equals(investment.getUnder().getTicker())
					&& investment.getInvType().equals(invType)) {
				foundInvestments.add(investment);
			}
		}
		return foundInvestments;
	}

	public List<Investment> search(Underlying under, Enum invType,
			Date expiration, Double strike) {
		List<Investment> foundInvestments = new ArrayList<Investment>();
		for (Investment investment : getInvestments()) {
			if (investment.getInvType().equals(InvType.call)
					|| investment.getInvType().equals(InvType.put)) {
				InvestmentOption option = (InvestmentOption) investment;
				if (under.getTicker().equals(investment.getUnder().getTicker())
						&& option.getExpirationDate().equals(expiration)
						&& option.getStrikePrice().equals(strike)) {
					foundInvestments.add(investment);
				}
			}
		}
		return foundInvestments;
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