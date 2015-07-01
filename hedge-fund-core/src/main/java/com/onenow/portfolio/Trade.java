package com.onenow.portfolio;

import com.onenow.constant.InvType;
import com.onenow.constant.TradeType;
import com.onenow.instrument.Investment;
import com.onenow.instrument.InvestmentOption;
import com.onenow.risk.Reward;
import com.onenow.risk.Risk;

public class Trade {

	public Investment investment; // INTC, option, call
	public TradeType tradeType; // ie. buy
	public int quantity; // 50 shares
	public Double netPremium; // +$10 net, -$23 net
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
		InvType invType = investment.getInvType();
		if (invType.equals(InvType.CALL) || invType.equals(InvType.PUT)) {
			InvestmentOption option = (InvestmentOption) investment;
			strike = option.getStrikePrice();
		}
		return strike;
	}

	public Double getValue(Double marketPrice) { // TODO: stock handling
		Double value = 0.0;
		InvType invType = investment.getInvType();
		if (invType.equals(InvType.CALL) || invType.equals(InvType.PUT)) {
			InvestmentOption option = (InvestmentOption) investment;
			value = option.getValue(marketPrice) * quantity;
		}
		if (tradeType.equals(TradeType.SELL)) {
			value = -value;
		}
		return value;
	}
	
	public Risk getRisk() {
		Risk risk;
		risk = investment.getRisk();
		return risk;
	}
	
	public Reward getReward() {
		Reward reward;
		reward = investment.getReward();
		return reward;
	}

	// PRIVATE
	private void setNetPremium() {
		Double cost = quantity * unitPrice;

		if (tradeType.equals(TradeType.BUY)) {
			setNetPremium(-cost);
		} else {
			setNetPremium(cost);
		}
	}

	// PRINT
	public String toString() {
		String s = "";
		s = s + tradeType + " " + quantity + " " + investment + ". " + 
			  "Net Cost: $" + netPremium;
		return (s);
	}

	// SET GET

	private void setInvestment(Investment inv) {
		this.investment = inv;
	}

	private void setTradeType(TradeType tradeType) {
		this.tradeType = tradeType;
	}

	private void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	private void setUnitPrice(Double price) {
		this.unitPrice = price;
	}

	private void setNetPremium(Double netPremium) {
		this.netPremium = netPremium;
	}

}
