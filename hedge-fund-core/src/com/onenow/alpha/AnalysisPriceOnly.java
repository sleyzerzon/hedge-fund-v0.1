package com.onenow.alpha;

import java.util.ArrayList;
import java.util.List;

import com.onenow.research.Candle;

public class AnalysisPriceOnly {
	List<Candle> prices;
	
	List<Integer> newHighIndex;
	List<Integer> newLowIndex;
	
	// TODO: CHANNES, WEDGES, RAISING RANGES, 
	public AnalysisPriceOnly(List<Candle> prices) {
		setPrices(prices);
	}
		
	// MOST POWERFUL
	public boolean isUpTrend(int which) { // THREE WHITE SOLDIERS: three+ sessions
		boolean upTrend = false;
		
		if(which>1) {			
			upTrend = isOpenPriceUpCurrentToPrevious(which-2) && 
					  isClosePriceUpCurrentToSelf(which-2) &&
					  isOpenPriceUpCurrentToPrevious(which-1) && 
					  isClosePriceUpCurrentToSelf(which-1) &&
					  isOpenPriceUpCurrentToPrevious(which) && 
					  isClosePriceUpCurrentToSelf(which);
			
			if(upTrend) {
				System.out.println("\t\t"  + "up-trend " + (which-2) + " to " + (which-1) + " to " + which);
			}

		}
		return upTrend;
	}

	public boolean isDownTrend(int which) { // THREE BLACK CROWS: three+ sessions
		boolean downTrend = false;

		if(which>1) {
			downTrend = isOpenPriceDownCurrentToPrevious(which-2) &&
					    isClosePriceDownCurrentToSelf(which-2) &&
					    isOpenPriceDownCurrentToPrevious(which-1) && 
					    isClosePriceDownCurrentToSelf(which-1) &&
					    isOpenPriceDownCurrentToPrevious(which) && 
					    isClosePriceDownCurrentToSelf(which);

			if(downTrend) {
				System.out.println("\t\t"  + "down-trend " + (which-2) + " to " + (which-1) + " to " + which);

			}
		}

		return downTrend;
	}
	
	public boolean isEngulfingBearish(int which) { 
		boolean engulfing = false;
		if(which>0) {
			if(isHigherHighAndLowerLowPriceCurrentToPreviousIndex(which) && 
			   isOutsideBarClosingDown(which)) {
				engulfing=true;
				System.out.println("\t\t"  + "engulfing bearish at " + which);
			}
		}
		return engulfing;
	}

	public boolean isEngulfingBullish(int which) { 
		boolean engulfing = false;
		if(which>0) {
			if(isHigherHighAndLowerLowPriceCurrentToPreviousIndex(which) &&
			   isOutsideBarClosingUp(which)) {
				engulfing=true;
				System.out.println("\t\t"  + "engulfing bullish at " + which);
			}
		}
		return engulfing;
	}

	public boolean isPriceGap(int which) {
		boolean gap = false;
		if(which>0) {
			if(isOpenPriceUpCurrentToPrevious(which) || 
					isOpenPriceDownCurrentToPrevious(which)) {
				gap = true;
				if(gap) {
					System.out.println("\t\t"  + "gap at " + which);
				}
			}
		}
		return gap;
	}
	
	// SWING
	public boolean isNarrowRangeDay(int which) {  
		boolean narrowRange = false;
		// TODO DOJI by small % rather than exactly equal
		
		Candle current = getPrices().get(which);
		Double range = Math.abs(current.getClosePrice()-current.getOpenPrice());
		if(range<5) {
			narrowRange = true;
			System.out.println("\t\t"  + "narrow range at " + which);
		}
		
		return narrowRange;
	}
	
	public boolean isReversalDay(int which) {
		boolean reversalDay = false;
	
		if(isUpTrend(which-1) && !isUpTrend(which)) {			
			reversalDay = true;
			System.out.println("\t\t"  + "reversal day up at " + which);
		}
		if(isDownTrend(which-1) && !isDownTrend(which)) {
			System.out.println("\t\t"  + "reversal day down at " + which);
			reversalDay = true;
		}
		return reversalDay;
	}
	

