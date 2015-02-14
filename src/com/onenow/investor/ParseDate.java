package com.onenow.investor;

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
		end = year + month + day + " 16:30:00";
//		System.out.println("End " + date + " "+ end);
		return end;
	}
	
	public String getDashedYear(String dashed) {
		String s = "";
		s = dashed.substring(0, 4);
		return s;
	}
	public String getDashedMonth(String dashed) {
		String s = "";
		s= dashed.substring(5, 7);
		return s;
	}
	public String getDashedDay(String dashed) {
		String s = "";
		s = dashed.substring(8, 10);
		return s;
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
	public String getDatePlus(String date, Integer num){
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
			day = Integer.parseInt(getDashedDay(date));
		} catch (NumberFormatException e) { } // nothing to do		
		try {
			month = Integer.parseInt(getDashedMonth(date));
		} catch (NumberFormatException e) { } // nothing to do 
		try {
			year = Integer.parseInt(getDashedYear(date));
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
}







