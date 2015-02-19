package com.onenow.investor;

public class Candle {
	
	private Double high;
	private Double low;
	private Double open;
	private Double close;
	
	private Integer volume;
	
	public Candle() {
		
	}

	public Double getHigh() {
		return high;
	}

	private void setHigh(Double high) {
		this.high = high;
	}

	public Double getLow() {
		return low;
	}

	private void setLow(Double low) {
		this.low = low;
	}

	public Double getOpen() {
		return open;
	}

	private void setOpen(Double open) {
		this.open = open;
	}

	public Double getClose() {
		return close;
	}

	private void setClose(Double close) {
		this.close = close;
	}

	public Integer getVolume() {
		return volume;
	}

	private void setVolume(Integer volume) {
		this.volume = volume;
	}

}
