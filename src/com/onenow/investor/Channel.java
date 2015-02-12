package com.onenow.investor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.ib.client.Types.SecType;
import com.onenow.finance.Underlying;

public class Channel {

	private Contract contract;
	private HashMap resistanceDayMap = new HashMap();
	private HashMap supportDayMap = new HashMap();
	private HashMap recentDayMap = new HashMap();
	// open, close...
	
	private List<String> resistanceDayList = new ArrayList<String>();
	private List<String> supportDayList = new ArrayList<String>();
	private List<String> recentDayList = new ArrayList<String>();
	
	ParseDate parser = new ParseDate();
		
	public Channel() {
		
	}
	
	public Channel(Contract contract) {
		setContract(contract);
		
	}

	public void addResistance(String date) {
		if(!getResistanceDayMap().containsKey(date)) {
			getResistanceDayList().add(date);
		}
		getResistanceDayMap().put(date, -999999.0);
	}

	public void addResistance(String date, Double price) {
		addResistance(date);
		getResistanceDayMap().put(date, price);
	}

	public void addSupport(String date) {
		if(!getSupportDayMap().containsKey(date)) {
			getSupportDayList().add(date);
		}
		getSupportDayMap().put(date, 999999.0);
	}

	public void addSupport(String date, Double price) {
		addSupport(date);
		getSupportDayMap().put(date, price);		
	}

	public void addRecent(String date) {
		if(!getRecentDayMap().containsKey(date)) {
			getRecentDayList().add(date);
			getRecentDayMap().put(date, 999999.0);
		}
	}
	
	public void addRecent(String date, Double price) {
		addRecent(date);
		getRecentDayMap().put(date, price);
	}
	
	public Double getForecastResistance(int range) {
		Double price=0.0;
		Double min=999999.0;

		Collections.sort(resistanceDayList);
		int size = resistanceDayList.size()-1;
		for(int i=size; i>size-range; i--){
			String date = resistanceDayList.get(i);
			Double level = (Double) getResistanceDayMap().get(date);
			if(Math.round(level) < Math.round(min)) {
				price=(double) Math.round(level);
			}
		}
		
		return price;
	}
	
	public Double getForecastSupport(int range) {
		Double price=0.0;
		Double max=-999999.0;

		Collections.sort(supportDayList);
		int size = supportDayList.size()-1;
		for(int i=size; i>size-range; i--){
			String date = supportDayList.get(i);
			Double level = (Double) getSupportDayMap().get(date);
			if(Math.round(level) > Math.round(max)) {
				price=(double) Math.round(level);
			}
		}
		return price;
	}
	
	// PRINT
	public String toString() {
		String s="\n";
		s = s + getContract().toString() + "\n";
		
		List<Double> rangeToResistance = new ArrayList<Double>();
		List<Double> rangeToSupport = new ArrayList<Double>();
		List<Double> halfCycleToResistance = new ArrayList<Double>();		
		List<Double> halfCycleToSupport = new ArrayList<Double>();		

		s = s + "DATE" + "\t\t" + "SPIKE" + "\t\t" + "RANGE" + "\t\t" + "DAYS" + "\n";

		s = outlineChannel(	s, 
							rangeToResistance, halfCycleToResistance,
							rangeToSupport, halfCycleToSupport) + "\n";
		
		if(contract.secType().equals(SecType.IND)) {
			s = s + "kpi " + "\t\t" + getSupportSlope(6) + "\t" + getResistanceSlope(6) + "\t" + 
						Math.round(getMean(rangeToSupport)) + "/" + Math.round(getMean(rangeToResistance)) + "\t\t" +
						Math.round(getMean(halfCycleToSupport)) + "/" + Math.round(getMean(halfCycleToResistance)) + "\n";
	
			s = s + "forecast" + "\t" + Math.round(getForecastSupport(1)+getSupportSlope(6)) + "\t" +
					  Math.round(getForecastResistance(1)+getResistanceSlope(6)) + 
					  "\n\n";
	
			s = s + "alert=> " + "\t*" + Math.round(getForecastSupport(3)+10) + "*\t*" + 
				     				    Math.round(getForecastResistance(3)-10) + "*\t" +
				     				    "\n";				
		}
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
		
		List<String> allDates = getDayList();
						
		int size = allDates.size()-1;
		for(int i=size; i>=0; i--) {
			String date=allDates.get(i);
			s = s + date;
			try { // SUPPORT
				Double supPrice = (Double) getSupportDayMap().get(date);
				String prevDate = allDates.get(i-1);
				s = getSupportString(	date, supPrice, prevDate,
										widthToSupport, halfCycleToSupport, s);
			} catch (Exception e) {  } // it's normal b/c adAll
			
			try { // RESISTANCE
				Double resPrice = (Double) getResistanceDayMap().get(date);
				String prevDate = allDates.get(i-1);
				s = getResistanceString(	date, resPrice, prevDate,
											widthToResistance, halfCycleToResistance, s);
			} catch (Exception e) {  } //	it's normal b/c adAll
			
			try { // RECENT
				Double recentPrice = (Double) getRecentDayMap().get(date);
				String prevDate = allDates.get(i-1);
				s = getRecentString(	date, recentPrice, prevDate,
										widthToResistance, halfCycleToResistance, s);
			} catch (Exception e) {  } //	it's normal b/c adAll
			s = s + "\n";
			
//			System.out.println(s);
		}
		return s;
	}

