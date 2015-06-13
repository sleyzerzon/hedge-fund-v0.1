package com.onenow.data;


public class EventActivity extends Event {
	
	public Long time;
	public Double price;
	public Long size; 
	
	public EventActivity() {
		
	}
	
	public String toString() {
		String s = "";
		
		s = super.toString() + " ";
		s = s + "time " + time + " ";
		s = s + "price " + price + " ";
		s = s + "size " + size;
		
		return s;
	}

}
