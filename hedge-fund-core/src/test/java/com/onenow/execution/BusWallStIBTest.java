package com.onenow.execution;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.onenow.admin.NetworkConfig;
import com.onenow.constant.Topology;

public class BusWallStIBTest {

  @Test
  public void BusWallStIB() {

	  BusWallStInteractiveBrokers bus = new BusWallStInteractiveBrokers();
	  
	  Assert.assertTrue(bus.controller!=null);
  }
  
//  @Test
//  public void BusWallSt() {
//	  BusWallStInteractiveBrokers bus = new BusWallStInteractiveBrokers();
//	  Assert.assertTrue(bus.gateway.equals(NetworkConfig.getGateway(Topology.LOCAL)));
//  }
  
  @Test
  public void getClientId() {
	  BusWallStInteractiveBrokers bus = new BusWallStInteractiveBrokers();
	  Assert.assertTrue(bus.getClientID()!=null);
  }

  @Test
  public void BusWallStTopology() {
	  BusWallStInteractiveBrokers bus;
	  
//	  if(NetworkConfig.isMac()) {
//		  bus = new BusWallSt(Topology.LOCAL);
//		  Assert.assertTrue(bus.gateway.equals(NetworkConfig.getGateway(Topology.LOCAL)));
//		  bus = new BusWallSt(Topology.AWSLOCAL);
//		  Assert.assertTrue(bus.gateway.equals(NetworkConfig.getGateway(Topology.AWSLOCAL)));
//		  bus = new BusWallSt(Topology.AWSREMOTE);
//		  Assert.assertTrue(bus.gateway.equals(NetworkConfig.getGateway(Topology.AWSREMOTE)));
//	  } else {	  
//		  bus = new BusWallSt(Topology.LOCAL);
//		  Assert.assertTrue(bus.gateway.equals(NetworkConfig.getGateway(Topology.AWSLOCAL)));
//		  bus = new BusWallSt(Topology.AWSLOCAL);
//		  Assert.assertTrue(bus.gateway.equals(NetworkConfig.getGateway(Topology.AWSLOCAL)));
//		  bus = new BusWallSt(Topology.AWSREMOTE);
//		  Assert.assertTrue(bus.gateway.equals(NetworkConfig.getGateway(Topology.AWSLOCAL)));		  
//	  } 
  }
  
}