	// IN OUT: CLARIFIES IF ENGULFING UP/DOWN
	public boolean isOutsideBarClosingDown(int which) {
		boolean outsideBar = false;
		if(isHigherHighAndLowerLowPriceCurrentToPreviousIndex(which) && 
				isClosePriceDownCurrentToSelf(which)) { 
			outsideBar = true;
			System.out.println("\t\t"  + "outside bar closing down at " + which);
		}
		return outsideBar;
	}

	public boolean isOutsideBarClosingUp(int which) {
		boolean outsideBar = false;
		if(isHigherHighAndLowerLowPriceCurrentToPreviousIndex(which) && 
				isClosePriceUpCurrentToSelf(which)) { 
			outsideBar = true;
			System.out.println("\t\t"  + "outside bar closign up at " + which);
		}
		return outsideBar;
	}
	
	public boolean isInsideBar(int which) {
		boolean insideBar = false;
		if(which>0) {
			if(isLowerHighPriceCurrentToPreviousIndex(which) && 
					isHigherLowPriceCurrentToPreviousIndex(which)) {
				insideBar = true;
				System.out.println("\t\t"  + "inside bar at " + which);
			}
		}
		return insideBar;
	}
	
	// LAST HIGH/LOW LINE
	public void setMeaningfulHighsAndLowsForVolume() {
		setNewHighIndex(new ArrayList<Integer>());
		setNewLowIndex(new ArrayList<Integer>());
		for(int i=0; i<getPrices().size(); i++) {
			if(!isIgnorePriceSignalForVolume(i)) {
				if(isHigherHighPriceCurrentToPreviousIndex(i)) {
					getNewHighIndex().add(i);
					System.out.println("\t\t"  + "new high at " + i);
				}
				if(isLowerLowPriceCurrentToPreviousIndex(i)) {
					getNewLowIndex().add(i);
					System.out.println("\t\t"  + "new low at " + i);
				}
			}
		}
	}

	public boolean isIndexNewHigh(Integer which) { 
		boolean newHigh = false;

		Integer last = getNewHighIndex().size()-1; 
		for(int i=last; i>=0; i++) {
			if(getNewHighIndex().get(last).equals(which)) { // for which specifically
				newHigh = true;
				System.out.println("\t\t"  + "current is new high at " + which);
			}
		}
		return newHigh;
	}
		
	public boolean isCurrentIndexNewLow(Integer which) { // looks back
		boolean newLow = false;

		Integer last = getNewLowIndex().size()-1; 
		for(int i=last; i>=0; i++) {		
			if(getNewLowIndex().get(last).equals(which)) { // for which specifically
				newLow = true;
				System.out.println("\t\t"  + "current is low high at " + which);
			}
		}

		return newLow;
	}

	
	// IMPORTANCE
	public boolean isIgnorePriceSignalForVolume(Integer which) {
		boolean ignore = false;
		if(which.equals(0)) {
			ignore=true;
			System.out.println("\t\t"  + "ignore: first element");
		}
		if(which>0) {
			Candle previous = getPrices().get(which-1);
			Candle current = getPrices().get(which);
			if(!isHigherHighPriceCurrentToPreviousCandle(previous, current)) {
				ignore=true;
				System.out.println("\t\t"  + "ignore: not higher high");
			}
			if(!isLowerLowPriceCurrentToPreviousCandle(previous, current)) {
				ignore=true;
				System.out.println("\t\t"  + "ignore: not lower low");
			}
			if(isHigherHighAndLowerLowCurrentToPreviousCandle(previous, current)) {
				if( !(current.getHighPrice()==current.getClosePrice()) ||
					 !(current.getLowPrice()==current.getClosePrice()) ) {
					ignore=true;
					System.out.println("\t\t"  + "ignore: higher high and lower low, but not closing high/low");
				}
			}
		}
		return ignore;
	}
	
	public boolean isLessImportantPriceSignal() {
		boolean lessImportant = false;
		
		// TODO
		lessImportant = !isAtResistanceOrSupport();
		
		return lessImportant;
	}
	
