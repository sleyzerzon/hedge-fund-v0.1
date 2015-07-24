package com.onenow.io;

import java.util.List;
import java.util.logging.Level;

import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessor;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorCheckpointer;
import com.amazonaws.services.kinesis.clientlibrary.types.ShutdownReason;
import com.amazonaws.services.kinesis.model.Record;
import com.onenow.constant.StreamName;
import com.onenow.constant.TestValues;
import com.onenow.data.EventActivity;
import com.onenow.data.EventActivityGenericStreaming;
import com.onenow.data.EventActivitySizeStreaming;
import com.onenow.data.EventActivityGreekHistory;
import com.onenow.data.EventActivityPriceHistory;
import com.onenow.data.EventActivityPriceSizeRealtime;
import com.onenow.data.EventActivityPriceStreaming;
import com.onenow.data.EventActivityGreekStreaming;
import com.onenow.data.EventActivityVolatilityStreaming;
import com.onenow.main.ClerkMain;
import com.onenow.util.Piping;
import com.onenow.util.Watchr;

public class BusRecordProcessor<T> implements IRecordProcessor {

	Class<T> recordType;
	StreamName streamName;
	    
    // The shard this processor is processing
    private String kinesisShardId;


	public BusRecordProcessor() {
	}
	
	public BusRecordProcessor(Class<T> recordType, StreamName streamName) {
		
		this.recordType = recordType;
		this.streamName = streamName;
		
	}


	@Override
	public void initialize(String shardId) {

		this.kinesisShardId = shardId;
		
	}

	@Override
	public void shutdown(IRecordProcessorCheckpointer checkpointer, ShutdownReason reason) {

		String log = "Shutting down record processor for shard: " + kinesisShardId;
    	Watchr.log(Level.INFO, log);

	}

	@Override
	public void processRecords(List<Record> records, IRecordProcessorCheckpointer checkpointer) {
		
		Watchr.log(Level.INFO, "processRecords: " + records.toString());
		
        for (Record r : records) {
            
            final Object activityObject;
            
            try {            	
              String json = new String(r.getData().array());
        	  activityObject = Piping.deserialize(json, recordType);
  			  Watchr.log(Level.FINE, "Received activity object: " + activityObject.toString());
  			
        		new Thread () {
        			@Override public void run () {
        				try {
							handleByRecordType(activityObject);
						} catch (Exception e) {
							e.printStackTrace();
						}
        			}
        		}.start();
                
            } catch (Exception e) {
            	String log = 	"Skipping record. Unable to parse record into: " + recordType + 
            					". Partition Key: " + r.getPartitionKey() + 
            					". Sequence Number: " + r.getSequenceNumber() + 
            					e.toString();
            	Watchr.log(Level.SEVERE, log);
                continue;
            }
            
        }	
                
	}

	
	/** 
	 * Handle records in Kinesis, in some cases with multiple consumers
	 * @param recordObject
	 */
	private void handleByRecordType(Object recordObject) {		
		EventActivity event = new EventActivity();
		boolean success = true;
		try {
			event = getEvent(recordObject, event);
		} catch (Exception e) {
			success = false;
			e.printStackTrace();
		}
		
		if(success) {
	    	Watchr.log(Level.INFO, "********** RECORD PROCESSOR <" + recordType.toString() + "> READ RECORD: " + event.toString(), "\n", "");
	    	DBTimeSeries.writeToL2(event);				
		}
	}

	/**
	 * Casts objects incoming in data streams.  
	 * It is possible that the wrong kind of object arrives, causing exceptions that must be handled.
	 * Only if there are no exceptions then the events are handled fully.
	 * @param recordObject
	 * @param event
	 * @return
	 * @throws Exception
	 */
	private EventActivity getEvent(Object recordObject, EventActivity event) throws Exception {
		
		// STRING
		if(recordType.equals(String.class)) {			
			runStringTest();
		}
		// HISTORY
		if(recordType.equals(EventActivityPriceHistory.class)) {
			event = (EventActivityPriceHistory) recordObject;
		}
		if(recordType.equals(EventActivityGreekHistory.class)) {
			event = (EventActivityGreekHistory) recordObject;
		}
		// REALTIME
		if(recordType.equals(EventActivityPriceSizeRealtime.class)) {			
			event = (EventActivityPriceSizeRealtime) recordObject;
		}
		// STREAMING
		if(recordType.equals(EventActivityPriceStreaming.class)) {			
			event = (EventActivityPriceStreaming) recordObject;
		}
		if(recordType.equals(EventActivitySizeStreaming.class)) {			
			event = (EventActivitySizeStreaming) recordObject;
		}
		if(recordType.equals(EventActivityGreekStreaming.class)) {
			event = (EventActivityGreekStreaming) recordObject;
		}		
		if(recordType.equals(EventActivityVolatilityStreaming.class)) {
			event = (EventActivityVolatilityStreaming) recordObject;
		}
		if(recordType.equals(EventActivityGenericStreaming.class)) {
			event = (EventActivityGenericStreaming) recordObject;
		}
		return event;
	}
	
	private void runStringTest() {
		try {
			// Kinesis + Elasticache Test
			// Read to see if the cache already has the test value
			String testValue = (String) CacheElastic.readAsync(TestValues.KEY.toString());
			Watchr.log(Level.INFO, "********** READ RECORD FROM STREAM: ->String<- " + testValue.toString(), "\n", "");

			boolean valuesMatch = testValue.equals(TestValues.VALUE.toString()); 
			if(valuesMatch) {
				Watchr.log(Level.WARNING, "Kinesis test PASS");
			} else {
				Watchr.log(Level.WARNING, "Kinesis test FAIL");
			}
			// TODO 
			// Write the last one to cache to validate the stream works
			// CacheElastic.write(TestValues.KEY.toString(), (Object) recordObject);
			
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}
}
