package com.onenow.database;

import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.instrument.Investment;

public class EventRT {

	private Long time;
	private Investment inv;
	private String dataType; 

	private Double price;
	private Integer size;
	
	private InvDataSource source;
	private InvDataTiming timing;

	/** Set real-time price/size/etc 
	 * 
	 * @param time
	 * @param inv
	 * @param dataType
	 * @param price
	 * @param size
	 */
	public EventRT(	Long time, Investment inv, String dataType, Double price, int size,
					InvDataSource source, InvDataTiming timing) {
		setTime(time);
		setInv(inv);
		setDataType(dataType);
		setPrice(price);
		setSize(size);
		setSource(source);
		setTiming(timing);
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


	public InvDataSource getSource() {
		return source;
	}


	public void setSource(InvDataSource source) {
		this.source = source;
	}


	public InvDataTiming getTiming() {
		return timing;
	}


	public void setTiming(InvDataTiming timing) {
		this.timing = timing;
	}
}
