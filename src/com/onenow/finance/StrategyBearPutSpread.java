package com.onenow.finance;

import java.util.Date;

public class StrategyBearPutSpread extends StrategyPutSpread {
	
	public StrategyBearPutSpread(	Underlying under, int quantity, Date exp,
									Double callSpread, Double callBuyPrice, 
									Double callSellStrike, Double callSellPrice) {		
		super(	under, quantity, exp,
				callSellStrike-callSpread, callBuyPrice, 
				callSellStrike, callSellPrice); 
		}
}
