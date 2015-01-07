package com.onenow.finance;

import java.util.Date;

public class StrategyBullCallSpread extends StrategyCallSpread {
	
	public StrategyBullCallSpread(	Underlying under, int quantity, Date exp,
									Double callSpread, Double callBuyPrice, 
									Double callSellStrike, Double callSellPrice) {		
		super(	under, quantity, exp,
				callSellStrike-callSpread, callBuyPrice, 
				callSellStrike, callSellPrice); 
		}
}
