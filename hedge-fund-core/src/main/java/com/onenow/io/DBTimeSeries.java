package com.onenow.io;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Serie;

import com.onenow.admin.NetworkConfig;
import com.onenow.admin.NetworkService;
import com.onenow.constant.ColumnName;
import com.onenow.constant.DBname;
import com.onenow.data.DataSampling;
import com.onenow.data.EventActivity;
import com.onenow.data.EventRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import com.onenow.util.TimeParser;
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



public static void writeThread(final EventActivity event, final Serie serie, final DBname dbName) {
	
	new Thread () {
		@Override public void run () {

		Long before = TimeParser.getTimestampNow();
		try {
			DBTimeSeries.influxDB.write(dbName.toString(), TimeUnit.MILLISECONDS, serie);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Long after = TimeParser.getTimestampNow();
	
		Watchr.log(Level.INFO, 	"TSDB WRITE: "+ "<" + dbName + ">" + " " + 
								event.toString() + " " +  
								"-ELAPSED " + (after-before) + "ms ",
								// "ELAPSED TOTAL " + (after-event.origin.start) + "ms ", // TODO: CloudWatch
								"\n", "");
		}
	}.start();
	
}


/** 
 * Each environment has its own database
 * @return
 */
public static DBname getPriceDatabaseName() {
	
	DBname name = DBname.PRICE_DEVELOPMENT;
	
	if(!NetworkConfig.isMac()) {
		name = DBname.PRICE_STAGING;
	} 
	return name;
}

/** 
 * Each environment has its own database
 * @return
 */
public static DBname getSizeDatabaseName() {
	
	DBname name = DBname.SIZE_DEVELOPMENT;
	
	if(!NetworkConfig.isMac()) {
		name = DBname.SIZE_STAGING;
	} 
	return name;

}

/** 
 * Each environment has its own database
 * @return
 */
public static DBname getGreekDatabaseName() {

	DBname name = DBname.GREEK_DEVELOPMENT;

	if(!NetworkConfig.isMac()) {
		name = DBname.GREEK_STAGING;
	} 
	
	return name;

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
public static List<Serie> query(ColumnName columnName, DBname dbName, EventRequest request) {

	List<Serie> series = new ArrayList<Serie>();

	try {
		String serieName = Lookup.getEventKey(request);
			 
		String query = getQuery(columnName, request, serieName);
						
		Watchr.log(Level.FINEST, "DATABASE " + dbName + " QUERY " + query + " RETURNED " + series.toString());  
		series = DBTimeSeries.influxDB.query(dbName.toString(), query, TimeUnit.MILLISECONDS);
	} catch (Exception e) {
		// e.printStackTrace(); // some series don't exist or have data 
	}
	return series;
}

// TODO The where clause supports comparisons against regexes, strings, booleans, floats, integers, and the times listed before. 
// Comparators include = equal to, > greater than, < less than, <> not equal to, =~ matches against, !~ doesn’t match against. 
// You can chain logic together using and and or and you can separate using ( and )

// TODO: SELECT PERCENTILE(column_name, N) FROM series_name group by time(10m) ...
// TODO: SELECT HISTOGRAM(column_name) FROM series_name ...
// TODO: SELECT TOP(column_name, N) FROM series_name ...
// TODO: SELECT BOTTOM(column_name, N) FROM series_name ...
//"FILL(0) " +


private static String getQuery(ColumnName columnName, EventRequest request, String serieName) {
	
	String query = "";
	
	query = query + "SELECT " + DBTimeSeries.getThoroughSelect(columnName.toString()) + " "; 						
	query = query + "FROM " + "\"" + serieName + "\" ";
	query = query + "GROUP BY " + "time" + "(" + DataSampling.getGroupByTimeString(request.sampling) + ") ";
	query = query + "WHERE " + getQueryTime(request);
	
	return query;
}

public static String getQueryTime(EventRequest request) {
	String s = "";
	
	if(request.toDashedDate!=null) {
		s = s + "time > " + "'" + request.fromDashedDate + "' "; 
		s = s + "AND ";
		s = s + "time < " + "'" + request.toDashedDate + "' ";
	} else {
		s = s + "time > " + request.endPoint + " " + request.timeGap; 
	}
	
	return s;
}

public static String getThoroughSelect(String columnName) {
	String s = "";
	s = 	"FIRST(" + columnName + ")" + ", " +			//  1
			"LAST("  + columnName + ")" + ", " +			//  2
			// "DIFFERENCE(" + columnName + ")" + ", " +							
			"MIN("  + columnName + ")" + ", " +				//  3	
			"MAX(" +  columnName + ")" + ", " +				//  4
			"MEAN(" + columnName + ")" + ", " +				//  5
			"MODE(" + columnName + ")" + ", " +				//  6
			"MEDIAN(" +  columnName + ")" + ", " +			//  7		
			"STDDEV(" +  columnName + ")" + ", " +			//  8			
			"DISTINCT(" + columnName + ")" + ", " +			// 9		
			"COUNT("  + columnName + ")" + ", " +			// 10
			"SUM("  + columnName + ")";						// 11
			// "DERIVATIVE(" + columnName + ")" + " ";	    // 12			  

	return s;
}

/**
 * Iterate through series JSON to acquire information about a single time increment 
 * @param serie
 * @param s
 * @return
 */
public static DBTimeIncrement seriesToIncrements(Serie serie, String s) {
	
	DBTimeIncrement increment = new DBTimeIncrement();;
	
	for (Map<String, Object> rowMap : serie.getRows()) {
				
		Integer i=0;
		
		for (String column : serie.getColumns()) {	// iterate columns to create candle
			s = s + rowMap.get(column) + "\t";
			// System.out.println("row " + row + " " + row.get(col)); // full row
			if(i.equals(1)) {
				increment.first = new Double(extractQueryString(rowMap, column));
			}
			if(i.equals(2)) {
				increment.last = new Double(extractQueryString(rowMap, column));
			}
			if(i.equals(3)) {
				increment.min = new Double(extractQueryString(rowMap, column));
			}
			if(i.equals(4)) {
				increment.max = new Double(extractQueryString(rowMap, column));
			}
			if(i.equals(5)) {
				increment.mean = new Double(extractQueryString(rowMap, column));
			}
			if(i.equals(6)) {
				increment.mode = new Double(extractQueryString(rowMap, column));
			}
			if(i.equals(7)) {
				increment.median = new Double(extractQueryString(rowMap, column));
			}
			if(i.equals(8)) {
				increment.stddev = new Double(extractQueryString(rowMap, column));
			}
			if(i.equals(9)) {
				increment.distinct = new Double(extractQueryString(rowMap, column));
			}
			if(i.equals(10)) {
				increment.count = new Double(extractQueryString(rowMap, column));
			}
			if(i.equals(11)) {
				increment.sum = new Double(extractQueryString(rowMap, column));
			}
			
			// increment.difference = new Double(extractQueryString(rowMap, column));
			// increment.derivative = new Double(extractQueryString(rowMap, column));
			
			i++;
		}
		
		s = s + "\n";
	}
	
	Watchr.log(Level.FINEST, "INCREMENT FROM SERIE: " + increment.toString());
	return increment;
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