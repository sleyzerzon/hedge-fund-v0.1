package com.onenow.broker;

import java.util.List;

import com.amazonaws.services.simpleworkflow.flow.annotations.Activities;
import com.amazonaws.services.simpleworkflow.flow.annotations.Activity;
import com.amazonaws.services.simpleworkflow.flow.annotations.ActivityRegistrationOptions;
import com.onenow.finance.Investment;
import com.onenow.finance.Trade;
import com.onenow.finance.Transaction;
import com.onenow.finance.Underlying;
import com.onenow.workflow.ConstantsWorkflow;

public  interface Analyst {
	public abstract List<Underlying> getUnderlying();

	
}
