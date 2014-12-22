package com.onenow.orchestrator;

public class Trade {
	
	private Investment investment; // INTC, option, call
	private Enum tradeType; // ie. buy
	private int quantity; // 50 shares, 50 contracts
	private Float netCost; // +$10 net, -$23 net
	
	
	public Trade(Investment inv, Enum tradeType, int quantity) {
		// constructor
		
	}
	
	public Double getNetCost() {
		Double cost = this.quantity * investment.getmarketPrice();
		if (tradeType == InvestmentTradeTypeEnum.BUY) {
			cost = -cost;
		}
		return(cost);		
	}

	public String toString() {
		String string = investment.toString() + " " + getTradeType() + " " + getQuantity() + " " + getNetCost();
		System.out.println("Trade: " + string);
		return (string);
	}

	
	
	public Investment getVehicle() {
		return investment;
	}

	public void setVehicle(Investment vehicle) {
		this.investment = vehicle;
	}

	public Enum getTradeType() {
		return tradeType;
	}

	public void setTradeType(Enum tradeType) {
		this.tradeType = tradeType;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}





