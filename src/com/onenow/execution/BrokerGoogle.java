package com.onenow.execution;

import java.util.List;

import com.onenow.constant.TradeType;
import com.onenow.instrument.Investment;
import com.onenow.instrument.Underlying;
import com.onenow.portfolio.Portfolio;
import com.onenow.portfolio.Trade;
import com.onenow.portfolio.Transaction;

public class BrokerGoogle implements Broker {

	@Override
	public List<Underlying> getUnderlying() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Portfolio getMyPortfolio() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Trade> getTrades() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void enterTransaction(Transaction trans) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Portfolio getMarketPortfolio() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double getPrice(Investment inv, TradeType type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void readHistoricalQuotes(Investment inv, String end,
			QuoteHistory history) {
		// TODO Auto-generated method stub
		
	}

}
