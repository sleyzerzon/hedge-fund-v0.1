package com.onenow.database;

import com.onenow.instrument.Investment;

public class EventPriceWrite {

	private Long time;
	private Investment inv;
	private String dataType; 
	private Double price;
	
	public EventPriceWrite(Long time, Investment inv, String dataType, Double price) {
		setTime(time);
		setInv(inv);
		setDataType(dataType);
		setPrice(price);
	}

	// SET GET
	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public Investment getInv() {
		return inv;
	}

	public void setInv(Investment inv) {
		this.inv = inv;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
}