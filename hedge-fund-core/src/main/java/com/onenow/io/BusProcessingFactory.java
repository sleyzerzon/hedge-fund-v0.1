package com.onenow.io;

import kinesis.CountingRecordProcessorFactory;

import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorFactory;
import com.onenow.data.DynamoDBCountPersister;
import com.onenow.data.EventHistoryRT;
import com.onenow.data.HttpReferrerPair;

public class BusProcessingFactory {

	public BusProcessingFactory() {
		
	}
	
	public static IRecordProcessorFactory recordProcessorString() {
		
        IRecordProcessorFactory recordProcessor = new BusRecordProcessorFactory<String>(String.class);

        return recordProcessor;
	}

	public static IRecordProcessorFactory eventProcessorFactory() {
		
        IRecordProcessorFactory recordProcessor = new BusRecordProcessorFactory<EventHistoryRT>(EventHistoryRT.class);

        return recordProcessor;
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
