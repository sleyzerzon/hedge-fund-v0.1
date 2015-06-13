package com.onenow.data;

import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.TradeType;
import com.onenow.instrument.Investment;

public class EventActivityRealtime extends EventActivity {
	
	public EventActivityRealtime() {
		
	}
	
	/** Set real-time price/size/etc 
	 * 
	 * @param time
	 * @param inv
	 * @param dataType
	 * @param price
	 * @param size
	 */
	public EventActivityRealtime(	Long time, Investment inv, TradeType tradeType, Double price, long size,
									InvDataSource source, InvDataTiming timing) {

		this.investment = inv;
		this.tradeType = tradeType;		
		this.source = source;
		this.timing = timing;

		this.time = time;
		this.price = price;
		this.size = size;

	}
	
	public String toString() {
		String s = "\n";
		s = s + super.toString();
//		s = s + "- inv " + investment.toString() + "\n";
//		s = s + "- tradeType " + tradeType.toString() + "\n";
//		s = s + "- price " + price + "\n";
//		s = s + "- size " + size + "\n";
//		s = s + "- source " + source.toString() + "\n";
//		s = s + "- timing " + timing.toString() + "\n";
		return s;
	}
}
