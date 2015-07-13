package com.onenow.data;

import com.onenow.constant.ColumnName;
import com.onenow.constant.DBQuery;
import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.SamplingRate;
import com.onenow.constant.PriceType;
import com.onenow.instrument.Investment;

public class EventRequest extends Event {
	
	public SamplingRate sampling;
	public String toDashedDate;
	public String fromDashedDate;
	
	// where time > now() - 1h limit 1000
	public String timeGap;
	public String endPoint;
	

	public EventRequest() {
		super();
	}
	


	public String toString() {
		String s = "";
		
		s = s + super.toString() + " ";

		try {
			s = s + "-sampling " + sampling + " ";
		} catch (Exception e) {
		}
		
		try {
			s = s + "-from " + fromDashedDate + " ";
		} catch (Exception e) {
		}
				
		try {
			s = s + "-to " + toDashedDate + " ";
		} catch (Exception e) {
		}	

		try {
			if(timeGap!=null) {
				s = s + "-timeGap " + timeGap + " ";
			}
		} catch (Exception e) {
		}	

		try {
			if(endPoint!=null) {
				s = s + "-endPoint " + endPoint + " "; 
			}
		} catch (Exception e) {
		}	

		return s;
	}

}
