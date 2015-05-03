package com.onenow.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.onenow.test.Testable;

public class ParseDate implements Testable {

	public ParseDate() {
		
	}	
	
	/**
	 * Returns today's date in dashed format without time
	 * @return
	 */
	public String getToday() {
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // "yyyy-MM-dd HH:mm"
		return sdf.format(today);
	}
	
	public String getClose(String date) {
		String s = "";
		s = date + " 16:30:00";
		return s;
	}
	
	public String getCloseToday() {
		String s = "";
		s = getClose(getToday());
		return s;
	}
	
	public String getTomorrow() {
		String s = "";
		s = s + getDashedDatePlus(getToday(), 1);
		return s;
	}
	
	public String getYesterday() {
		String s = "";
		s = s + getDashedDateMinus(getToday(), 1);
		return s;
	}
	
	

	/**
	 * Removes dashes from a dashed date
	 * @param dashed
	 * @return
	 */
	public String removeDash(String dashed) {
		String end="";
		String year=getDashedYear(dashed);
		String month=getDashedMonth(dashed);
		String day=getDashedDay(dashed);
		end = year + month + day;
//		System.out.println("End " + end);
		return end;
	}
	
	/** 
	 * Extracts the year from a dashed date
	 * @param dashed
	 * @return
	 */
	public String getDashedYear(String dashed) {
		String s = "";
		s = dashed.substring(0, 4);
		return s;
	}
	/**
	 * Extracts the year from an undashed date
	 * @param unDashed
	 * @return
	 */
	public String getUnDashedYear(String unDashed) {
		String s = "";
		s = unDashed.substring(0, 4);
		return s;
	}
	public String getDashedMonth(String dashed) {
		String s = "";
		s= dashed.substring(5, 7);
		return s;
	}
	public String getUnDashedMonth(String unDashed) {
		String s = "";
		s= unDashed.substring(4, 6);
		return s;
	}

	public String getDashedDay(String dashed) {
		String s = "";
		s = dashed.substring(8, 10);
		return s;
	}
	public String getUnDashedDay(String unDashed) {
		String s = "";
		s = unDashed.substring(6, 8);
		return s;
	}
	
	/**
	 * Gets an undashed date from a dashed date
	 * @param dashedDate
	 * @return
	 */
	public String getUndashedDate(String dashedDate) {
		String year = getDashedYear(dashedDate);
		String month = getDashedMonth(dashedDate);
		String day = getDashedDay(dashedDate);
		String unDashedDate = year + month + day;
		return unDashedDate;		
	}
	/**
	 * Gets a dashed date from an undashed date
	 * @param unDashedDate
	 * @return
	 */
	public String getDashedDate(String unDashedDate) {
		String year = getUnDashedYear(unDashedDate);
		String month = getUnDashedMonth(unDashedDate);
		String day = getUnDashedDay(unDashedDate);
		String dashedDate = year + "-" + month + "-" + day;
		return dashedDate;
	}
	
	public int getMonthsToPresent(String unDashedYearMonth) {
		int months = 0;
		
		return months;
	}
	
	/**
	 * Counts the number of weeks from an old date to present date
	 * @param dashedDate
	 * @return
	 */
	public int getWeeksToPresent(String dashedDate) {
		String dashedToday = getToday();
		int weeksToPresent = 0;
		while(true) {
			if(isLaterDate(dashedDate, dashedToday)) {
				weeksToPresent++;
//				System.out.println("week++");
			} else {
				return weeksToPresent;
			}
			dashedDate = getDashedDatePlus(dashedDate, 7); // skip a week
		}
	}
	
	public boolean isLaterMonth(String yearMonth1, String yearMonth2) {
		boolean later = true;
		
		return later;
	}

