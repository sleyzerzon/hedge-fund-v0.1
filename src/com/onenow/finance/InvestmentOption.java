package com.onenow.finance;
import java.util.Date;


public class InvestmentOption extends Investment { // call, put

	private Double strike;
	private Date expiration;
	private Double price;
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
		// System.out.println("Strike: " + getStrike() + " Market price: " + marketPrice);
		return value;
	}
	

	// PRIVATE
	
	
	// PRINT


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

	public Double getPrice() {
		return price;
	}

	private void setPrice(Double price) {
		this.price = price;
	}

	private int getShares() {
		return shares;
	}

	private void setShares(int shares) {
		this.shares = shares;
	}
	
}
