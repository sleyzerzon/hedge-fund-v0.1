package com.onenow.io;

import java.util.HashMap;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorFactory;
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
		
		// default to east region
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

		kinesis.createStreamIfNotExists(streamName, numShards);
		
		return true;
	}
	
	public static boolean writeToBusIndefnitely(Kinesis kinesis, StreamName streamName) {
		String s = "Hola World! ";
		Integer i=0;
		while(true) {
			s = s + i;
			write(kinesis, streamName, s);
			TimeParser.wait(1);
			i++;
		}
		
//		return true;
	}

	public static void write(Kinesis kinesis, StreamName streamName, Object objToSend) {
		kinesis.sendObject(objToSend, streamName);
		System.out.println("&&&&&&&&&&&&& WROTE: " + objToSend.toString());
	}
	

	public static boolean read(Kinesis kinesis, StreamName streamName, IRecordProcessorFactory recordProcessor) {

		KinesisClientLibConfiguration clientConfig = configureReadingClient(kinesis, streamName);
		
		Worker kinesysWorker = new Worker(recordProcessor, clientConfig);
		
        return runProcessor(kinesysWorker);

	}
	private static KinesisClientLibConfiguration configureReadingClient(Kinesis kinesis, StreamName streamName) {
		
		String applicationName = "appName";
		String workerId = "fulano";
		
		KinesisClientLibConfiguration clientConfig = kinesis.configureClient(	applicationName, 
																				streamName.toString(), 
																				workerId, 
																				kinesisRegion.get(kinesis));
		return clientConfig;
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
