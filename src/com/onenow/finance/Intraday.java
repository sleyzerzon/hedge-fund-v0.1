package com.onenow.finance;

import java.util.ArrayList;
import java.util.List;

import com.onenow.investor.Candle;
import com.onenow.investor.Stats;

public class Intraday {
	
	List<Candle> candles;
	
	
	public Intraday() {
		setCandles(new ArrayList<Candle>());
	}

	// PRIVATE
	public Double getMeanSize() {
		Double mean = 0.0;
		List<Double> sizes = new ArrayList<Double>();
		Stats stats = new Stats(sizes);
		for(Candle candle:getCandles()) {
			Integer size = candle.getSizeTotal();	
			sizes.add(size*1.0);
		}
		mean = stats.getMean();		
		return mean;
	}
	
	public Double getMeanSpread() {
		Double mean = 0.0;
		List<Double> spreads = new ArrayList<Double>();
		Stats stats = new Stats(spreads);
		for(Candle candle:getCandles()) {
			Double close = candle.getClosePrice();		
			Double open = candle.getOpenPrice();
			Double spread = close-open;
			spreads.add(spread);
		}
		mean = stats.getMean();
		return mean;
	}

	public Double getMeanVWAPSpread() {
		Double mean = 0.0;
		List<Double> spreads = new ArrayList<Double>();
		Stats stats = new Stats(spreads);
		for(Candle candle:getCandles()) {
			Double close = candle.getCloseVWAP();
			Double open = candle.getOpenVWAP();		
			Double spread = close-open;
			spreads.add(spread);
		}
		mean = stats.getMean();
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

}
