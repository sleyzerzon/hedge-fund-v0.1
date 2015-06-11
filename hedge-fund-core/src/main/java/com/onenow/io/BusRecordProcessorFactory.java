package com.onenow.io;

import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessor;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorFactory;
import com.onenow.constant.StreamName;

public class BusRecordProcessorFactory<T> implements IRecordProcessorFactory {

    private Class<T> recordType;
    public StreamName streamName;

	public BusRecordProcessorFactory() {
		
	}
	
	public BusRecordProcessorFactory(Class<T> recordType, StreamName streamName) {

	    this.recordType = recordType;
	    this.streamName = streamName;

	}

	@Override
	public IRecordProcessor createProcessor() {
		
		return new BusRecordProcessor<>(recordType, streamName);
	}
}
