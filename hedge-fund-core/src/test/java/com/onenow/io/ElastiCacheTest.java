package com.onenow.io;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.onenow.util.TimeParser;
import com.onenow.util.Watchr;

public class ElastiCacheTest {

	  @Test
	  public void ElastiCache() {
		  Assert.assertTrue(ElastiCache.cache.port.equals("11211"));
	  }

	  @Test
	  public void write() {
		  
		  String key = "hello";
		  String value = "hello back";
		  
		  ElastiCache.write(key, (Object) value);

		  TimeParser.wait(5);
		  
		  String testValue = (String) ElastiCache.readAsync(key);

		  TimeParser.wait(5);

		  Watchr.log("ElastiCache test: " + value + " vs. " + testValue);
		  Assert.assertTrue(testValue.equals(value));
	  }
}
