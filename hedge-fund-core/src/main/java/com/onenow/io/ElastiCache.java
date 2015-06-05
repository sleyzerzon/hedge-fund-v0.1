package com.onenow.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Level;

import com.onenow.util.Watchr;

import net.spy.memcached.MemcachedClient;

public class ElastiCache {
	
	String host = "hedgefundcache.cusyjv.cfg.use1.cache.amazonaws.com";
	int portNum = 11211;
	
	MemcachedClient client = null;

	public ElastiCache() {
		client = connect();
	}

	private MemcachedClient connect() {
		MemcachedClient c=null;
		try {
			c = new MemcachedClient(new InetSocketAddress(host, portNum));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return c;
	}

	public void read(String key) {
		// Retrieve a value (synchronously).
		Object myObject = null;
		try {
			myObject = client.get(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String log = "GOT FROM CACHE: " + myObject.toString();
    	Watchr.log(Level.INFO, log);

	}

	public void write(String key, Object someObject) {
		// Store a value (async) for one hour
		try {
			client.set(key, 3600, someObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String log = "WROTE TO CACHE: " + someObject.toString();
    	Watchr.log(Level.INFO, log);
	}
}
