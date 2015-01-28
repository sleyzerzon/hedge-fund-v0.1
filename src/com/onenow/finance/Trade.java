package com.onenow.finance;

public class Trade {

	private Investment investment; // INTC, option, call
	private TradeType tradeType; // ie. buy
	private int quantity; // 50 shares
	private Double netPremium; // +$10 net, -$23 net
	private Double unitPrice; // $7.50 per share

	public Trade() {
		// TODO
	}

	public Trade(Investment inv, TradeType tradeType, int quantity, Double unitPrice) {
			setInvestment(inv);
			setTradeType(tradeType);
			setQuantity(quantity);
			setUnitPrice(unitPrice);
			setNetPremium();
	}

	// PUBLIC
	public Double getStrike() {
		Double strike = 0.0;
		InvType invType = getInvestment().getInvType();
		if (invType.equals(InvType.CALL) || invType.equals(InvType.PUT)) {
			InvestmentOption option = (InvestmentOption) getInvestment();
			strike = option.getStrikePrice();
		}
		return strike;
	}

	public Double getValue(Double marketPrice) { // TODO: stock handling
		Double value = 0.0;
		InvType invType = getInvestment().getInvType();
		if (invType.equals(InvType.CALL) || invType.equals(InvType.PUT)) {
			InvestmentOption option = (InvestmentOption) getInvestment();
			value = option.getValue(marketPrice) * getQuantity();
		}
		if (getTradeType().equals(TradeType.SELL)) {
			value = -value;
		}
		return value;
	}
	
	public Risk getRisk() {
		Risk risk;
		risk = getInvestment().getRisk();
		return risk;
	}
	
	public Reward getReward() {
		Reward reward;
		reward = getInvestment().getReward();
		return reward;
	}

	// PRIVATE
	private void setNetPremium() {
		Double cost = getQuantity() * getUnitPrice();

		if (getTradeType().equals(TradeType.BUY)) {
			setNetPremium(-cost);
		} else {
			setNetPremium(cost);
		}
	}

	// PRINT
	public String toString() {
		String s = "";
		s = s + getTradeType() + " " + getQuantity() + " " + getInvestment() + ". " + 
			  "Net Cost: $" + getNetPremium();
		return (s);
	}

	// SET GET
	public Investment getInvestment() {
		return investment;
	}

	private void setInvestment(Investment inv) {
		this.investment = inv;
	}

	public TradeType getTradeType() {
		return tradeType;
	}

	private void setTradeType(TradeType tradeType) {
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

	public Double getNetPremium() {
		return netPremium;
	}

	private void setNetPremium(Double netPremium) {
		this.netPremium = netPremium;
	}

}
