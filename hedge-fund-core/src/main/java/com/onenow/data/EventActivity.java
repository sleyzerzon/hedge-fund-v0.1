package com.onenow.data;

import com.onenow.constant.ColumnName;
import com.onenow.constant.StreamingData;


public class EventActivity extends Event {
	
	public StreamingData streamingData;
	
	public Double price;
	public Long size; 
		
	
	public EventActivity() {
		super();
	}
		
	public Object getValue(ColumnName columnName) {
		Object value = null;
		
		try {
			// default to price
			value = (Object) price;
		} catch (Exception e) {
		}
		
		try {
			if(columnName.equals(ColumnName.SIZE)) {
				value = (Object) size;
			}
		} catch (Exception e) {
		}
		
		return value;
	}

	public String toString() {
		String s = "";
		
		s = s + "-streamingData " + streamingData.toString();
				
		try {
			if(price!=null) {
				s = s + "-price " + price + " ";
			}
		} catch (Exception e) {
		}

		try {
			if(size!=null) {
				s = s + "-size " + size;
			}
		} catch (Exception e) {
		}
		
		s = s + super.toString() + " ";

		return s;
	}

}
