package com.onenow.io;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Serie;

import com.onenow.admin.NetworkConfig;
import com.onenow.admin.NetworkService;
import com.onenow.constant.ColumnName;
import com.onenow.constant.DBname;
import com.onenow.constant.SamplingRate;
import com.onenow.data.DataSampling;
import com.onenow.data.EventRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
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

/** 
 * Each environment has its own database
 * @return
 */
public static DBname getPriceDatabaseName() {
	return DBname.PRICE_STAGING;
}

/** 
 * Each environment has its own database
 * @return
 */
public static DBname getSizeDatabaseName() {
	return DBname.SIZE_STAGING;
}

/** 
 * Each environment has its own database
 * @return
 */
public static DBname getGreekDatabaseName() {
	return DBname.GREEK_STAGING;
}



/**
 * Price queries per http://influxdb.com/docs/v0.7/api/aggregate_functions.html
 * @param dbName
 * @param serieName
 * @param sampling
 * @param fromDate
 * @param toDate
 * @return
 */
public static List<Serie> query(ColumnName columnName, String dbName, EventRequest request) {
	
	String serieName = Lookup.getEventKey(request);

	List<Serie> series = new ArrayList<Serie>();
	 
	String query = 	"SELECT " + DBTimeSeries.getThoroughSelect(columnName.toString()) + " " + 						
					"FROM " + "\"" + serieName + "\" " +
					"GROUP BY " +
						"time" + "(" + DataSampling.getGroupByTimeString(request.sampling) + ") " + 
					// "FILL(0) " +
					"WHERE " +
					"time > " + "'" + request.fromDashedDate + "' " + 
					"AND " +
					"time < " + "'" + request.toDashedDate + "' "; 
					
	
	// TODO The where clause supports comparisons against regexes, strings, booleans, floats, integers, and the times listed before. 
	// Comparators include = equal to, > greater than, < less than, <> not equal to, =~ matches against, !~ doesn’t match against. 
	// You can chain logic together using and and or and you can separate using ( and )
	
	// TODO: SELECT PERCENTILE(column_name, N) FROM series_name group by time(10m) ...
	// TODO: SELECT HISTOGRAM(column_name) FROM series_name ...
	// TODO: SELECT TOP(column_name, N) FROM series_name ...
	// TODO: SELECT BOTTOM(column_name, N) FROM series_name ...
	
	try {
		series = DBTimeSeries.influxDB.query(dbName, query, TimeUnit.MILLISECONDS);
		// Watchr.log(Level.INFO, query + " RETURNED " + series.toString());  
	} catch (Exception e) {
		// e.printStackTrace(); // some series don't exist or have data 
	}
	return series;
}


public static String getThoroughSelect(String columnName) {
	String s = "";
	s = 	"FIRST(" + columnName + ")" + ", " +			
			"LAST("  + columnName + ")" + ", " +			
			"DIFFERENCE(" + columnName + ")" + ", " +							
			"MIN("  + columnName + ")" + ", " +			
			"MAX(" +  columnName + ")" + ", " +			
			"MEAN(" + columnName + ")" + ", " +			
			"MODE(" + columnName + ")" + ", " +			
			"MEDIAN(" +  columnName + ")" + ", " +						
			"STDDEV(" +  columnName + ")" + ", " +						
			"DISTINCT(" + columnName + ")" + ", " +					
			"COUNT("  + columnName + ")" + ", " +			
			"SUM("  + columnName + ")" + ", " +
			"DERIVATIVE(" + columnName + ")" + " ";						  

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