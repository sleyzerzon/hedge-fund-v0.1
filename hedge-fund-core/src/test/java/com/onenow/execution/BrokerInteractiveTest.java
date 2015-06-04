package com.onenow.execution;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.onenow.constant.BrokerMode;

public class BrokerInteractiveTest {

	BrokerInteractive broker = new BrokerInteractive();
	
  @Test
  public void BrokerInteractive() {
    Assert.assertTrue(broker.getMode().equals(BrokerMode.REALTIME));
  }
}
