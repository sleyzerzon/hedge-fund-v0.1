package com.onenow.io;

import java.util.HashMap;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.KinesisClientLibConfiguration;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.Worker;
import com.onenow.util.TimeParser;
import com.onenow.constant.StreamName;
import com.onenow.io.Lookup;

public class BusSystem {

	private static HashMap<String, Kinesis> kinesis = new HashMap<String, Kinesis>();
	private static HashMap<Kinesis, Region> kinesisRegion = new HashMap<Kinesis, Region>();

	public BusSystem() {
		
	}
			
	// KINESIS
	public static Kinesis getKinesis() {
		
		Region region = Region.getRegion(Regions.US_EAST_1);
		
		return getKinesis(region);
	}
	
	public static Kinesis getKinesis(Region region) {
		
		Kinesis kin = null;
		
		String key = Lookup.getKinesisKey(region);
		
		if(kinesis.get(key)==null) {
			
			kin = new Kinesis(region);
			kinesis.put(key, kin);
			kinesisRegion.put(kin, region);
			
		} else {
			kin = kinesis.get(key);
		}
		
		return kin;
	}
	
	public static boolean createStream(Kinesis kinesis, StreamName streamName, Integer numShards) {

		kinesis.createStream(streamName, numShards);
		
		return true;
	}
	
	public static boolean writeToBus(Kinesis kinesis, StreamName streamName) {	
		Integer i=0;
		while(true) {
			Object objToSend = (Object) "Hola World!" + i.toString();
			kinesis.sendObject(objToSend, streamName);
			System.out.println("&&&&&&&&&&&&& WROTE: " + objToSend.toString());
			TimeParser.wait(1);
			i++;
		}
		
//		return true;
	}
	
	public static boolean readFromBus(Kinesis kinesis, StreamName streamName) {

		String applicationName = "appName";
		String workerId = "fulano";
		
		KinesisClientLibConfiguration clientConfig = kinesis.configureClient(	applicationName, 
																				streamName.toString(), 
																				workerId, 
																				kinesisRegion.get(kinesis));
		
		Worker kinesysWorker = new Worker(kinesis.ibRecordProcessor(), clientConfig);
		
        return runProcessor(kinesysWorker);

	}

	private static boolean runProcessor(Worker kinesysWorker) {
		try {
            kinesysWorker.run();
        } catch (Throwable t) {
        	System.out.println("Caught throwable while processing data." + t);
            return false;
        }        
		return true;
	}
}
