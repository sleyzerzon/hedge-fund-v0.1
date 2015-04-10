package com.onenow.execution;

import java.util.List;

import com.amazonaws.services.simpleworkflow.flow.annotations.Activities;
import com.amazonaws.services.simpleworkflow.flow.annotations.Activity;
import com.amazonaws.services.simpleworkflow.flow.annotations.ActivityRegistrationOptions;
import com.onenow.constant.ConstantsWorkflow;
import com.onenow.constant.TradeType;
import com.onenow.instrument.Investment;
import com.onenow.instrument.Underlying;
import com.onenow.portfolio.Portfolio;
import com.onenow.portfolio.Trade;
import com.onenow.portfolio.Transaction;

public  interface Broker {
	public abstract List<Underlying> getUnderlying();
	public abstract Portfolio getMarketPortfolio();
	public abstract Portfolio getMyPortfolio();
	public abstract Double getPrice(Investment inv, String type);
	public abstract List<Trade> getTrades();
	public abstract void enterTransaction(Transaction trans); 
}
