package com.onenow.execution;

import java.util.List;

import com.amazonaws.services.simpleworkflow.flow.annotations.Activities;
import com.amazonaws.services.simpleworkflow.flow.annotations.Activity;
import com.amazonaws.services.simpleworkflow.flow.annotations.ActivityRegistrationOptions;
import com.onenow.constant.ConstantsWorkflow;
import com.onenow.constant.TradeType;
import com.onenow.instrument.Investment;
import com.onenow.instrument.Portfolio;
import com.onenow.instrument.Trade;
import com.onenow.instrument.Transaction;
import com.onenow.instrument.Underlying;

public  interface Broker {
	public abstract List<Underlying> getUnderlying();
	public abstract Portfolio getMarketPortfolio();
	public abstract Portfolio getMyPortfolio();
	public abstract Double getPrice(Investment inv, String type);
	public abstract List<Trade> getTrades();
	public abstract void enterTransaction(Transaction trans); 
}
