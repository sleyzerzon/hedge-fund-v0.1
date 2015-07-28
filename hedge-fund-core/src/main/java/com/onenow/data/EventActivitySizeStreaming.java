package com.onenow.data;

import com.onenow.constant.InvDataSource;
import com.onenow.constant.DataTiming;
import com.onenow.constant.SizeType;
import com.onenow.constant.DataType;
import com.onenow.instrument.Investment;

public class EventActivitySizeStreaming extends EventActivity {

	
	public EventActivitySizeStreaming(	Long timeInMilisec, Investment inv,  Integer size, SizeType sizeType,
										InvDataSource source) {

		super();
		super.dataType = DataType.SIZE_STREAM;
		
		setInvestment(inv);
		super.sizeType = sizeType; 
		super.source = source;
		super.timing = DataTiming.STREAM;
		
		super.timeInMsec = timeInMilisec;
		super.size = new Long(size);
	
	}
	
	public String toString() {
		String s = "";
		
		s = s + super.toString();
		
		return s;
	}

}
