package com.onenow.exchange;
import java.util.List;

public class Transaction {
		
	private Counterparty broker;
	
	private List<Trade> trades;

	public Counterparty getBroker() {
		return broker;
	}

	public void setBroker(Counterparty broker) {
		this.broker = broker;
	}

	public List<Trade> getTrades() {
		return trades;
	}

	public void setTrades(List<Trade> trades) {
		this.trades = trades;
	}

}
