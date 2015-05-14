package com.onenow.execution;

import java.util.Date;
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

/**
 * Standard interface for all brokers
 * Architecture to run as an Amazon Simple Work Flow activity 
 */
@ActivityRegistrationOptions(	defaultTaskScheduleToStartTimeoutSeconds = 300, 
								defaultTaskStartToCloseTimeoutSeconds = 300, 
								defaultTaskList = ConstantsWorkflow.AWS_SWF_TASK_LIST_NAME)
@Activities(version = ConstantsWorkflow.AWS_SWF_VERSION_DEV)
public interface BrokerActivity extends Broker {
	
	public List<Underlying> getUnderlying();
	public Portfolio getMarketPortfolio();
	public Portfolio getMyPortfolio();
	public Double getBestBid(TradeType type, Investment inv, Double agression); // the extension
	public Double getPrice(Investment inv, TradeType type);
	public List<Trade> getTrades();
	public void enterTransaction(Transaction trans);
}