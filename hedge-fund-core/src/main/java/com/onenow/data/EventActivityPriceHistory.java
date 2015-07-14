package com.onenow.data;

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

	// other
	public final double wap;
	public final int count;
	
	
//	public EventHistory() {
//	
//	}

	// TODO: what time zone is this time?
	public EventActivityPriceHistory( 	int reqId, long timeActivity, 
										double high, double low, double open, double close, 
										double wap, long volume, int count) {
		
		
		super();
		
		// super
		super.time = timeActivity;
		super.price = open;
		super.size = volume;

		// this
		this.reqId = reqId;
		this.high = high;
		this.low = low;
		this.open = open;
		this.close = close;
		this.wap = wap;
		this.volume = volume;
		this.count = count;
				
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
			s = s + "-reqID " + reqId + " ";
		} catch (Exception e) {
		} 
							
		try {
			s = s + "-open " + open + " ";
		} catch (Exception e) {
		}		
		
		try {
			s = s + "-close " + close;
		} catch (Exception e) {
		}

		return s;
	}
		
}
