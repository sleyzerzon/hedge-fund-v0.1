package com.onenow.util;

import java.util.logging.Level;

import org.testng.Assert;
import org.testng.annotations.Test;

public class WatchLogTest {

  @Test
  public void addToLog() {	  
	Assert.assertTrue(!Watchr.log(Level.SEVERE, "", "", "").isEmpty());
	Assert.assertTrue(!Watchr.log(Level.FINE, "", "", "").isEmpty());
	Assert.assertTrue(!Watchr.log(Level.FINER, "", "", "").isEmpty());
	Assert.assertTrue(!Watchr.log(Level.FINEST, "", "", "").isEmpty());
	Assert.assertTrue(!Watchr.log(Level.INFO, "", "", "").isEmpty());
  }
  @Test
  public void getLogPath() {
	Assert.assertTrue(!Watchr.getLogPath().isEmpty());	  
  }  
}
