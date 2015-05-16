/* Copyright (C) 2013 Interactive Brokers LLC. All rights reserved.  This code is subject to the terms
 * and conditions of the IB API Non-Commercial License or the IB API Commercial License, as applicable. */

package com.onenow.execution;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.ib.controller.Formats;

public class QuoteRow {
	private static final SimpleDateFormat FORMAT = new SimpleDateFormat( "yyyyMMdd HH:mm:ss"); // format for historical query

	// meta
	private int reqId=0;
	// time
	private final long m_time;
	//candle
	private final double m_high;
	private final double m_low;
	private final double m_open;
	private final double m_close;
	
	private final double m_wap;
	private final long m_volume;
	private final int m_count;

	public QuoteRow( int reqId, long time, double high, double low, double open, double close, double wap, long volume, int count) {
		
		this.reqId = reqId;
		
		m_time = time;
		m_high = high;
		m_low = low;
		m_open = open;
		m_close = close;
		m_wap = wap;
		m_volume = volume;
		m_count = count;
	}
	
	public QuoteRow( long time, double high, double low, double open, double close, double wap, long volume, int count) {
		

		// TODO: translate to UTZ
		
		m_time = time; // TODO: what time zone is this time?

		
		m_high = high;
		m_low = low;
		m_open = open;
		m_close = close;
		m_wap = wap;
		m_volume = volume;
		m_count = count;
	}

	public String formattedTime() {
		return Formats.fmtDate( m_time * 1000);
	}

	/** Format for query. */
	public static String format( long ms) {
		return FORMAT.format( new Date( ms) );
	}

	@Override public String toString() {
		return String.format(	"id " + reqId + 
								". open " + m_open + ". high " +  m_high + ". low " + m_low + ". close " + m_close +
								". AT " + formattedTime() + " TIME ZONE?");
	}
	
	
	// SET GET
	
	public long time()		{ return m_time; }
	public double high() 	{ return m_high; }
	public double low() 	{ return m_low; }
	public double open() 	{ return m_open; }
	public double close() 	{ return m_close; }
	public double wap() 	{ return m_wap; }
	public long volume() 	{ return m_volume; }
	public int count() 		{ return m_count; }

//	public long getM_time() {
//		return m_time;
//	}
//	public double getM_high() {
//		return m_high;
//	}
//	public double getM_low() {
//		return m_low;
//	}
//	public double getM_open() {
//		return m_open;
//	}
//	public double getM_close() {
//		return m_close;
//	}
	
	
}
