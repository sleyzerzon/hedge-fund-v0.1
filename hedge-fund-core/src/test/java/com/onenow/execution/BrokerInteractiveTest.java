package com.onenow.execution;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.onenow.constant.InvestorRole;
import com.onenow.constant.StreamName;
import com.onenow.portfolio.Portfolio;

public class BrokerInteractiveTest {

	
	@Test
	public void BrokerInteractiveDefaultInit() {

		BrokerInteractive broker = new BrokerInteractive();

		Assert.assertTrue(broker.getRole().equals(InvestorRole.REALTIME));
	  
		
	}

//	@Test
//	public void BrokerInteractiveRealtimeInit() {
//	
//		BrokerInteractive broker = new BrokerInteractive(StreamName.REALTIME, new Portfolio(),  new BusWallStInteractiveBrokers());
//	
//		Assert.assertTrue(!(broker.quoteRealtimeChain==null));
//	  
//		
//	}
//
//	@Test
//	public void BrokerInteractiveHistoryInit() {
//	
//		BrokerInteractive broker = new BrokerInteractive(StreamName.HISTORY, new BusWallStInteractiveBrokers());
//	
//		Assert.assertTrue(!(broker.quoteHistoryChain==null));
//	  
//	}

}

	



