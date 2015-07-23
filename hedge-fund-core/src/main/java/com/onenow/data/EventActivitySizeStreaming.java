package com.onenow.data;

import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.SizeType;
import com.onenow.constant.StreamingData;
import com.onenow.instrument.Investment;

public class EventActivitySizeStreaming extends EventActivity {

	
	public EventActivitySizeStreaming(	Long timeInMilisec, Investment inv,  Integer size, SizeType sizeType,
										InvDataSource source) {

		super();
		super.streamingData = StreamingData.SIZE_STREAMING;
		
		setInvestment(inv);
		super.sizeType = sizeType; 
		super.source = source;
		super.timing = InvDataTiming.STREAMING;
		
		super.timeInMilisec = timeInMilisec;
		super.size = new Long(size);
	
	}
	
	public String toString() {
		String s = "";
		
		s = s + super.toString();
		
		return s;
	}

}
