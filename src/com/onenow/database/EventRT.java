package com.onenow.database;

import com.onenow.instrument.Investment;

public class EventRT {

	private Long time;
	private Investment inv;
	private String dataType; 

	private Double price;
	private Integer size;

	/** Set real-time price/size/etc 
	 * 
	 * @param time
	 * @param inv
	 * @param dataType
	 * @param price
	 * @param size
	 */
	public EventRT(Long time, Investment inv, String dataType, Double price, int size) {
		setTime(time);
		setInv(inv);
		setDataType(dataType);
		setPrice(price);
		setSize(size);
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

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}
}
