package com.onenow.broker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.amazonaws.services.simpleworkflow.flow.core.Promise;
import com.onenow.database.DatabaseSystemSForce;
import com.onenow.finance.Investment;
import com.onenow.finance.Underlying;

public class BrokerActivityImpl implements BrokerWallSt, BrokerCloud, BrokerActivity {

	private static BrokerWallStEmulator brokerEmulator;
	private static BrokerWallStIntBro brokerIntBro;
	
	public BrokerActivityImpl() {
		setBrokerEmulator(new BrokerWallStEmulator());
		setBrokerIntBro(new BrokerWallStIntBro());
	}

	
	@Override
	public List<Underlying> getUnderlying() {
		List<Underlying> list = new ArrayList<Underlying>();
		
		List<Underlying> emulatorList = getBrokerEmulator().getUnderlying();
		// List<Underlying> intBroList = getBrokerIntBro().getUnderlying();
		List<Underlying> intBroList = new ArrayList<Underlying>();
		
		list.addAll(emulatorList);
		list.addAll(intBroList);
		 
		return list;
	}


	@Override
	public Double getPriceAsk(Investment inv) {
		Double number = 0.0;
		// TODO Auto-generated method stub
		return number;
	}

	@Override
	public Double getPriceBid(Investment inv) {
		Double number = 0.0;
		// TODO Auto-generated method stub
		return number;
	}

	@Override
	public Investment getBest(Underlying under, Enum invType) {
		Investment inv = new Investment();
		// TODO Auto-generated method stub
		return inv;
	}

	@Override
	public Investment getBest(Underlying under, Enum invType, Date expiration,
			Double strike) {
		Investment inv = new Investment();
		// TODO Auto-generated method stub
		return inv;
	}

	@Override
	public Investment getBest(Underlying under, Enum invType, Enum InvTerm) {
		// TODO Auto-generated method stub
		return null;
	}


	private static BrokerWallStEmulator getBrokerEmulator() {
		return brokerEmulator;
	}


	private static void setBrokerEmulator(BrokerWallStEmulator brokerEmulator) {
		BrokerActivityImpl.brokerEmulator = brokerEmulator;
	}


	private static BrokerWallStIntBro getBrokerIntBro() {
		return brokerIntBro;
	}


	private static void setBrokerIntBro(BrokerWallStIntBro brokerIntBro) {
		BrokerActivityImpl.brokerIntBro = brokerIntBro;
	}

	
	
}


