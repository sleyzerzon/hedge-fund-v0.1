package com.onenow.data;

import com.onenow.constant.StreamName;
import com.onenow.constant.DataType;

public class EventActivityGenericStreaming extends EventActivity {

	public EventActivityGenericStreaming() {
		
		super();
		
		super.streamingData = DataType.GENERIC_STREAM;
		
	}
}
