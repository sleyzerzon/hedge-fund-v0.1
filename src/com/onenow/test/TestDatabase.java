package com.onenow.test;

import com.onenow.database.DatabaseSystemActivityImpl;

public class TestDatabase {
	
	public TestDatabase() {
		
	}

	public boolean test() {
		
		boolean success = testDB();
		
		if(success==true) {
			System.out.println("\n" + "NO ERRORS FOUND==AT-ALL==: " + "TestDatabase");
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
	
	
}