	public boolean isAtResistanceOrSupport() {
		boolean supportOrResistance = false;
		// TODO: within 5-10 points of level defended 3 times
		return supportOrResistance;
	}
	
	
	// ISOLATED
	public boolean isIsolatedHigh(int which) {
		boolean isolatedHigh = false;
		if(which>0) {
			if(isHigherHighPriceCurrentToPreviousIndex(which)) {
				Candle current = getPrices().get(which);
				if(current.getClosePrice()<current.getHighPrice()) {
					isolatedHigh=true;
					System.out.println("\t\t"  + "isolated high at " + which);
				}
			}
		}
		return isolatedHigh;
	}
	public boolean isIsolatedLow(int which) {
		boolean isolatedLow = false;
		if(which>0) {
			if(isLowerLowPriceCurrentToPreviousIndex(which)) {
				Candle current = getPrices().get(which);
				if(current.getClosePrice()>current.getLowPrice()) {
					isolatedLow=true;
					System.out.println("\t\t"  + "isolated low at " + which);
				}
			}		
		}
		return isolatedLow;
	}
		
	// THREE BAR REVERSAL
	public boolean isThreeBarReversalDown(int which) {
		boolean threeBarReversalDown = false;
		if(which>1) {
			if(isHigherHighPriceCurrentToPreviousIndex(which-1) && 
					isLowerLowPriceCurrentToPreviousIndex(which)) {
				threeBarReversalDown = true;
				System.out.println("\t\t"  + "three bar reversal down at " + which);
			}
		}
		return threeBarReversalDown;
	}
	
	public boolean isThreeBarReversalUp(int which) {
		boolean threeBarReversalUp = false;
		if(which>1) {
			if(isLowerLowPriceCurrentToPreviousIndex(which-1) && 
					isHigherHighPriceCurrentToPreviousIndex(which)) {
				threeBarReversalUp = true;
				System.out.println("\t\t"  + "three bar reversal up at " + which);
			}
		}		
		return threeBarReversalUp;
	}
	
	// DOJI
	public boolean isDojiBar(int which) {
		boolean dojiBar = false;
		if(!isClosePriceUpCurrentToSelf(which) && 
				!isClosePriceDownCurrentToSelf(which)) {
			dojiBar = true;
			System.out.println("\t\t"  + "doji at " + which);
		}
		return dojiBar;
	}
	
	
	// OPEN AND CLOSE: CURRENT
	private boolean isOpenPriceUpCurrentToPrevious(int which) {
		boolean openUp = false;
		double benchmarkGap = 0.5; // TODO: correct? do based on underlying price
		
		if(isIgnorePriceSignalForVolume(which)) {
			return false;
		}
		if(which>0) {
			Candle current = getPrices().get(which);
			Candle previous = getPrices().get(which-1);
			if(current.getOpenPrice()> (previous.getClosePrice()+benchmarkGap) ) {
				openUp = true;
				System.out.println("\t\t"  + "opening up by " + benchmarkGap + " at " + which);
			}
		}
		return openUp;
	}

	private boolean isOpenPriceDownCurrentToPrevious(int which) {
		boolean openDown = false;
		if(isIgnorePriceSignalForVolume(which)) {
			return false;
		}
		if(which>0) {
			Candle current = getPrices().get(which);
			Candle previous = getPrices().get(which-1);
			if(current.getOpenPrice()<previous.getClosePrice()) {
				openDown = true;
				System.out.println("\t\t"  + "opening down at " + which);
			}
		}
		return openDown;
	}

	private boolean isClosePriceUpCurrentToSelf(int which) {
		boolean closeUp = false;
		if(isIgnorePriceSignalForVolume(which)) {
			return false;
		}
		if(which>0) {
			Candle current = getPrices().get(which);
			if(current.getClosePrice()>current.getOpenPrice()) {
				closeUp = true;
				System.out.println("\t\t"  + "close up at " + which);
			}
		}
		return closeUp;
	}
	
	private boolean isClosePriceDownCurrentToSelf(int which) {
		boolean closeDown = false;
		if(isIgnorePriceSignalForVolume(which)) {
			return false;
		}
		if(which>0) {
			Candle current = getPrices().get(which);
			if(current.getClosePrice()<current.getOpenPrice()) {
				closeDown = true;
				System.out.println("\t\t"  + "close down at " + which);
			}
		}		
		return closeDown;
	}
	
	// HIGH AND LOW: CURRENT TO PREVIOUS
	private boolean isHigherHighAndLowerLowPriceCurrentToPreviousIndex(Integer which) {
		boolean isUpAndDown = false;
		if(isHigherHighPriceCurrentToPreviousIndex(which) && 
				isLowerLowPriceCurrentToPreviousIndex(which)) {
			isUpAndDown = true;
			System.out.println("\t\t"  + "higher high and lower low at " + which);
		}
		return isUpAndDown;
	}

