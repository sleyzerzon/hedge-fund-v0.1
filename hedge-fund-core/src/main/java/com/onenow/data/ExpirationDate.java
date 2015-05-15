package com.onenow.data;

import java.util.ArrayList;
import java.util.List;

import com.onenow.util.ParseDate;

public class ExpirationDate {
	
	List<String> indexExpList = new ArrayList<String>();
	List<String> indexCompleteExpList = new ArrayList<String>();
	List<String> futuresExpList = new ArrayList<String>();
	List<String> futuresCompleteExpList = new ArrayList<String>();
	
	ParseDate parseDate = new ParseDate();
	
	public ExpirationDate() {

	}
	
	// PRIVATE
	public void initOptionExpList() {
//		getIndexExpList().add("20150402");
//		getIndexExpList().add("20150409");
//		getIndexExpList().add("20150416");
//		getIndexExpList().add("20150424");
//		getIndexExpList().add("20150431");
//		getIndexExpList().add("20150501");
//		getIndexExpList().add("20150508");
		getIndexExpList().add("20150514");
//		getIndexExpList().add("20150522");
//		getIndexExpList().add("20150529");

	}	
	
	public void initCompleteOptionExpList() {
//		String unDashedStartDate = "20150402";
//		String dashedStartDate = parseDate.getDashedDate(unDashedStartDate);
//		
//		int weeksToPresent = parseDate.getWeeksToPresent(dashedStartDate);
////		System.out.println("weeks to present " + weeksToPresent);
//		
//		int numExpDates = 3; 		
//		for(int i=weeksToPresent; i<(weeksToPresent+numExpDates); i++) {
////			System.out.println("i " + i);
//			String dashedDate = parseDate.getDatePlus(dashedStartDate, 7*i); // a number of weeks later
//			String unDashedDate = parseDate.getUndashedDate(dashedDate);
//			getIndexExpList().add(unDashedDate);
//			System.out.println("+ Added index option expiration " + unDashedDate);			
//		}
		
	}

	public void initFuturesExpList() {
//		getFuturesExpList().add("201503");
//		getFuturesExpList().add("201504");
//		getFuturesExpList().add("201505");
		getFuturesExpList().add("201506");
//		getFuturesExpList().add("201507");
//		getFuturesExpList().add("201508");
//		getFuturesExpList().add("201509");
//		getFuturesExpList().add("201510");
//		getFuturesExpList().add("201511");
//		getFuturesExpList().add("201512");
	}
	
	public void initCompleteFuturesExpList() {
//		String unDashedStartMonth = "201506";
//		
//		int monthsToPresent = parseDate.getMonthsToPresent(unDashedStartMonth);
//		System.out.println("months to present " + monthsToPresent);
//		
//		int numExpDates = 3;
//		for(int i=monthsToPresent; i<(monthsToPresent+numExpDates); i++) {
////			System.out.println("i " + i);
//			String undashedYearMonth = parseDate.getMonthPlus(unDashedStartMonth, i);
//			getFuturesExpList().add(undashedYearMonth);
//		}

	}

	// VALID SET
	/**
	 * Return the only valid expiration dates, occurring within x months, to a basis date
	 * @param undashedBasis
	 * @return
	 */
	public List<String> getValidIndexExpList(String undashedBasis) {
		List<String> validIndexExp = new ArrayList<String>();
		
		
		for(String date:indexExpList) {
			if(	parseDate.getMonthDelta(date, undashedBasis)<3 && 
				parseDate.getMonthDelta(date, undashedBasis)>3
				) {
				validIndexExp.add(date);
			}
		}
		
		return validIndexExp;
	}
	
	public List<String> getValidFuturesExpList(String undashedBasis) {
		List<String> validExp = new ArrayList<String>();
		
		for(String date:futuresExpList) {
			if(	parseDate.getMonthDelta(date, undashedBasis)<3 && 
				parseDate.getMonthDelta(date, undashedBasis)>3
				) {
				validExp.add(date);
			}
		}

		return validExp;
	}
	
	
	// TYPICAL TIMELINES
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

	public List<String> getIndexCompleteExpList() {
		return indexCompleteExpList;
	}

	public void setIndexCompleteExpList(List<String> indexCompleteExpList) {
		this.indexCompleteExpList = indexCompleteExpList;
	}

	public List<String> getFuturesCompleteExpList() {
		return futuresCompleteExpList;
	}

	public void setFuturesCompleteExpList(List<String> futuresCompleteExpList) {
		this.futuresCompleteExpList = futuresCompleteExpList;
	}

}
