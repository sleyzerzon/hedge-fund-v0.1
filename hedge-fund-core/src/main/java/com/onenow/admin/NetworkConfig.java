package com.onenow.admin;

import com.onenow.constant.Topology;


public class NetworkConfig {
	
	private static String OS = System.getProperty("os.name").toLowerCase();
	
	private static NetworkService tsdbInfluxSAAS = new NetworkService(	"root", "b45547741dd1709b",
																		"http", "calvinklein-fluxcapacitor-1.c.influxdb.com", "8086",
																		"");
	private static NetworkService gatewayAWSIB = new NetworkService(	"", "",
																		"", "ec2-54-86-144-36.compute-1.amazonaws.com", "4001",
																		"");
	private static NetworkService gatewayLocalIB = new NetworkService(	"", "",
																		"", "127.0.0.1", "4001",	
																		"");
	private static NetworkService twsLocalIB = new NetworkService(	"", "",
																		"", "127.0.0.1", "7496",	
																		"");
	private static NetworkService accountPaperIB = new NetworkService(	"pablo0000", "pablo777",
																		"", "", "",	
																		"");
	private static NetworkService accountDemoIB = new NetworkService(	"edemo", "demouser",
																		"", "", "",	
																		"");	
	private static NetworkService rdsAWS = new NetworkService(			"pablo0000", "pablo777",
																		"jdbc:mysql", "hedge-fund-dev-free.c38uxrs0s2gx.us-east-1.rds.amazonaws.com", "3306",
																		"core_dev_free");
	private static NetworkService elastiCache = new NetworkService(		"", "",
																		"", "hedgefundcache.cusyjv.cfg.use1.cache.amazonaws.com", "11211",
																		"");

	
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
		// telnet ec2-52-4-110-54.compute-1.amazonaws.com 4001
		
		NetworkService net = tsdbInfluxSAAS;
		
		return net;
	}

	public static NetworkService getGateway(Topology env) {

		// NetworkService net = gatewayAWSIB;
		
		NetworkService net = gatewayLocalIB;
		
		if(env.equals(Topology.LOCAL)) {
			 net = gatewayLocalIB;
		}
			
		return net;
	}
	
	public static NetworkService getRDBMS() {
		// 		CREATE TABLE urler(id INT UNSIGNED NOT NULL AUTO_INCREMENT,author VARCHAR(63) NOT NULL,message TEXT,PRIMARY KEY (id))

		NetworkService net = rdsAWS;
		
		return net;		
	}
	
	public static NetworkService getCache() {
		
		NetworkService net = elastiCache;
		
		return net;
	}

	public static boolean isWindows() { 
		return (OS.indexOf("win") >= 0);
	}
 
	public static boolean isMac() {
		return (OS.indexOf("mac") >= 0);
	}
 
	public static boolean isUnix() {
		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
	}
 
	public static boolean isSolaris() {
		return (OS.indexOf("sunos") >= 0);
	}		

}
