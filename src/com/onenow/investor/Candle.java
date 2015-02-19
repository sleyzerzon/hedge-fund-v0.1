package com.onenow.investor;

public class Candle {
	
	private Double highPrice;
	private Double lowPrice;
	private Double openPrice;
	private Double closePrice;
	
	private Double highVWAP;
	private Double lowVWAP;
	private Double openVWAP;
	private Double closeVWAP;
	
	private Integer sizeTotal;
	
	private Integer volumeOpen;
	private Integer volumeClose;
	
	public Candle() {
		
	}

	public Double getHighPrice() {
		return highPrice;
	}

	private void setHighPrice(Double highPrice) {
		this.highPrice = highPrice;
	}

	public Double getLowPrice() {
		return lowPrice;
	}

	private void setLowPrice(Double lowPrice) {
		this.lowPrice = lowPrice;
	}

	public Double getOpenPrice() {
		return openPrice;
	}

	private void setOpenPrice(Double openPrice) {
		this.openPrice = openPrice;
	}

	public Double getClosePrice() {
		return closePrice;
	}

	private void setClosePrice(Double closePrice) {
		this.closePrice = closePrice;
	}

	public Double getHighVWAP() {
		return highVWAP;
	}

	private void setHighVWAP(Double highVWAP) {
		this.highVWAP = highVWAP;
	}

	public Double getLowVWAP() {
		return lowVWAP;
	}

	private void setLowVWAP(Double lowVWAP) {
		this.lowVWAP = lowVWAP;
	}

	public Double getOpenVWAP() {
		return openVWAP;
	}

	private void setOpenVWAP(Double openVWAP) {
		this.openVWAP = openVWAP;
	}

	public Double getCloseVWAP() {
		return closeVWAP;
	}

	private void setCloseVWAP(Double closeVWAP) {
		this.closeVWAP = closeVWAP;
	}

	public Integer getSizeTotal() {
		return sizeTotal;
	}

	private void setSizeTotal(Integer sizeTotal) {
		this.sizeTotal = sizeTotal;
	}

	public Integer getVolumeOpen() {
		return volumeOpen;
	}

	private void setVolumeOpen(Integer volumeOpen) {
		this.volumeOpen = volumeOpen;
	}

	public Integer getVolumeClose() {
		return volumeClose;
	}

	private void setVolumeClose(Integer volumeClose) {
		this.volumeClose = volumeClose;
	}


}
