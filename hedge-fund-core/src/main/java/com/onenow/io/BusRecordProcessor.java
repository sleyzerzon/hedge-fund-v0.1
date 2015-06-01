package com.onenow.io;

import java.io.IOException;
import java.util.List;

import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessor;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorCheckpointer;
import com.amazonaws.services.kinesis.clientlibrary.types.ShutdownReason;
import com.amazonaws.services.kinesis.model.Record;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

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

	// TODO: handle NON-STRING records (i.e. EventHistory)
	@Override
	public void processRecords(List<Record> records, IRecordProcessorCheckpointer checkpointer) {
		
		System.out.println("&&&&&&&&&&&&& PROCESSING RECORDS");
		
        for (Record r : records) {
            // Deserialize each record as an UTF-8 encoded JSON String of the type provided
            T record;
            try {
                
            	record = JSON.readValue(r.getData().array(), recordType);
            	System.out.println("******************************** RECORD: " + record.toString());
                
            } catch (IOException e) {
            	String s = "Skipping record. Unable to parse record into HttpReferrerPair. Partition Key: "
                        + r.getPartitionKey() + ". Sequence Number: " + r.getSequenceNumber() + e;
            	System.out.println(s);
                continue;
            }
            
        }	
                
	}

	@Override
	public void shutdown(IRecordProcessorCheckpointer checkpointer, ShutdownReason reason) {

		System.out.println("Shutting down record processor for shard: " + kinesisShardId);
	}

}
