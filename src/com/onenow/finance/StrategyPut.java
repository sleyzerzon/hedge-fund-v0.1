package com.onenow.finance;

public class StrategyPut {
	
	StrategyPutSpread putSpread;
	
	public StrategyPut() {
		
	}
	
	public StrategyPut(Trade putBuy) {
		
		setPutSpread(new StrategyPutSpread(putBuy, new Trade())); // use buy side only
		
	}

	private StrategyPutSpread getPutSpread() {
		return putSpread;
	}

	private void setPutSpread(StrategyPutSpread putSpread) {
		this.putSpread = putSpread;
	}

	// SET GET

}
