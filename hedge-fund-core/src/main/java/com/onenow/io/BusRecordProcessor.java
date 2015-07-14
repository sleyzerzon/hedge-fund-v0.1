package com.onenow.io;

import java.util.List;
import java.util.logging.Level;

import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessor;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorCheckpointer;
import com.amazonaws.services.kinesis.clientlibrary.types.ShutdownReason;
import com.amazonaws.services.kinesis.model.Record;
import com.onenow.constant.StreamName;
import com.onenow.constant.TestValues;
import com.onenow.data.EventActivitySizeStreaming;
import com.onenow.data.EventActivityGreekHistory;
import com.onenow.data.EventActivityPriceHistory;
import com.onenow.data.EventActivityPriceSizeRealtime;
import com.onenow.data.EventActivityPriceStreaming;
import com.onenow.data.EventActivityGreekStreaming;
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
				
		if(recordType.equals(String.class)) {
			
			try {
				// Kinesis + Elasticache Test
				// Read to see if the cache already has the test value
				String testValue = (String) CacheElastic.readAsync(TestValues.KEY.toString());
		    	Watchr.log(Level.INFO, "********** READ RECORD FROM STREAM: ->String<- " + testValue.toString(), "\n", "");

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
		
		if(recordType.equals(EventActivityPriceHistory.class)) {
			try {
				EventActivityPriceHistory event = (EventActivityPriceHistory) recordObject;
		    	Watchr.log(Level.INFO, "********** READ RECORD FROM STREAM: ->EventActivityPriceHistory<- " + event.toString(), "\n", "");
				ClerkHistoryMain.writeHistoryPriceToL2(event);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(recordType.equals(EventActivityGreekHistory.class)) {
			// TODO
		}

		if(recordType.equals(EventActivityPriceSizeRealtime.class)) {			
			try {
				EventActivityPriceSizeRealtime event = (EventActivityPriceSizeRealtime) recordObject;
		    	Watchr.log(Level.INFO, "********** READ RECORD FROM STREAM: ->EventActivityPriceSizeRealtime<- " + event.toString(), "\n", "");
				ClerkRealTimeMain.writeRealtimePriceSizeToL2(event);
				try {
					if(streamName.equals(StreamName.PRIMARY_STAGING) || streamName.equals(StreamName.STANDBY_STAGING) ) {
						ChartistMain.prefetchCharts(event);				
					}
				} catch (Exception e) {
					// e.printStackTrace();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if(recordType.equals(EventActivityPriceStreaming.class)) {			
			// TODO
		}

		if(recordType.equals(EventActivitySizeStreaming.class)) {			
			// TODO
		}

		if(recordType.equals(EventActivityGreekStreaming.class)) {			
			// TODO
		}
		
		
	}
}
