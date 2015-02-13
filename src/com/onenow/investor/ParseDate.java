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
		String day=getDahsedDay(dashed);
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
	public String getDahsedDay(String dashed) {
		String s = "";
		s = dashed.substring(8, 10);
		return s;
	}
	public Integer getElapsedDays(String d1, String d2) {
		Integer elapsedDays = 0;
		Integer elapsedMonths = 0;
		Integer elapsedYears = 0; // TODO
		
		try {
			Integer month1 = Integer.parseInt(getDashedMonth(d1));
			Integer month2 = Integer.parseInt(getDashedMonth(d2));
			if(month2>=month1) {
				elapsedMonths = month2-month1;				
			} else {
				elapsedMonths = month1+month2-12;
			}
		} catch (NumberFormatException e) { } // some intentionally come null
		
		
		try {
			Integer day1 = Integer.parseInt(getDahsedDay(d1));
			Integer day2 = Integer.parseInt(getDahsedDay(d2));
			if(day2>day1) { // same month
				elapsedDays = day2 - day1; 
			}
			else {
				elapsedDays = 31 - day1 + day2; // assumes 31d month
			}
		} catch (NumberFormatException e) { } // some intentionally come null

		Integer elapsedTotal = elapsedMonths*31+elapsedDays;
		return elapsedTotal;
	}
}







