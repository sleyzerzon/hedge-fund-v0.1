package com.onenow.portfolio;

import com.onenow.instrument.InvIndex;
import com.onenow.instrument.Underlying;

public class EntranceExitDecisioning {

	Underlying under;
	
	public EntranceExitDecisioning() {
		
	}

	public EntranceExitDecisioning(Underlying under) {

	}

	// PUBLIC
	public boolean EnterNowAtTop() {
		boolean enter = false;
		
		// TODO: detect top of market
		
		return enter;
	}

	public boolean EnterNowAtBottom() {
		boolean enter = true;
		
		// TODO: detect bottom of market 
		
		return enter;
	}

	public boolean exitNow() {
		boolean enter = true;
		
		// TODO: determine it's time to exit 
		
		return enter;
	}


	// PRIVATE
	private static boolean isVolumeSpike() {
		return true;
	}
	
	private static boolean isFuturesGuidingUp(){
		return true;
	}
	
	private static boolean isFuturesGuidingDown() {
		return true;
	}
	
	private boolean isCounterMarket() { // price under VWAP in bull market, over in bear market
		return true;
	}
	
	private static boolean isUnderVWAP(Integer buffer) {
		return true;
	}
	private static boolean isOverVWAP(Integer buffer) { 
		return true;
	}
	private static boolean isMomentumReversedUp() { // commensurate: by deviation
		return isPriceUp() && isVolumeUp();
	}
	
	private static boolean isPriceUp() {
		return true;
	}
	private static boolean isVolumeUp() {
		return true;
	}
	
	private static boolean isMomentumReversedDown() { // commensurate: by deviation 
		return isPriceDown() && isVolumeDown();
	}
	private static boolean isPriceDown() {
		return true;
	}
	private static boolean isVolumeDown() {
		return true;
	}
	
	private static boolean isGoalAchieved() {
		return false;
	}
	
	private static boolean isMarketClose() {
		return false;
	}
	private static boolean isBullMarket() {
		boolean bull=true;
		return bull;
	}
	private static boolean isBearMarket() {
		return !isBullMarket();
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

	
	// SET GET
	private Underlying getUnder() {
		return under;
	}

	private void setUnder(Underlying under) {
		this.under = under;
	}

	

}
