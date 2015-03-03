package com.onenow.investor;

import java.util.List;

public class OptionExpiration {
	
	List<String> indexExpList;
	List<String> futuresExpList;
	
	public OptionExpiration() {
		initIndexExpList();
		initFuturesExpList();
	}
	
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

	// PRIVATE
	private void initIndexExpList() {
		getIndexExpList().add("20150307");
		getIndexExpList().add("20150314");
		getIndexExpList().add("20150321");
		getIndexExpList().add("20150328");
		getIndexExpList().add("20150404");
		getIndexExpList().add("20150411");
		getIndexExpList().add("20150418");
		getIndexExpList().add("20150425");

	}

	private void initFuturesExpList() {
		getIndexExpList().add("20150305");
		getIndexExpList().add("20150312");
		getIndexExpList().add("20150319");
		getIndexExpList().add("20150326");
		getIndexExpList().add("20150402");
		getIndexExpList().add("20150409");
		getIndexExpList().add("20150416");
		getIndexExpList().add("20150423");

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
