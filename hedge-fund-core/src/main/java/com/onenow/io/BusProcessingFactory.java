package com.onenow.io;

import kinesis.CountingRecordProcessorFactory;

import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorFactory;
import com.onenow.constant.StreamName;
import com.onenow.data.DynamoDBCountPersister;
import com.onenow.data.EventActivityPriceSizeRealtime;
import com.onenow.data.EventActivityPriceHistory;
import com.onenow.data.HttpReferrerPair;


public class BusProcessingFactory {

	public BusProcessingFactory() {
		
	}

	
	public static IRecordProcessorFactory createProcessorFactoryString(StreamName streamName) {
		
        IRecordProcessorFactory processorFactory = new BusRecordProcessorFactory<String>(String.class, streamName);

        return processorFactory;
	}

	public static IRecordProcessorFactory createProcessorFactoryEventRealTime(StreamName streamName) {
		
        IRecordProcessorFactory processorFactory = new BusRecordProcessorFactory<EventActivityPriceSizeRealtime>(EventActivityPriceSizeRealtime.class, streamName);

        return processorFactory;
	}

	public static IRecordProcessorFactory createProcessorFactoryEventHistory(StreamName streamName) {
		
        IRecordProcessorFactory processorFactory = new BusRecordProcessorFactory<EventActivityPriceHistory>(EventActivityPriceHistory.class, streamName);

        return processorFactory;
	}

    
    // Persist counts to DynamoDB
	/**
	 * Use:
	 * String tableName = "tableName";
	 * DynamoDBCountPersister persister = dynamo.getCountPersister(tableName);
	 * Worker kinesysWorker = new Worker(kinesis.dynamoRecordProcessor(tableName, persister), clientConfig);
	 */	
    // Count occurrences of HTTP referrer pairs over a range of 10 seconds
    private static final int COMPUTE_RANGE_FOR_COUNTS_IN_MILLIS = 10000;
    // Update the counts every 1 second
    private static final int COMPUTE_INTERVAL_IN_MILLIS = 1000;

	public static IRecordProcessorFactory recordProcessorDynamo(String tableName, DynamoDBCountPersister persister) {
		
        IRecordProcessorFactory recordProcessor =
                new CountingRecordProcessorFactory<HttpReferrerPair>(HttpReferrerPair.class,
                        persister,
                        COMPUTE_RANGE_FOR_COUNTS_IN_MILLIS,
                        COMPUTE_INTERVAL_IN_MILLIS);


        return recordProcessor;
	}

}
