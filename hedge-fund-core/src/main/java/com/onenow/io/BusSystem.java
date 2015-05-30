package com.onenow.io;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.KinesisClientLibConfiguration;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.Worker;
import com.onenow.data.DynamoDB;
import com.onenow.util.TimeParser;

public class BusSystem {

	private static String streamName = "BusXYZ";
	private static Integer numShards = 1;	
	private static Region region = Region.getRegion(Regions.US_EAST_1); 
	
	private static Kinesis kinesis = new Kinesis(streamName, region);	
	private static DynamoDB dynamo = new DynamoDB(region);

	public BusSystem() {
		
	}
	
	public static boolean writeToBus() {	
		
		while(true) {
			//		kinesis.createStream(streamName, numShards);
			Object objToSend = (Object) "Hola World!";
			kinesis.sendObject(objToSend, streamName);
			System.out.println("&&&&&&&&&&&&& WROTE: " + objToSend.toString());

			TimeParser.wait(1);
		}
		
//		return true;
	}
	
	public static boolean readFromBus() {

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
