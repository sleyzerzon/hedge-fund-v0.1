package com.onenow.data;

import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.PriceType;
import com.onenow.instrument.Investment;

public class EventActivityPriceSizeRealtime extends EventActivity {
	
	public EventActivityPriceSizeRealtime() {
		super();
	}
	
	/** Set real-time price/size/etc 
	 * 
	 * @param timeActivity
	 * @param inv
	 * @param dataType
	 * @param price
	 * @param size
	 */
	public EventActivityPriceSizeRealtime(	Long timeActivity, Investment inv, PriceType tradeType, Double price, long size,
									InvDataSource source, InvDataTiming timing) {

		super();
		
		setInvestment(inv);
		super.priceType = tradeType;		
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
