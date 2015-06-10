package com.onenow.execution;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.onenow.admin.NetworkConfig;
import com.onenow.constant.Topology;

public class BusWallStIBTest {

  @Test
  public void BusWallStIB() {

	  BusWallStIB bus = new BusWallStIB();
	  
	  Assert.assertTrue(bus.controller!=null);
  }
  
  @Test
  public void BusWallSt() {
	  BusWallStIB bus = new BusWallStIB();
	  Assert.assertTrue(bus.gateway.equals(NetworkConfig.getGateway(Topology.LOCAL)));
  }
  
  @Test
  public void getClientId() {
	  BusWallStIB bus = new BusWallStIB();
	  Assert.assertTrue(bus.getClientID()!=null);
  }

  @Test
  public void BusWallStTopology() {
	  BusWallStIB bus;
	  
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
