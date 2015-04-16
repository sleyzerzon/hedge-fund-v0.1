package com.onenow.database;

import com.onenow.instrument.Investment;

public class EventSizeWrite {

	private Long time;
	private Investment inv;
	private String dataType;
	private Integer size;
	
	public EventSizeWrite(Long time, Investment inv, String dataType, Integer size) {
		setTime(time);
		setInv(inv);
		setDataType(dataType);
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

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}
}
