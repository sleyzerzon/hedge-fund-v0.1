package com.onenow.data;

import java.util.Date;


public class EventActivity extends Event {
	
	public Long time;
	public Double price;
	public Long size; 
	
	public EventActivity() {
		
	}
	
	public String toString() {
		String s = "";
		
		s = super.toString() + " ";
		s = s + "-time " + time + " ";
		s = s + "-timeDate" + new Date(time*1000).toString() + " ";
		s = s + "-price " + price + " ";
		s = s + "-size " + size;
		
		return s;
	}

}
