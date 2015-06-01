package com.onenow.io;

import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.TradeType;
import com.onenow.instrument.Investment;

public class EventHistoryRT {

	public Long time;
	public Investment inv;
	public TradeType tradeType; 

	public Double price;
	public Integer size;
	
	public InvDataSource source;
	public InvDataTiming timing;

	/** Set real-time price/size/etc 
	 * 
	 * @param time
	 * @param inv
	 * @param dataType
	 * @param price
	 * @param size
	 */
	public EventHistoryRT(	Long time, Investment inv, TradeType tradeType, Double price, int size,
					InvDataSource source, InvDataTiming timing) {
		this.time = time;
		this.inv = inv;
		this.tradeType = tradeType;
		this.price = price;
		this.size = size;
		this.source = source;
		this.timing = timing;
	}
}
