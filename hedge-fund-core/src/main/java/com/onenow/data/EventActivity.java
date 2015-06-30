package com.onenow.data;


public class EventActivity extends Event {
	
	public Double price;
	public Long size; 
	
	
	
	public EventActivity() {
		
	}
		
	public String toString() {
		String s = "";
		
		s = super.toString() + " ";
		
		try {
			s = s + "-time " + time + " ";
		} catch (Exception e) {
		}

		try {
			s = s + "-date " + getFormatedTime() + " ";
		} catch (Exception e) {
		}

		try {
			s = s + "-price " + price + " ";
		} catch (Exception e) {
		}

		try {
			s = s + "-size " + size;
		} catch (Exception e) {
		}
		
		return s;
	}

}
