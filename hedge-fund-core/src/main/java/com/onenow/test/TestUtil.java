package com.onenow.test;

import com.onenow.admin.DatabaseSystemActivityImpl;
import com.onenow.util.TimeParser;
import com.sforce.ws.ConnectionException;

public class TestUtil {
	
	private DatabaseSystemActivityImpl logDB;
	
	private TimeParser parseDate;

	public TestUtil() {
		
	}
	
	public TestUtil(DatabaseSystemActivityImpl logDB) {
		this.logDB = logDB;
		this.parseDate = new TimeParser();
	}
	
	
	
	public boolean test() {
		boolean result;
		try {
			result = parseDate.test();
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
		}
		handleResult(result);	
		return result;
	}

	private void handleResult(boolean success) {
		String s="";
		if(success==true) {
			s = s + "NO ERRORS FOUND==AT-ALL==: " + "TestUtil" + "\n\n";
		} else {
			s = s + "TEST ERROR: " + "TestUtil"  + "\n\n";
		}
		// print to screen
		System.out.println(s);
		// save to database
		if(logDB.isDBLive()) {
			try {
				logDB.newLog("TestBroker", s);
			} catch (ConnectionException e) {
				e.printStackTrace();
			}
		}
	}
}
