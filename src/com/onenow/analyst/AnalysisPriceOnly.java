package com.onenow.analyst;

import java.util.List;

public class AnalysisPriceOnly {
	List<Candle> prices;
	
	// TODO: CHANNES, WEDGES, RAISING RANGES, 
	public AnalysisPriceOnly(List<Candle> prices) {
		setPrices(prices);
	}
		
	// MOST POWERFUL
	public boolean isUpTrend(int which) { // THREE WHITE SOLDIERS: three+ sessions
		boolean upTrend = false;
		
		if(which>1) {			
			upTrend = isOpenUpCurrentToPrevious(which-2) && 
					  isCloseUpCurrentToSelf(which-2) &&
					  isOpenUpCurrentToPrevious(which-1) && 
					  isCloseUpCurrentToSelf(which-1) &&
					  isOpenUpCurrentToPrevious(which) && 
					  isCloseUpCurrentToSelf(which);

		}
		return upTrend;
	}

	public boolean isDownTrend(int which) { // THREE BLACK CROWS: three+ sessions
		boolean downTrend = false;

		if(which>1) {
			downTrend = isOpenDownCurrentToPrevious(which-2) &&
					    isCloseDownCurrentToSelf(which-2) &&
					    isOpenDownCurrentToPrevious(which-1) && 
					    isCloseDownCurrentToSelf(which-1) &&
					    isOpenDownCurrentToPrevious(which) && 
					    isCloseDownCurrentToSelf(which);
		}

		return downTrend;
	}
	
	public boolean isEngulfingBearish(int which) { 
		boolean engulfing = false;
		if(which>0) {
			if(isHighUpAndLowDownCurrentToPrevious(which) && 
			   isOutsideBarClosingDown(which)) {
				engulfing=true;
			}
		}
		return engulfing;
	}

	public boolean isEngulfingBullish(int which) { 
		boolean engulfing = false;
		if(which>0) {
			if(isHighUpAndLowDownCurrentToPrevious(which) &&
			   isOutsideBarClosingUp(which)) {
				engulfing=true;
			}
		}
		return engulfing;
	}

	public boolean isPriceGap(int which) {
		boolean gap = false;
		if(which>0) {
			
			
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
		}
		
		return narrowRange;
	}
	
	public boolean isReversalDay(int which) {
		boolean reversalDay = false;
	
		if(isUpTrend(which-1) && !isUpTrend(which)) {			
			reversalDay = true;
		}
		if(isDownTrend(which-1) && !isDownTrend(which)) {
			reversalDay = true;
		}
		return reversalDay;
	}
	

	// IN OUT: CLARIFIES IF ENGULFING UP/DOWN
	public boolean isOutsideBarClosingDown(int which) {
		boolean outsideBar = false;
		if(isHighUpAndLowDownCurrentToPrevious(which) && isCloseDownCurrentToSelf(which)) { 
			outsideBar = true;
		}
		return outsideBar;
	}

	public boolean isOutsideBarClosingUp(int which) {
		boolean outsideBar = false;
		if(isHighUpAndLowDownCurrentToPrevious(which) && isCloseUpCurrentToSelf(which)) { 
			outsideBar = true;
		}
		return outsideBar;
	}
	
	public boolean isInsideBar(int which) {
		boolean insideBar = false;
		if(which>0) {
			if(isHighDownCurrentToPrevious(which) && isLowUpCurrentToPrevious(which)) {
				insideBar = true;
			}
		}
		return insideBar;
	}
	
	// IMPORTANCE
	public boolean isIgnorePriceSignalForVolume(Integer which) {
		boolean ignore = false;
		if(which.equals(0)) {
			ignore=true;
			System.out.println("ignore: first element");
		}
		if(which>0) {
			Candle previous = getPrices().get(which-1);
			Candle current = getPrices().get(which);
			if(!isHigherHighPriceCurrentToPrevious(previous, current)) {
				ignore=true;
				System.out.println("ignore: not higher high");
			}
			if(!isLowerLowPriceCurrentToPrevious(previous, current)) {
				ignore=true;
			}
			if(isHigherHighAndLowerLowCurrentToPrevious(previous, current)) {
				if( !(current.getHighPrice()==current.getClosePrice()) ||
					 !(current.getLowPrice()==current.getClosePrice()) ) {
					ignore=true;
					System.out.println("ignore: higher high and lower low, but not closing high/low");
				}
			}
		}
		return ignore;
	}
	
