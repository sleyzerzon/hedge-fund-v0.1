package com.onenow.data;

import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.TradeType;
import com.onenow.instrument.Investment;

public class EventRealTime extends Event {
	
	public EventRealTime() {
		
	}
	
	/** Set real-time price/size/etc 
	 * 
	 * @param time
	 * @param inv
	 * @param dataType
	 * @param price
	 * @param size
	 */
	public EventRealTime(	Long time, Investment inv, TradeType tradeType, Double price, long size,
					InvDataSource source, InvDataTiming timing) {
		this.time = time;
		this.investment = inv;
		this.tradeType = tradeType;
		this.price = price;
		this.size = size;
		this.source = source;
		this.timing = timing;
	}
	
	public String toString() {
		String s = "\n";
		s = s + super.toString();
		s = s + "- inv " + investment.toString() + "\n";
		s = s + "- tradeType " + tradeType.toString() + "\n";
		s = s + "- price " + price + "\n";
		s = s + "- size " + size + "\n";
		s = s + "- source " + source.toString() + "\n";
		s = s + "- timing " + timing.toString() + "\n";
		return s;
	}
}
