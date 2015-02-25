package com.onenow.analyst;


public class Candle {
	
	private Double highPrice;
	private Double lowPrice;
	private Double openPrice;
	private Double closePrice;
	
//	private Double highVWAP;
//	private Double lowVWAP;
//	private Double openVWAP;
//	private Double closeVWAP;
//	
//	private Integer sizeTotal;
//	
//	private Integer volumeOpen;
//	private Integer volumeClose;
	
	
	public Candle() {
		
	}
		
	// PRICE SPREAD
//	public boolean isPriceSpreadNormal() {
//		boolean normal=true;
//		normal = !isPriceSpreadAboveNormal() && !isPriceSpreaBelowdNormal();		
//		return normal;
//	}
//	public boolean isPriceSpreadAboveNormal() {
//		boolean normal=true;
//		normal = getIntraDay().getPriceSpreadStats().isAboveNormal(getPriceSpread());		
//		return normal;
//	}
//	public boolean isPriceSpreaBelowdNormal() {
//		boolean normal=true;
//		normal = !getIntraDay().getPriceSpreadStats().isBelowNormal(getPriceSpread());		
//		return normal;
//	}
//	public Double getPriceSpread() {
//		Double spread=0.0;
//		spread = closePrice-openPrice;
//		return spread;
//	}

	// VWAP SPREAD
//	public boolean isVWAPSpreadNormal() {
//		boolean normal=true;
//		normal = !isVWAPSpreadAboveNormal() && !isVWAPSpreadBelowNormal();
//		return normal;
//	}
//	public boolean isVWAPSpreadAboveNormal() {
//		boolean normal=true;
//		normal = getIntraDay().getVWAPSpreadStats().isAboveNormal(getVWAPSpread());
//		return normal;
//	}
//	public boolean isVWAPSpreadBelowNormal() {
//		boolean normal=true;
//		normal = getIntraDay().getVWAPSpreadStats().isBelowNormal(getVWAPSpread());
//		return normal;
//	}
//	
//	public Double getVWAPSpread() {
//		Double spread=0.0;
//		spread = closeVWAP-openVWAP;
//		return spread;
//	}
	
	// SIZE
//	public boolean isSizeNormal() {
//		boolean normal=true;
//		normal = !isSizeAboveNormal() && !isSizeBelowNormal();		
//		return normal;
//	}
//	public boolean isSizeAboveNormal() {
//		boolean normal=true;
//		normal = getIntraDay().getSizeStats().isAboveNormal(getSizeTotal()*1.0);		
//		return normal;
//	}
//	public boolean isSizeBelowNormal() {
//		boolean normal=true;
//		normal = getIntraDay().getSizeStats().isBelowNormal(getSizeTotal()*1.0);		
//		return normal;
//	}
	
	// PRICE / VOLUME RATIO
//	public boolean isPriceSpreadVolumeRatioNormal() {
//		boolean normal=true;
//		normal = !getIntraDay().getPriceSpreadToSizeRatioStats().isAboveNormal(getPriceSpreadToSizeRatio()) &&
//				 !getIntraDay().getPriceSpreadToSizeRatioStats().isBelowNormal(getPriceSpreadToSizeRatio());		
//		return normal;
//	}
//	
//	public Double getPriceSpreadToSizeRatio() {
//		Double ratio=0.0;
//		ratio= getPriceSpread() / getSizeTotal(); // result to effort
//		return ratio;
//	}

	// VWAP / VOLUME RATIO
//	public boolean isVWAPSpreadVolumeRatioNormal() {
//		boolean normal=true;
//		normal = getIntraDay().getVWAPSpreadToSizeRatioStats().isAboveNormal(getVWAPSpreadToSizeRatio()) &&
//				getIntraDay().getVWAPSpreadToSizeRatioStats().isBelowNormal(getVWAPSpreadToSizeRatio());
//		return normal;
//	}
//
//	public Double getVWAPSpreadToSizeRatio() {
//		Double ratio=0.0;
//		ratio= getVWAPSpread() / getSizeTotal(); // result to effort
//		return ratio;
//	}

	// PRIVATE

	// TEST
	
	// PRINT
	public String toString() {
		String s = "\n";
		s = s + "high=" + getHighPrice() + " " +
				"low=" + getLowPrice() + " " +
				"open=" + getOpenPrice() + " " +
				"close=" + getClosePrice() + "\n";
		
//		+
//				"size=" + getSizeTotal() + "\n";

		return s;
	}
	
	// SET GET
	public Double getHighPrice() {
		return highPrice;
	}

	public void setHighPrice(Double highPrice) {
		this.highPrice = highPrice;
	}

	public Double getLowPrice() {
		return lowPrice;
	}

	public void setLowPrice(Double lowPrice) {
		this.lowPrice = lowPrice;
	}

	public Double getOpenPrice() {
		return openPrice;
	}

	public void setOpenPrice(Double openPrice) {
		this.openPrice = openPrice;
	}

	public Double getClosePrice() {
		return closePrice;
	}

	public void setClosePrice(Double closePrice) {
		this.closePrice = closePrice;
	}

//	public Double getHighVWAP() {
//		return highVWAP;
//	}
//
//	private void setHighVWAP(Double highVWAP) {
//		this.highVWAP = highVWAP;
//	}
//
//	public Double getLowVWAP() {
//		return lowVWAP;
//	}
//
//	private void setLowVWAP(Double lowVWAP) {
//		this.lowVWAP = lowVWAP;
//	}
//
//	public Double getOpenVWAP() {
//		return openVWAP;
//	}
//
//	private void setOpenVWAP(Double openVWAP) {
//		this.openVWAP = openVWAP;
//	}
//
//	public Double getCloseVWAP() {
//		return closeVWAP;
//	}
//
//	private void setCloseVWAP(Double closeVWAP) {
//		this.closeVWAP = closeVWAP;
//	}
//
//	public Integer getSizeTotal() {
//		return sizeTotal;
//	}
//
//	public void setSizeTotal(Integer sizeTotal) {
//		this.sizeTotal = sizeTotal;
//	}
//
//	public Integer getVolumeOpen() {
//		return volumeOpen;
//	}
//
//	private void setVolumeOpen(Integer volumeOpen) {
//		this.volumeOpen = volumeOpen;
//	}
//
//	public Integer getVolumeClose() {
//		return volumeClose;
//	}
//
//	private void setVolumeClose(Integer volumeClose) {
//		this.volumeClose = volumeClose;
//	}



}
