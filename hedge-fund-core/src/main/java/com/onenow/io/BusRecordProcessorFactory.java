package com.onenow.io;

import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessor;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorFactory;

public class BusRecordProcessorFactory<T> implements IRecordProcessorFactory {

    private Class<T> recordType;

	public BusRecordProcessorFactory() {
		
	}
	
	public BusRecordProcessorFactory(Class<T> recordType) {

	    this.recordType = recordType;

	}

	@Override
	public IRecordProcessor createProcessor() {
		
		return new BusRecordProcessor(recordType);
	}
}
