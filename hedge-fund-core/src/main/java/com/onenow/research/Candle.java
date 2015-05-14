package com.onenow.research;


public class Candle {
	
	private Double highPrice;
	private Double lowPrice;
	private Double openPrice;
	private Double closePrice;
	
	private Long timeStart;
	private Long timeEnd;
	
//	private Double highVWAP;
//	private Double lowVWAP;
//	private Double openVWAP;
//	private Double closeVWAP;
	
//	private Integer sizeTotal;
	
	private Integer volumeOpen;
	private Integer volumeClose;
	
	
	public Candle() {
		
	}
	
	public Candle(Chart intraDay) {
//		setIntraDay(intraDay);
	}
	

	// PRIVATE

	// TEST
	
	// PRINT
	public String toString() {
		String s = "";
		s = s + "high=" + getHighPrice() + " " +
				"low=" + getLowPrice() + " " +
				"open=" + getOpenPrice() + " " +
				"close=" + getClosePrice() + "\n"; // +
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

//	public Integer getSizeTotal() {
//		return sizeTotal;
//	}
//
//	public void setSizeTotal(Integer sizeTotal) {
//		this.sizeTotal = sizeTotal;
//	}

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
