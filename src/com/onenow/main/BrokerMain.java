package com.onenow.main;

import com.onenow.execution.BrokerActivityImpl;
import com.onenow.execution.BrokerInteractive;

public class BrokerMain {

	private static BrokerInteractive IB;
	private static BrokerActivityImpl broker;

	/**
	 * The principal process for all interactions with Wall Street and gateways thereof
	 * @param args
	 */
	public static void main(String[] args) {

		// create Interactive Brokers broker & start getting quotes
		try {
			setIB(new BrokerInteractive()); 
		} catch (Exception e) {
			System.out.println("COULD NOT CREATE INTERACTIVE BROKER\n");
			e.printStackTrace();
		}
		
		
		// set the overall broker: for when there are multiple brokers
		try {
			setBroker(new BrokerActivityImpl(getIB()));  
		} catch (Exception e) {
			System.out.println("COULD NOT SET MASTER BROKER\n");
			e.printStackTrace();
		}
		
	}

	// SET GET
	private static BrokerInteractive getIB() {
		return IB;
	}

	private static void setIB(BrokerInteractive iB) {
		IB = iB;
	}

	private static BrokerActivityImpl getBroker() {
		return broker;
	}

	private static void setBroker(BrokerActivityImpl broker) {
		BrokerMain.broker = broker;
	}
}
