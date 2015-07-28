package com.onenow.data;

import com.onenow.constant.DataTiming;
import com.onenow.constant.DataType;

public class EventActivityPriceHistory extends EventActivity {
	
	// meta
	private int reqId = 0;

	// candle
	public final double high;
	public final double low;
	public final double open;
	public final double close;
	
	// weight
	public final Long volume; 

	// other: removed to fit 
	// com.amazonaws.AmazonServiceException: 1 validation error detected: Member must have length less than or equal to 256
//	public final double wap;
//	public final int count;
	
	
//	public EventHistory() {
//	
//	}

	// TODO: what time zone is this time?
	public EventActivityPriceHistory( 	int reqId, long timeInSec, 
										double high, double low, double open, double close, 
										double wap, long volume, int count) {
		
		
		super();
		super.dataType = DataType.PRICE_HIST;
		super.timing = DataTiming.HISTORY;

		// super
		super.timeInMilisec = timeInSec*1000;
		super.price = close;  	// simplifies to only capture open in the EventActivity
		super.size = volume;
		// super.priceType is set after construction, accessing the variable

		// this
		this.reqId = reqId;
		this.high = high;
		this.low = low;
		this.open = open;
		this.close = close;
		this.volume = volume;
//		this.wap = wap;
//		this.count = count;
				
	}
	
	@Override public String toString() {
		String s = "";
		
		s = s  + super.toString() + " ";

		try {
			s = s + "-high " +  high + " ";
		} catch (Exception e) {
		}
		
		try {
			s = s + "-low " + low + " ";
		} catch (Exception e) {
		}
							
		try {
			s = s + "-open " + open + " ";
		} catch (Exception e) {
		}		
		
		try {
			s = s + "-close " + close + " ";
		} catch (Exception e) {
		}

		try {
			s = s + "-reqID " + reqId + " ";
		} catch (Exception e) {
		} 

		return s;
	}
		
}
