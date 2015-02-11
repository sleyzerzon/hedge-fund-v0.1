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
	
	public Double getForecastResistance(int range) {
		Double price=0.0;
		Double min=999999.0;

		Collections.sort(resDate);
		int size = resDate.size()-1;
		for(int i=size; i>size-range; i--){
			String date = resDate.get(i);
			Double level = (Double) getResistance().get(date);
			if(Math.round(level) < Math.round(min)) {
				price=(double) Math.round(level);
			}
		}
		
		return price;
	}
	
	public Double getForecastSupport(int range) {
		Double price=0.0;
		Double max=-999999.0;

		Collections.sort(supDate);
		int size = supDate.size()-1;
		for(int i=size; i>size-range; i--){
			String date = supDate.get(i);
			Double level = (Double) getSupport().get(date);
			if(Math.round(level) > Math.round(max)) {
				price=(double) Math.round(level);
			}
		}
		return price;
	}
	
	// PRINT
	public String toString() {
		String s="\n";
		s = s + getUnder().getTicker() + "\n";
		
		List<Double> rangeToResistance = new ArrayList<Double>();
		List<Double> rangeToSupport = new ArrayList<Double>();
		List<Double> halfCycleToResistance = new ArrayList<Double>();		
		List<Double> halfCycleToSupport = new ArrayList<Double>();		

		s = s + "DATE" + "\t\t" + "SPIKE" + "\t\t" + "RANGE" + "\t\t" + "DAYS" + "\n";

		s = outlineChannel(	s, 
							rangeToResistance, halfCycleToResistance,
							rangeToSupport, halfCycleToSupport) + "\n";
		
		s = s + "kpi " + "\t\t" + getSupportSlope(6) + "\t" + getResistanceSlope(6) + "\t" + 
					Math.round(getMean(rangeToSupport)) + "/" + Math.round(getMean(rangeToResistance)) + "\t\t" +
					Math.round(getMean(halfCycleToSupport)) + "/" + Math.round(getMean(halfCycleToResistance)) + "\n";

		s = s + "forecast" + "\t" + Math.round(getForecastSupport(1)+getSupportSlope(6)) + "\t" +
				  Math.round(getForecastResistance(1)+getResistanceSlope(6)) + 
				  "\n\n";

		s = s + "alert=> " + "\t*" + Math.round(getForecastSupport(3)+10) + "*\t*" + 
			     				    Math.round(getForecastResistance(3)-10) + "*\t" +
			     				    "\n";				
		return s;
	}
	/*
	SPX
	DATE		LOW		HIGH	WIDTH	DAYS
	2015-02-06			2072	92		4
	2015-02-02	1981			84		11
	2015-01-22			2065	73		7
	2015-01-15	1992			72		7
	2015-01-08			2064	72		2
	2015-01-06	1992			101		8
	2014-12-29			2094	121		13
	2014-12-16	1973			107		11
	2014-12-05			2079	259		21
	2014-10-15	1820			192		28
	2014-09-18			2012	108		11
	2014-08-07	1905			87		14

	kpi 		13		10		114		11
	forecast	1994	2082

	alert=> 	*2002*	*2054*	
*/

	private Integer getResistanceSlope(Integer range) {
		Double recentRes=getForecastResistance(1);
		Double oldRes=getForecastResistance(range);		
		Double resSlope = (recentRes-oldRes)/range;
		return (int) Math.round(resSlope);
	}

	private Integer getSupportSlope(Integer range) {
		Double recentSup=getForecastSupport(1);
		Double oldSup=getForecastSupport(range);
		Double supSlope = (recentSup-oldSup)/range;
		return (int) Math.round(supSlope);
	}

	private String outlineChannel(	String s, 
									List<Double> widthToResistance, List<Double> halfCycleToResistance,
									List<Double> widthToSupport, List<Double> halfCycleToSupport) {
		List<String> both = new ArrayList<String>();
		both.addAll(resDate);
		both.addAll(supDate);
		Collections.sort(both);
				
		Double supPrice=0.0;
		Double resPrice=0.0;
		int size = both.size()-1;
		for(int i=size; i>=0; i--) {
			String date=both.get(i);
			try { // SUPPORT
				supPrice = (Double) getSupport().get(date);
				String prevDate = both.get(i-1);
				if(supPrice>0.0) {
					Double prevResprice = (Double) getResistance().get(prevDate);
					Double range = prevResprice-supPrice;
					widthToSupport.add(range);
					Integer elapsed = parser.getElapsedDays(prevDate, date);
					halfCycleToSupport.add(elapsed*1.0);
					s = s + date + "\t" + Math.round(supPrice) + "\t\t" + 
										  Math.round(range) + "\t\t" + 
										  elapsed + 
										  "\n";
				}
			} catch (Exception e) {
				// it's normal b/c adAll
			}
			
			try { // RESISTANCE
				resPrice = (Double) getResistance().get(date);
				String prevDate = both.get(i-1);
				if(resPrice>0.0) {
					Double prevSubprice = (Double) getSupport().get(prevDate);
					Double range = resPrice-prevSubprice;
					widthToResistance.add(range);
					Integer elapsed = parser.getElapsedDays(prevDate, date);
					halfCycleToResistance.add(elapsed*1.0);
					s = s + date + "\t    " + Math.round(resPrice) + "\t   " + 
											Math.round(range) + "\t\t  " + 
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
