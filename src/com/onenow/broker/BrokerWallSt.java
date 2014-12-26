package com.onenow.broker;

import java.util.Date;

import com.amazonaws.services.simpleworkflow.flow.annotations.Activity;
import com.onenow.finance.Investment;
import com.onenow.finance.Underlying;

public interface BrokerWallSt extends Broker {
	public abstract Investment getBest(Underlying under, Enum invType); // stocks
	public abstract Investment getBest(	Underlying under, Enum invType, // options
										Date expiration, Double strike);

}
