package com.onenow.broker;

public class BrokerMain {

	private static BrokerInteractive IB;
	private static BrokerActivityImpl broker;

	public static void main(String[] args) {

		setIB(new BrokerInteractive());
		setBroker(new BrokerActivityImpl(IB));
		getIB().initMarket("SPX", "20150319", 2100);

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
