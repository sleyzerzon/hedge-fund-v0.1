package com.onenow.data;

import com.onenow.constant.ColumnName;


public class EventActivity extends Event {
	
	public static Double price;
	public static Long size; 
		
	
	public EventActivity() {
		super();
	}
		
	public Object getValue(ColumnName columnName) {
		Object value = null;
		if(columnName.equals(ColumnName.PRICE)) {
			value = (Object) price;
		}
		if(columnName.equals(ColumnName.SIZE)) {
			value = (Object) size;
		}
		return value;
	}

	public String toString() {
		String s = "";
		
		s = super.toString() + " ";
		
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
		
		return s;
	}

}
