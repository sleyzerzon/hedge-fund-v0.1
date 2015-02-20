package com.onenow.finance;

import java.util.ArrayList;
import java.util.List;

import com.onenow.investor.Candle;
import com.onenow.investor.Stats;

public class Intraday {
	
	List<Candle> candles;
	
	Stats sizeStats;
	Stats priceSpreadStats;
	Stats VWAPSpreadStats;
	Stats priceSpreadToSizeRatioStats;
	Stats VWAPSpreadToSizeRatioStats;
	
	
	public Intraday() {
		setCandles(new ArrayList<Candle>());
	}

	// PRIVATE
	public Double getMeanSize() {
		Double mean = 0.0;
		List<Double> sizes = new ArrayList<Double>();
		setSizeStats(new Stats(sizes));
		for(Candle candle:getCandles()) {
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
		for(Candle candle:getCandles()) {
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
		for(Candle candle:getCandles()) {
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
		for(Candle candle:getCandles()) {
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
		for(Candle candle:getCandles()) {
			Double ratio = candle.getVWAPSpreadToSizeRatio();
			ratios.add(ratio);
		}	
		mean = getVWAPSpreadToSizeRatioStats().getMean();		
		return mean;
	}
	
	// TEST
	
	// PRINT
	
	// SET GET
	public List<Candle> getCandles() {
		return candles;
	}

	public void setCandles(List<Candle> candles) {
		this.candles = candles;
	}

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

}
