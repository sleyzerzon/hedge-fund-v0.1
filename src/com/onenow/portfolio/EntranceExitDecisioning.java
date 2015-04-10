package com.onenow.portfolio;

import com.onenow.instrument.InvestmentIndex;
import com.onenow.instrument.Underlying;

public class EntranceExitDecisioning {

	Underlying under;
	
	public EntranceExitDecisioning() {
		
	}

	public EntranceExitDecisioning(Underlying under) {

	}

	public boolean EnterNowAtTop() {
		boolean enter = false;
		
		return enter;
	}

	public boolean EnterNowAtBottom() {
		boolean enter = true;
		
		return enter;
	}

	public boolean exitNow() {
		boolean enter = true;
		
		return enter;
	}


//	if(isBullMarket()) { // TODO: futures market?
//	// has been trading in range! And it is breaking out!
//	if(isVolumeSpike() &&isMomentumReversedUp() && isFuturesGuidingUp()) { // BUY call
////		isUnderVWAP(6)
//		launchBottomExocet();
//	}
//	if(isVolumeSpike() && isMomentumReversedDown() && isFuturesGuidingDown()) { // BUY put
//		// isOverVWAP(12)
//		// launchTopExocet();
//	}
//}
//if(isBearMarket()){
//	
//}
//if(isGoalAchieved() || isMarketClose() ) { // SELL
//	
//}

	private Underlying getUnder() {
		return under;
	}

	private void setUnder(Underlying under) {
		this.under = under;
	}

	

}
