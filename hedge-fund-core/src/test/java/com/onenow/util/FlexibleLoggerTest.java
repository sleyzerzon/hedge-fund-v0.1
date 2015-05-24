package com.onenow.util;

import java.util.logging.Level;

import org.testng.Assert;
import org.testng.annotations.Test;

public class FlexibleLoggerTest {

  @Test
  public void setup() {
	  // java.io.IOException: Couldn't get lock for /var/log/HedgeFundLog.txt
	   Assert.assertTrue(FlexibleLogger.setup());
  }
}
