package com.onenow.io;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Level;

import kinesis.CountingRecordProcessorFactory;

import com.amazonaws.AmazonClientException;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
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
import com.onenow.constant.TestValues;
import com.onenow.data.DynamoDBCountPersister;
import com.onenow.data.HttpReferrerPair;
import com.onenow.util.StreamUtils;
import com.onenow.util.TimeParser;
import com.onenow.util.Watchr;

public class Kinesis {

	public static AmazonKinesis kinesis;
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	public Kinesis() {
		this.kinesis = InitAmazon.getKinesis(Region.getRegion(Regions.US_EAST_1));
	}
	
	public Kinesis(Region region) {
		this.kinesis = InitAmazon.getKinesis(region);
	}
	
	public void createStreamIfNotExists(StreamName streamName, int numShards) {
		
        // Creates a stream to write to with N shards if it doesn't exist
        StreamUtils streamUtils = new StreamUtils(kinesis);
        
        streamUtils.createStreamIfNotExists(streamName.toString(), numShards);
        
        String log = streamName + " stream is ready for use";
    	Watchr.log(Level.FINE, log);

	}
	
    public void sendObject(Object objectToSend, StreamName streamName) {
		
		boolean success = true;
		
    	jsonMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    	
        byte[] bytes;
        try {
            bytes = jsonMapper.writeValueAsBytes(objectToSend);
        } catch (IOException e) {
        	Watchr.log(Level.SEVERE, "Skipping pair. Unable to serialize: " + e.getMessage());
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
        	Watchr.log(Level.SEVERE, "Throughput exceeded" + ex.getMessage());
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } catch (AmazonClientException ex) {
        	Watchr.log(Level.SEVERE, "Error sending record to Amazon Kinesis: " + ex.getMessage());
        }
        
        if(success) {
        	String log = "&&&&&&&&&&&&& WROTE: " + objectToSend.toString() + " INTO STREAM: " + streamName;
        	Watchr.log(Level.INFO, log, "\n", "");
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

    
    
    // TESTING
	static IRecordProcessorFactory testingProcessorFactory = BusProcessingFactory.processorFactoryString();

//	private Long time = new Long("1424288913903");
//	private Investment inv = new Investment();
//	private TradeType tradeType = TradeType.TRADED; 
//
//	private Double price = 2011.0;
//	private Integer size = 5;
//	
//	private InvDataSource source = InvDataSource.IB;
//	private InvDataTiming timing = InvDataTiming.REALTIME;

	/**
	 * Write repeatedly to a data stream.  Have record processor write to cache.  Then read and validate it write the right amount.
	 */
	public static void selfTest() {	
		
		// initialize to an incorrect value
		BusSystem.write(StreamName.TESTING, TestValues.BOGUS.toString());
		
		new Thread () {
			@Override public void run () {
				writeRepeatedly();
			}
		}.start();
		new Thread () {
			@Override public void run () {
				readRepeatedly();
			}
		}.start();
	}
	
	  private static void writeRepeatedly() {
		  	int count = 0;
		  	int maxTries=3;
		  	while(true) {	
		  		Watchr.log(Level.WARNING, "KINESIS SELF TEST: ");
				BusSystem.write(StreamName.TESTING, TestValues.VALUE.toString());
				TimeParser.wait(15);
				count ++;
				if(count>maxTries) {
					return;
				}
		  	}
	  }
	  
	  /**
	   * Reading processor will look for write-read matches in the cache
	   */
	  private static void readRepeatedly() {
			BusSystem.read(StreamName.TESTING, testingProcessorFactory);
	  }

}
