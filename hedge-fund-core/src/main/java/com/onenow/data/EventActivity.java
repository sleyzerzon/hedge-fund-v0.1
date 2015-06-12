package com.onenow.data;


public class EventActivity extends Event {
	
	public Long time;
	public Double price;
	public Long size; 
	
	public EventActivity() {
		
	}
	
	public String toString() {
		String s = "";
		s = s + "- id " + origin.id.toString();
		s = s + "- start " + origin.start.toString();
		return s;
	}

}
