package com.onenow.test;

import com.onenow.database.DatabaseSystemActivityImpl;

public class Test {

	public static void main(String[] args) {


		DatabaseSystemActivityImpl logDB = new DatabaseSystemActivityImpl();

		TestDatabase database = new TestDatabase(logDB);
		database.test();

		TestFinance finance = new TestFinance(logDB);
		finance.test();
		
		TestBroker broker = new TestBroker(logDB);
		broker.test();


	}

}
