package com.onenow.io;

import org.junit.Assert;
import org.testng.annotations.Test;

import com.onenow.alpha.BrokerInterface;
import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.InvType;
import com.onenow.constant.TradeType;
import com.onenow.data.EventActivityRealtime;
import com.onenow.execution.BrokerInteractive;
import com.onenow.instrument.Investment;
import com.onenow.instrument.InvestmentIndex;
import com.onenow.instrument.Underlying;

public class PriceSizeCacheTest {

	BrokerInterface broker = new BrokerInteractive();
	
	CacheInProcess cache = new CacheInProcess(broker);

	EventActivityRealtime event = new EventActivityRealtime(	(long) 1234, new InvestmentIndex(new Underlying("ON")), 
																TradeType.BUY, 
																2.34, 5, 
																InvDataSource.AMERITRADE, InvDataTiming.REALTIME);
	
  @Test
  // test for initialization of in process cache
  public void writeEventRT() {
	  
	  Assert.assertTrue(cache.writeEventRT(event));
	  
  }
}
