package com.onenow.data;

import java.util.ArrayList;
import java.util.List;

import com.onenow.util.ParseDate;

public class OptionExpiration {
	
	List<String> indexExpList = new ArrayList<String>();
	List<String> futuresExpList = new ArrayList<String>();
	
	ParseDate parser = new ParseDate();
	
	public OptionExpiration() {
		initOptionExpList();
		initFuturesExpList(); 
	}
	
	// PRIVATE
	private void initOptionExpList() {
		String unDashedStartDate = "20150402";
		String dashedStartDate = getParser().getDashedDate(unDashedStartDate);
		
		int weeksToPresent = getParser().getWeeksToPresent(dashedStartDate);
//		System.out.println("weeks to present " + weeksToPresent);
		
		int numExpDates = 3; 		
		for(int i=weeksToPresent; i<(weeksToPresent+numExpDates); i++) {
//			System.out.println("i " + i);
			String dashedDate = getParser().getDatePlus(dashedStartDate, 7*i); // a number of weeks later
			String unDashedDate = getParser().getUndashedDate(dashedDate);
			getIndexExpList().add(unDashedDate);
			System.out.println("+ Added index option expiration " + unDashedDate);			
		}
	}	

	private void initFuturesExpList() {
		getFuturesExpList().add("201506");
//		getIndexExpList().add("20150529");
	}

	// PUBLIC
	public String getNextIndexExp() {
		String s = "";
		s = getIndexExpList().get(0);
		return s;
	}

	public String getTwoWeekIndexExp() {
		String s = "";
		s = getIndexExpList().get(2);		
		return s;
	}

	public String getFourWeekIndexExp() {
		String s = "";
		s = getIndexExpList().get(4);				
		return s;
	}

	public String getNextFuturesExp() {
		String s = "";
		s = getFuturesExpList().get(0);
		return s;
	}

	public String getTwoWeekFuturesExp() {
		String s = "";
		s = getFuturesExpList().get(2);		
		return s;
	}

	public String getFourWeekFuturesExp() {
		String s = "";
		s = getFuturesExpList().get(4);				
		return s;
	}

	// SET GET
	public List<String> getIndexExpList() {
		return indexExpList;
	}

	private void setIndexExpList(List<String> expList) {
		this.indexExpList = expList;
	}

	public List<String> getFuturesExpList() {
		return futuresExpList;
	}

	private void setFuturesExpList(List<String> futuresExpList) {
		this.futuresExpList = futuresExpList;
	}

	public ParseDate getParser() {
		return parser;
	}

	public void setParser(ParseDate parser) {
		this.parser = parser;
	}

}
