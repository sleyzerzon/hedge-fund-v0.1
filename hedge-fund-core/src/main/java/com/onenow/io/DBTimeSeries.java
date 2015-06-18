package com.onenow.io;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;

import com.onenow.admin.NetworkConfig;
import com.onenow.admin.NetworkService;
import com.onenow.constant.DBname;

import java.util.Map;
import java.util.logging.Level;

import com.onenow.util.Watchr;

public class DBTimeSeries {
	
	public static InfluxDB influxDB = dbConnect();
	
	/**
	 * Default constructor connects to database
	 */
	public DBTimeSeries() {
	}
	
// INI
public static InfluxDB dbConnect() { 
	InfluxDB db  = null;
	boolean tryToConnect = true;
	while(tryToConnect) {		
		try {	
			tryToConnect = false;
			Watchr.log(Level.INFO, "CONNECTING TO TSDB...", "\n", "");
			NetworkService tsdbService = NetworkConfig.getTSDB();
			db = InfluxDBFactory.connect(	tsdbService.protocol+"://"+tsdbService.URI+":"+tsdbService.port, 
											tsdbService.user, tsdbService.pass);	
			dbCreateAndConnect();	
		} catch (Exception e) {
			tryToConnect = true;
			Watchr.log(Level.SEVERE, "...COULD NOT CONNECT TO TSDB: ", "\n", "");
			e.printStackTrace();
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e1) {
				// nothing to do
			}
		}
	} 
	Watchr.log(Level.INFO, "CONNECTED TO TSDB!");
	return db;
}

private static void dbCreateAndConnect() {
	try {
		influxDB.createDatabase(DBname.PRICE_DEVELOPMENT.toString());
		influxDB.createDatabase(DBname.PRICE_STAGING.toString());
		influxDB.createDatabase(DBname.PRICE_PRODUCTION.toString());

		influxDB.createDatabase(DBname.SIZE_DEVELOPMENT.toString());
		influxDB.createDatabase(DBname.SIZE_STAGING.toString());
		influxDB.createDatabase(DBname.SIZE_PRODUCTION.toString());

		influxDB.createDatabase(DBname.GREEK_DEVELOPMENT.toString());
		influxDB.createDatabase(DBname.GREEK_STAGING.toString());
		influxDB.createDatabase(DBname.GREEK_PRODUCTION.toString());

	} catch (Exception e) {
		// Throws exception if the DB already exists
	}
}

public static String getThoroughSelect(String table) {
	String s = "";
	s = 	"FIRST(" + table + ")" + ", " +			
			"LAST("  + table + ")" + ", " +			
			"DIFFERENCE(" + table + ")" + ", " +							
			"MIN("  + table + ")" + ", " +			
			"MAX(" +  table + ")" + ", " +			
			"MEAN(" + table + ")" + ", " +			
			"MODE(" + table + ")" + ", " +			
			"MEDIAN(" +  table + ")" + ", " +						
			"STDDEV(" +  table + ")" + ", " +						
			"DISTINCT(" + table + ")" + ", " +					
			"COUNT("  + table + ")" + ", " +			
			"SUM("  + table + ")" + ", " +
			"DERIVATIVE(" + table + ")" + " ";						  

	return s;
}

public static String extractQueryString(Map<String, Object> row, String col) {
	String s = "";
	try {
		s = row.get(col).toString();
	} catch (Exception e) {
		s = "-1.0";
		Watchr.log(Level.FINE, "TSDB NULL query result for ROW " + row + " " + row.get(col));
	}
	return s;
}



	
}