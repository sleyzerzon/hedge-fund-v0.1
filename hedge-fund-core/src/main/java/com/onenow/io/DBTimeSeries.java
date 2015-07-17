package com.onenow.io;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Serie;

import com.onenow.admin.NetworkConfig;
import com.onenow.admin.NetworkService;
import com.onenow.constant.ColumnName;
import com.onenow.constant.DBQuery;
import com.onenow.constant.DBname;
import com.onenow.data.DataSampling;
import com.onenow.data.EventActivity;
import com.onenow.data.EventRequest;
import com.onenow.data.EventRequestRaw;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import com.onenow.util.TimeParser;
import com.onenow.util.Watchr;

public class DBTimeSeries {
	
	public static InfluxDB influxDB = dbConnect();
	
	private static boolean tryingToConnect = true;

	/**
	 * Default constructor connects to database
	 */
	public DBTimeSeries() {
	}
	
// INI
public static InfluxDB dbConnect() { 
	InfluxDB db  = null;
	tryingToConnect = true;
	
	while(tryingToConnect) {		
		try {	
			Watchr.log(Level.INFO, "CONNECTING TO TSDB...", "\n", "");
			NetworkService tsdbService = NetworkConfig.getTSDB();
			db = InfluxDBFactory.connect(	tsdbService.protocol+"://"+tsdbService.URI+":"+tsdbService.port, 
											tsdbService.user, tsdbService.pass);	
			dbCreateAndConnect();	
			tryingToConnect = false;
		} catch (Exception e) {
			tryingToConnect = true;
			Watchr.log(Level.SEVERE, "...COULD NOT CONNECT TO TSDB: ", "\n", "");
			e.printStackTrace();
			TimeParser.wait(10);
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
			
		boolean tryToWrite = true;

		Long before = TimeParser.getTimeMilisecondsNow();
		int count = 0;
		int maxCount = 10;
		while(tryToWrite) {
			try {
				DBTimeSeries.influxDB.write(dbName.toString(), TimeUnit.MILLISECONDS, serie);
				tryToWrite = false;
			} catch (Exception e) {
				tryToWrite = true;
				// e.printStackTrace();
				TimeParser.wait(1);
				count++;
			}
			if(count>maxCount) {
				Watchr.severe("TSDB WRITE: Failed after attempts");
				return;
			}
		}
		Long after = TimeParser.getTimeMilisecondsNow();
	
		Watchr.log(Level.INFO, 	"TSDB WRITE: "+ "<" + dbName + ">" + 
								" INTO " + "[" + serie.getName() + "]" + " " + "SERIE " + serie.toString() + " " +  
								"-ELAPSED " + (after-before) + "ms ", // "ELAPSED TOTAL " + (after-event.origin.start) + "ms ", // TODO: CloudWatch
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
	// DBname name = DBname.PRICE_STAGING;
			
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
public static List<Serie> query(DBname dbName, EventRequestRaw request) {

	List<Serie> series = new ArrayList<Serie>();

	try {
		String serieName = Lookup.getEventKey(request);
			 
		String query = getQuery(request, serieName);
			
		series = DBTimeSeries.influxDB.query(dbName.toString(), query, TimeUnit.MILLISECONDS);
		String log = "DATABASE <" + dbName + "> QUERY " + query + " RETURNED with length: " + series.size(); // + " RETURNED " + series.toString();
		Watchr.log(Level.INFO, log);  
		
	} catch (Exception e) {
		e.printStackTrace(); // some series don't exist or have data 
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


private static String getQuery(EventRequestRaw request, String serieName) {
	// select mean(PRICE) from AAPL-STOCK-TRADED-IB-REALTIME group by TIME(1h) WHERE time > -1h AND time < now() order asc
	// select mean(PRICE) from /^AAPL-STOCK.*/i  where $timeFilter group by time($interval) order asc	
	String query = "";
	
	query = query + DBQuery.SELECT.toString().toLowerCase() + " " + DBTimeSeries.getSelect(request) + " "; 						
	query = query + DBQuery.FROM.toString().toLowerCase() + " " + "\"" + serieName + "\"" + " ";
	query = query + DBQuery.GROUP.toString().toLowerCase() + " " + DBQuery.BY.toString() + " " + DBQuery.TIME.toString().toLowerCase() + 
					"(" + DataSampling.getGroupByTimeString(request.sampling) + ")" + " ";
	query = query + DBQuery.WHERE.toString().toLowerCase() + " " + getQueryTime(request);
	
	return query;
}

public static String getQueryTime(EventRequest request) {
	String s = "";
	
	if(request.toDashedDate!=null) {
		s = s + DBQuery.TIME.toString().toLowerCase() + " > " + "'" + request.fromDashedDate + "'" + " "; 
		s = s + DBQuery.AND.toString() + " ";
		s = s + DBQuery.TIME.toString().toLowerCase() + " < " + "'" + request.toDashedDate + "'" + " ";
	} else {
		s = s + DBQuery.TIME.toString().toLowerCase() + " > " + request.endPoint + " " + request.timeGap; 
	}
	
	return s;
}

public static String getSelect(EventRequestRaw request) {
	String s = "";
	s = s + request.dbQuery + "(" + request.columnName + ")"; 			
	return s;
}

public static String getThoroughSelect(String columnName) {
	String s = "";
	
	s = s + DBQuery.FIRST.toString() + "(" + columnName + ")" + ", "; 		//  1
	s = s + DBQuery.LAST.toString() + "("  + columnName + ")" + ", ";		//  2
	s = s + DBQuery.MIN.toString() + "("  + columnName + ")" + ", ";		//  3	
	s = s + DBQuery.MAX.toString() + "(" +  columnName + ")" + ", ";		//  4
	s = s + DBQuery.MEAN.toString() + "(" + columnName + ")" + ", ";		//  5
	s = s + DBQuery.MODE.toString() + "(" + columnName + ")" + ", ";		//  6
	s = s + DBQuery.MEDIAN.toString() + "(" +  columnName + ")" + ", ";		//  7		
	s = s + DBQuery.STDDEV.toString() + "(" +  columnName + ")" + ", ";		//  8			
	s = s + DBQuery.DISTINCT.toString() + "(" + columnName + ")" + ", ";	// 9		
	s = s + DBQuery.COUNT.toString() + "("  + columnName + ")" + ", ";		// 10
	s = s + DBQuery.SUM.toString() + "("  + columnName + ")";				// 11
	
	// "DIFFERENCE(" + columnName + ")" + ", " +							
	// "DERIVATIVE(" + columnName + ")" + " ";	  

	return s;
}

public static DBTimeIncrement individualSeriesToIncrements(Serie serie, String s) { 
	
	DBTimeIncrement increment = new DBTimeIncrement();;
	
	for (Map<String, Object> rowMap : serie.getRows()) {
				
		Integer i=0;
		
		for (String column : serie.getColumns()) {	// iterate columns to create candle

			s = s + rowMap.get(column) + "\t";
			// System.out.println("row " + row + " " + row.get(col)); // full row

			// Column 0 is time stamp
			if(i.equals(1)) {
				increment.first = new Double(extractQueryString(rowMap, column));
			}	
			i++;
		}
		s = s + "\n";
	}
	
	// Watchr.log(Level.FINEST, "INCREMENT FROM SERIE: " + increment.toString());
	return increment;
}

/**
 * Iterate through series JSON to acquire information about a single time increment 
 * @param serie
 * @param s
 * @return
 */
public static DBTimeIncrement thoroughSeriesToIncrements(Serie serie, String s) {
	
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
	
	// Watchr.log(Level.FINEST, "INCREMENT FROM SERIE: " + increment.toString());
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