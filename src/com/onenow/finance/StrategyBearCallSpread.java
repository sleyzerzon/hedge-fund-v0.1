package com.onenow.finance;

import java.util.Date;

public class StrategyBearCallSpread extends StrategyCallSpread {

	public StrategyBearCallSpread(	Underlying under, int quantity, Date exp,
			  						Double callBuyStrike, Double callBuyPrice, 
			  						Double callSpread, Double callSellPrice) {		
		super(	under, quantity, exp,
				callBuyStrike, callBuyPrice, 
				callBuyStrike-callSpread, callSellPrice); 
	}

}
