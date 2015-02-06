package com.onenow.investor;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.onenow.finance.Underlying;

import samples.rfq.SampleRfq;
import apidemo.ApiDemo;


public class InvestorMain {


//	static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd hh:mm:ss"); // "20111231 16:30:00"

	public static void main(String[] args) throws ParseException {


		InteractiveBrokers ib = new InteractiveBrokers();		
		ib.run();


	}

}
