package com.onenow.io;

import java.util.List;
import java.util.logging.Level;

import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessor;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorCheckpointer;
import com.amazonaws.services.kinesis.clientlibrary.types.ShutdownReason;
import com.amazonaws.services.kinesis.model.Record;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onenow.constant.StreamName;
import com.onenow.constant.TestValues;
import com.onenow.data.EventActivityHistory;
import com.onenow.data.EventActivityRealtime;
import com.onenow.data.EventRequestHistory;
import com.onenow.main.ChartistMain;
import com.onenow.main.ClerkHistoryMain;
import com.onenow.main.ClerkRealTimeMain;
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
        				handleByRecordType(activityObject);
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
		
    	Watchr.log(Level.INFO, "********** READ RECORD FROM STREAM: " + recordObject.toString(), "\n", "");
		
		if(recordType.equals(String.class)) {
			
			try {
				// Kinesis + Elasticache Test
				// Read to see if the cache already has the test value
				String testValue = (String) CacheElastic.readAsync(TestValues.KEY.toString());
				boolean valuesMatch = testValue.equals(TestValues.VALUE.toString()); 
				if(valuesMatch) {
					Watchr.log(Level.WARNING, "Kinesis test PASS");
					return;
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
		
		if(recordType.equals(EventActivityHistory.class)) {
			try {
				EventActivityHistory event = (EventActivityHistory) recordObject;
				ClerkHistoryMain.writeHistoryToL2(event);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if(recordType.equals(EventActivityRealtime.class)) {			
			try {
				EventActivityRealtime event = (EventActivityRealtime) recordObject;
				ClerkRealTimeMain.writeRealtimeRTtoL2(event);
				if(streamName.equals(StreamName.PRIMARY) || streamName.equals(StreamName.STANDBY) ) {
					ChartistMain.prefetchCharts(event);				
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
}
