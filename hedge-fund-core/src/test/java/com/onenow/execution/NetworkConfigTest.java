package com.onenow.execution;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.onenow.admin.NetworkConfig;
import com.onenow.constant.Environment;
import com.onenow.data.InvestmentList;

public class NetworkConfigTest {

  @Test
  public void getGateway() {
	  Assert.assertTrue(NetworkConfig.getGateway(Environment.LOCAL).port.equals("4001"));
	  Assert.assertTrue(NetworkConfig.getGateway(Environment.AWSREMOTE).port.equals("4001"));
	  Assert.assertTrue(NetworkConfig.getGateway(Environment.AWSLOCAL).port.equals("4001"));
  }

  @Test
  public void getRDBMS() {
	  Assert.assertTrue(NetworkConfig.getRDBMS().port.equals("3306"));
  }

  @Test
  public void getTSDB() {
	  Assert.assertTrue(NetworkConfig.getTSDB().port.equals("8086"));  
  }
}
