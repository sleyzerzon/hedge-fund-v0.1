package com.onenow.finance;

public class Trade {
	
	private Investment investment; // INTC, option, call
	private Enum tradeType; // ie. buy
	private int quantity; // 50 shares
	private Double netCost; // +$10 net, -$23 net
	private Double unitPrice;  // $7.50 per share
	
	public Trade() {
		// TODO
	}

	public Trade(Investment inv, Enum tradeType, int quantity, Double unitPrice) {
		setInvestment(inv);
		setTradeType(tradeType);
		setQuantity(quantity);
		setUnitPrice(unitPrice);
		setNetCost();
	}

	// PUBLIC
	public Double getStrike() {
		Double strike = 0.0;
		Enum invType = getInvestment().getInvType();
		if( invType.equals(InvType.CALL) ||
			invType.equals(InvType.PUT)) {
			InvestmentOption option = (InvestmentOption) getInvestment();
			strike = option.getStrikePrice();
		}
		return strike;
	}
	
	public Double getValue(Double marketPrice) { // TODO: stock handling
		Double value = 0.0;
		Enum invType = getInvestment().getInvType();
		if( invType.equals(InvType.CALL) ||
			invType.equals(InvType.PUT)) {
			InvestmentOption option = (InvestmentOption) getInvestment();
			value = option.getValue(marketPrice) * getQuantity();
		}
		if(getTradeType().equals(TradeType.SELL)) {
			value = -value;
		}
		return value;
	}
	
//	public Double getMaxProfit() { 
//		Double value=0.0;
//		// TODO
//		return value;		
//	}
//
//	public Double getMaxLoss() { // TODO: other investment types
//		Double sum=0.0;
//		// TODO
//		return sum;		
//	}

	// PRIVATE
	private void setNetCost() {	
		Double cost = getQuantity() * getUnitPrice();
		
		if (getTradeType().equals(TradeType.BUY)) {
			this.netCost = -cost;
		} else { 
			this.netCost = cost;		
		}
	}

	public Double getNetCost() {
		return(this.netCost);
	}		
	
	// PRINT
	public String toString() {
		String string = ((InvestmentOption)getInvestment()).toString() + " " + getTradeType() + " Quantity: " + getQuantity() + " Net Cost: $" + getNetCost();
		System.out.println("TRADE " + string);
		return (string);
	}

	
	// SET GET
	public Investment getInvestment() {
		return investment;
	}

	private void setInvestment(Investment inv) {
		this.investment = inv;
	}

	public Enum getTradeType() {
		return tradeType;
	}

	private void setTradeType(Enum tradeType) {
		this.tradeType = tradeType;
	}

	public int getQuantity() {
		return quantity;
	}

	private void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	private Double getUnitPrice() {
		return this.unitPrice;
	}

	private void setUnitPrice(Double price) {
		this.unitPrice = price;
	}

}




