package com.onenow.data;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.ib.controller.Formats;

public class EventActivityHistory extends EventActivity {
	
	private static final SimpleDateFormat FORMAT = new SimpleDateFormat( "yyyyMMdd HH:mm:ss"); // format for historical query

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
	public EventActivityHistory( 	int reqId, 
							long time, double high, double low, double open, double close, double wap, long volume, int count) {
		
		this.reqId = reqId;
		this.high = high;
		this.low = low;
		this.open = open;
		this.close = close;
		this.wap = wap;
		this.volume = volume;
		this.count = count;
		
		// for general use (EventActivity)
		this.time = time*1000;
		this.price = open;
		this.size = volume;
		
	}
	
	public String formattedTime() {
		return Formats.fmtDate( time * 1000);
	}

	/** Format for query. */
	public static String format( long ms) {
		return FORMAT.format( new Date( ms) );
	}

	@Override public String toString() {
		String s = "";
		
		s = String.format(	super.toString() + " " +
							"-reqID " + reqId + " " + 
							"-open " + open + " " +
							"-high " +  high + " " +
							"-low " + low + " " +
							"-close " + close );
							// "-time " + time + " " + 
							// "-timeDate " + formattedTime() + " " +
							// "-TIME ZONE???"); // TODO: time zone

		return s;
	}
		
}
