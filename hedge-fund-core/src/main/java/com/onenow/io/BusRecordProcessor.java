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
import com.onenow.instrument.Investment;
import com.onenow.main.TSDBWriteMain;
import com.onenow.util.WatchLog;

public class BusRecordProcessor<T> implements IRecordProcessor {

	Class<T> recordType;
	
    // Our JSON object mapper for deserializing records
    private final ObjectMapper JSON = new ObjectMapper();
    
    // The shard this processor is processing
    private String kinesisShardId;


	public BusRecordProcessor() {	
	}
	
	public BusRecordProcessor(Class<T> recordType) {
		
		System.out.println("&&&&&&&&&&&&& CREATED PROCESSOR");

		this.recordType = recordType;
		
        // Create an object mapper to deserialize records that ignores unknown properties
        JSON.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        
	}

	@Override
	public void initialize(String shardId) {

		System.out.println("&&&&&&&&&&&&& INITIALIZED PROCESSOR");

		this.kinesisShardId = shardId;
		
	}

	@Override
	public void shutdown(IRecordProcessorCheckpointer checkpointer, ShutdownReason reason) {

		System.out.println("Shutting down record processor for shard: " + kinesisShardId);
	}

	// TODO: handle NON-STRING records (i.e. EventHistory)
	@Override
	public void processRecords(List<Record> records, IRecordProcessorCheckpointer checkpointer) {
		
		System.out.println("&&&&&&&&&&&&& PROCESSING RECORDS");
		
        for (Record r : records) {
            // Deserialize each record as an UTF-8 encoded JSON String of the type provided
            T record;
            try {
                
            	record = JSON.readValue(r.getData().array(), recordType);
            	handleByRecordType(record);
                
            } catch (IOException e) {
            	String s = "Skipping record. Unable to parse record into HttpReferrerPair. Partition Key: "
                        + r.getPartitionKey() + ". Sequence Number: " + r.getSequenceNumber() + e;
            	System.out.println(s);
                continue;
            }
            
        }	
                
	}

	private void handleByRecordType(T record) {
		
		System.out.println("******************************** RECORD: " + record.toString());
		
		if(recordType.equals(String.class)) {
			
		}
		
		if(recordType.equals(EventHistory.class)) {
			EventHistory event = (EventHistory) record;
			TSDBWriteMain.writeRTtoL2(event);
			
		}

		if(recordType.equals(EventHistoryRT.class)) {
			
			EventHistoryRT event = (EventHistoryRT) record;
			TSDBWriteMain.writeRTtoL2(event);
		}

	}
	
}
