package com.onenow.test;

import com.onenow.admin.DatabaseSystemActivityImpl;
import com.sforce.ws.ConnectionException;

public class TestDatabase {
	
	private DatabaseSystemActivityImpl logDB = null;
	
	// CONSTRUCTOR
	public TestDatabase() {
		
	}
	
	public TestDatabase(DatabaseSystemActivityImpl logDB) {
		setLogDB(logDB);
	}

	// PUBLIC 
	public boolean test() {
		
		boolean success;
		try {
			success = testDB();
		} catch (Exception e1) {
			success = false;
			e1.printStackTrace();
		}
		
		String s="";
		if(success==true) {
			s = s + "\n" + "NO ERRORS FOUND==AT-ALL==: " + "TestDatabase";
		} else {
			s = s + "TEST ERROR: " + "TestDatabase";
		}
		// print to screen
		System.out.println(s);
		// save to DB
		if(getLogDB()!=null) {
			try {
				getLogDB().newLog("TestDatabase", s);
			} catch (ConnectionException e) {
				e.printStackTrace();
			}
		}
		return success;
		
	}
	
	
	private boolean testDB() {
		
		boolean success = true;
		
		
		try {
			DatabaseSystemActivityImpl db = new DatabaseSystemActivityImpl();	
			System.out.println("\n\n" + "DB");
			db.toString();
		} catch (Exception e) {
			success = false;
			e.printStackTrace();
			System.out.println("ERROR: Could not initialize: DatabaseSystemActivityImpl");
		}
		
//		if(!tx.getNetPremium().equals(39761.0)) {
//			System.out.println("ERROR premium " + tx.getNetPremium());
//			return false;
//		}
		
		return success;
	}

	private DatabaseSystemActivityImpl getLogDB() {
		return logDB;
	}

	private void setLogDB(DatabaseSystemActivityImpl logDB) {
		this.logDB = logDB;
	}
	
	
}