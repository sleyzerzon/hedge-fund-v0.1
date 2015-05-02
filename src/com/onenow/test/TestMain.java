package com.onenow.test;

import com.onenow.admin.DatabaseSystemActivityImpl;

public class TestMain {

	public static void main(String[] args) {

		DatabaseSystemActivityImpl logDB = new DatabaseSystemActivityImpl();

		if(logDB.isDBLive()) {
			System.out.println("TESTING DATABASE");
			TestDatabase database = new TestDatabase(logDB);
			database.test();
		}
		
		TestFinance finance = new TestFinance(logDB);
		finance.test();
		
		TestBroker broker = new TestBroker(logDB);
		broker.test();

	}

}
