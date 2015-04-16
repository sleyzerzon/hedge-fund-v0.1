package com.onenow.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ParseDate {

	public ParseDate() {
		
	}
	
	
	public String getToday() {
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // "yyyy-MM-dd HH:mm"
		return sdf.format(today);
	}

	public String removeDash(String dashed) {
		String end="";
		String year=getDashedYear(dashed);
		String month=getDashedMonth(dashed);
		String day=getDashedDay(dashed);
		end = year + month + day;
//		System.out.println("End " + end);
		return end;
	}
	
	public String getDashedYear(String dashed) {
		String s = "";
		s = dashed.substring(0, 4);
		return s;
	}
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
	
	public String getUndashedDate(String dashedDate) {
		String year = getDashedYear(dashedDate);
		String month = getDashedMonth(dashedDate);
		String day = getDashedDay(dashedDate);
		String unDashedDate = year + month + day;
		return unDashedDate;		
	}
	public String getDashedDate(String unDashedDate) {
		String year = getUnDashedYear(unDashedDate);
		String month = getUnDashedMonth(unDashedDate);
		String day = getUnDashedDay(unDashedDate);
		String dashedDate = year + "-" + month + "-" + day;
		return dashedDate;
	}
	

	public boolean isLaterDate(String date1, String date2) {
		boolean later = true;
		Integer year1 = Integer.parseInt(getDashedYear(date1));
		Integer year2 = Integer.parseInt(getDashedYear(date2));
		if(year1>year2) { return false; }
		Integer month1 = Integer.parseInt(getDashedMonth(date1));
		Integer month2 = Integer.parseInt(getDashedMonth(date2));
		if(month1>month2) { return false; }
		Integer day1 = Integer.parseInt(getDashedDay(date1));
		Integer day2 = Integer.parseInt(getDashedDay(date2));
		if(day1>day2) { return false; }
		return later;
	}
	public String getDatePlus(String dashedDate, Integer num){
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

		newDay=day+num;
		if(newDay>31) {
			newDay=newDay-31;
			month=month+1;
		}
		newMonth=month;
		if(month>12) {
			newMonth=newMonth-12;
			year=year+1;
		}
		newYear=year;

		sDay=newDay.toString();
		if(newDay<10){
			sDay="0"+sDay;
		}
		sMonth=newMonth.toString();
		if(newMonth<10){
			sMonth="0"+sMonth;
		}
		sYear=newYear.toString();
		s=sYear+"-"+sMonth+"-"+sDay;
		return s;
	}
	public Integer getElapsedDays(String d1, String d2) {
		Integer elapsedDays = 0;
		Integer elapsedMonths = 0;
		Integer elapsedYears = 0; // TODO
		
		Integer month1=0;
		Integer month2=0;
		Integer day1=0;
		Integer day2=0;
		
		try {
			month1 = Integer.parseInt(getDashedMonth(d1));
			month2 = Integer.parseInt(getDashedMonth(d2));
			elapsedMonths = 11-month1+month2;
			if(month2>month1) {
				elapsedMonths = month2-month1-1;				
			} 
			if(month1.equals(month2)) {
				elapsedMonths = 0;
			}
		} catch (NumberFormatException e) { } // some intentionally come null
		
		
		try {
			day1 = Integer.parseInt(getDashedDay(d1));
			day2 = Integer.parseInt(getDashedDay(d2));
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
	
	private int getMonthLenth(int month) {
		if(month==1) { // jan
			return 30;
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
		if(month==11) { // jan
			return 30;
		}
		if(month==12) { // jan
			return 31;
		}
		return -1;
	}
	
	// TEST
	
	// PRINT
	
	// SET GET
}







