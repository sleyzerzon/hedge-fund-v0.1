package com.onenow.broker;

import java.util.Date;
import java.util.List;

import com.amazonaws.services.simpleworkflow.flow.annotations.Activities;
import com.amazonaws.services.simpleworkflow.flow.annotations.Activity;
import com.amazonaws.services.simpleworkflow.flow.annotations.ActivityRegistrationOptions;
import com.onenow.finance.Investment;
import com.onenow.finance.Trade;
import com.onenow.finance.Transaction;
import com.onenow.finance.Underlying;
import com.onenow.workflow.ConstantsWorkflow;

@ActivityRegistrationOptions(	defaultTaskScheduleToStartTimeoutSeconds = 300, 
								defaultTaskStartToCloseTimeoutSeconds = 300, 
								defaultTaskList = ConstantsWorkflow.AWS_SWF_TASK_LIST_NAME)
@Activities(version = ConstantsWorkflow.AWS_SWF_VERSION_DEV)
public interface BrokerActivity extends Broker {
	
	public List<Underlying> getUnderlying();
	public List<Investment> getInvestments(boolean myPortfolio);
	public Double getPriceAsk(Investment inv);
	public Double getPriceBid(Investment inv);
	public Investment getBest(Underlying under, Enum invType);
//	public Investment getBest(Underlying under, Enum invType, Date expiration, Double strike);
//	public Investment getBest(Underlying under, Enum invType, Enum InvTerm);
	public List<Trade> getTrades();
	public void addTrade(Transaction transaction);
}