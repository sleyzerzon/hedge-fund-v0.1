package com.onenow.io;

import io.netty.util.concurrent.Future;

import java.awt.List;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import com.amazonaws.services.elasticache.AmazonElastiCacheClient;
import com.amazonaws.services.elasticache.model.CacheCluster;
import com.amazonaws.services.elasticache.model.CacheNode;
import com.amazonaws.services.elasticache.model.DescribeCacheClustersRequest;
import com.amazonaws.services.elasticache.model.DescribeCacheClustersResult;
import com.onenow.admin.InitAmazon;
import com.onenow.admin.NetworkConfig;
import com.onenow.admin.NetworkService;
import com.onenow.util.Watchr;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.BinaryConnectionFactory;
import net.spy.memcached.ConnectionFactory;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.DefaultConnectionFactory;
import net.spy.memcached.HashAlgorithm;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.auth.AuthDescriptor;
import net.spy.memcached.auth.PlainCallbackHandler;
import net.spy.memcached.internal.GetFuture;
import net.spy.memcached.internal.OperationFuture;

import net.spy.memcached.HashAlgorithm;

public class ElastiCache {
	
	public static NetworkService cache = NetworkConfig.getCache();
	private static MemcachedClient client = connect();

	public ElastiCache() {
	}

	private static MemcachedClient connect() {
		
		// AmazonElastiCacheClient eCache = new AmazonElastiCacheClient(InitAmazon.getCredentials());

//		try {
//			client.shutdown();
//		} catch (Exception e1) {
//		}
		
//		DescribeCacheClustersRequest dccRequest = new DescribeCacheClustersRequest();
//		dccRequest.setShowCacheNodeInfo(true);
//		DescribeCacheClustersResult clusterResult = client.describeCacheClusters(dccRequest);
//		
//		ArrayList<CacheCluster> cacheClusters = (ArrayList<CacheCluster>) clusterResult.getCacheClusters();
//		for (CacheCluster cacheCluster : cacheClusters) {
//		    ArrayList<CacheNode> cacheNodes = (ArrayList<CacheNode>) cacheCluster.getCacheNodes();
//
//		    System.out.println("List size: " + cacheNodes.size());
//		}
		
		boolean success = true;
		MemcachedClient c=null;
		
//		ConnectionFactory connFactory = new DefaultConnectionFactory(
//				 DefaultConnectionFactory.DEFAULT_OP_QUEUE_LEN,
//				 DefaultConnectionFactory.DEFAULT_READ_BUFFER_SIZE,
//				 HashAlgorithm.KETAMA_HASH);

		try {
			  c = new MemcachedClient(	AddrUtil.getAddresses(cache.URI+":"+cache.port));
			  
//					  					new InetSocketAddress(cache.URI, new Integer(cache.port)));

			  
			// Get a memcached client connected to several servers
			// MemcachedClient c=new MemcachedClient(
			//        AddrUtil.getAddresses("server1:11211 server2:11211"));

			
		} catch (Exception e) {
			success = false;
			Watchr.log(Level.SEVERE, e.getMessage());
		}
		if(success) {
			Watchr.log(Level.INFO, "Connected to Cache: " + cache.URI + ":" + cache.port);
		}
		return c;
	}

	public static Object read(String key) {
		boolean success = true;
		// Retrieve a value (synchronously).
		Object myObject = null;
		try {
			myObject = client.get(key);
		} catch (Exception e) {
			success = false;
			Watchr.log(Level.SEVERE, e.getMessage());
		}
		if(success) {
			Watchr.log(Level.INFO, "GOT FROM CACHE: " + myObject.toString());
		} 
    	return myObject;
	}
	
	public static Object readAsync(String key) { 
		boolean success = true;
		// Try to get a value, for up to 5 seconds, and cancel if it doesn't return
		Object myObj=null;
		GetFuture<Object> f = client.asyncGet(key);
		try {
		    myObj = f.get(5, TimeUnit.SECONDS);
		} catch(Exception e) {
			success = false;
		    // Since we don't need this, go ahead and cancel the operation.  This
		    // is not strictly necessary, but it'll save some work on the server.
		    f.cancel(false);
		    Watchr.log(Level.SEVERE, e.getMessage());
		}
		if(success) {
			Watchr.log(Level.INFO, "GOT FROM CACHE: " + myObj.toString());
		} 	
	return myObj;
	}

	public static void write(String key, Object someObject) {
		boolean success = true;
		// Store a value (async) for one hour
		try {
			client.set(key, 3600, someObject);
			OperationFuture<Boolean> result = client.set(key, 3600, someObject);
		} catch (Exception e) {
			success = false;
			Watchr.log(Level.SEVERE, e.getMessage());
		}
		if(success) {
			Watchr.log(Level.INFO, "WROTE TO CACHE: " + someObject.toString());
		} 
	}
}
