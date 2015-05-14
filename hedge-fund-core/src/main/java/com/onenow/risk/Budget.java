package com.onenow.risk;

public class Budget {

	private Double balance;
	private Double invRate;
	
	public Budget() {
		setBalance(38000.0);
		setInvRate(0.02);
	}
	
	private Double getAvailableFunds() {
		Double funds=0.0;
		funds = getBalance()*getInvRate();
		return funds;
	}

	private Double getBalance() {
		return balance;
	}

	private void setBalance(Double balance) {
		this.balance = balance;
	}

	private Double getInvRate() {
		return invRate;
	}

	private void setInvRate(Double invRate) {
		this.invRate = invRate;
	}
	
}
