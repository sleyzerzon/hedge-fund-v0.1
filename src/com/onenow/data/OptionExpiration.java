package com.onenow.data;

import java.util.ArrayList;
import java.util.List;

public class OptionExpiration {
	
	List<String> indexExpList = new ArrayList<String>();
	List<String> futuresExpList = new ArrayList<String>();
	
	public OptionExpiration() {
		initIndexExpList();
		initFuturesExpList(); 
	}
	
	// PRIVATE
	private void initIndexExpList() {
		String startingDate = "20150418";
		
		getIndexExpList().add("20150416");
//		getIndexExpList().add("20150424");

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

}
