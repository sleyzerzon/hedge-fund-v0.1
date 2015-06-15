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
import com.onenow.main.ChartistMain;
import com.onenow.main.ClerkHistoryMain;
import com.onenow.main.ClerkRealTimeMain;
import com.onenow.util.Watchr;

public class BusRecordProcessor<T> implements IRecordProcessor {

	Class<T> recordType;
	StreamName streamName;
	
	// TODO: https://code.google.com/p/google-gson/
    // Our JSON object mapper for deserializing records
    private final ObjectMapper jsonMapper = new ObjectMapper();
    
    // The shard this processor is processing
    private String kinesisShardId;


	public BusRecordProcessor() {
	}
	
	public BusRecordProcessor(Class<T> recordType, StreamName streamName) {
		
		this.recordType = recordType;
		
        // Create an object mapper to deserialize records that ignores unknown properties
        jsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        
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

	// TODO: handle NON-STRING records (i.e. EventHistory)
	@Override
	public void processRecords(List<Record> records, IRecordProcessorCheckpointer checkpointer) {
		
		Watchr.log(Level.INFO, "processRecords: " + records.toString());
		
        for (Record r : records) {
            // Deserialize each record as an UTF-8 encoded JSON String of the type provided
            final T record;
            try {            	
            	
				String json = new String(r.getData().array());
            	record = jsonMapper.readValue(json, recordType);

        		new Thread () {
        			@Override public void run () {
        				handleByRecordType(record);
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
	 * @param record
	 */
	private void handleByRecordType(T record) {
		
    	Watchr.log(Level.INFO, "********** READ RECORD FROM STREAM: " + record.toString(), "\n", "");
		
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
				// Write the last one to cache to validate the stream works
				CacheElastic.write(TestValues.KEY.toString(), (Object) record);
			} catch (Exception e) {
				// e.printStackTrace();
			}
		}
		
		if(recordType.equals(EventActivityHistory.class)) {
			EventActivityHistory event = (EventActivityHistory) record;
			ClerkHistoryMain.writeHistoryToL2(event);
		}

		if(recordType.equals(EventActivityRealtime.class)) {			
			EventActivityRealtime event = (EventActivityRealtime) record;
			ClerkRealTimeMain.writeRealtimeRTtoL2(event);
			if(streamName.equals(StreamName.PRIMARY) || streamName.equals(StreamName.STANDBY) ) {
				ChartistMain.prefetchCharts(event);				
			}
		}

	}
	
}
