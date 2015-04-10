package com.onenow.execution;

import java.util.List;

import com.onenow.constant.TradeType;
import com.onenow.instrument.Investment;
import com.onenow.instrument.Portfolio;
import com.onenow.instrument.Trade;
import com.onenow.instrument.Transaction;
import com.onenow.instrument.Underlying;

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
	public Double getPrice(Investment inv, String type) {
		// TODO Auto-generated method stub
		return null;
	}

}