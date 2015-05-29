package com.onenow.main;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.amazonaws.AmazonClientException;
import com.amazonaws.regions.Region;
import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.model.ProvisionedThroughputExceededException;
import com.amazonaws.services.kinesis.model.PutRecordRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onenow.admin.InitAmazon;

import kinesis.HttpReferrerKinesisPutter;
import kinesis.HttpReferrerPair;
import kinesis.HttpReferrerPairFactory;
import kinesis.StreamUtils;

public class BusMain {

	private static String streamName="test";
	private static Region region = null; // Regions.US_EAST_1;
	private static AmazonKinesis kinesis = InitAmazon.getKinesis(region);
	
	private final ObjectMapper JsonMapper = new ObjectMapper();

    
	public static void main(String[] args) {

		
        // Creates a stream to write to with 2 shards if it doesn't exist
        StreamUtils streamUtils = new StreamUtils(kinesis);
        streamUtils.createStreamIfNotExists(streamName, 2);
//        LOG.info(String.format("%s stream is ready for use", streamName));

        
        // Creates a stream to write to with 2 shards if it doesn't exist
        StreamUtils streamUtils1 = new StreamUtils(kinesis);
        streamUtils1.createStreamIfNotExists(streamName, 2);
//        LOG.info(String.format("%s stream is ready for use", streamName));

//        final HttpReferrerKinesisPutter putter = new HttpReferrerKinesisPutter(pairFactory, kinesis, streamName);

        ExecutorService es = Executors.newCachedThreadPool();

	}
	
    private void sendPair(Object objectToSend) {
//        HttpReferrerPair pair = referrerFactory.create();
    			
        byte[] bytes;
        try {
            bytes = JsonMapper.writeValueAsBytes(objectToSend);
        } catch (IOException e) {
//            LOG.warn("Skipping pair. Unable to serialize: '" + pair + "'", e);
            return;
        }

        PutRecordRequest putRecord = new PutRecordRequest();
        putRecord.setStreamName(streamName);
        
        // We use the resource as the partition key so we can accurately calculate totals for a given resource
//        putRecord.setPartitionKey(objectToSend.getResource());
        putRecord.setPartitionKey(objectToSend.toString());
        putRecord.setData(ByteBuffer.wrap(bytes));
        
        // Order is not important for this application so we do not send a SequenceNumberForOrdering
        putRecord.setSequenceNumberForOrdering(null);

        try {
            kinesis.putRecord(putRecord);
        } catch (ProvisionedThroughputExceededException ex) {
//            if (LOG.isDebugEnabled()) {
//                LOG.debug(String.format("Thread %s's Throughput exceeded. Waiting 10ms", Thread.currentThread().getName()));
//            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } catch (AmazonClientException ex) {
//            LOG.warn("Error sending record to Amazon Kinesis.", ex);
        }
    }
}

