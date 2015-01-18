package com.onenow.finance;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

public class Portfolio {

	private List<Investment> investments = new ArrayList<Investment>();
	private Hashtable<String, Integer> quantity = new Hashtable<String, Integer>();

	public Portfolio() {
	
	}

	public void addTrade(Transaction trans) {
		List<Trade> trades = trans.getTrades();
		for (Trade trade : trades) {
			Investment inv = trade.getInvestment();
			if (trade.getTradeType().equals(TradeType.BUY)) {
				addQuantity(inv.getUnder(), trade.getQuantity());
			} else {
				// addQuantity(inv.getUnderlying(), -trade.getQuantity());
			}
		}
	}

	public boolean addInvestment(Investment inv) {
		boolean inList = false;

		for (Investment invIt : getInvestments()) { // go through existing
													// investments
			if (invIt.equals(inv)) {
				inList = true;
			}
		}

		if (!inList) { // first inclusion of investment
			getInvestments().add(inv);
			// getQuantity().put(inv.getUnderlying().getTicker(), 0);
		}

		return inList;
	}

	private void addQuantity(Underlying under, int quantity) {
		// TODO the sale case where I do'nt have enough to sell
		Integer init = getQuantity().get(under.getTicker());
		if (init == null) {
			init = 0;
		}
		getQuantity().put(under.getTicker(), init + quantity);
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
			if (investment.getInvType().equals(InvType.CALL)
					|| investment.getInvType().equals(InvType.PUT)) {
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
		String string = "";
		for (Investment investment : getInvestments()) {
			string.concat(investment.toString());
		}
		return string;
	}
	
	// TEST

	// GET SET
	public List<Investment> getInvestments() {
		return investments;
	}

	public void setInvestments(List<Investment> investments) {
		this.investments = investments;
	}

	public Hashtable<String, Integer> getQuantity() {
		return quantity;
	}

	public void setQuantity(Hashtable<String, Integer> quantity) {
		this.quantity = quantity;
	}

}