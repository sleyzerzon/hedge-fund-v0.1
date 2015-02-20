package com.onenow.finance;

public class StrategyCall {
	
	StrategyCallSpread callSpread;
	
	public StrategyCall() {
		
	}
	
	public StrategyCall(Trade callBuy) {
		
		setCallSpread(new StrategyCallSpread(callBuy, new Trade())); // use buy side only
		
	}

	// SET GET
	private StrategyCallSpread getCallSpread() {
		return callSpread;
	}

	private void setCallSpread(StrategyCallSpread callSpread) {
		this.callSpread = callSpread;
	}

}
