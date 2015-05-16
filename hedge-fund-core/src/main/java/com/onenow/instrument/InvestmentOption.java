package com.onenow.instrument;

import java.util.Date;

import com.onenow.constant.InvType;

public class InvestmentOption extends Investment { // call, put

	private Double strikePrice;
	String expirationDate;
	private int shares = 100;

	// CONSTRUCTORS
	public InvestmentOption() {
	}

	public InvestmentOption(Underlying under, InvType type, String exp,
			Double strike) {
		super(under, type);
		setExpirationDate(exp);
		setStrikePrice(strike);
	}

	// PUBLIC
	public Double getValue(Double marketPrice) {
		Double value = 0.0;
		if (getInvType().equals(InvType.CALL)) { // call
			if (marketPrice > getStrikePrice()) {
				value = marketPrice - getStrikePrice();
			}
		} else { // put
			if (marketPrice < getStrikePrice()) {
				value = getStrikePrice() - marketPrice;
			}
		}
		return value;
	}

	////////////////////////////////////////
	public Double getProbabilityOfProfit() {
		Double prob = 0.0;
		// TODO VTRRR etc
		return prob;
	}
	
	
	// PRIVATE

	// PRINT
	public String toString() { // TODO add shares
		String s = 	super.toString() + 
					"-Expires"+ getExpirationDate().toString() +
					"-Strike" + getStrikePrice().toString(); 
		return s;
	}

	// TEST
	public boolean test() {
		System.out.println("\n\n" + "TESTING INVESTMENT OPTION" + toString());
		
		return (
				true
				);
	}
	
	
	// SET GET
	private int getShares() {
		return shares;
	}

	public Double getStrikePrice() {
		return strikePrice;
	}

	public void setStrikePrice(Double strikePrice) {
		this.strikePrice = strikePrice;
	}

	private void setShares(int shares) {
		this.shares = shares;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	private void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

}
