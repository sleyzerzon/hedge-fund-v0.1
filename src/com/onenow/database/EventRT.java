package com.onenow.database;

import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.TradeType;
import com.onenow.instrument.Investment;

public class EventRT {

	public Long time;
	private Investment inv;
	public TradeType tradeType; 

	public Double price;
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
	public EventRT(	Long time, Investment inv, TradeType tradeType, Double price, int size,
					InvDataSource source, InvDataTiming timing) {
		this.time = time;
		setInv(inv);
		this.tradeType = tradeType;
		this.price = price;
		setSize(size);
		setSource(source);
		setTiming(timing);
	}
	
	
	// SET GET
	public Investment getInv() {
		return inv;
	}

	public void setInv(Investment inv) {
		this.inv = inv;
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