	public boolean isHigherHighPriceCurrentToPreviousIndex(Integer which) {
		boolean isUp = false;
		if(isIgnorePriceSignalForVolume(which)) {
			return false;
		}
		if(which>0) {
			Candle previous = getPrices().get(which-1);
			Candle current = getPrices().get(which);
			if(isHigherHighPriceCurrentToPreviousCandle(previous, current)) {
				isUp=true;
				System.out.println("\t\t"  + "higher high at " + which);
			}
		}
		return isUp;
	}

	private boolean isLowerHighPriceCurrentToPreviousIndex(Integer which) {
		boolean isUp = false;
		if(isIgnorePriceSignalForVolume(which)) {
			return false;
		}
		if(which>0) {
			Candle previous = getPrices().get(which-1);
			Candle current = getPrices().get(which);
			if(isLowerHighPriceCurrentToPreviousCandle(previous, current)) {
				isUp=true;
				System.out.println("\t\t"  + "lower high at " + which);
			}
		}
		return isUp;
	}

	public boolean isLowerLowPriceCurrentToPreviousIndex(Integer which) {
		boolean isDown = false;
		if(isIgnorePriceSignalForVolume(which)) {
			return false;
		}
		if(which>0) {
			Candle previous = getPrices().get(which-1);
			Candle current = getPrices().get(which);
			if(isLowerLowPriceCurrentToPreviousCandle(previous, current)) {
				isDown=true;
				System.out.println("\t\t"  + "lower low at " + which);
			}
		}
		return isDown;
	}

	private boolean isHigherLowPriceCurrentToPreviousIndex(Integer which) {
		boolean isDown = false;
		if(isIgnorePriceSignalForVolume(which)) {
			return false;
		}
		if(which>0) {
			Candle previous = getPrices().get(which-1);
			Candle current = getPrices().get(which);
			if(isHigherLowPriceCurrentToPreviousCandle(previous, current)) {
				isDown=true;
				System.out.println("\t\t"  + "higher low at " + which);
			}
		}
		return isDown;
	}

	// CANDLE COMPARISON: CURRENT TO PREVIOUS
	public boolean isHigherHighPriceCurrentToPreviousCandle(Candle previous, Candle current) {
		boolean higher = false;
		if(current.getHighPrice()>previous.getHighPrice()) {
			higher = true;
			System.out.println("\t\t"  + "higher high at current to prevous candle");
		}
		return higher;
	}
	
	private boolean isLowerHighPriceCurrentToPreviousCandle(Candle previous, Candle current) {
		boolean higher = false;
		if(current.getHighPrice()<previous.getHighPrice()) {
			higher = true;
			System.out.println("\t\t"  + "lower high at current to prevous candle");
		}
		return higher;
	}

	public boolean isLowerLowPriceCurrentToPreviousCandle(Candle previous, Candle current) {
		boolean lower = false;
		if(current.getLowPrice()<previous.getLowPrice()) {
			lower = true;
			System.out.println("\t\t"  + "lower low at current to prevous candle");
		}		
		return lower;
	}

	private boolean isHigherLowPriceCurrentToPreviousCandle(Candle previous, Candle current) {
		boolean lower = false;
		if(current.getLowPrice()>previous.getLowPrice()) {
			lower = true;
			System.out.println("\t\t"  + "higher low at current to prevous candle");
		}		
		return lower;
	}

	private boolean isHigherHighAndLowerLowCurrentToPreviousCandle(Candle previous, Candle current) {
		boolean isBoth = false;
		if(isHigherHighPriceCurrentToPreviousCandle(previous, current) && isLowerLowPriceCurrentToPreviousCandle(previous, current)) {
			isBoth=true;
			System.out.println("\t\t"  + "higher high and lower low at current to prevous candle");
		}
		return isBoth;
	}


	// TEST
	
	
	// PRINT
	public String toString() {
		String s = "";
		
		for(int i=0; i<getPrices().size(); i++) {
			s = s + toString(i);
		}
			
		return s;
	}
	
