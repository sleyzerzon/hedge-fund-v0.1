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
	
	ParseDate parser = new ParseDate();
		
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
		int range=3;
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
		String s="\n";
		List<Double> width = new ArrayList<Double>();
		List<Double> halfCycle = new ArrayList<Double>();		

		s = s + "DATE" + "\t\t" + "LOW" + "\t" + "HIGH" + "\t" + "WIDTH" + "\t" + "DAYS" + "\n";
//		s = s + "Bias ";
		s = outlineChannel(s, width, halfCycle) + "\n";
		s = s + "tbd est " + "\t" + Math.round(getForecastSupport()) + "\t" + 
			     					Math.round(getForecastResistance()) + "\t" +
			     					Math.round(getMean(width)) + "\t" +
			     					Math.round(getMean(halfCycle)) + "\t" +
			     					"\n";
				
		return s;
	}

	private String outlineChannel(String s, List<Double> width, List<Double> halfCycle) {
		List<String> both = new ArrayList<String>();
		both.addAll(resDate);
		both.addAll(supDate);
		Collections.sort(both);
				
		Double supPrice=0.0;
		Double resPrice=0.0;
		int size = both.size()-1;
		for(int i=size; i>=0; i--) {
			String date=both.get(i);
			try {
				supPrice = (Double) getSupport().get(date);
				String prevDate = both.get(i-1);
				if(supPrice>0.0) {
					Double prevResprice = (Double) getResistance().get(prevDate);
					Double range = prevResprice-supPrice;
					width.add(range);
					Integer elapsed = parser.getElapsedDays(prevDate, date);
					halfCycle.add(elapsed*1.0);
					s = s + date + "\t" + Math.round(supPrice) + "\t\t" + 
										  Math.round(range) + "\t" + 
										  elapsed + 
										  "\n";
				}
			} catch (Exception e) {
				// it's normal b/c adAll
			}
			
			try {
				resPrice = (Double) getResistance().get(date);
				String prevDate = both.get(i-1);
				if(resPrice>0.0) {
					Double prevSubprice = (Double) getSupport().get(prevDate);
					Double range = resPrice-prevSubprice;
					width.add(range);
					Integer elapsed = parser.getElapsedDays(prevDate, date);
					halfCycle.add(elapsed*1.0);
					s = s + date + "\t\t" + Math.round(resPrice) + "\t" + 
											Math.round(range) + "\t" + 
											elapsed + 
											"\n";
				}
			} catch (Exception e) {
				//	it's normal b/c adAll
			}
		}
		return s;
	}
	
	public double getMean(List<Double> nums) {
        double sum = 0.0;
        for(double a : nums)
            sum += a;
        return sum/nums.size();
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
