package com.onenow.execution;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.onenow.constant.StreamName;

public class BrokerInteractiveTest {

	BrokerInteractive broker = new BrokerInteractive();
	
  @Test
  public void BrokerInteractive() {
    Assert.assertTrue(broker.getStream().equals(StreamName.REALTIME));
  }
}
