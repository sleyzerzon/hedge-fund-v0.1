package com.onenow.investor;

import com.onenow.finance.Intraday;

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
	
	private Intraday intraDay;
	
	public Candle() {
		
	}
	
	public Candle(Intraday intraDay) {
		setIntraDay(intraDay);
	}
	
	public boolean isPriceSpreadNormal() {
		boolean normal=true;
		normal = !getIntraDay().getPriceSpreadStats().isAboveNormal(getPriceSpread()) &&
				 !getIntraDay().getPriceSpreadStats().isBelowNormal(getPriceSpread());		
		return normal;
	}
	public boolean isVWAPSpreadNormal() {
		boolean normal=true;
		normal = !getIntraDay().getVWAPSpreadStats().isAboveNormal(getVWAPSpread()) &&
				 !getIntraDay().getVWAPSpreadStats().isBelowNormal(getVWAPSpread());
		return normal;
	}
	public boolean isSizeNormal() {
		boolean normal=true;
		normal = !getIntraDay().getSizeStats().isAboveNormal(getSizeTotal()*1.0) &&
				 !getIntraDay().getSizeStats().isBelowNormal(getSizeTotal()*1.0);		
		return normal;
	}
	
	public boolean isPriceSpreadVolumeRatioNormal() {
		boolean normal=true;
		normal = !getIntraDay().getPriceSpreadToSizeRatioStats().isAboveNormal(getPriceSpreadToSizeRatio()) &&
				 !getIntraDay().getPriceSpreadToSizeRatioStats().isBelowNormal(getPriceSpreadToSizeRatio());		
		return normal;
	}

	public boolean isVWAPSpreadVolumeRatioNormal() {
		boolean normal=true;
		normal = getIntraDay().getVWAPSpreadToSizeRatioStats().isAboveNormal(getVWAPSpreadToSizeRatio()) &&
				getIntraDay().getVWAPSpreadToSizeRatioStats().isBelowNormal(getVWAPSpreadToSizeRatio());
		return normal;
	}

	public Double getPriceSpread() {
		Double spread=0.0;
		spread = closePrice-openPrice;
		return spread;
	}

	public Double getVWAPSpread() {
		Double spread=0.0;
		spread = closeVWAP-openVWAP;
		return spread;
	}
	
	public Double getPriceSpreadToSizeRatio() {
		Double ratio=0.0;
		ratio= getPriceSpread() / getSizeTotal(); // result to effort
		return ratio;
	}

	public Double getVWAPSpreadToSizeRatio() {
		Double ratio=0.0;
		ratio= getVWAPSpread() / getSizeTotal(); // result to effort
		return ratio;
	}

	// PRIVATE

	// TEST
	
	// PRINT
	
	// SET GET
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

	private Intraday getIntraDay() {
		return intraDay;
	}

	private void setIntraDay(Intraday intraDay) {
		this.intraDay = intraDay;
	}



}