	/**
	 * Returns true if dashedDate2 is later than dashedDate1
	 * @param dashedDate
	 * @param dashedCheckAgainstDate
	 * @return
	 */
	public boolean isLaterDate(String dashedDate, String dashedCheckAgainstDate) {
		boolean later = true;
		Integer year1 = Integer.parseInt(getDashedYear(dashedDate));
		Integer year2 = Integer.parseInt(getDashedYear(dashedCheckAgainstDate));
		if(year1>year2) { return false; }
		Integer month1 = Integer.parseInt(getDashedMonth(dashedDate));
		Integer month2 = Integer.parseInt(getDashedMonth(dashedCheckAgainstDate));
		if(month1>month2) { return false; }
		Integer day1 = Integer.parseInt(getDashedDay(dashedDate));
		Integer day2 = Integer.parseInt(getDashedDay(dashedCheckAgainstDate));
		if(day1>day2) { return false; }
		return later;
	}
	
	
	public String getMonthPlus(String yearMonth, Integer num) {
		String s="";
		
		return s;
	}
	

	
	private int getMonthLength(int month) {
		if(month==1) { // jan
			return 31;
		}
		if(month==2) { // feb TODO: every 4 years it has 29 days
			return 28;
		}
		if(month==3) { // mar
			return 31;
		}
		if(month==4) { // apr
			return 30;
		}
		if(month==5) { // may
			return 31;
		}
		if(month==6) { // jun
			return 30;
		}
		if(month==7) { // jul
			return 31;
		}
		if(month==8) { // aug
			return 31;
		}
		if(month==9) { // sep
			return 30;
		}
		if(month==10) { // oct
			return 31;
		}
		if(month==11) { // nov
			return 30;
		}
		if(month==12 || month==0) { // dec
			return 31;
		}
		return -1;
	}
	

	
	public Integer getElapsedDays(String dashedDay1, String dahsedDay2) {
		Integer elapsedDays = 0;
		Integer elapsedMonths = 0;
		Integer elapsedYears = 0; // TODO
		
		Integer month1=0;
		Integer month2=0;
		Integer day1=0;
		Integer day2=0;
		
		try {
			month1 = Integer.parseInt(getDashedMonth(dashedDay1));
			month2 = Integer.parseInt(getDashedMonth(dahsedDay2));
			elapsedMonths = 11-month1+month2;
			if(month2>month1) {
				elapsedMonths = month2-month1-1;				
			} 
			if(month1.equals(month2)) {
				elapsedMonths = 0;
			}
		} catch (NumberFormatException e) { } // some intentionally come null
		
		
		try {
			day1 = Integer.parseInt(getDashedDay(dashedDay1));
			day2 = Integer.parseInt(getDashedDay(dahsedDay2));
			elapsedDays = 31-day1+day2; // assumes 31d month

			if(day2>day1) { // same month
				elapsedDays = day2-day1-1; 
			}
			if(day1.equals(day2)) {
				elapsedDays = 0;
			}
		} catch (NumberFormatException e) { } // some intentionally come null

//		System.out.println("DAY " + day1 + " " + day2);
//		System.out.println("MONTH " + month1 + " " + month2);
//		System.out.println("ELAPSED " + elapsedMonths + " " + elapsedDays);
		Integer elapsedTotal = elapsedMonths*31+elapsedDays;
		return elapsedTotal;
	}
	
	/**
	 * Returns a new date from an old date plus the number of days since
	 * @param dashedDate
	 * @param plusDays
	 * @return
	 */
	public String getDashedDatePlus(String dashedDate, Integer plusDays){
//		System.out.println("Dashed date " + dashedDate + ". Num " + num);
		
		String s= "";
		Integer day=0;
		Integer month=0;
		Integer year=0;
		Integer newDay=0;
		Integer newMonth=0;
		Integer newYear=0;
		String sDay="";
		String sMonth="";
		String sYear="";
		try {
			day = Integer.parseInt(getDashedDay(dashedDate));
		} catch (NumberFormatException e) { } // nothing to do		
		try {
			month = Integer.parseInt(getDashedMonth(dashedDate));
		} catch (NumberFormatException e) { } // nothing to do 
		try {
			year = Integer.parseInt(getDashedYear(dashedDate));
		} catch (NumberFormatException e) { } // nothing to do

		newDay=day+plusDays;
		int monthLength = getMonthLength(month);
//		System.out.println("New day " + newDay + ". Old day " + day + ". Delta " + num);
		if(newDay>monthLength) {
			newDay=newDay-monthLength;
			month=month+1;
		}
		newMonth=month;
//		System.out.println("New month " + month);
		if(month>12) {
			newMonth=newMonth-12;
			year=year+1;
		}
		
		newYear=year;
//		System.out.println("New year " + year);

		sDay = getTwoDigitString(newDay);
		sMonth = getTwoDigitString(newMonth);
		sYear=newYear.toString();
		s=sYear+"-"+sMonth+"-"+sDay;
		
//		System.out.println("Date plus is " + s + "\n");
		return s;
	}
	
