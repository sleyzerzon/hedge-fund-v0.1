package com.onenow.finance;

import java.util.Date;

public class InvestmentOption extends Investment { // call, put

	private Double strikePrice;
	private Date expirationDate;
	private int shares = 100;
	private Greeks greeks;   // TODO

	// CONSTRUCTORS
	public InvestmentOption() {
	}

	public InvestmentOption(Underlying under, InvType type, Date exp,
			Double strike) {
		super(under, type);
		setExpirationDate(exp);
		setStrikePrice(strike);
		setGreeks(new Greeks(under));
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
		String s = super.toString() + " Strike: $"
				+ getStrikePrice().toString() + " Exp: "
				+ getExpirationDate().toString();
		// TODO print
		return s;
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

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	private void setShares(int shares) {
		this.shares = shares;
	}

	public Greeks getGreeks() {
		return greeks;
	}

	private void setGreeks(Greeks greeks) {
		this.greeks = greeks;
	}

}
