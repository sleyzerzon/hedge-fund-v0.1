package com.onenow.util;

import java.util.logging.Level;

import org.testng.Assert;
import org.testng.annotations.Test;

public class WatchLogTest {

  @Test
  public void addToLog() {	  
	Assert.assertTrue(!WatchLog.add(Level.SEVERE, "", "", "").isEmpty());
	Assert.assertTrue(!WatchLog.add(Level.FINE, "", "", "").isEmpty());
	Assert.assertTrue(!WatchLog.add(Level.FINER, "", "", "").isEmpty());
	Assert.assertTrue(!WatchLog.add(Level.FINEST, "", "", "").isEmpty());
	Assert.assertTrue(!WatchLog.add(Level.INFO, "", "", "").isEmpty());
  }
  @Test
  public void getLogPath() {
	Assert.assertTrue(!WatchLog.getLogPath().isEmpty());	  
  }  
}
