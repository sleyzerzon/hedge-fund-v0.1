package com.onenow.test;

import com.onenow.admin.DatabaseSystemActivityImpl;
import com.sforce.ws.ConnectionException;

public class TestDatabase {
	
	private DatabaseSystemActivityImpl logDB;
	
	// CONSTRUCTOR
	public TestDatabase() {
		
	}
	
	public TestDatabase(DatabaseSystemActivityImpl logDB) {
		setLogDB(logDB);
	}

	// PUBLIC 
	public boolean test() {
		
		boolean success = testDB();
		
		String s="";
		if(success==true) {
			s = s + "\n" + "NO ERRORS FOUND==AT-ALL==: " + "TestDatabase";
		} else {
			s = s + "ERROR " + "TestDatabase";
		}
		System.out.println(s);
		try {
			getLogDB().newLog("TestDatabase", s);
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return success;
		
	}
	
	
	private boolean testDB() {
		
		DatabaseSystemActivityImpl db = new DatabaseSystemActivityImpl();
		
		System.out.println("\n\n" + "DB");
		db.toString();
		
//		if(!tx.getNetPremium().equals(39761.0)) {
//			System.out.println("ERROR premium " + tx.getNetPremium());
//			return false;
//		}
		return true;
	}

	private DatabaseSystemActivityImpl getLogDB() {
		return logDB;
	}

	private void setLogDB(DatabaseSystemActivityImpl logDB) {
		this.logDB = logDB;
	}
	
	
}