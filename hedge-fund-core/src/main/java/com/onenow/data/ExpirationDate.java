package com.onenow.data;

import java.util.ArrayList;
import java.util.List;

import com.onenow.util.TimeParser;

public class ExpirationDate {
	
	List<String> indexExpList = new ArrayList<String>();
	List<String> indexCompleteExpList = new ArrayList<String>();
	List<String> futuresExpList = new ArrayList<String>();
	List<String> futuresCompleteExpList = new ArrayList<String>();
	
	private int optionsMaxTimeRange = 45; // from 90
	private int optionsMinTimeRange = 0;

	private int futuresMaxTimeRange = 120; 
	private int futuresMinTimeRange = 0; // from -30

		
	public ExpirationDate() {

	}
	
	// PRIVATE
	public void initIndexOptionExpList() {
		getIndexExpList().add("20150402");
		getIndexExpList().add("20150409");
		getIndexExpList().add("20150416");
		getIndexExpList().add("20150424");
		getIndexExpList().add("20150431");
		getIndexExpList().add("20150501");
		getIndexExpList().add("20150508");
		getIndexExpList().add("20150514");
		getIndexExpList().add("20150522");
		getIndexExpList().add("20150529");
		getIndexExpList().add("20150529");
		getIndexExpList().add("20150612");
		getIndexExpList().add("20150618"); // TODO monthly options
		getIndexExpList().add("20150626");
		getIndexExpList().add("20150630");
		getIndexExpList().add("20150702");
		getIndexExpList().add("20150710");
		getIndexExpList().add("20150715");
		getIndexExpList().add("20150724");
		getIndexExpList().add("20150731");
		
		// request contract 201508
		// requestContractdDetail
		getIndexExpList().add("20150807");
		getIndexExpList().add("20150814");
		getIndexExpList().add("20150815"); // wrong
		getIndexExpList().add("20150820"); // added
		getIndexExpList().add("20150828");
		getIndexExpList().add("20150831");

	}	
	
	public void initFuturesExpList() {
		getFuturesExpList().add("201503");
		getFuturesExpList().add("201504");
		getFuturesExpList().add("201505");
		getFuturesExpList().add("201506");
		// getFuturesExpList().add("201507");
		// getFuturesExpList().add("201508");
		getFuturesExpList().add("201509");
		// getFuturesExpList().add("201510");
		// getFuturesExpList().add("201511");
		getFuturesExpList().add("201512");

	}
	

	// VALID SET
	/**
	 * Return the only valid expiration dates, occurring within x months, to a basis date
	 * @param date
	 * @return
	 */
	public List<String> getValidOptionExpList(String date) {
		List<String> validIndexExp = new ArrayList<String>();		
		// TODO: rule out current month dates with days in the past
		for(String basisDate:indexExpList) {
			// System.out.println("OPTIONS " + "date " + date + " basis " + basisDate + " delta " + TimeParser.getMonthDelta(date, basisDate));
			if(	TimeParser.getElapsedDaysUndashed(date, basisDate)<optionsMaxTimeRange && 
				TimeParser.getElapsedDaysUndashed(date, basisDate)>=optionsMinTimeRange
				) {
				validIndexExp.add(basisDate);
			}
		}
		return validIndexExp;
	}
	
	public List<String> getValidFuturesExpList(String date) {
		List<String> validExp = new ArrayList<String>();
		for(String basisDate:futuresExpList) {
			// System.out.println("FUTURES " + "date " + date + " basis " + basisDate + " delta " + TimeParser.getElapsedUndashedDays(date, basisDate));
			// future expire at end of the month designated
			if(	TimeParser.getElapsedDaysUndashed(date, basisDate)<futuresMaxTimeRange && 
				TimeParser.getElapsedDaysUndashed(date, basisDate)>=futuresMinTimeRange
				) {
				validExp.add(basisDate);
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
