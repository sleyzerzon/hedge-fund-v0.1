package com.onenow.io;

import org.testng.Assert;
import org.testng.annotations.Test;

public class CacheElasticTest {

		// Amazon ElastiCache Nodes, deployed within a VPC, can never be accessed from the Internet or from EC2 Instances outside the VPC.
		//	Unit tests should mock outside resources. It should be fairly simple to mock out elasticache with just a simple hashmap based key/value store.
	
	  @Test
	  public void ElastiCache() {
		  Assert.assertTrue(CacheElastic.cache.port.equals("11211"));
	  }


}
