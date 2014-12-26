package com.onenow.broker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.onenow.finance.Investment;
import com.onenow.finance.Underlying;

public class BrokerWallStIntBroActivityImpl implements BrokerWallSt, BrokerWallStIntBroActivity {

	@Override
	public List<Underlying> getUnderlying() {
		List<Underlying> list = new ArrayList<Underlying>();
		// TODO 
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

	
	
}


