package com.onenow.analyst;

import java.util.ArrayList;
import java.util.List;

import com.onenow.investor.Stats;

public class Chart {
	
	List<Candle> prices;
	List<Integer> sizes;
	
	Stats sizeStats;
	Stats priceSpreadStats;
	Stats VWAPSpreadStats;
	Stats priceSpreadToSizeRatioStats;
	Stats VWAPSpreadToSizeRatioStats;
	
	
	public Chart() {
//		setCandles(new ArrayList<Candle>());
	}
	
	// PUBLIC
	
	// TODO take only complete candles; ignore opening / close high volume?

	public boolean isLastCandleNormal() { 
		boolean normal=true;
		Integer last = getPrices().size()-1;
		normal = isCandleNormal(last);
		return normal;
	}
	public boolean isCandleNormal(Integer which) { // VWAP-based
		boolean normal = false;
		Candle candle = getPrices().get(which);
		String s="";
		if(candle.isVWAPSpreadAboveNormal() && candle.isSizeAboveNormal()) {
			return true;
		} 
		if(candle.isVWAPSpreadBelowNormal() && candle.isSizeBelowNormal()) {
			return true;
		} 
		if(candle.isVWAPSpreadNormal() && candle.isSizeNormal()) {
			return true;
		} 
		System.out.println("Price-Volume anomaly(candle): VWAP");
		return normal;
	}
	
	public boolean isLastPriceSpreadNormal() { 
		boolean normal=true;
		normal = getLastCandle().isPriceSpreadNormal();
		return normal;
	}
	public boolean isLastVWAPSpreadNormal() {
		boolean normal=true;
		Candle last = getPrices().get(getPrices().size()-1);
		normal = getLastCandle().isVWAPSpreadNormal();		
		return normal;		
	}
	public boolean isLastSizeNormal() {
		boolean normal=true;
		Candle last = getPrices().get(getPrices().size()-1);
		normal = getLastCandle().isSizeNormal();				
		return normal;
	}
	private Candle getLastCandle() {
		Candle last;
		last=getPrices().get(getPrices().size()-1);
		return last;
	}

	public Double getMeanSize() {
		Double mean = 0.0;
		List<Double> sizes = new ArrayList<Double>();
		setSizeStats(new Stats(sizes));
		for(Candle candle:getPrices()) {
			Integer size = candle.getSizeTotal();	
			sizes.add(size*1.0);
		}
		mean = getSizeStats().getMean();		
		return mean;
	}
	
	public Double getMeanPriceSpread() {
		Double mean = 0.0;
		List<Double> spreads = new ArrayList<Double>();
		setPriceSpreadStats(new Stats(spreads));
		for(Candle candle:getPrices()) {
			Double spread = candle.getPriceSpread();		
			spreads.add(spread);
		}
		mean = getPriceSpreadStats().getMean();
		return mean;
	}

	public Double getMeanVWAPSpread() {
		Double mean = 0.0;
		List<Double> spreads = new ArrayList<Double>();
		setVWAPSpreadStats(new Stats(spreads));
		for(Candle candle:getPrices()) {
			Double spread = candle.getVWAPSpread();
			spreads.add(spread);
		}
		mean = getVWAPSpreadStats().getMean();
		return mean;
	}
	
	public Double getMeanPriceSpreadToSizeRatio() {
		Double mean=0.0;
		List<Double> ratios = new ArrayList<Double>();
		setPriceSpreadToSizeRatioStats(new Stats(ratios));
		for(Candle candle:getPrices()) {
			Double ratio = candle.getPriceSpreadToSizeRatio();
			ratios.add(ratio);
		}	
		mean = getPriceSpreadToSizeRatioStats().getMean();
		return mean;
	}

	public Double getMeanVWAPSpreadToSizeRatio() {
		Double mean=0.0;
		List<Double> ratios = new ArrayList<Double>();
		setVWAPSpreadToSizeRatioStats(new Stats(ratios));
		for(Candle candle:getPrices()) {
			Double ratio = candle.getVWAPSpreadToSizeRatio();
			ratios.add(ratio);
		}	
		mean = getVWAPSpreadToSizeRatioStats().getMean();		
		return mean;
	}
	
	// PRIVATE

	
	// TEST
	
	// PRINT
	
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

	public Stats getVWAPSpreadStats() {
		return VWAPSpreadStats;
	}

	private void setVWAPSpreadStats(Stats vWAPStats) {
		VWAPSpreadStats = vWAPStats;
	}

	public Stats getPriceSpreadToSizeRatioStats() {
		return priceSpreadToSizeRatioStats;
	}

	private void setPriceSpreadToSizeRatioStats(Stats priceSpreadToSizeRatioStats) {
		this.priceSpreadToSizeRatioStats = priceSpreadToSizeRatioStats;
	}

	public Stats getVWAPSpreadToSizeRatioStats() {
		return VWAPSpreadToSizeRatioStats;
	}

	private void setVWAPSpreadToSizeRatioStats(Stats vWAPSpreadToSizeRatioStats) {
		VWAPSpreadToSizeRatioStats = vWAPSpreadToSizeRatioStats;
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

}