	private List<String> getDayList() {
		List<String> allDates = new ArrayList<String>();
		allDates.addAll(getResistanceDayList());
		allDates.addAll(getSupportDayList());
		allDates.addAll(getRecentDayList());
		Collections.sort(allDates);
		List<String> uniqueDates = new ArrayList<String>();		
		for (String date:allDates) {
			  if (!uniqueDates.contains(date)) {
				  uniqueDates.add(date);
			  }
		}
		return uniqueDates;
	}

	private String getRecentString(	String date, Double recentPrice, 
									String prevDate, List<Double> widthToRecent,
									List<Double> halfCycleToRecent, String s) {
		
		s = s + " ?" + "\t" + Math.round(recentPrice);
		
		return s;
	}
	
	private String getResistanceString(	String date, Double resPrice,
										String prevDate, List<Double> widthToResistance,
										List<Double> halfCycleToResistance, String s) {
		
		Double prevSubprice = (Double) getSupportDayMap().get(prevDate);
		Double range = resPrice-prevSubprice;
		widthToResistance.add(range);
		Integer elapsed = parser.getElapsedDays(prevDate, date);
		halfCycleToResistance.add(elapsed*1.0);
		
		if(resPrice>0 && resPrice<9999) {
			s = s + " +" + "\t    " + 	Math.round(resPrice) + "\t   ";
			
			if(contract.secType().equals(SecType.IND)) {
				s = s + 			Math.round(range) + "\t\t  " + 
									elapsed;
			}
		}
		
		return s;
	}

	private String getSupportString(String date, Double supPrice,
									String prevDate, List<Double> widthToSupport,
									List<Double> halfCycleToSupport, String s) {
		
		Double prevResprice = (Double) getResistanceDayMap().get(prevDate);
		Double range = prevResprice-supPrice;
		widthToSupport.add(range);
		Integer elapsed = parser.getElapsedDays(prevDate, date);
		halfCycleToSupport.add(elapsed*1.0);

		if(supPrice>0 && supPrice<9999) {
			s = s + " -" + "\t" + 	Math.round(supPrice) + "\t\t";
			
			if(contract.secType().equals(SecType.IND)) {
				s = s +			Math.round(range) + "\t\t" + 
								elapsed;
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
	public HashMap getResistanceDayMap() {
		return resistanceDayMap;
	}

	private void setResistanceDayMap(HashMap resistance) {
		this.resistanceDayMap = resistance;
	}

	public HashMap getSupportDayMap() {
		return supportDayMap;
	}

	private void setSupportDayMap(HashMap support) {
		this.supportDayMap = support;
	}

	public List<String> getResDayList() {
		return resistanceDayList;
	}

	private void setResDayList(List<String> resDate) {
		this.resistanceDayList = resDate;
	}

	public List<String> getSupDayList() {
		return supportDayList;
	}

	private void setSupDayList(List<String> supDate) {
		this.supportDayList = supDate;
	}

	public HashMap getRecentDayMap() {
		return recentDayMap;
	}

	private void setRecentDayMap(HashMap recent) {
		this.recentDayMap = recent;
	}

	private List<String> getResistanceDayList() {
		return resistanceDayList;
	}

	private void setResistanceDayList(List<String> date) {
		this.resistanceDayList = date;
	}

	private List<String> getSupportDayList() {
		return supportDayList;
	}

	private void setSupportDayList(List<String> date) {
		this.supportDayList = date;
	}

	public List<String> getRecentDayList() {
		return recentDayList;
	}

	private void setRecentDayList(List<String> date) {
		this.recentDayList = date;
	}

	public Contract getContract() {
		return contract;
	}

	private void setContract(Contract contract) {
		this.contract = contract;
	}

	

}
