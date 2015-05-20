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
		
		String actualDateOut1 = ParseDate.getDashedDatePlus(date1, delta);	
		String actualDateOut2 = ParseDate.getDashedDatePlus(date2, delta);	
		String actualDateOut3 = ParseDate.getDashedDatePlus(date3, delta);	

		Assert.assertEquals(actualDateOut1, "2015-03-01");
		Assert.assertEquals(actualDateOut2, "2016-01-01");
		Assert.assertEquals(actualDateOut3, "2015-05-16");
		
//		if(	actualDateOut1.equals("2015-03-01") &&
//			actualDateOut2.equals("2016-01-01") &&
//			actualDateOut3.equals("2015-05-16")
//			) {
//			result = true;
//		}		
		
//		System.out.println("testDate1 " + actualDateOut1);
//		System.out.println("testDate2 " + actualDateOut2);
//		System.out.println("testDate3 " + actualDateOut3);
		}
}
