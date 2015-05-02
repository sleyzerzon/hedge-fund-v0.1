package com.onennow.main;

import com.onenow.admin.DatabaseSystemActivityImpl;
import com.onenow.test.TestBroker;
import com.onenow.test.TestDatabase;
import com.onenow.test.TestFinance;
import com.onenow.test.TestUtil;

public class TestMain {

	public static void main(String[] args) {

		DatabaseSystemActivityImpl logDB = new DatabaseSystemActivityImpl();

		if(logDB.isDBLive()) {
			System.out.println("TESTING DATABASE");
			TestDatabase database = new TestDatabase(logDB);
			database.test();
		}

		System.out.println("TESTING FINANCE");
		TestFinance financeTester = new TestFinance(logDB);
//		financeTester.test();
		
		System.out.println("TESTING BROKER");
		TestBroker brokerTester = new TestBroker(logDB);
//		brokerTester.test();
		
		System.out.println("TESTING UTIL");
		TestUtil utilTester = new TestUtil(logDB);
		utilTester.test();

	}

}
