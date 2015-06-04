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
import com.fasterxml.jackson.databind.SerializationFeature;
import com.onenow.admin.InitAmazon;
import com.onenow.constant.StreamName;
import com.onenow.data.DynamoDBCountPersister;
import com.onenow.data.HttpReferrerPair;
import com.onenow.util.StreamUtils;

public class Kinesis {

	private static AmazonKinesis kinesis;
	
	private final ObjectMapper jsonMapper = new ObjectMapper();

	public Kinesis() {
	}
	
	public Kinesis(Region region) {
		this.kinesis = InitAmazon.getKinesis(region);
	}
	
	public void createStreamIfNotExists(StreamName streamName, int numShards) {
		
		System.out.println("&&&&&&&&&&&&& CREATED STREAM: " + streamName);

        // Creates a stream to write to with N shards if it doesn't exist
        StreamUtils streamUtils = new StreamUtils(kinesis);
        
        streamUtils.createStreamIfNotExists(streamName.toString(), numShards);
        
        System.out.println(streamName + " is ready for use");
	}
	
    public void sendObject(Object objectToSend, StreamName streamName) {
		
		// System.out.println("&&&&&&&&&&&&& TRYING TO WRITE: " + objectToSend.toString() + " INTO " + streamName);

		boolean success = true;
		
    	jsonMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    	
        byte[] bytes;
        try {
            bytes = jsonMapper.writeValueAsBytes(objectToSend);
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
        	success = false;
        	System.out.println("Throughput exceeded");
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } catch (AmazonClientException ex) {
        	System.out.println("Error sending record to Amazon Kinesis: " + ex);
        }
        
        if(success) {
    		System.out.println("&&&&&&&&&&&&& WROTE: " + objectToSend.toString() + " INTO STREAM: " + streamName);
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

}
