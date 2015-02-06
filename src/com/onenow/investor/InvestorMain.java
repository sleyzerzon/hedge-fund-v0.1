package com.onenow.investor;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.onenow.finance.Underlying;

import samples.rfq.SampleRfq;
import apidemo.ApiDemo;


public class InvestorMain {

	// LOOK FOR FREEK-OUT IN LAST 30 DAYS, SET CHANNEL
	// HAVE 14 DAYS IN CASE OVER-BUYING/SELLING CONTINUES
	// measured at time that run-up + sell-off end (Warn level); before OVER-REACTION begins
	// buy AFTER over-sold ends, sell after over-buying ends (Act level)
	private static Channel spx = new Channel(new Underlying("SPX"));
	private static Channel rut = new Channel(new Underlying("RUT"));
	private static Channel ndx = new Channel(new Underlying("NDX"));

//	static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd hh:mm:ss"); // "20111231 16:30:00"

	public static void main(String[] args) throws ParseException {

		setChannels();

		InteractiveBrokers ib = new InteractiveBrokers();		
		ib.run();


	}

	private static void setChannels() {
		// SPX
		getSpx().addResistance("20150205"); 
		getSpx().addSupport("20150202");
		getSpx().addSupport("20150129");
		getSpx().addResistance("20150122");
		getSpx().addSupport("20150114");
		getSpx().addResistance("20150108");
		// *** 30-day trend change
		getSpx().addResistance("20141229");  
		getSpx().addSupport("20141216"); // fundamentals t2 low 
		getSpx().addResistance("20141205"); 
		// November: mild market 
		getSpx().addResistance("20141015"); // CRASH
		getSpx().addResistance("20140905"); 
		getSpx().addSupport("20140807"); // fundamentals t1 low

		
//		spx.addResistance(sdf.parse("20150205 14:30:00"), 2063.0); // ET
//		spx.addSupport(sdf.parse("20150202 09:05:00"), 1983.0);
//		spx.addSupport(sdf.parse("20150129 12:00:00"), 1990.0);
//		spx.addResistance(sdf.parse("20150122 14:00:00"), 2063.0);
//		spx.addSupport(sdf.parse("20150114 12:00:00"), 1992.0);
//		// spx.addResistance(sdf.parse("20150113 09:00:00"), 2055.0); // faux
//		spx.addResistance(sdf.parse("20150108 14:00:00"), 2064.0);
////		spx.addSupport(sdf.parse("20150106 16:30:00"), 2002.0); 
//		spx.addResistance(sdf.parse("20141229 16:00:00"), 2093.0); // old high
//		spx.addSupport(sdf.parse("20141216 09:05:00"), 1970.0); // old low
//		spx.addResistance(sdf.parse("20141205 16:30:00"), 2076.0); // ET
//		// November: fundamentals slow
//		// October: market goes down
//		// September births the top, sets the fundamental
//		spx.addResistance(sdf.parse("20140905 16:30:00"), 2008.0); // old high
//		// August births the bottom, sets the fundamental
//		spx.addSupport(sdf.parse("20140807 09:05:00"), 1906.0); // old low
	}

	private Channel getRut() {
		return rut;
	}

	private void setRut(Channel rut) {
		this.rut = rut;
	}

	private static Channel getSpx() {
		return spx;
	}

	private void setSpx(Channel spx) {
		this.spx = spx;
	}

	private Channel getNdx() {
		return ndx;
	}

	private void setNdx(Channel ndx) {
		this.ndx = ndx;
	}


}
