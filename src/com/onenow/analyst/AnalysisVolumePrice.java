package com.onenow.analyst;

import java.util.List;

public class AnalysisVolumePrice {

	List<Candle> prices;
	List<Integer> sizes;
	AnalysisPriceOnly priceAnalysis;

	public AnalysisVolumePrice(List<Candle> prices, List<Integer> sizes, AnalysisPriceOnly analysis) {
		setPrices(prices);
		setSizes(sizes);
		setPriceAnalysis(analysis);
	}
	

	// ANOMALIES
	// on up-trend: volume should increase on high
	// on down-trend: volume increase on low
	public boolean isVolumeWeaknessForUptrend(Integer which) { // signal before entry
		boolean uptrendWeakness = false;
		if(!getPriceAnalysis().isIgnorePriceSignalForVolume(which) && 
			getPriceAnalysis().isHigherHighPriceCurrentToPreviousIndex(which)) {
			Integer currentHighIndex = which;
			Integer previousHighIndex = which-1;
			if( getSizes().get(currentHighIndex) < getSizes().get(previousHighIndex) ) {
				uptrendWeakness = true;
				System.out.println("weak high: " + previousHighIndex + " to " + currentHighIndex);
			}
		}
		return uptrendWeakness;
	}
	
	public boolean isVolumeWeaknessForDowntrend(Integer which) { // signal before entry
		boolean downtrendWeakness = false;
		if(!getPriceAnalysis().isIgnorePriceSignalForVolume(which) && 
			getPriceAnalysis().isLowerLowPriceCurrentToPreviousIndex(which)) {
			Integer currentLowIndex = which;
			Integer previousLowIndex = which-1;
			if( getSizes().get(currentLowIndex) < getSizes().get(previousLowIndex)) {
				downtrendWeakness = true;
				System.out.println("weak low: " + previousLowIndex + " to " + currentLowIndex);
			}
		}
		return downtrendWeakness;
	}
	
	public boolean isVolumeHighAnomaly(Integer which) {
		boolean volumeAnomaly = false;	
		if(		!getPriceAnalysis().isHigherHighPriceCurrentToPreviousIndex(which) ||
				!getPriceAnalysis().isLowerLowPriceCurrentToPreviousIndex(which) ) {
		
				if(getSizes().get(which) > getSizes().get(which-1)) {
					volumeAnomaly = true;
					System.out.println("volume high anomaly: " + which + " to " + (which-1));
				}				
		}
		return volumeAnomaly;
	}

		
	// AGREEMENT
	// after weakness signal
	// use this for confirmation before entry
	public boolean isVolumePriceAgreementUp(Integer which) { 		
		boolean volumePriceAgreement = false;
		if(!getPriceAnalysis().isIgnorePriceSignalForVolume(which) && 
				getPriceAnalysis().isHigherHighPriceCurrentToPreviousIndex(which)) {
			Integer currentHighIndex = which;
			Integer previousHighIndex = which-1;
			if( getSizes().get(currentHighIndex) > getSizes().get(previousHighIndex) ) {
				volumePriceAgreement = true;
				System.out.println("volume-price agreement high: " + previousHighIndex + " to " + currentHighIndex);
			}
		}
		return volumePriceAgreement;
	}

	public boolean isVolumePriceAgreementDown(Integer which) { 		
		boolean volumePriceAgreement = false;
		if(!getPriceAnalysis().isIgnorePriceSignalForVolume(which) && 
				getPriceAnalysis().isLowerLowPriceCurrentToPreviousIndex(which)) {
				Integer currentLowIndex = which;
				Integer previousLowIndex = which-1;
				if( getSizes().get(currentLowIndex) > getSizes().get(previousLowIndex)) {
					volumePriceAgreement = true;
					System.out.println("volume-price agreement low: " + previousLowIndex + " to " + currentLowIndex);
				}
			}
		return volumePriceAgreement;
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
		
		getPriceAnalysis().setMeaningfulHighsAndLowsForVolume();

		// VOLUME INDICATORS:  		
		// MOMENTUM rsi>0.45, uptrending chi (positive slope), stoch (?), 1hr....goign up
		// On Balance Volume (OBV)
		// Chaikin Money Flow (CMF)
		// Accumulation Distribution (A/D)

		if(getPriceAnalysis().isIgnorePriceSignalForVolume(which)) {
			s = s + "IGNORE. ";
		} else {
			
			if(isVolumeWeaknessForUptrend(which)) {
				s = s + "ANOMALY <<<POSSIBLE ENTRY POINT>>>: up-trend weakness. ";
			}

			if(isVolumeWeaknessForDowntrend(which)) {
				s = s + "ANOMALY <<<POSSIBLE ENTRY POINT>>>: down-trend weakness. ";
			}

			if(isVolumePriceAgreementUp(which)) {
				s = s + "<<<ENTRY POINT>>: bullish after anomaly";
			}

			if(isVolumePriceAgreementDown(which)) {
				s = s + "<<<ENTRY POINT>>: bearish after anomaly";
			}
			
			if(isVolumeHighAnomaly(which)) {
				s = s + "ANOMALY: volume high";
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
