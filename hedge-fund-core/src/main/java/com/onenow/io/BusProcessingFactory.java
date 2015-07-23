package com.onenow.io;

import kinesis.CountingRecordProcessorFactory;

import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorFactory;
import com.onenow.constant.StreamName;
import com.onenow.data.DynamoDBCountPersister;
import com.onenow.data.EventActivityGenericStreaming;
import com.onenow.data.HttpReferrerPair;
import com.onenow.data.EventActivityPriceHistory;
import com.onenow.data.EventActivityPriceSizeRealtime;
import com.onenow.data.EventActivityPriceStreaming;
import com.onenow.data.EventActivitySizeStreaming;
import com.onenow.data.EventActivityGreekStreaming;
import com.onenow.data.EventActivityGreekHistory;
import com.onenow.data.EventActivityVolatilityStreaming;


public class BusProcessingFactory {

	public BusProcessingFactory() {
		
	}

	
	// STRING
	public static IRecordProcessorFactory createProcessorFactoryString(StreamName streamName) {
		
        IRecordProcessorFactory processorFactory = new BusRecordProcessorFactory<String>(String.class, streamName);

        return processorFactory;
	}

	// HISTORY
	public static IRecordProcessorFactory createProcessorFactoryEventPriceHistory(StreamName streamName) {
		
        IRecordProcessorFactory processorFactory = new BusRecordProcessorFactory<EventActivityPriceHistory>(EventActivityPriceHistory.class, streamName);

        return processorFactory;
	}

	public static IRecordProcessorFactory createProcessorFactoryEventGreekHistory(StreamName streamName) {
		
        IRecordProcessorFactory processorFactory = new BusRecordProcessorFactory<EventActivityGreekHistory>(EventActivityGreekHistory.class, streamName);

        return processorFactory;
	}

	// REALTIME
	public static IRecordProcessorFactory createProcessorFactoryEventPriceSizeRealtime(StreamName streamName) {
		
        IRecordProcessorFactory processorFactory = new BusRecordProcessorFactory<EventActivityPriceSizeRealtime>(EventActivityPriceSizeRealtime.class, streamName);

        return processorFactory;
	}

	// STREAMING
	public static IRecordProcessorFactory createProcessorFactoryEventPriceStreaming(StreamName streamName) {
		
        IRecordProcessorFactory processorFactory = new BusRecordProcessorFactory<EventActivityPriceStreaming>(EventActivityPriceStreaming.class, streamName);

        return processorFactory;
	}

	public static IRecordProcessorFactory createProcessorFactoryEventSizeStreaming(StreamName streamName) {
		
        IRecordProcessorFactory processorFactory = new BusRecordProcessorFactory<EventActivitySizeStreaming>(EventActivitySizeStreaming.class, streamName);

        return processorFactory;
	}

	public static IRecordProcessorFactory createProcessorFactoryEventGreekStreaming(StreamName streamName) {
		
        IRecordProcessorFactory processorFactory = new BusRecordProcessorFactory<EventActivityGreekStreaming>(EventActivityGreekStreaming.class, streamName);

        return processorFactory;
        
	}
	
	public static IRecordProcessorFactory createProcessorFactoryEventVolatilityStreaming(StreamName streamName) {
		
        IRecordProcessorFactory processorFactory = new BusRecordProcessorFactory<EventActivityVolatilityStreaming>(EventActivityVolatilityStreaming.class, streamName);

        return processorFactory;
	}

	public static IRecordProcessorFactory createProcessorFactoryEventGenericStreaming(StreamName streamName) {
		
        IRecordProcessorFactory processorFactory = new BusRecordProcessorFactory<EventActivityGenericStreaming>(EventActivityGenericStreaming.class, streamName);

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
