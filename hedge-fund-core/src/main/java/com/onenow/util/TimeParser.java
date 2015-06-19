package com.onenow.util;

import com.onenow.test.Testable;

import org.testng.Assert;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class TimeParser implements Testable {

	public TimeParser() {
		
	}	
	
	public static void wait(int seconds) {
		try {
			Watchr.info("Going to sleep: " + seconds + "seconds");
			Thread.sleep(seconds*1000);
		} catch (InterruptedException e1) { 
			// nothing to do 
		}
	}
	
	public static String getFormatedPacificDateTime(Long time) {
		
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss zzzz MM-dd-yyyy");
		TimeZone zone = TimeZone.getTimeZone("PST");
		format.setTimeZone(zone);

		return format.format(time);
	}
	

	
	/**
	 * Returns today's date in dashed format without time
	 * @return
	 */
	public static String getTodayDashed() {
		
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		TimeZone UTC = TimeZone.getTimeZone("UTC");
		sdf.setTimeZone(UTC);
		
		return sdf.format(today);
	}
	
	public static String getTodayUndashed() {
		String date = "";
		date = getDateUndashed(getTodayDashed());
		return date;
	}

	public static String getNowDashed() {
		Date today = new Date();
		// TODO: it uses local time zone, what should it use?
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
		return sdf.format(today);
	}

	public static long getTimestampNow() {

		long timeStamp=0;
		
		try {
			Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
			timeStamp = calendar.getTimeInMillis(); ;
		} catch (Exception e) {
			// e.printStackTrace();
		}

		return timeStamp;
	}
	
	public static long getElapsedStamps(long oldStamp) {
		long elapsed = 0;
		elapsed = getTimestampNow() - oldStamp;
		return elapsed;
	}
	
	/**
	 * Defines closing date, by midnight of UTZ
	 * @param date
	 * @return
	 */
	// The NYSE and NYSE MKT are open from Monday through Friday 9:30 a.m. to 4:00 p.m. ET.
	// Closing is 1pm PT, 4pm AR, 9PM UTZ
	public static String getClose(String date) {
		String s = "";
		int endOfDay = 24;
		int offset = getOffsetInMillis();
		int endDayLocal = endOfDay + getTimezoneOffsetSign(offset)*getTimezoneHourOffset(offset); // TODO: Minute offset 
		// s = date + " 16:30:00";
		// s = date + " 15:00:00"; // TODO: Computer time		
		s = date + " " + endDayLocal + ":00:00";
		return s;
	}
	
	public static String getCurrentTimezoneOffset() {

	    int offsetInMillis = getOffsetInMillis();

	    String offset = String.format("%02d:%02d", getTimezoneHourOffset(offsetInMillis), getTimezoneMinuteOffset(offsetInMillis));
	    offset = (offsetInMillis >= 0 ? "+" : "-") + offset;

	    return offset;
	} 
	
	private static int getOffsetInMillis() {
		int offsetInMillis = 0;
		
	    TimeZone tz = TimeZone.getDefault();  
	    Calendar cal = GregorianCalendar.getInstance(tz);
	    offsetInMillis = tz.getOffset(cal.getTimeInMillis());

		return offsetInMillis;
	}
	
	private static int getTimezoneOffsetSign(int offsetInMillis) {
		int sign = 0;
		sign = (offsetInMillis >= 0 ? +1 : -1);
		return sign;
	}
	
	private static int getTimezoneHourOffset(int offsetInMillis) {
		int hours = 0;
		hours = Math.abs(offsetInMillis / 3600000);
		return hours;
	}

	private static int getTimezoneMinuteOffset(int offsetInMillis) {
		int minutes  = 0;
		minutes = Math.abs((offsetInMillis / 60000) % 60);
		return minutes;
	}

	public static String getDashedCloseToday() {
		String s = "";
		s = getClose(getTodayDashed());
		return s;
	}
	
	

	/**
	 * Removes dashes from a dashed date
	 * @param dashedDate
	 * @return
	 */
	public static String removeDash(String dashedDate) {
		String date = "";
		String year = "";
		String month = "";
		String day = "";
		year = getYearDashed(dashedDate);
		month = getMonthDashed(dashedDate);
		day = getDayDashed(dashedDate);
		date = year + month + day;
//		System.out.println("End " + end);
		return date;
	}
	
	public static String addDash(String undashedDate) {
		String date = "";
		String year = "";
		String month = "";
		String day = "";
		year = getYearUndashed(undashedDate);
		month = getMonthUndashed(undashedDate);
		day = getDayUndashed(undashedDate);
		if(!day.equals("")) {
			date = year + "-" + month + "-" + day;					
		} else {
			date = year + "-" + month;		
		}
		return date;
	}
	
	/** 
	 * Extracts the year from a dashed date
	 * @param dashed
	 * @return
	 */
	public static String getYearDashed(String dashed) {
		String s = "";
		try {
			s = dashed.substring(0, 4);
		} catch (Exception e) { } 
		return s;
	}
	/**
	 * Extracts the year from an undashed date
	 * @param unDashed
	 * @return
	 */
	public static String getYearUndashed(String unDashed) {
		String s = "";
		try {
			s = unDashed.substring(0, 4);
		} catch (Exception e) { } 
		return s;
	}
	public static String getMonthDashed(String dashed) {
		String s = "";
		try {
			s= dashed.substring(5, 7);
		} catch (Exception e) { } 
		return s;
	}
	public static String getMonthUndashed(String unDashed) {
		String s = "";
		try {
			s= unDashed.substring(4, 6);
		} catch (Exception e) { } 
		return s;
	}

	public static String getDayDashed(String dashed) {
		String s = "";
		try {
			s = dashed.substring(8, 10);
		} catch (Exception e) { } 
		return s;
	}
	public static String getDayUndashed(String unDashed) {
		String s = "";
		try {
			s = unDashed.substring(6, 8);
		} catch (Exception e) { } 
		return s;
	}
	
	/**
	 * Gets an undashed date from a dashed date
	 * @param dashedDate
	 * @return
	 */
	public static String getDateUndashed(String dashedDate) {
		String year = getYearDashed(dashedDate);
		String month = getMonthDashed(dashedDate);
		String day = getDayDashed(dashedDate);
		String unDashedDate = year + month + day;
		return unDashedDate;		
	}
	/**
	 * Gets a dashed date from an undashed date
	 * @param unDashedDate
	 * @return
	 */
	public static String getDateDashed(String unDashedDate) {
		String year = getYearUndashed(unDashedDate);
		String month = getMonthUndashed(unDashedDate);
		String day = getDayUndashed(unDashedDate);
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
		String dashedToday = getTodayDashed();
		int weeksToPresent = 0;
		while(true) {
			if(isLaterDate(dashedDate, dashedToday)) {
				weeksToPresent++;
//				System.out.println("week++");
			} else {
				return weeksToPresent;
			}
			dashedDate = getDatePlusDashed(dashedDate, 7); // skip a week
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
		Integer year1 = Integer.parseInt(getYearDashed(dashedDate));
		Integer year2 = Integer.parseInt(getYearDashed(dashedCheckAgainstDate));
		if(year1>year2) { return false; }
		Integer month1 = Integer.parseInt(getMonthDashed(dashedDate));
		Integer month2 = Integer.parseInt(getMonthDashed(dashedCheckAgainstDate));
		if(month1>month2) { return false; }
		Integer day1 = Integer.parseInt(getDayDashed(dashedDate));
		Integer day2 = Integer.parseInt(getDayDashed(dashedCheckAgainstDate));
		if(day1>day2) { return false; }
		return later;
	}
	
	
	public static String getMonthPlus(String yearMonth, Integer num) {
		String s="";
		
		return s;
	}
	

	
	private static int getMonthLength(int month) {
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
	

	public static Integer getElapsedDaysUndashed(String undashedDay1, String undahsedBasisDay2) {
		int elapsed = 0;
		elapsed = getElapsedDaysDashed(addDash(undashedDay1), addDash(undahsedBasisDay2));
		return elapsed;
	}
	
	public static Integer getElapsedDaysDashed(String dashedDay1, String dahsedBasisDay2) {
		
		Integer year1=0;
		Integer year2=0;
		Integer month1=0;
		Integer month2=0;
		Integer day1=0;
		Integer day2=0;
		
		try {
			year1 = Integer.parseInt(getYearDashed(dashedDay1));  
			year2 = Integer.parseInt(getYearDashed(dahsedBasisDay2));  				
		} catch (NumberFormatException e) { } // some intentionally come null
		
		try {
			month1 = Integer.parseInt(getMonthDashed(dashedDay1));
			month2 = Integer.parseInt(getMonthDashed(dahsedBasisDay2));
		} catch (NumberFormatException e) { } // some intentionally come null
				
		try {
			day1 = Integer.parseInt(getDayDashed(dashedDay1));
			day2 = Integer.parseInt(getDayDashed(dahsedBasisDay2));
		} catch (NumberFormatException e) { } // some intentionally come null

		Integer elapsedTotal = (year2-year1)*365 + (month2-month1)*31 + day2-day1;
		
//		System.out.println("YEAR " + year1 + " " + year2);
//		System.out.println("MONTH " + month1 + " " + month2);
//		System.out.println("DAY " + day1 + " " + day2);
//		System.out.println("ELAPSED " + elapsedTotal);		

		return elapsedTotal;
	}
	
	public static String getDatePlusUndashed(String undashedDate, Integer plusDays) {
		String s = "";
		s = getDatePlusDashed(getDateDashed(undashedDate), plusDays);
		s = getDateUndashed(s);
		return s;
	}
	/**
	 * Returns a new date from an old date plus the number of days since
	 * @param dashedDate
	 * @param plusDays
	 * @return
	 */
	public static String getDatePlusDashed(String dashedDate, Integer plusDays){
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
			day = Integer.parseInt(getDayDashed(dashedDate));
		} catch (NumberFormatException e) { } // nothing to do		
		try {
			month = Integer.parseInt(getMonthDashed(dashedDate));
		} catch (NumberFormatException e) { } // nothing to do 
		try {
			year = Integer.parseInt(getYearDashed(dashedDate));
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
	

	public static String getDateMinusUndashed(String undashedDate, Integer minusDays) {
		String s = "";
		s = getDateMinusDashed(getDateDashed(undashedDate), minusDays);
		s = getDateUndashed(s);
		return s;
	}
	
	public static String getDateMinusDashed(String dashedDate, Integer minusDays){
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
			day = Integer.parseInt(getDayDashed(dashedDate));
		} catch (NumberFormatException e) { } // nothing to do		
		try {
			month = Integer.parseInt(getMonthDashed(dashedDate));
		} catch (NumberFormatException e) { } // nothing to do 
		try {
			year = Integer.parseInt(getYearDashed(dashedDate));
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

	private static String getTwoDigitString(Integer newDay) {
		String sDay;
		sDay=newDay.toString();
		if(newDay<10){
			sDay="0"+sDay;
		}
		return sDay;
	}
	
	// HISTORICAL QUERY
	public static void paceHistoricalQuery(long lastHistQuery) {		
		System.out.println("...pacing historical query: " + getSleepTime(lastHistQuery)/1000 + "s");
	    try {
			Thread.sleep(getSleepTime(lastHistQuery));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}	
	
	private static long getSleepTime(long lastHistQuery) {
		long sleepTime = 0;

		long elapsed = TimeParser.getElapsedStamps(lastHistQuery);
		sleepTime = 12000-elapsed; // 12s target
		if(sleepTime<0) {
			sleepTime = 0;
		}
		
		return sleepTime;
	}

	
	// TEST
	public boolean test() {
		
		boolean result = 	testDatePlus() && 
							testDateMinus() && 
							testMonthDelta();
		
		
		return result;
		
	}
	
	private boolean testDatePlus() {
		
		boolean result = false;
		
		String date1 = "2015-02-28";
		String date2 = "2015-12-31";
		String date3 = "2015-05-15";
		int delta = 1;
		
		String actualDateOut1 = getDatePlusDashed(date1, delta);	
		String actualDateOut2 = getDatePlusDashed(date2, delta);	
		String actualDateOut3 = getDatePlusDashed(date3, delta);	

		Assert.assertEquals(actualDateOut1, "2015-03-01");
		
		if(	actualDateOut1.equals("2015-03-01") &&
			actualDateOut2.equals("2016-01-01") &&
			actualDateOut3.equals("2015-05-16")
			) {
			result = true;
		}		
		
		System.out.println("testDate1 " + actualDateOut1);
		System.out.println("testDate2 " + actualDateOut2);
		System.out.println("testDate3 " + actualDateOut3);
		
		return result;
	}
	
	private boolean testDateMinus() {
		
		boolean result = false;

		String date1 = "2015-03-01";
		String date2 = "2015-01-01";
		String date3 = "2015-05-15";

		int delta = 1;
		
		String dateOut1 = getDateMinusDashed(date1, delta);
		String dateOut2 = getDateMinusDashed(date2, delta);
		String dateOut3 = getDateMinusDashed(date3, delta);	

		
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
	
	private boolean testMonthDelta() {
		
		boolean result = false;

		String date1 = "20141201";
		String date2 = "201512";
		String date3 = "201412";

		String dateBasis1 = "20150301";
		String dateBasis2 = "201403";
		String dateBasis3 = "201505";

//		Integer deltaOut1 = getMonthDelta(date1, dateBasis1);
//		Integer deltaOut2 = getMonthDelta(date2, dateBasis2);
//		Integer deltaOut3 = getMonthDelta(date3, dateBasis3);

		Integer deltaOut1 = getElapsedDaysUndashed(date1, dateBasis1);
		Integer deltaOut2 = getElapsedDaysUndashed(date2, dateBasis2);
		Integer deltaOut3 = getElapsedDaysUndashed(date3, dateBasis3);
		
		if(		deltaOut1.equals(86) &&
				deltaOut2.equals(-644) && 
				deltaOut3.equals(148)) {
				result = true;
			}
		
		System.out.println("deltaOut1 " + deltaOut1);
		System.out.println("deltaOut2 " + deltaOut2);
		System.out.println("deltaOut3 " + deltaOut3);

		return result;
	}
	
	// PRINT
	
	// SET GET
}







