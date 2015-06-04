package com.onenow.io;

import java.io.IOException;
import java.net.InetSocketAddress;

import net.spy.memcached.MemcachedClient;

public class ElastiCache {
	
	int portNum;

	public ElastiCache() throws IOException {
		
		MemcachedClient c=new MemcachedClient(
			    new InetSocketAddress("hostname", portNum));

		Object someObject = null;
		
			// Store a value (async) for one hour
			c.set("someKey", 3600, someObject);
			// Retrieve a value (synchronously).
			Object myObject=c.get("someKey");
	}
}
