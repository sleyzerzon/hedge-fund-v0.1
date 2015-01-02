package com.onenow.finance;
import java.util.Date;


public class InvestmentOption extends Investment { // call, put

	private Double strike;
	private Date expiration;
	private int shares=100;

	// CONSTRUCTORS
	public InvestmentOption() {
	}
	
	public InvestmentOption(Underlying under, Enum type, Date exp, Double strike) {
		super(under, type);
		setExpiration(exp);
		setStrike(strike);
	}
	
	// PUBLIC		
	public Double getValue(Double marketPrice) {
		Double value=0.0;
		if(getInvType().equals(InvType.CALL)){ // call
			if(marketPrice > getStrike()) {
				value = marketPrice-getStrike();
			}
		} else { // put
			if(marketPrice < getStrike()) {
				value = getStrike()-marketPrice; 
			}
		}
		return value;
	}
	

	// PRIVATE
	
	
	// PRINT
	public String toString() { // TODO add shares
		String s="Strike: $" + getStrike().toString() + " Exp: " + getExpiration().toString();
		// TODO print
		return s;
	}

	// SET GET
	
	
	public Double getStrike() {
		return strike;
	}

	private void setStrike(Double strike) {
		this.strike = strike;
	}

	public Date getExpiration() {
		return expiration;
	}

	private void setExpiration(Date expiration) {
		this.expiration = expiration;
	}

	private int getShares() {
		return shares;
	}

	private void setShares(int shares) {
		this.shares = shares;
	}
	
}
