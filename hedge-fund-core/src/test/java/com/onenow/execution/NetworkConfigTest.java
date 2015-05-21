package com.onenow.execution;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.onenow.data.InvestmentList;

public class NetworkConfigTest {

  @Test
  public void getGateway() {
	  Assert.assertTrue(NetworkConfig.getGateway().port == 4001);
  }

  @Test
  public void getRDBMS() {
	  Assert.assertTrue(NetworkConfig.getRDBMS().port == 3306);
  }

  @Test
  public void getTSDB() {
	  Assert.assertTrue(NetworkConfig.tsdb!=null);  
  }
}
