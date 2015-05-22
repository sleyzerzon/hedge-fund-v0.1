package com.onenow.execution;

public class NetworkConfig {
	
	public static NetworkService tsdb = getTSDB();
	public static NetworkService IBgatewayAWS = getGateway();
	public static NetworkService IBgatewayLocal = new NetworkService(	"", "",
																		"", "127.0.0.1", 4001,	
																		"");
	public static NetworkService rdbms = getRDBMS();
	
	public NetworkConfig() {
	}
	
	public static NetworkService getTSDB() {
		// INFLUX HOSTING
		//		Hostname: calvinklein-fluxcapacitor-1.c.influxdb.com (45.55.169.140)
		//		API Ports: 8086 (HTTP) and 8087 (HTTPS)
		//		Admin User: root
		//		Admin Password: b45547741dd1709b
		//		Admin Interface: http://calvinklein-fluxcapacitor-1.c.influxdb.com:8083
		//		And if you need some pointers on how to get started, check out our documentation:
		//		http://influxdb.com/docs/v0.8/introduction/getting_started.html
		//		setDB(InfluxDBFactory.connect("http://calvinklein-fluxcapacitor-1.c.influxdb.com:8086", "root", "b45547741dd1709b"));
		//		setDB(InfluxDBFactory.connect("http://tsdb.enremmeta.com:8086", "root", "root"));
		
		// ADMIN http://calvinklein-fluxcapacitor-1.c.influxdb.com:8083
		// list series
		// SELECT FIRST(price), LAST(price), MIN(price), MAX(price), SUM(price) FROM "BBY-STOCK-TRADED-IB-HISTORICAL" WHERE time > '2015-05-08' AND time < '2015-05-09' GROUP BY time(60m)
		// count(), min(), max(), mean(), mode(), median(), distinct(), percentile(), histogram(), derivative(), sum(), stddev(), first(), last()
		NetworkService net = new NetworkService(	"root", "b45547741dd1709b",
													"http", "calvinklein-fluxcapacitor-1.c.influxdb.com", 8086,
													"");
		return net;
	}

	public static NetworkService getGateway() {
	    // 		default Trader Work Station port: 7496 
	    // 		default IB Gateway port: 4001
		// 		getController().connect("127.0.0.1", 4001, 0, null);  // app port 7496

//		NetworkService net = new NetworkService(	"", "",
//				"", "127.0.0.1", 4001,
//				"");

		// ec2-52-4-110-54.compute-1.amazonaws.com
		// 7462
		
		NetworkService net = new NetworkService(	"", "",
													"", "ec2-54-86-144-36.compute-1.amazonaws.com", 4001,
													"");

		//		Note that you do not need an IB account to try out IBController, as you can use the IB demo account (username edemo, password demouser).
		return net;
	}
	
	public static NetworkService getRDBMS() {
		// 		hedge-fund-dev-free.c38uxrs0s2gx.us-east-1.rds.amazonaws.com
		// 		CREATE TABLE urler(id INT UNSIGNED NOT NULL AUTO_INCREMENT,author VARCHAR(63) NOT NULL,message TEXT,PRIMARY KEY (id))
		//		String driver = "jdbc:mysql://";
		//		String endpoint = "hedge-fund-dev-free.c38uxrs0s2gx.us-east-1.rds.amazonaws.com";
		//		String port = ":3306/";
		//		String db = "core_dev_free";
		//		String url = driver + endpoint + port + db;

		NetworkService net = new NetworkService(	"pablo0000", "pablo777",
													"jdbc:mysql", "hedge-fund-dev-free.c38uxrs0s2gx.us-east-1.rds.amazonaws.com", 3306,
													"core_dev_free");
		
		return net;		
	}

}
