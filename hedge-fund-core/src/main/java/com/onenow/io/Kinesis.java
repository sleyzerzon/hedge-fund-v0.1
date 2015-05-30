package com.onenow.io;

import java.io.IOException;
import java.nio.ByteBuffer;

import kinesis.StreamUtils;

import com.amazonaws.AmazonClientException;
import com.amazonaws.regions.Region;
import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.model.ProvisionedThroughputExceededException;
import com.amazonaws.services.kinesis.model.PutRecordRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onenow.admin.InitAmazon;

public class Kinesis {

	private static AmazonKinesis kinesis;
	
	private final ObjectMapper JsonMapper = new ObjectMapper();

	public Kinesis() {
	}
	
	public Kinesis(String streamName, Region region) {
		this.kinesis = InitAmazon.getKinesis(region);
	}
	
	public void createStream(String streamName, int numShards) {
		
        // Creates a stream to write to with N shards if it doesn't exist
        StreamUtils streamUtils = new StreamUtils(kinesis);
        streamUtils.createStreamIfNotExists(streamName, numShards);
        
        System.out.println(streamName + " is ready for use");
	}
	
    public void sendPair(Object objectToSend, String streamName) {
		
        byte[] bytes;
        try {
            bytes = JsonMapper.writeValueAsBytes(objectToSend);
        } catch (IOException e) {
        	System.out.println("Skipping pair. Unable to serialize: " + e);
            return;
        }

        PutRecordRequest putRecord = new PutRecordRequest();
        putRecord.setStreamName(streamName);
        
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

}
