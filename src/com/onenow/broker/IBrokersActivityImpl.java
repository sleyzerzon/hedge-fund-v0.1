package com.onenow.broker;

import java.util.Date;
import java.util.List;

import com.onenow.finance.Investment;
import com.onenow.finance.Underlying;

public class IBrokersActivityImpl implements BrokerWallSt, IBrokersActivity {

	@Override
	public List<Underlying> getUnderlying() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double getPriceAsk(Investment inv) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double getPriceBid(Investment inv) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Investment getBest(Underlying under, Enum invType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Investment getBest(Underlying under, Enum invType, Date expiration,
			Double strike) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}


