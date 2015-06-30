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
	 * @param timeActivity
	 * @param inv
	 * @param dataType
	 * @param price
	 * @param size
	 */
	public EventActivityRealtime(	Long timeActivity, Investment inv, TradeType tradeType, Double price, long size,
									InvDataSource source, InvDataTiming timing) {

		setInvestment(inv);
		super.tradeType = tradeType;		
		super.source = source;
		super.timing = timing;

		super.time = timeActivity;
		super.price = price;
		super.size = size;

	}
	
	public String toString() {
		String s = "";
		
		s = s + super.toString();
		
		return s;
	}
}