	public boolean isLessImportantPriceSignal() {
		boolean lessImportant = false;
		
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
			if(isHighUpCurrentToPrevious(which)) {
				Candle current = getPrices().get(which);
				if(current.getClosePrice()<current.getHighPrice()) {
					isolatedHigh=true;
				}
			}
		}
		return isolatedHigh;
	}
	public boolean isIsolatedLow(int which) {
		boolean isolatedLow = false;
		if(which>0) {
			if(isLowDownCurrentToPrevious(which)) {
				Candle current = getPrices().get(which);
				if(current.getClosePrice()>current.getLowPrice()) {
					isolatedLow=true;
				}
			}		
		}
		return isolatedLow;
	}
		
	// THREE BAR REVERSAL
	public boolean isThreeBarReversalDown(int which) {
		boolean threeBarReversalDown = false;
		if(which>1) {
			if(isHighUpCurrentToPrevious(which-1) && isLowDownCurrentToPrevious(which)) {
				threeBarReversalDown = true;
			}
		}
		return threeBarReversalDown;
	}
	
	public boolean isThreeBarReversalUp(int which) {
		boolean threeBarReversalUp = false;
		if(which>1) {
			if(isLowDownCurrentToPrevious(which-1) && isHighUpCurrentToPrevious(which)) {
				threeBarReversalUp = true;
			}
		}		
		return threeBarReversalUp;
	}
	
	// DOJI
	public boolean isDojiBar(int which) {
		boolean dojiBar = false;
		if(!isCloseUpCurrentToSelf(which) && !isCloseDownCurrentToSelf(which)) {
			dojiBar = true;
		}
		return dojiBar;
	}
	
	
	// OPEN AND CLOSE: CURRENT
	private boolean isOpenUpCurrentToPrevious(int which) {
		boolean openUp = false;
		if(isIgnorePriceSignalForVolume(which)) {
			return false;
		}
		if(which>0) {
			Candle current = getPrices().get(which);
			if(current.getOpenPrice()>current.getClosePrice()) {
				
			}
		}
		return openUp;
	}

	private boolean isOpenDownCurrentToPrevious(int which) {
		boolean openUp = false;
		if(isIgnorePriceSignalForVolume(which)) {
			return false;
		}
		if(which>0) {
			Candle current = getPrices().get(which);
			if(current.getOpenPrice()<current.getClosePrice()) {
				
			}
		}
		return openUp;
	}

	private boolean isCloseUpCurrentToSelf(int which) {
		boolean closeUp = false;
		if(isIgnorePriceSignalForVolume(which)) {
			return false;
		}
		if(which>0) {
			Candle current = getPrices().get(which);
			if(current.getClosePrice()>current.getOpenPrice()) {
				
			}
		}
		return closeUp;
	}
	
	private boolean isCloseDownCurrentToSelf(int which) {
		boolean closeDown = false;
		if(isIgnorePriceSignalForVolume(which)) {
			return false;
		}
		if(which>0) {
			Candle current = getPrices().get(which);
			if(current.getClosePrice()<current.getOpenPrice()) {
				closeDown = true;
			}
		}		
		return closeDown;
	}
	
	// HIGH AND LOW: CURRENT TO PREVIOUS
	private boolean isHighUpAndLowDownCurrentToPrevious(Integer which) {
		boolean isUpAndDown = false;
		if(isHighUpCurrentToPrevious(which) && isLowDownCurrentToPrevious(which)) {
			isUpAndDown = true;
		}
		return isUpAndDown;
	}

	private boolean isHighUpCurrentToPrevious(Integer which) {
		boolean isUp = false;
		if(isIgnorePriceSignalForVolume(which)) {
			return false;
		}
		if(which>0) {
			Candle previous = getPrices().get(which-1);
			Candle current = getPrices().get(which);
			if(isHigherHighPriceCurrentToPrevious(previous, current)) {
				isUp=true;
			}
		}
		return isUp;
	}

	private boolean isHighDownCurrentToPrevious(Integer which) {
		boolean isUp = false;
		if(isIgnorePriceSignalForVolume(which)) {
			return false;
		}
		if(which>0) {
			Candle previous = getPrices().get(which-1);
			Candle current = getPrices().get(which);
			if(isLowerHighPriceCurrentToPrevious(previous, current)) {
				isUp=true;
			}
		}
		return isUp;
	}

	private boolean isLowDownCurrentToPrevious(Integer which) {
		boolean isDown = false;
		if(isIgnorePriceSignalForVolume(which)) {
			return false;
		}
		if(which>0) {
			Candle previous = getPrices().get(which-1);
			Candle current = getPrices().get(which);
			if(isLowerLowPriceCurrentToPrevious(previous, current)) {
				isDown=true;
			}
		}
		return isDown;
	}

	private boolean isLowUpCurrentToPrevious(Integer which) {
		boolean isDown = false;
		if(isIgnorePriceSignalForVolume(which)) {
			return false;
		}
		if(which>0) {
			Candle previous = getPrices().get(which-1);
			Candle current = getPrices().get(which);
			if(isHigherLowPriceCurrentToPrevious(previous, current)) {
				isDown=true;
			}
		}
		return isDown;
	}

	// CANDLE COMPARISON: CURRENT TO PREVIOUS
	private boolean isHigherHighPriceCurrentToPrevious(Candle previous, Candle current) {
		boolean higher = false;
		if(current.getHighPrice()>previous.getHighPrice()) {
			higher = true;
		}
		return higher;
	}
	
	private boolean isLowerHighPriceCurrentToPrevious(Candle previous, Candle current) {
		boolean higher = false;
		if(current.getHighPrice()<previous.getHighPrice()) {
			higher = true;
		}
		return higher;
	}

	private boolean isLowerLowPriceCurrentToPrevious(Candle previous, Candle current) {
		boolean lower = false;
		if(current.getLowPrice()<previous.getLowPrice()) {
			lower = true;
		}		
		return lower;
	}

	private boolean isHigherLowPriceCurrentToPrevious(Candle previous, Candle current) {
		boolean lower = false;
		if(current.getLowPrice()>previous.getLowPrice()) {
			lower = true;
		}		
		return lower;
	}

	private boolean isHigherHighAndLowerLowCurrentToPrevious(Candle previous, Candle current) {
		boolean isBoth = false;
		if(isHigherHighPriceCurrentToPrevious(previous, current) && isLowerLowPriceCurrentToPrevious(previous, current)) {
			isBoth=true;
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
		s = s + "> PRICE(" + which + ")\t= ";  

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
		s = s + "REVERSAL. " + "\t";  
		
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
		String s = "\t";
		s = s + "CONFIRMATION. " + "\t";
		
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
		String s = "\t"; 
		s = s + "SWING. " + "\t";
		
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
		String s = "\t"; 
		s = s + "CONTINUATION" + "\t";

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
	
}
