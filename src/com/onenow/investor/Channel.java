package com.onenow.investor;

import java.util.ArrayList;
import java.util.Collections;
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
	
	public Double getForecastResistance() {
		int range=4;
		Double price=0.0;
		Double min=999999.0;

		Collections.sort(resDate);
		int size = resDate.size()-1;
		for(int i=size; i>size-range; i--){
//			System.out.println(size + " " + i);
			String date = resDate.get(i);
			Double level = (Double) getResistance().get(date);
//			System.out.println(date + " " + level);
			if(Math.round(level) < Math.round(min)) {
				price=(double) Math.round(level);
			}
		}
		
		return price;
	}
	
	public Double getForecastSupport() {
		int range=3;
		Double price=0.0;
		Double max=-999999.0;

		Collections.sort(supDate);
		int size = supDate.size()-1;
		for(int i=size; i>size-range; i--){
//			System.out.println(size + " " + i);
			String date = supDate.get(i);
			Double level = (Double) getSupport().get(date);
//			System.out.println(date + " " + level);
			if(Math.round(level) > Math.round(max)) {
				price=(double) Math.round(level);
			}
		}
		return price;
	}
	
	// PRINT
	public String toString() {
		String s="";

		for(int i=0; i<resDate.size(); i++) {
			String date = resDate.get(i);
			Double price = (Double) getResistance().get(date);
			s = s + "Resistance " + date + " " + price + "\n";
		}
		s = s + "Forecast Resistance " + getForecastResistance() + "\n";

		for(int i=0; i<supDate.size(); i++) {
			String date = supDate.get(i);
			Double price = (Double) getSupport().get(date);
			s = s + "Support " + date + " " + price + "\n";
		}
		s = s + "Forecast Support " + getForecastSupport() + "\n";

		return s;
	}
	
	// TEST
	public void test() {
		
	}

	
	// SET GET
	public Underlying getUnder() {
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

	public List<String> getResDate() {
		return resDate;
	}

	private void setResDate(List<String> resDate) {
		this.resDate = resDate;
	}

	public List<String> getSupDate() {
		return supDate;
	}

	private void setSupDate(List<String> supDate) {
		this.supDate = supDate;
	}

	public List<String> getFundDate() {
		return fundDate;
	}

	private void setFundDate(List<String> fundDate) {
		this.fundDate = fundDate;
	}

	

}
