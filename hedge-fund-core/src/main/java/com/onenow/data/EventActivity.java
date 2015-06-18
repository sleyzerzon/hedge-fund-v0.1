package com.onenow.data;


import com.onenow.util.TimeParser;


public class EventActivity extends Event {
	
	public Double price;
	public Long size; 
	
	
	
	public EventActivity() {
		
	}
	
	public String getFormatedTime() {
		return TimeParser.getFormatedPacificDateTime(time);
	}
	
	public String toString() {
		String s = "";
		
		s = super.toString() + " ";
		s = s + "-time " + time + " ";
		s = s + "-date " + getFormatedTime() + " ";
		s = s + "-price " + price + " ";
		s = s + "-size " + size;
		
		return s;
	}

}
