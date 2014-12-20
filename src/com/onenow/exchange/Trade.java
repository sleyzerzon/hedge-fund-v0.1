package com.onenow.exchange;

public class Trade {
	
	private Investment vehicle; // INTC, option, call

	private Enum tradeType; // ie. buy
		
	private int quantity; // 50 shares, 50 contracts

	private Float cost; // +$10 net, -$23 net

	public Investment getVehicle() {
		return vehicle;
	}

	public void setVehicle(Investment vehicle) {
		this.vehicle = vehicle;
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

	public Float getCost() {
		return cost;
	}

	public void setCost(Float cost) {
		this.cost = cost;
	}

}
