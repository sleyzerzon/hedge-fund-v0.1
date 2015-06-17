package com.onenow.alpha;

import java.util.List;

import com.onenow.constant.StreamName;
import com.onenow.constant.TradeType;
import com.onenow.data.HistorianConfig;
import com.onenow.data.QuoteHistory;
import com.onenow.instrument.Investment;
import com.onenow.instrument.Underlying;
import com.onenow.portfolio.Portfolio;
import com.onenow.portfolio.Trade;
import com.onenow.portfolio.Transaction;

public  interface BrokerInterface {
	public abstract List<Underlying> getUnderlying();
	public abstract Portfolio getMarketPortfolio();
	public abstract Portfolio getMyPortfolio();
	public abstract Double getPrice(Investment inv, TradeType type);
	public abstract List<Trade> getTrades();
	public abstract void enterTransaction(Transaction trans); 
	public abstract Integer readHistoricalQuotes(Investment inv, String end, HistorianConfig config, QuoteHistory history);
	public abstract StreamName getStream();
}
