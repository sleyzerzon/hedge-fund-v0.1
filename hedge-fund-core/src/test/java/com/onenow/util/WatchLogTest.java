package com.onenow.util;

import org.testng.Assert;
import org.testng.annotations.Test;

public class WatchLogTest {

  @Test
  public void addToLog() {
	  
	Assert.assertTrue(!WatchLog.addToLog(null, null, null, null).isEmpty());
  }
}