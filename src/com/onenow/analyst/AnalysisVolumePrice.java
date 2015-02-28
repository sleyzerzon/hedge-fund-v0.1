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
	

	
	// TEST
	
	
	// PRINT
	public String toString() {
		String s = "";
		
		return s;
	}

	public String toString(int which) {
		String s = "";
		
		s = s + "> VOLUME(" + which + ")\t= ";  

		if(getPriceAnalysis().isIgnorePriceSignalForVolume(which)) {
			s = s + "IGNORE. ";
		} else {
			
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
