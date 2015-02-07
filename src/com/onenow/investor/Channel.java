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
	
	private List<String> resDate = new ArrayList<String>();
	private List<String> supDate = new ArrayList<String>();
	private List<String> fundDate = new ArrayList<String>();
		
	public Channel() {
		
	}
	
	public Channel(Underlying under) {
		setUnder(under);
		
	}

	public void addResistance(String date) {
		if(!getResistance().containsKey(date)) {
			resDate.add(date);
		}
		getResistance().put(date, -999999.0);
	}

	public void addResistance(String date, Double price) {
		if(!getResistance().containsKey(date)) {
			resDate.add(date);		
		}
		getResistance().put(date, price);
	}

	public void addSupport(String date) {
		if(!getSupport().containsKey(date)) {
			supDate.add(date);
		}
		getSupport().put(date, 999999.0);
	}

	public void addSupport(String date, Double price) {
		if(!getSupport().containsKey(date)) {
			supDate.add(date);
		}
		getSupport().put(date, price);		
	}

	public void addFundamental() {
		
	}
	
	
	// PRINT
	public String toString() {
		String s="";

		for(int i=0; i<resDate.size(); i++) {
			String date = resDate.get(i);
			Double price = (Double) getResistance().get(date);
			System.out.println("*" + date + " " + price);
		}
		
		return s;
	}
	
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

	public HashMap getResistance() {
		return resistance;
	}

	private void setResistance(HashMap resistance) {
		this.resistance = resistance;
	}

	public HashMap getSupport() {
		return support;
	}

	private void setSupport(HashMap support) {
		this.support = support;
	}

	public HashMap getFundamental() {
		return fundamental;
	}

	private void setFundamental(HashMap fundamental) {
		this.fundamental = fundamental;
	}

	

}
