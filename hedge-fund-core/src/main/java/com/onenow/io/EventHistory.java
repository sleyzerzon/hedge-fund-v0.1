package com.onenow.io;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.ib.controller.Formats;

public class EventHistory {
	
	private static final SimpleDateFormat FORMAT = new SimpleDateFormat( "yyyyMMdd HH:mm:ss"); // format for historical query

	// meta
	private int reqId=0;
	// time
	public final long time;
	//candle
	public final double high;
	public final double low;
	public final double open;
	public final double close;
	
	public final double wap;
	public final long volume;
	public final int count;

	// TODO: what time zone is this time?
	public EventHistory( int reqId, long time, double high, double low, double open, double close, double wap, long volume, int count) {
		
		this.reqId = reqId;
		this.time = time;
		this.high = high;
		this.low = low;
		this.open = open;
		this.close = close;
		this.wap = wap;
		this.volume = volume;
		this.count = count;
	}
	
	public String formattedTime() {
		return Formats.fmtDate( time * 1000);
	}

	/** Format for query. */
	public static String format( long ms) {
		return FORMAT.format( new Date( ms) );
	}

	@Override public String toString() {
		return String.format(	"id " + reqId + 
								"\t open " + open + "\t high " +  high + "\t low " + low + "\t close " + close +
								"\t FROM " + formattedTime() + "\t TIME ZONE?");
	}
		
}
