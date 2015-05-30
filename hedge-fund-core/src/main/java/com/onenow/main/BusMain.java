package com.onenow.main;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.onenow.io.Kinesis;


public class BusMain {

	private static String streamName = "Bus";

	private static Region region = Region.getRegion(Regions.US_EAST_1); 
	
	public static void main(String[] args) throws Exception {
		
		Kinesis kinesis = new Kinesis(streamName, region);

		Object objToSend = (Object) "Hola World!";
		kinesis.sendPair(objToSend, streamName);
		
	}
	
}



//ExecutorService es = Executors.newCachedThreadPool();
//
//Runnable pairSender = new Runnable() {
//    @Override
//    public void run() {
//        try {
//            putter.sendPairsIndefinitely(DELAY_BETWEEN_RECORDS_IN_MILLIS, TimeUnit.MILLISECONDS);
//        } catch (Exception ex) {
//            LOG.warn("Thread encountered an error while sending records. Records will no longer be put by this thread.",
//                    ex);
//        }
//    }
//};
//
//for (int i = 0; i < numberOfThreads; i++) {
//    es.submit(pairSender);
//}