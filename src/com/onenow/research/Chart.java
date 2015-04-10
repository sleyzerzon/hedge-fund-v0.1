package com.onenow.research;

import java.util.ArrayList;
import java.util.List;

import com.onenow.alpha.AnalysisMomentum;
import com.onenow.alpha.AnalysisPriceOnly;
import com.onenow.alpha.AnalysisVolumePrice;
import com.onenow.portfolio.Stats;

public class Chart {
	
	List<Candle> prices = new ArrayList<Candle>();
	List<Integer> sizes = new ArrayList<Integer>();

	AnalysisPriceOnly priceOnlyAnalysis;
	AnalysisVolumePrice volumePriceAnalysis;
	AnalysisMomentum momentumAnalysis;
	
	Stats sizeStats;
	Stats priceSpreadStats;
	Stats priceSpreadToSizeRatioStats;
	
//	Stats VWAPSpreadStats;
//	Stats VWAPSpreadToSizeRatioStats;
	

	// TODO take only complete candles; ignore opening / close high volume?
	public Chart() {
	}
	
	// PUBLIC	
	public void setAnalysis() {
		setPriceOnlyAnalysis(new AnalysisPriceOnly(prices));
		setVolumePriceAnalysis(new AnalysisVolumePrice(prices, sizes, priceOnlyAnalysis));
		setMomentumAnalysis(new AnalysisMomentum());		
	}
	
	public String getAnalysis(int which) {
		String s = "";
		s = s + getPriceAnalysis(which) + "\n";
		s = s + getVolumeAnalysis(which) + "\n";
		s = s + getMomentumAnalysis(which) + "\n";
		return s;
	}
	
	public String getPriceAnalysis(int which) {
		String s = "";
		s = s + getPriceOnlyAnalysis().toString(which) + "\n";		
		return s;
	}
	
	public String getVolumeAnalysis(int which) {
		String s = "";
		s = s + getVolumePriceAnalysis().toString(which) + "\n";		
		return s;
	}
	
	public String getMomentumAnalysis(int which) {
		String s = "";
		s = s + getMomentumAnalysis().toString(which) + "\n";		
		return s;
	}
	
	
	// PRIVATE

	
	// TEST
	
	// PRINT
	public String toString() {
		String s="";
		s = s + prices.toString() + "\n" +
				sizes.toString();
		return s;
	}

	
	// SET GET
	public Stats getSizeStats() {
		return sizeStats;
	}

	private void setSizeStats(Stats sizeStats) {
		this.sizeStats = sizeStats;
	}

	public Stats getPriceSpreadStats() {
		return priceSpreadStats;
	}

	private void setPriceSpreadStats(Stats priceStats) {
		this.priceSpreadStats = priceStats;
	}

	public Stats getPriceSpreadToSizeRatioStats() {
		return priceSpreadToSizeRatioStats;
	}

	private void setPriceSpreadToSizeRatioStats(Stats priceSpreadToSizeRatioStats) {
		this.priceSpreadToSizeRatioStats = priceSpreadToSizeRatioStats;
	}

	public List<Candle> getPrices() {
		return prices;
	}

	public void setPrices(List<Candle> prices) {
		this.prices = prices;
	}

	public List<Integer> getSizes() {
		return sizes;
	}

	public void setSizes(List<Integer> sizes) {
		this.sizes = sizes;
	}

	public AnalysisPriceOnly getPriceOnlyAnalysis() {
		return priceOnlyAnalysis;
	}

	private void setPriceOnlyAnalysis(AnalysisPriceOnly priceOnlyAnalysis) {
		this.priceOnlyAnalysis = priceOnlyAnalysis;
	}

	public AnalysisVolumePrice getVolumePriceAnalysis() {
		return volumePriceAnalysis;
	}

	private void setVolumePriceAnalysis(AnalysisVolumePrice volumePriceAnalysis) {
		this.volumePriceAnalysis = volumePriceAnalysis;
	}

	private AnalysisMomentum getMomentumAnalysis() {
		return momentumAnalysis;
	}

	private void setMomentumAnalysis(AnalysisMomentum momentumAnalysis) {
		this.momentumAnalysis = momentumAnalysis;
	}



}
