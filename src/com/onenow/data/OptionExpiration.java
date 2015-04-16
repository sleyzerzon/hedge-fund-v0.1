package com.onenow.data;

import java.util.ArrayList;
import java.util.List;

import com.onenow.util.ParseDate;

public class OptionExpiration {
	
	List<String> indexExpList = new ArrayList<String>();
	List<String> futuresExpList = new ArrayList<String>();
	
	ParseDate parser = new ParseDate();
	
	public OptionExpiration() {
		initIndexExpList();
		initFuturesExpList(); 
	}
	
	// PRIVATE
	private void initIndexExpList() {
		String unDashedDate = "20150416";
		int numDates = 3;
		
		for(int i=0; i<numDates; i++) {
			getIndexExpList().add(unDashedDate);
			System.out.println("+ Added index option expiration " + unDashedDate);
			
			String dashedDate = getParser().getDashedDate(unDashedDate);
			dashedDate = getParser().getDatePlus(dashedDate, 7); // a week later
			unDashedDate = getParser().getUndashedDate(dashedDate);
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
