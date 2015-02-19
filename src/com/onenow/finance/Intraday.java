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
	public Double getMeanVolume() {
		Double mean = 0.0;
		List<Double> volumes = new ArrayList<Double>();
		Stats stats = new Stats(volumes);
		for(Candle candle:getCandles()) {
			Integer volume = candle.getVolume();
			volumes.add(volume*1.0);
		}
		mean = stats.getMean();		
		return mean;
	}
	
	public Double getMeanSpread() {
		Double mean = 0.0;
		List<Double> spreads = new ArrayList<Double>();
		Stats stats = new Stats(spreads);
		for(Candle candle:getCandles()) {
			Double high = candle.getHigh();
			Double low = candle.getLow();
			Double spread = high-low;
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
