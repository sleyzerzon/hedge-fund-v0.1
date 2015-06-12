package com.onenow.io;

import org.junit.Assert;
import org.testng.annotations.Test;

import com.onenow.alpha.BrokerInterface;
import com.onenow.data.EventActivityRealtime;
import com.onenow.execution.BrokerInteractive;

public class PriceSizeCacheTest {

	BrokerInterface broker = new BrokerInteractive();
	
	CacheInProcess cache = new CacheInProcess(broker);

	EventActivityRealtime event = new EventActivityRealtime();
	
  @Test
  // test for initialization of in process cache
  public void writeEventRT() {
	  
	  Assert.assertTrue(cache.writeEventRT(event));
	  
  }
}
