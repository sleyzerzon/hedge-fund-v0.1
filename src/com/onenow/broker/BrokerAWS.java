package com.onenow.broker;

import java.util.List;

import com.onenow.finance.Investment;
import com.onenow.finance.Portfolio;
import com.onenow.finance.Trade;
import com.onenow.finance.TradeType;
import com.onenow.finance.Transaction;
import com.onenow.finance.Underlying;

public class BrokerAWS implements Broker {

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

}
