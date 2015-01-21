package com.onenow.test;

public class Test {

	public static void main(String[] args) {
		
		TestFinance finance = new TestFinance();
		finance.test();
		
		TestBroker broker = new TestBroker();
		broker.test();

		TestDatabase database = new TestDatabase();
		database.test();

	}

}
