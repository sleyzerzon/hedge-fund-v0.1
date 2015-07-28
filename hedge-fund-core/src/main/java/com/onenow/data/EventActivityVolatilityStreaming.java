package com.onenow.data;

import com.onenow.constant.DataType;

public class EventActivityVolatilityStreaming  extends EventActivity {

	public EventActivityVolatilityStreaming() {
		
		super();
		super.streamingData = DataType.VOLATILITY_STREAM;

	}
}
