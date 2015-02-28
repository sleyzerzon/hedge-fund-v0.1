package com.onenow.analyst;

import java.util.List;

public class AnalysisPriceOnly {
	List<Candle> prices;
	
	public AnalysisPriceOnly(List<Candle> prices) {
		setPrices(prices);
	}
	
	// SUPPORT AND RESISTANCE
	public boolean isAtResistanceOrSupport() {
		boolean supportOrResistance = false;
		// TODO: within 5-10 points of level defended 3 times
		return supportOrResistance;
	}
	
	// MOST POWERFUL
	private boolean isThreeWhiteSoldiers() { 
		boolean threeWhiteSoldiers = false;
		// TODO
		return threeWhiteSoldiers;
	}
	
	private boolean isThreeBlackCrows() {
		boolean threeBlackCrows = false;
		// TODO
		return threeBlackCrows;
	}
	
	public boolean isEngulfing(int which) { // break into up and down
		boolean engulfing = false;
		if(which>0) {
			if(isHighUpAndLowDown(which)) {
				engulfing=true;
			}
		}
		return engulfing;
	}

	
	// SEQUENCES OF HIGHS AND LOWS
	private Integer getPreviousHigh(Integer which) {
		Integer previous = 0;
		if(which>0) {
			
		}
		return previous;
	}
	private Integer getPreviousLow(Integer which) {
		Integer previous = 0;
		if(which>0) {
			
		}
		return previous;
	}
	
	
	// ISOLATED
	public boolean isIsolatedHigh(int which) {
		boolean isolatedHigh = false;
		if(which>0) {
			if(isHighUp(which)) {
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
			if(isLowDown(which)) {
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
			if(isHighUp(which-1) && isLowDown(which)) {
				threeBarReversalDown = true;
			}
		}
		return threeBarReversalDown;
	}
	
	public boolean isThreeBarReversalUp(int which) {
		boolean threeBarReversalUp = false;
		if(which>1) {
			if(isLowDown(which-1) && isHighUp(which)) {
				threeBarReversalUp = true;
			}
		}		
		return threeBarReversalUp;
	}
	
	// DOJI
	public boolean isDojiBar(int which) {
		boolean dojiBar = false;
		if(!isCloseUp(which) && !isCloseDown(which)) {
			dojiBar = true;
		}
		return dojiBar;
	}
	
	// IN OUT
	public boolean isOutsideBar(int which) {
		boolean outsideBar = false;
		if(isHighUpAndLowDown(which) && isCloseDown(which)) { // directionally down
			outsideBar = true;
		}
		return outsideBar;
	}
	
	public boolean isInsideBar(int which) {
		boolean insideBar = false;
		if(!isHighUp(which) && !isLowDown(which)) {
			insideBar = true;
		}
		return insideBar;
	}

	// PRIVATE
	public boolean isIgnore(Integer which) {
		boolean ignore = false;
		if(which.equals(0)) {
			ignore=true;
			System.out.println("ignore: first element");
		}
		if(which>0) {
			Candle previous = getPrices().get(which-1);
			Candle current = getPrices().get(which);
			if(!isHigherHighPrice(previous, current)) {
				ignore=true;
				System.out.println("ignore: not higher high");
			}
			if(!isLowerLowPrice(previous, current)) {
				ignore=true;
			}
			if(isHigherHighAndLowerLow(previous, current)) {
				if( !(current.getHighPrice()==current.getClosePrice()) ||
					 !(current.getLowPrice()==current.getClosePrice()) ) {
					ignore=true;
					System.out.println("ignore: higher high and lower low, but not closing high/low");
				}
			}
		}
		return ignore;
	}
	
	// OPEN AND CLOSE
	private boolean isCloseUp(int which) {
		boolean closeUp = false;
		if(isIgnore(which)) {
			return false;
		}
		if(which>0) {
			Candle previous = getPrices().get(which-1);
			Candle current = getPrices().get(which);
			if(current.getClosePrice()>previous.getClosePrice()) {
				
			}
		}
		return closeUp;
	}
	
	private boolean isCloseDown(int which) {
		boolean closeDown = false;
		if(isIgnore(which)) {
			return false;
		}
		if(which>0) {
			Candle previous = getPrices().get(which-1);
			Candle current = getPrices().get(which);
			if(current.getClosePrice()<previous.getClosePrice()) {
				closeDown = true;
			}
		}		
		return closeDown;
	}
	
	// HIGH AND LOW
	private boolean isHighUpAndLowDown(Integer which) {
		boolean isUpAndDown = false;
		if(isHighUp(which) && isLowDown(which)) {
			isUpAndDown = true;
		}
		return isUpAndDown;
	}

	private boolean isHighUp(Integer which) {
		boolean isUp = false;
		if(isIgnore(which)) {
			return false;
		}
		if(which>0) {
			Candle previous = getPrices().get(which-1);
			Candle current = getPrices().get(which);
			if(isHigherHighPrice(previous, current)) {
				isUp=true;
			}
		}
		return isUp;
	}
	
	private boolean isLowDown(Integer which) {
		boolean isDown = false;
		if(isIgnore(which)) {
			return false;
		}
		if(which>0) {
			Candle previous = getPrices().get(which-1);
			Candle current = getPrices().get(which);
			if(isLowerLowPrice(previous, current)) {
				isDown=true;
			}
		}
		return isDown;
	}
		
	// CANDLE COMPARISON
	private boolean isHigherHighPrice(Candle previous, Candle current) {
		boolean higher = false;
		if(current.getHighPrice()>previous.getHighPrice()) {
			higher = true;
		}
		return higher;
	}

	private boolean isLowerLowPrice(Candle previous, Candle current) {
		boolean lower = false;
		if(current.getLowPrice()<previous.getLowPrice()) {
			lower = true;
		}		
		return lower;
	}

	private boolean isHigherHighAndLowerLow(Candle previous, Candle current) {
		boolean isBoth = false;
		if(isHigherHighPrice(previous, current) && isLowerLowPrice(previous, current)) {
			isBoth=true;
		}
		return isBoth;
	}


	// TEST
	
	
	// PRINT
	public String toString() {
		String s = "";
		
		for(int i=0; i<getPrices().size(); i++) {
			s = s + "> PRICE [" + i + "]\t";  
			s = s + toString(i);
		}
			
		return s;
	}
	
	public String toString(int which) {
		String s = "";
		if(isIgnore(which)) {
			s = "= IGNORE" + "\n";
		} else {
			if(isAtResistanceOrSupport()) {
				s = "!!! @SUPPORT/RESISTANCE" + "\n";
			}
			if(isEngulfing(which)) {
				s = "ENGULFING: *most* powerful reversal down";
			}
			if(isThreeWhiteSoldiers()) {
				s = "THREE WHITE SOLIDERS: *" + "\n";
			}
			if(isThreeBlackCrows()) {
				s = "THREE BLACK CROWS: *" + "\n";				
			}
			
			if(isIsolatedHigh(which)) {
				s = "ISOLATED HIGH: look at closing (moving UP if closes near high, else moving DOWN)" + "\n";
			}
			if(isIsolatedLow(which)) {
				s = "ISOLATED LOW: look at closing (moving DOWN if closes near low, else moving UP)" + "\n";
			}
			if(isThreeBarReversalDown(which)) {
				s = "THREE BAR REVERSAL DOWN" + "\n";
			}
			if(isThreeBarReversalUp(which)) {
				s = "THREE BAR REVERSAL UP" + "\n";
			}
			if(isDojiBar(which)) {
				s = "DOJI BAR: reversal?" + "\n";
			}
			if(isOutsideBar(which)) {
				s = "OUTSIDE BAR: moving down" + "\n";
			}
			if(isInsideBar(which)) {
				s = "INSDIE BAR: pausing" + "\n";
			}
		}
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
