package com.onenow.io;

import java.io.IOException;
import java.nio.ByteBuffer;

import kinesis.CountingRecordProcessorFactory;

import com.amazonaws.AmazonClientException;
import com.amazonaws.regions.Region;
import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorFactory;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.InitialPositionInStream;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.KinesisClientLibConfiguration;
import com.amazonaws.services.kinesis.model.ProvisionedThroughputExceededException;
import com.amazonaws.services.kinesis.model.PutRecordRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onenow.admin.InitAmazon;
import com.onenow.constant.StreamName;
import com.onenow.data.DynamoDBCountPersister;
import com.onenow.data.HttpReferrerPair;
import com.onenow.util.StreamUtils;

public class Kinesis {

	private static AmazonKinesis kinesis;
	
	private final ObjectMapper JsonMapper = new ObjectMapper();

	public Kinesis() {
	}
	
	public Kinesis(Region region) {
		this.kinesis = InitAmazon.getKinesis(region);
	}
	
	public void createStream(StreamName streamName, int numShards) {
		
		System.out.println("&&&&&&&&&&&&& CREATED STREAM: " + streamName);

        // Creates a stream to write to with N shards if it doesn't exist
        StreamUtils streamUtils = new StreamUtils(kinesis);
        streamUtils.createStreamIfNotExists(streamName.toString(), numShards);
        
        System.out.println(streamName + " is ready for use");
	}
	
    public void sendObject(Object objectToSend, StreamName streamName) {
		
        byte[] bytes;
        try {
            bytes = JsonMapper.writeValueAsBytes(objectToSend);
        } catch (IOException e) {
        	System.out.println("Skipping pair. Unable to serialize: " + e);
            return;
        }

        PutRecordRequest putRecord = new PutRecordRequest();
        putRecord.setStreamName(streamName.toString());
        
        // We use the resource as the partition key so we can accurately calculate totals for a given resource
        putRecord.setPartitionKey(objectToSend.toString());
        putRecord.setData(ByteBuffer.wrap(bytes));
        
        // Order is not important for this application so we do not send a SequenceNumberForOrdering
        putRecord.setSequenceNumberForOrdering(null);

        try {
            kinesis.putRecord(putRecord);
        } catch (ProvisionedThroughputExceededException ex) {
        	System.out.println("Throughput exceeded");
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } catch (AmazonClientException ex) {
        	System.out.println("Error sending record to Amazon Kinesis: " + ex);
        }
    }
    
    public KinesisClientLibConfiguration configureClient(	String applicationName, String streamName, 
    														String workerId, Region region) {
    	
        KinesisClientLibConfiguration kclConfig =
                new KinesisClientLibConfiguration(applicationName, streamName, InitAmazon.getAWSCredentialProvider(), workerId);
        kclConfig.withCommonClientConfig(InitAmazon.getClientConfig());
        kclConfig.withRegionName(region.getName());
        kclConfig.withInitialPositionInStream(InitialPositionInStream.LATEST);

        return kclConfig;
    }
    
    
	public static IRecordProcessorFactory ibRecordProcessor() {
		
        IRecordProcessorFactory recordProcessor = new BusRecordProcessorFactory<String>(String.class);

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

	public static IRecordProcessorFactory dynamoRecordProcessor(String tableName, DynamoDBCountPersister persister) {
		
        IRecordProcessorFactory recordProcessor =
                new CountingRecordProcessorFactory<HttpReferrerPair>(HttpReferrerPair.class,
                        persister,
                        COMPUTE_RANGE_FOR_COUNTS_IN_MILLIS,
                        COMPUTE_INTERVAL_IN_MILLIS);


        return recordProcessor;
	}



}
