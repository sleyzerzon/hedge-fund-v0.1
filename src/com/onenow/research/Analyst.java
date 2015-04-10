package com.onenow.research;

import java.util.List;

import com.amazonaws.services.simpleworkflow.flow.annotations.Activities;
import com.amazonaws.services.simpleworkflow.flow.annotations.Activity;
import com.amazonaws.services.simpleworkflow.flow.annotations.ActivityRegistrationOptions;
import com.onenow.constant.ConstantsWorkflow;
import com.onenow.instrument.Investment;
import com.onenow.instrument.Trade;
import com.onenow.instrument.Transaction;
import com.onenow.instrument.Underlying;

public  interface Analyst {
	public abstract List<Underlying> getUnderlying();

	
}
