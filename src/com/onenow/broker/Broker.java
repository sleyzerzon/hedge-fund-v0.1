package com.onenow.broker;

import java.util.List;

import com.amazonaws.services.simpleworkflow.flow.annotations.Activities;
import com.amazonaws.services.simpleworkflow.flow.annotations.Activity;
import com.amazonaws.services.simpleworkflow.flow.annotations.ActivityRegistrationOptions;
import com.onenow.finance.Investment;
import com.onenow.finance.Underlying;
import com.onenow.workflow.ConstantsWorkflow;

public abstract interface Broker {
	public abstract List<Underlying> getUnderlying();
	public abstract List<Investment> getInvestments(boolean myPortfolio);
	public abstract Double getPriceAsk(Investment inv);
	public abstract Double getPriceBid(Investment inv);

}
