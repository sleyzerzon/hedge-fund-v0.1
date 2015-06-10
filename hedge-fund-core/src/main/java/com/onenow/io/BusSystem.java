package com.onenow.io;

import java.util.HashMap;
import java.util.logging.Level;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorFactory;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.InitialPositionInStream;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.KinesisClientLibConfiguration;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.Worker;
import com.onenow.util.TimeParser;
import com.onenow.util.Watchr;
import com.onenow.admin.InitAmazon;
import com.onenow.admin.NetworkConfig;
import com.onenow.constant.StreamName;
import com.onenow.io.Lookup;

public class BusSystem {

	private static HashMap<String, Kinesis> kinesisMap = new HashMap<String, Kinesis>();
	private static HashMap<Kinesis, Region> kinesisRegion = new HashMap<Kinesis, Region>();


	public BusSystem() {
		
	}
			
	// KINESIS
	public static Kinesis getKinesis() {
		
		Region region = InitAmazon.defaultRegion;
		
		return getKinesis(region);
	}
	
	public static Kinesis getKinesis(Region region) {
				
		Kinesis kin = null;
		
		String key = Lookup.getKinesisKey(region);
		
		if(kinesisMap.get(key)==null) {
			
			kin = new Kinesis(region);
			kinesisMap.put(key, kin);
			kinesisRegion.put(kin, region);
			
		} else {
			kin = kinesisMap.get(key);
		}
		
		Watchr.log(Level.INFO, "Kinesis map: " + kinesisMap.toString());
		Watchr.log(Level.INFO, "Kinesis region map: " + kinesisRegion.toString());

		return kin;
	}
	
	public static Region getRegion(Kinesis kinesis) {
		Region region = null;
		
		region = kinesisRegion.get(kinesis);
		
		if(region==null){
			region = InitAmazon.defaultRegion; 
		}
		
		// Watchr.log(Level.INFO, "Kinesis region is: " + region);
		
		return region;
	}
	
	private static boolean createStreamIfNotExists(StreamName name) {
		
		getKinesis().createStreamIfNotExists(name, getNumShards(name));
		
		return true;
	}

	/** 
	 * Set right number of shards for a given StreamName
	 * @param name
	 * @return
	 */
	private static int getNumShards(StreamName name) {
		Integer shards = 1;
		if(name.equals(StreamName.REALTIME)) {
			shards = 2;
		}
		return shards;
	}

	public static boolean createStream(StreamName streamName, Integer numShards) {

		getKinesis().createStreamIfNotExists(streamName, numShards);
		
		return true;
	}
	
	public static boolean writeToBusIndefnitely(StreamName streamName) {
		String s = "Hola World! ";
		Integer i=0;
		while(true) {
			s = s + i;
			write(streamName, s);
			TimeParser.wait(1);
			i++;
		}
		
//		return true;
	}

	public static void write(StreamName streamName, Object objToSend) {
		getKinesis().sendObject(objToSend, streamName);
	}
		
	// http://blogs.aws.amazon.com/bigdata/blog/author/Ian+Meyers
	// http://docs.aws.amazon.com/general/latest/gr/rande.html
	public static boolean read(StreamName streamName, IRecordProcessorFactory recordProcessorFactory) {

		InitialPositionInStream initPosition = InitialPositionInStream.LATEST;
		
		read(streamName, recordProcessorFactory, initPosition);
				
		return true;
	}
	
	public static boolean read(		StreamName streamName, IRecordProcessorFactory recordProcessorFactory,
									InitialPositionInStream initPosition) {
		
		String applicationName = "appName";
		String workerId = "fulano";
       
		KinesisClientLibConfiguration readClientConfig = null;
		
		readClientConfig = new KinesisClientLibConfiguration(	applicationName, 
																streamName.toString(), 
																InitAmazon.getCredentialsProvider(), 
																workerId);
		
		readClientConfig.withCommonClientConfig(InitAmazon.getClientConfig());
		readClientConfig.withRegionName(InitAmazon.defaultRegion.getName());
		readClientConfig.withInitialPositionInStream(initPosition);		
		
		Worker kinesysWorker = new Worker(recordProcessorFactory, readClientConfig);
		Watchr.log(Level.INFO, 	"Created kinesis read worker: " + kinesysWorker.toString() + " " + 
								"for kinesis: " + getKinesis());
		
		runProcessor(kinesysWorker);
		
        return true;

	}

	private static boolean runProcessor(Worker kinesysWorker) {
		try {
			Watchr.log(Level.INFO, "Running kinesis read worker: " + kinesysWorker.toString());
            kinesysWorker.run();
        } catch (Throwable t) {
        	String log = "Caught throwable while processing data." + t;
        	Watchr.log(Level.SEVERE, log);
            return false;
        }        
		return true;
	}
}
