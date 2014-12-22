package com.onenow.orchestrator;
import java.util.List;

public class TradeTransaction {
		
	private Counterparty counterParty;
	
	private List<Trade> trades;
	
	private Float netStock;
	private Float netCall;
	private Float netPut;
	
	public void Transaction() {
		// constructor
	}
	
	public void addTrade(Trade trade) {
		// 
	}
	
	public void delTrade(Trade trade) {
		// 
	}

	public String toString() {
		String string = "";
		for (Trade trade : this.trades) {
			trade.toString();
			string.concat(trade.toString());
		}
		return string;
	}
	
	public Float getNetCost() {
		return (getNetStock()+getNetCall()+getNetPut());
	}

	public List<Trade> getTrades() {
		return trades;
	}

	public void setTrades(List<Trade> trades) {
		this.trades = trades;
	}

	public Counterparty getCounterParty() {
		return counterParty;
	}

	public void setCounterParty(Counterparty counterParty) {
		this.counterParty = counterParty;
	}

	public Float getNetStock() {
		return netStock;
	}

	public void setNetStock(Float netStock) {
		this.netStock = netStock;
		}

	public Float getNetCall() {
		return netCall;
	}

	public void setNetCall(Float netCall) {
		this.netCall = netCall;
	}

	public Float getNetPut() {
		return netPut;
	}

	public void setNetPut(Float netPut) {
		this.netPut = netPut;
	}


}
