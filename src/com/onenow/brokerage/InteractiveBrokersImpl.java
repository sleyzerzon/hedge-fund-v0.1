package com.onenow.brokerage;

import java.util.ArrayList;
import java.util.List;

import com.onenow.orchestrator.Underlying;

public class InteractiveBrokersImpl implements InteractiveBrokers{

	@Override
	public List<Underlying> getUnderlying() {
		
		List<Underlying> underList = new ArrayList<Underlying>();
		
		Underlying apl = new Underlying("APL");
		Underlying intc = new Underlying("INTC");
		Underlying rus = new Underlying("RUS");
		Underlying amzn = new Underlying("AMZN");

		underList.add(apl);
		underList.add(intc);
		underList.add(rus);
		underList.add(amzn);
		
		return underList;
	}
	
}


