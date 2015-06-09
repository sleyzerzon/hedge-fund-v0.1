package com.onenow.io;


import org.testng.Assert;
import org.testng.annotations.Test;

public class BusSystemTest {

  @Test
  public void getRegion() {
	  
	  Kinesis kin = new Kinesis();
	  
	  Assert.assertTrue(BusSystem.getRegion(kin)!=null);

  }
}
