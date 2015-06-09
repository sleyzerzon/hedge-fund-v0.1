package com.onenow.io;

import java.util.HashMap;
import java.util.logging.Level;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorFactory;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.KinesisClientLibConfiguration;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.Worker;
import com.onenow.util.TimeParser;
import com.onenow.util.Watchr;
import com.onenow.constant.StreamName;
import com.onenow.io.Lookup;

public class BusSystem {

	private static HashMap<String, Kinesis> kinesisMap = new HashMap<String, Kinesis>();
	private static HashMap<Kinesis, Region> kinesisRegion = new HashMap<Kinesis, Region>();

	private static Region defaultRegion = Region.getRegion(Regions.US_EAST_1);

	public BusSystem() {
		
	}
			
	// KINESIS
	public static Kinesis getKinesis() {
		
		Region region = defaultRegion;
		
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
		
		return kin;
	}
	
	public static Region getRegion(Kinesis kinesis) {
		Region region = null;
		
		region = kinesisRegion.get(kinesis);
		
		if(region==null){
			region = defaultRegion; 
		}
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
	

	public static boolean read(StreamName streamName, IRecordProcessorFactory recordProcessor) {

		String applicationName = "appName";
		String workerId = "fulano";
		
		KinesisClientLibConfiguration clientConfig = getKinesis().configureClient(	applicationName, 
																					streamName.toString(), 
																					workerId, 
																					getRegion(getKinesis()));
		
		Worker kinesysWorker = new Worker(recordProcessor, clientConfig);
		
        return runProcessor(kinesysWorker);

	}

	private static boolean runProcessor(Worker kinesysWorker) {
		try {
            kinesysWorker.run();
        } catch (Throwable t) {
        	String log = "Caught throwable while processing data." + t;
        	Watchr.log(Level.SEVERE, log);
            return false;
        }        
		return true;
	}
}
