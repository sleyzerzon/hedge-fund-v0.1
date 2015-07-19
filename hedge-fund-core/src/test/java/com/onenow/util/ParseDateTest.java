package com.onenow.util;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ParseDateTest {

  @Test
  public void getDashedDatePlus() {
		String date1 = "2015-02-28";
		String date2 = "2015-12-31";
		String date3 = "2015-05-15";
		int delta = 1;
		
		String actualDateOut1 = TimeParser.getDatePlusDashed(date1, delta);	
		String actualDateOut2 = TimeParser.getDatePlusDashed(date2, delta);	
		String actualDateOut3 = TimeParser.getDatePlusDashed(date3, delta);	

		Assert.assertEquals(actualDateOut1, "2015-03-01");
		Assert.assertEquals(actualDateOut2, "2016-01-01");
		Assert.assertEquals(actualDateOut3, "2015-05-16");
		
//		System.out.println("testDate1 " + actualDateOut1);
//		System.out.println("testDate2 " + actualDateOut2);
//		System.out.println("testDate3 " + actualDateOut3);
		}

  @Test
  public void isWeekDay() {
	  String date0 = "07-17-2015"; // friday
	  String date1 = "07-18-2015"; // saturday
	  String date2 = "07-19-2015"; // sunday
	  String date3 = "07-20-2015";
	  String date4 = "07-21-2015";
	  String date5 = "07-22-2015";
	  String date6 = "07-23-2015";
	  String date7 = "07-24-2015";

	  Assert.assertTrue(TimeParser.isWeekDay(date0));
	  Assert.assertTrue(!TimeParser.isWeekDay(date1));
	  Assert.assertTrue(!TimeParser.isWeekDay(date2));
	  Assert.assertTrue(TimeParser.isWeekDay(date3));
	  Assert.assertTrue(TimeParser.isWeekDay(date4));
	  Assert.assertTrue(TimeParser.isWeekDay(date5));
	  Assert.assertTrue(TimeParser.isWeekDay(date6));
	  Assert.assertTrue(TimeParser.isWeekDay(date7));
  }
}
