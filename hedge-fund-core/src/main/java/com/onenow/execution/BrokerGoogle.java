package com.onenow.execution;

import java.util.List;

import com.onenow.alpha.BrokerInterface;
import com.onenow.constant.StreamName;
import com.onenow.constant.PriceType;
import com.onenow.data.HistorianConfig;
import com.onenow.data.QuoteHistoryInvestment;
import com.onenow.instrument.Investment;
import com.onenow.instrument.Underlying;
import com.onenow.portfolio.Portfolio;
import com.onenow.portfolio.Trade;
import com.onenow.portfolio.Transaction;

public class BrokerGoogle implements BrokerInterface {

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
	public Double getPrice(Investment inv, PriceType type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer readHistoricalQuotes(Investment inv, String end,
			HistorianConfig config, QuoteHistoryInvestment history) {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public StreamName getStream() {
		// TODO Auto-generated method stub
		return null;
	}

}
