package com.onenow.data;

import java.util.UUID;
import java.util.logging.Level;

import com.onenow.util.TimeParser;
import com.onenow.util.Watchr;

public class ObjectOrigination {

	public final String id = String.valueOf(UUID.randomUUID());
	public final Long start = TimeParser.getTimestampNow();

	public ObjectOrigination() {
		
	}
	
	public String toString() {
		
		String s = "";

		toString("");
		
		return s;
	}
	
	public String toString(String note) {
		String s = "";
		
		try {
			s = note + " " + "-id " + id + " " + "-start " + start;
		} catch (Exception e) {
			Watchr.log(Level.SEVERE, e.toString());
		}
		
		return s;
	}
}
