package com.onenow.data;

import com.onenow.constant.InvDataSource;
import com.onenow.constant.DataTiming;
import com.onenow.constant.PriceType;
import com.onenow.constant.DataType;
import com.onenow.instrument.Investment;

public class EventActivityPriceStreaming extends EventActivity {

	public EventActivityPriceStreaming() {
		super();
	}
	
	public EventActivityPriceStreaming(	Long timeInMilisec, Investment inv,  Double price, PriceType priceType,
										InvDataSource source) {

		super();
		super.dataType = DataType.PRICE_STREAM;

		setInvestment(inv);
		super.priceType = priceType; 
		super.source = source;
		super.timing = DataTiming.STREAM;
		
		super.timeInMsec = timeInMilisec;
		super.price = price;
	
	}
	
	public String toString() {
		String s = "";
		
		s = s + super.toString();
		
		return s;
	}

	
}
