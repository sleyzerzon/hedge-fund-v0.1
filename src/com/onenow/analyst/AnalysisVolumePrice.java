package com.onenow.analyst;

import java.util.List;

public class AnalysisVolumePrice {

	List<Candle> prices;
	List<Integer> sizes;
	AnalysisPriceOnly priceAnalysis;

	// on up-trend: volume increases on high
	// on down-trend: volume decreases on lows
	public AnalysisVolumePrice(List<Candle> prices, List<Integer> sizes, AnalysisPriceOnly analysis) {
		setPrices(prices);
		setSizes(sizes);
		setPriceAnalysis(analysis);
	}
	

	public boolean isVolumeWeaknessForUptrend(Integer which) {
		
		boolean uptrendWeakness = false;
		
		if(!getPriceAnalysis().isIgnorePriceSignalForVolume(which) && 
			getPriceAnalysis().isHighUpCurrentToPrevious(which)) {
			
			getPriceAnalysis().setMeaningfulHighsAndLowsForVolume();
			
			Integer currentHighIndex = getPriceAnalysis().getCurrentHighIndex(which);
			Integer previousHighIndex = getPriceAnalysis().getPreviousHighIndex(which); 
	
			if( getSizes().get(currentHighIndex) < getSizes().get(previousHighIndex) ) {
				uptrendWeakness = true;
			}
		}
		
		return uptrendWeakness;
	}
	
	public boolean isVolumeWeaknessForDowntrend(Integer which) {
		
		boolean downtrendWeakness = false;
		
		if(!getPriceAnalysis().isIgnorePriceSignalForVolume(which) && 
			getPriceAnalysis().isHighUpCurrentToPrevious(which)) {
			
			getPriceAnalysis().setMeaningfulHighsAndLowsForVolume();
	
			Integer currentLowIndex = getPriceAnalysis().getCurrentLowIndex(which);
			Integer previousLowIndex = getPriceAnalysis().getPreviousLowIndex(which);
			
			if( getSizes().get(currentLowIndex) < getSizes().get(previousLowIndex)) {
				downtrendWeakness = true;
			}
		}
		return downtrendWeakness;
	}
	
	public void getSlope() {
		
		// getPreviousHighIndex
		
	}
	
	// TEST
	
	
	// PRINT
	public String toString() {
		String s = "";
		
		return s;
	}

	public String toString(int which) {
		String s = "";
		
		s = s + "> VOLUME(" + which + ")\t= ";  
		
		// VOLUME INDICATORS
		// On Balance Volume (OBV)
		// Chaikin Money Flow (CMF)
		// Accumulation Distribution (A/D)

		if(getPriceAnalysis().isIgnorePriceSignalForVolume(which)) {
			s = s + "IGNORE. ";
		} else {
			
			if(isVolumeWeaknessForUptrend(which)) {
				s = s + "***ENTRY POINT*** up-trend weakness. ";
			}

			if(isVolumeWeaknessForDowntrend(which)) {
				s = s + "***ENTRY POINT*** down-trend weakness. ";
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

	private List<Integer> getSizes() {
		return sizes;
	}

	private void setSizes(List<Integer> sizes) {
		this.sizes = sizes;
	}


	private AnalysisPriceOnly getPriceAnalysis() {
		return priceAnalysis;
	}


	private void setPriceAnalysis(AnalysisPriceOnly priceAnalysis) {
		this.priceAnalysis = priceAnalysis;
	}
	
	//
}
