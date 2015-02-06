package com.onenow.investor;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.onenow.finance.Underlying;

public class Channel {

	private Underlying under;
	private HashMap resistance = new HashMap();
	private HashMap support = new HashMap();
	private HashMap fundamental = new HashMap();
		
	public Channel() {
		
	}
	
	public Channel(Underlying under) {
		setUnder(under);
		
	}

	public void addResistance(String date) {
		getResistance().put(date, 0.0);
	}

	public void addResistance(String date, Double price) {
		getResistance().put(date, price);
	}

	public void addSupport(String string) {
		getSupport().put(string, 0.0);		
	}

	public void addSupport(Date date, Double price) {
		getSupport().put(date, price);		
	}

	public void addFundamental() {
		
	}
	
	
	// PRINT
	
	
	// TEST
	public void test() {
		
	}

	
	// SET GET
	private Underlying getUnder() {
		return under;
	}


	private void setUnder(Underlying under) {
		this.under = under;
	}

	private HashMap getResistance() {
		return resistance;
	}

	private void setResistance(HashMap resistance) {
		this.resistance = resistance;
	}

	private HashMap getSupport() {
		return support;
	}

	private void setSupport(HashMap support) {
		this.support = support;
	}

	private HashMap getFundamental() {
		return fundamental;
	}

	private void setFundamental(HashMap fundamental) {
		this.fundamental = fundamental;
	}

	

}