	public String toString(int which) {
		String s = "";
		s = s + "> PRICE(" + which + ")";  

		// EMPHASIS
		if(isAtResistanceOrSupport()) {  // TODO: ignore all others otherwise; not reversal otherwise
			s = s + "!!! @SUPPORT/RESISTANCE. ";
		}

		s = s + toStringReversal(which) + "\n";
		s = s + toStringConfirmation(which) + "\n";
		s = s + toStringSwing(which) + "\n";
		s = s + toStringContinuation(which) + "\n";
		
		return s;
	}
	
	public String toStringReversal(int which) {
		String s = "\t";
		s = s + "REVERSAL " + "\t= ";  
		
		// REVERSAL SIGNALS
		// head and shoulders
		// double top or bottom
		// wedges
		// triangles
		if(isPriceGap(which)) {
			s = s + "PRICE GAP: *fast* REVERSAL signal. ";
		}		
		// OTHER REVERSAL SIGNALS (swinch ch6)
		// cup and handle reversal
		// price spike reversal
		
		return s;
	}
	
	public String toStringConfirmation(int which) {
		String s = "\t\t";
		s = s + "CONFIRMATION " + "\t= ";
		
		// MOST IMPORTANT: CONFIRMATION
		// bullish engulfing
		// bearish engulfing
		// three white soldiers
		// three black crows
		
		if(isEngulfingBearish(which)) {
			s = s + "ENGULFING: *most* powerful REVERSAL DOWN (80%). ";
		}
		if(isEngulfingBullish(which)) {
			s = s + "ENGULFING: *most* powerful REVERSAL UP (80%). ";
		}
		
		// inside up
		// inside down
		// outside up
		// outside down
		if(isOutsideBarClosingDown(which)) {
			s = s + "OUTSIDE BAR: moving down. ";
		}
		if(isOutsideBarClosingUp(which)) {
			s = s + "OUTSIDE BAR: moving up. ";
		}
		if(isInsideBar(which)) {
			s = s + "INSDIE BAR: pausing. ";
		}
		
		return s;
	}
	
	public String toStringSwing(int which) {
		String s = "\t\t"; 
		s = s + "SWING " + "\t\t= ";
		
		// SWING TRADING
		if(isNarrowRangeDay(which)) {
			s = s + "*NARROW* RANGE. ";
		}
		
		if(isReversalDay(which)) {
			s = s + "*REVERSAL* DAY. ";
		}

		if(isUpTrend(which)) { 
			s = s + "UP-TREND: three white soldiers. ";
		}

		if(isDownTrend(which)) { 
			s = s + "DOWN-TREND: three black crows. ";
		}
		
		// OTHER SIGNALS
		if(isIsolatedHigh(which)) {
			s = s + "ISOLATED HIGH: look at closing (moving UP if closes near high, else moving DOWN). ";
		}
		if(isIsolatedLow(which)) {
			s = s + "ISOLATED LOW: look at closing (moving DOWN if closes near low, else moving UP). ";
		}
		if(isThreeBarReversalDown(which)) {
			s = s + "THREE BAR REVERSAL DOWN. ";
		}
		if(isThreeBarReversalUp(which)) {
			s = s + "THREE BAR REVERSAL UP. ";
		}
		
		return s;
	}
	
	public String toStringContinuation(int which) {
		String s = "\t\t"; 
		s = s + "CONTINUATION " + "\t= ";

		//CONINUATION SIGNALS
		// long-legged doji and spinning top
		// doji star
		if(isDojiBar(which)) {
			s = s + "DOJI BAR: reversal? ";
		}
		// thrusting lines
		// separating lines
		// in neck lines
		// black side-by-side lines
		// tasuki gap
		// gap filled
		
		
		// YET MORE REVERSAL SIGNALS
		// hammer and hanging man
		// harami
		// harami cross
		// inverted hammer
		// abandoned baby
		// squeeze alert
		// morning star and evening star
		
		return s;
	}
	
	// SET GET
	private List<Candle> getPrices() {
		return prices;
	}


	private void setPrices(List<Candle> prices) {
		this.prices = prices;
	}

	private List<Integer> getNewHighIndex() {
		return newHighIndex;
	}

	private void setNewHighIndex(List<Integer> newHighIndex) {
		this.newHighIndex = newHighIndex;
	}

	private List<Integer> getNewLowIndex() {
		return newLowIndex;
	}

	private void setNewLowIndex(List<Integer> newLowIndex) {
		this.newLowIndex = newLowIndex;
	}

	
}
