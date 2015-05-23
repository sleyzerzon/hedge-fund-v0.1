package com.onenow.execution;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.onenow.admin.NetworkConfig;
import com.onenow.constant.Topology;

public class BusWallStTest {

  @Test
  public void BusWallSt() {
	  BusWallSt bus = new BusWallSt();
	  Assert.assertTrue(bus.gateway.equals(NetworkConfig.getGateway(Topology.AWSLOCAL)));
  }

  @Test
  public void BusWallStTopology() {
	  BusWallSt bus;
	  
	  bus = new BusWallSt(Topology.LOCAL);
	  Assert.assertTrue(bus.gateway.equals(NetworkConfig.getGateway(Topology.LOCAL)));
	  bus = new BusWallSt(Topology.AWSLOCAL);
	  Assert.assertTrue(bus.gateway.equals(NetworkConfig.getGateway(Topology.AWSLOCAL)));
	  bus = new BusWallSt(Topology.AWSREMOTE);
	  Assert.assertTrue(bus.gateway.equals(NetworkConfig.getGateway(Topology.AWSREMOTE)));
	  
	  if(!NetworkConfig.isMac()) {
		  System.out.println("TOPOLOGY is not Mac");
		  bus = new BusWallSt(Topology.LOCAL);
		  Assert.assertTrue(bus.gateway.equals(NetworkConfig.getGateway(Topology.AWSLOCAL)));
		  bus = new BusWallSt(Topology.AWSLOCAL);
		  Assert.assertTrue(bus.gateway.equals(NetworkConfig.getGateway(Topology.AWSLOCAL)));
		  bus = new BusWallSt(Topology.AWSREMOTE);
		  Assert.assertTrue(bus.gateway.equals(NetworkConfig.getGateway(Topology.AWSLOCAL)));		  
	  } else {
		  System.out.println("TOPOLOGY is Mac");		  
	  }
  }
}
