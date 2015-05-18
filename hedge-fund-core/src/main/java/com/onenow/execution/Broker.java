package com.onenow.execution;

import java.util.List;

import com.onenow.constant.BrokerMode;
import com.onenow.constant.TradeType;
import com.onenow.instrument.Investment;
import com.onenow.instrument.Underlying;
import com.onenow.portfolio.Portfolio;
import com.onenow.portfolio.Trade;
import com.onenow.portfolio.Transaction;

public  interface Broker {
	public abstract BrokerMode getMode();
	public abstract List<Underlying> getUnderlying();
	public abstract Portfolio getMarketPortfolio();
	public abstract Portfolio getMyPortfolio();
	public abstract Double getPrice(Investment inv, TradeType type);
	public abstract List<Trade> getTrades();
	public abstract void enterTransaction(Transaction trans); 
	public abstract void readHistoricalQuotes(Investment inv, String end, QuoteHistory history); 
}
