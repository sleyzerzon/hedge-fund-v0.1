package com.onenow.io;

import java.util.HashMap;

import com.amazonaws.regions.Region;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.KinesisClientLibConfiguration;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.Worker;
import com.onenow.util.TimeParser;
import com.onenow.io.Lookup;

public class BusSystem {

//	private static String streamName = "BusXYZ";
//	private static Integer numShards = 1;		
//	private static Region region = Region.getRegion(Regions.US_EAST_1); 

//	private static DynamoDB dynamo = new DynamoDB(region);
	
	private static HashMap<String, Kinesis> kinesis = new HashMap<String, Kinesis>();

	public BusSystem() {
		
	}
	
	public static Kinesis getKinesis(String streamName, Region region) {
		
		Kinesis kin = new Kinesis();
		
		String key = Lookup.getKinesisKey(streamName, region);
		
		if(kinesis.get(key)==null) {
			kin = new Kinesis(streamName, region);
		} else {
			kin = kinesis.get(key);
		}
		
		return kin;
	}
	
	public static boolean createStream(Kinesis kinesis, String streamName, Integer numShards) {

		kinesis.createStream(streamName, numShards);
		
		return true;
	}
	
	public static boolean writeToBus(Kinesis kinesis, String streamName) {	
		
		while(true) {
			Object objToSend = (Object) "Hola World!";
			kinesis.sendObject(objToSend, streamName);
			System.out.println("&&&&&&&&&&&&& WROTE: " + objToSend.toString());

			TimeParser.wait(1);
		}
		
//		return true;
	}
	
	public static boolean readFromBus(Kinesis kinesis, String streamName, Region region) {

		String applicationName = "appName";
		String workerId = "fulano";
		KinesisClientLibConfiguration clientConfig = kinesis.configureClient(applicationName, streamName, workerId, region);
		
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
