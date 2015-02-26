package com.onenow.analyst;

import java.util.ArrayList;
import java.util.List;

import com.onenow.investor.Stats;

public class Chart {
	
	List<Candle> prices = new ArrayList<Candle>();
	List<Integer> sizes = new ArrayList<Integer>();
	
	List<Integer> upSize = new ArrayList<Integer>();
	List<Integer> downSize = new ArrayList<Integer>();
	
	Stats sizeStats;
	Stats priceSpreadStats;
	Stats priceSpreadToSizeRatioStats;
	
//	Stats VWAPSpreadStats;
//	Stats VWAPSpreadToSizeRatioStats;
	
	
	public Chart() {
	}
	
	// PUBLIC
	
	
	
	private List<Integer> getUpDownSize() {
		List<Integer> upVolume = new ArrayList<Integer>();

		int i=0;
		Candle prevPrice = new Candle();
		for(Candle price:getPrices()) {
			if(i>0) { // wait to have two candles
				if(isHigherAndLower(prevPrice, price)) {
//					getUpSize().add(getSizes().get(i));
				}
				if(isHigherPrice(prevPrice, price)) {
					getUpSize().add(getSizes().get(i));
				} else {
					getUpSize().add(0);
				}
				if(isLowerPrice(prevPrice, price)) {
					getDownSize().add(getSizes().get(i));
				} else {
					getDownSize().add(0);
				}
			} else { // zero when n/a
				getUpSize().add(0);
				getDownSize().add(0);
			}
			prevPrice = price;
			i++;
		}
		
		return upVolume;
	}
	
	private boolean isIgnore(Integer which) {
		boolean ignore = false;
		if(which.equals(0)) {
			ignore=true;
		}
		return ignore;
	}
	
	private boolean isUp(Integer which) {
		boolean isUp = false;
		if(which.equals(0)) {
			return false;
		}
		
		return isUp;
	}
	
	private boolean isDown(Integer which) {
		boolean isDown = false;
		if(which.equals(0)) {
			return false;
		}
		
		return isDown;
	}
	
	private boolean isHigherPrice(Candle first, Candle second) {
		boolean higher = false;
		if(second.getHighPrice()>first.getHighPrice()) {
			higher = true;
		}
		return higher;
	}

	private boolean isLowerPrice(Candle first, Candle second) {
		boolean lower = false;
		if(second.getLowPrice()<first.getLowPrice()) {
			lower = true;
		}		
		return lower;
	}

	private boolean isHigherAndLower(Candle first, Candle second) {
		boolean isBoth = false;
		if(isHigherPrice(first, second) && isLowerPrice(first, second)) {
			isBoth=true;
		}
		return isBoth;
	}

	
	// TODO take only complete candles; ignore opening / close high volume?

//	public boolean isLastCandleNormal() { 
//		boolean normal=true;
//		Integer last = getPrices().size()-1;
//		normal = isCandleNormal(last);
//		return normal;
//	}
	
//	public boolean isCandleNormal(Integer which) { // VWAP-based
//		boolean normal = false;
//		Candle candle = getPrices().get(which);
//		String s="";
//		if(candle.isVWAPSpreadAboveNormal() && candle.isSizeAboveNormal()) {
//			return true;
//		} 
//		if(candle.isVWAPSpreadBelowNormal() && candle.isSizeBelowNormal()) {
//			return true;
//		} 
//		if(candle.isVWAPSpreadNormal() && candle.isSizeNormal()) {
//			return true;
//		} 
//		System.out.println("Price-Volume anomaly(candle): VWAP");
//		return normal;
//	}
	
//	public boolean isLastPriceSpreadNormal() { 
//		boolean normal=true;
//		normal = getLastCandle().isPriceSpreadNormal();
//		return normal;
//	}
//	public boolean isLastVWAPSpreadNormal() {
//		boolean normal=true;
//		Candle last = getPrices().get(getPrices().size()-1);
//		normal = getLastCandle().isVWAPSpreadNormal();		
//		return normal;		
//	}
//	public boolean isLastSizeNormal() {
//		boolean normal=true;
//		Candle last = getPrices().get(getPrices().size()-1);
//		normal = getLastCandle().isSizeNormal();				
//		return normal;
//	}
//	private Candle getLastCandle() {
//		Candle last;
//		last=getPrices().get(getPrices().size()-1);
//		return last;
//	}
//
//	public Double getMeanSize() {
//		Double mean = 0.0;
//		List<Double> sizes = new ArrayList<Double>();
//		setSizeStats(new Stats(sizes));
//		for(Candle candle:getPrices()) {
//			Integer size = candle.getSizeTotal();	
//			sizes.add(size*1.0);
//		}
//		mean = getSizeStats().getMean();		
//		return mean;
//	}
//	
//	public Double getMeanPriceSpread() {
//		Double mean = 0.0;
//		List<Double> spreads = new ArrayList<Double>();
//		setPriceSpreadStats(new Stats(spreads));
//		for(Candle candle:getPrices()) {
//			Double spread = candle.getPriceSpread();		
//			spreads.add(spread);
//		}
//		mean = getPriceSpreadStats().getMean();
//		return mean;
//	}
//
//	public Double getMeanVWAPSpread() {
//		Double mean = 0.0;
//		List<Double> spreads = new ArrayList<Double>();
//		setVWAPSpreadStats(new Stats(spreads));
//		for(Candle candle:getPrices()) {
//			Double spread = candle.getVWAPSpread();
//			spreads.add(spread);
//		}
//		mean = getVWAPSpreadStats().getMean();
//		return mean;
//	}
//	
//	public Double getMeanPriceSpreadToSizeRatio() {
//		Double mean=0.0;
//		List<Double> ratios = new ArrayList<Double>();
//		setPriceSpreadToSizeRatioStats(new Stats(ratios));
//		for(Candle candle:getPrices()) {
//			Double ratio = candle.getPriceSpreadToSizeRatio();
//			ratios.add(ratio);
//		}	
//		mean = getPriceSpreadToSizeRatioStats().getMean();
//		return mean;
//	}
//
//	public Double getMeanVWAPSpreadToSizeRatio() {
//		Double mean=0.0;
//		List<Double> ratios = new ArrayList<Double>();
//		setVWAPSpreadToSizeRatioStats(new Stats(ratios));
//		for(Candle candle:getPrices()) {
//			Double ratio = candle.getVWAPSpreadToSizeRatio();
//			ratios.add(ratio);
//		}	
//		mean = getVWAPSpreadToSizeRatioStats().getMean();		
//		return mean;
//	}
	
	// PRIVATE

	
	// TEST
	
	// PRINT
	public String toString() {
		String s="";
		s = s + prices.toString() + "\n" +
				sizes.toString() + "\n\n";
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

	private List<Integer> getUpSize() {
		return upSize;
	}

	private void setUpSize(List<Integer> upSize) {
		this.upSize = upSize;
	}

	private List<Integer> getDownSize() {
		return downSize;
	}

	private void setDownSize(List<Integer> downSize) {
		this.downSize = downSize;
	}

}