	public String getDashedDateMinus(String dashedDate, Integer minusDays){
//		System.out.println("Dashed date " + dashedDate + ". Num " + num);
		
		String s= "";
		Integer day=0;
		Integer month=0;
		Integer year=0;
		Integer newDay=0;
		Integer newMonth=0;
		Integer newYear=0;
		String sDay="";
		String sMonth="";
		String sYear="";
		try {
			day = Integer.parseInt(getDashedDay(dashedDate));
		} catch (NumberFormatException e) { } // nothing to do		
		try {
			month = Integer.parseInt(getDashedMonth(dashedDate));
		} catch (NumberFormatException e) { } // nothing to do 
		try {
			year = Integer.parseInt(getDashedYear(dashedDate));
		} catch (NumberFormatException e) { } // nothing to do

		newDay=day-minusDays;
		int prevMonthLength = getMonthLength(month-1);
//		System.out.println("New day " + newDay + ". Old day " + day + ". Delta " + num);
		if(newDay<1) {
			newDay=prevMonthLength-newDay;
			month=month-1;
		}
		newMonth=month;
//		System.out.println("New month " + month);
		if(month<1) {
			newMonth=newMonth+12;
			year=year-1;
		}
		newYear=year;
//		System.out.println("New year " + year);

		sDay = getTwoDigitString(newDay);
		sMonth = getTwoDigitString(newMonth);
		sYear=newYear.toString();
		s=sYear+"-"+sMonth+"-"+sDay;
		
//		System.out.println("Date plus is " + s + "\n");
		return s;
	}

	private String getTwoDigitString(Integer newDay) {
		String sDay;
		sDay=newDay.toString();
		if(newDay<10){
			sDay="0"+sDay;
		}
		return sDay;
	}
	
	// TEST
	public boolean test() {
		
		boolean result = 	testDatePlus() && 
							testDateMinus();
		
		
		return result;
		
	}
	
	private boolean testDatePlus() {
		
		boolean result = false;
		
		String date1 = "2015-02-28";
		String date2 = "2015-12-31";
		String date3 = "2015-05-15";
		int delta = 1;
		
		String dateOut1 = getDashedDatePlus(date1, delta);	
		String dateOut2 = getDashedDatePlus(date2, delta);	
		String dateOut3 = getDashedDatePlus(date3, delta);	

		if(	dateOut1.equals("2015-03-01") &&
			dateOut2.equals("2016-01-01") &&
			dateOut3.equals("2015-05-16")
			) {
			result = true;
		}		
		
		System.out.println("testDate1 " + dateOut1);
		System.out.println("testDate2 " + dateOut2);
		System.out.println("testDate3 " + dateOut3);
		
		return result;
	}
	
	private boolean testDateMinus() {
		
		boolean result = false;

		String date1 = "2015-03-01";
		String date2 = "2015-01-01";
		String date3 = "2015-05-15";

		int delta = 1;
		
		String dateOut1 = getDashedDateMinus(date1, delta);
		String dateOut2 = getDashedDateMinus(date2, delta);
		String dateOut3 = getDashedDateMinus(date3, delta);	

		
		if(	dateOut1.equals("2015-02-28") &&
			dateOut2.equals("2014-12-31") && 
			dateOut3.equals("2015-05-14")) {
			result = true;
		}

		System.out.println("testDate1 " + dateOut1);
		System.out.println("testDate2 " + dateOut2);
		System.out.println("testDate3 " + dateOut3);

		
		return result;
	}
	
	// PRINT
	
	// SET GET
}







