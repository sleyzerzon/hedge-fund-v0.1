package com.onenow.execution;

public class BrokerMain {

	private static BrokerInteractive IB;
	private static BrokerActivityImpl broker;

	public static void main(String[] args) {
		// 1 100 Max rate of messages per second has been exceeded:max=50 rec=138 (1)

		try {
			setIB(new BrokerInteractive()); // create Interactive Brokers
		} catch (Exception e) {
			System.out.println("COULD NOT CREATE BROKER\n");
			e.printStackTrace();
		}
		
		try {
			setBroker(new BrokerActivityImpl(getIB())); // run Broker 
		} catch (Exception e) {
			System.out.println("COULD NOT EXECUTE BROKER\n");
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
