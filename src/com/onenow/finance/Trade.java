package com.onenow.finance;

public class Trade {
	
	private Investment investment; // INTC, option, call
	private Enum tradeType; // ie. buy
	private int quantity; // 50 shares
	private Double netCost; // +$10 net, -$23 net
	private MarketPrice marketPrice;  // $7.50 per share
	

	public Trade(Investment inv, Enum tradeType, int quantity, MarketPrice market) {
		setInvestment(inv);
		setTradeType(tradeType);
		setQuantity(quantity);
		setMarketPrice(market);
		setNetCost();
	}
	
	public void setNetCost() {	
		Double cost = getQuantity() * getMarketPrice().getBuyPrice(getInvestment());
		
		if (getTradeType().equals(TradeType.BUY)) {
			this.netCost = -cost;
		} else { 
			this.netCost = cost;		
		}
	}

	public Double getNetCost() {
		return(this.netCost);
	}
	
	public Double getNet(InvType invType) {
		if(getInvestment().getInvestmentType().equals(invType)) {
			return(getNetCost());
		} else return (0.0);		
	}
	
	
	public String toString() {
		String string = investment.toString() + " " + getTradeType() + " " + getQuantity() + " " + getNetCost();
		System.out.println("Trade: " + string);
		return (string);
	}

	
	
	public Investment getInvestment() {
		return investment;
	}

	public void setInvestment(Investment inv) {
		this.investment = inv;
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
	public MarketPrice getMarketPrice() {
		return this.marketPrice;
	}

	public void setMarketPrice(MarketPrice marketPrice) {
		this.marketPrice = marketPrice;
	}

}





