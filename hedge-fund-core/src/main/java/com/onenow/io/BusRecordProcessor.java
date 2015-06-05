package com.onenow.io;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessor;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorCheckpointer;
import com.amazonaws.services.kinesis.clientlibrary.types.ShutdownReason;
import com.amazonaws.services.kinesis.model.Record;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.TradeType;
import com.onenow.data.EventHistory;
import com.onenow.data.EventRealTime;
import com.onenow.instrument.Investment;
import com.onenow.main.ChartistMain;
import com.onenow.main.ClerkHistoryMain;
import com.onenow.main.ClerkRealTimeMain;
import com.onenow.util.WatchLog;

public class BusRecordProcessor<T> implements IRecordProcessor {

	Class<T> recordType;
	
    // Our JSON object mapper for deserializing records
    private final ObjectMapper jsonMapper = new ObjectMapper();
    
    // The shard this processor is processing
    private String kinesisShardId;


	public BusRecordProcessor() {
	}
	
	public BusRecordProcessor(Class<T> recordType) {
		
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
    	WatchLog.addToLog(Level.INFO, log);

	}

	// TODO: handle NON-STRING records (i.e. EventHistory)
	@Override
	public void processRecords(List<Record> records, IRecordProcessorCheckpointer checkpointer) {
		
        for (Record r : records) {
            // Deserialize each record as an UTF-8 encoded JSON String of the type provided
            T record;
            try {
                String json = new String(r.getData().array());
            	record = jsonMapper.readValue(json, recordType);
            	handleByRecordType(record);
                
            } catch (IOException e) {
            	String log = 	"Skipping record. Unable to parse record into: " + recordType + 
            				". Partition Key: " + r.getPartitionKey() + 
            				". Sequence Number: " + r.getSequenceNumber() + 
            				e;
            	WatchLog.addToLog(Level.INFO, log);
                continue;
            }
            
        }	
                
	}

	/** 
	 * Handle records in Kinesis, in some cases with multiple consumers
	 * @param record
	 */
	private void handleByRecordType(T record) {
		
		String log = "******************************** GOT RECORD: " + record.toString();
    	WatchLog.addToLog(Level.INFO, log);
		
		if(recordType.equals(String.class)) {
			
		}
		
		if(recordType.equals(EventHistory.class)) {
			EventHistory event = (EventHistory) record;
			ClerkHistoryMain.writeHistoryToL2(event);
		}

		if(recordType.equals(EventRealTime.class)) {			
			EventRealTime event = (EventRealTime) record;
			ChartistMain.prefetchCharts(event);
			ClerkRealTimeMain.writeHistoryRTtoL2(event);
		}

	}
	
}
