package com.onenow.data;

import java.util.logging.Level;

import com.onenow.util.Watchr;


public class EventActivity extends Event {
	
	public Long time;
	public Double price;
	public Long size; 
	
	public EventActivity() {
		
	}
	
	public String toString() {
		String s = "";
		
		try {
			s = super.toString() + " ";
			s = s + "time " + time + " ";
			s = s + "price " + price + " ";
			s = s + "size " + size;
		} catch (Exception e) {
			Watchr.log(Level.SEVERE, e.toString());
		}
		
		return s;
	}

}
