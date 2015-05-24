package com.onenow.util;

import java.util.logging.Level;

import org.testng.Assert;
import org.testng.annotations.Test;

public class FlexibleLoggerTest {

  @Test
  public void setup() {
	  Assert.assertTrue(FlexibleLogger.setup());
  }
}
