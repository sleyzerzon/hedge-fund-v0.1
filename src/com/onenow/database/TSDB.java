package com.onenow.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Serie;

import com.onenow.analyst.Candle;
import com.onenow.finance.Investment;
import com.onenow.investor.SamplingRate;

public class TSDB {
	
	InfluxDB DB;
	Lookup lookup;

	public TSDB() {
		setLookup(new Lookup());
		dbConnect();
//		dbCreate();
	}


private void dbConnect() { 
	try {
		setDB(InfluxDBFactory.connect("http://tsdb.enremmeta.com:8086", "root", "root"));
	} catch (Exception e) {
		System.out.println("COULD NOT CONNECT TO DB\n");
		e.printStackTrace();
		return;
	}
}

private void dbCreate() {
	getDB().createDatabase(DBname.PRICE.toString());
	getDB().createDatabase(DBname.SIZE.toString());
}

// WRITE
public void writePrice(Long time, Investment inv, String dataType, Double price) {
	String name = getLookup().getKey(inv, dataType);
	Serie serie = new Serie.Builder(name)
	.columns("time", "price")
	.values(time, price)
	.build();
	System.out.println("WRITE " + DBname.PRICE.toString() + " " + serie + "\n");
	getDB().write(DBname.PRICE.toString(), TimeUnit.MILLISECONDS, serie);
}

public void writeSize(Long time, Investment inv, String dataType, Integer size) {
	String name = getLookup().getKey(inv, dataType);
	Serie serie = new Serie.Builder(name)
	.columns("time", "size")
	.values(time, size)
	.build();
	System.out.println("WRITE " + DBname.SIZE.toString() + " " + serie);
	getDB().write(DBname.SIZE.toString(), TimeUnit.MILLISECONDS, serie);
}

// READ
public List<Serie> readPrice(	Investment inv, String dataType,
								String fromDate, String toDate, String sampling) {

	List<Serie> series = new ArrayList<Serie>();
	String name = getLookup().getKey(inv, dataType);
	
	series = queryPrice(	DBname.PRICE.toString(), name, fromDate, toDate, sampling);
	return series;
}

public List<Serie> readSize(	Investment inv, String dataType,
								String fromDate, String toDate, String sampling) {

	List<Serie> series = new ArrayList<Serie>();
	String name = getLookup().getKey(inv, dataType);
	
	series = querySize(	DBname.SIZE.toString(), name,  fromDate, toDate, sampling);
	return series;
}

// PRICE
public List<Serie> queryPrice(String dbName, String serieName, String fromDate, String toDate, String sampling) {
	List<Serie> series = new ArrayList<Serie>();
	
	String query = 	"SELECT " +
						"FIRST(price)" + ", " +
						"LAST(price)" + ", " +
						"MIN(price)" + ", " +
						"MAX(price)" + ", " + 
						"SUM(price) " +  
					"FROM " + "\"" + serieName + "\" " +
					"WHERE " +
						"time > " + "'" + fromDate + "' " + 
						"AND " +
						"time < " + "'" + toDate + "' " + 
					"GROUP BY " +
						"time" + "(" + getDBSamplingString(sampling) + ")";
					
	try {
//		System.out.println("QUERY " + query);
		series = getDB().query(	dbName, query, TimeUnit.MILLISECONDS);
	} catch (Exception e) {
//		e.printStackTrace(); some time series don't exist or have data
	}
	
	return series;
}

public List<Candle> queryToPriceCandles(List<Serie> series) {
	List<Candle> candles = new ArrayList<Candle>();
	
	
	String s="";
	for (Serie ser : series) {
		for (String col : ser.getColumns()) {
			s = s + col + "\t";
			System.out.println("column " + col);
		}
		s = s + "\n";
		for (Map<String, Object> row : ser.getRows()) {
			Candle candle = new Candle();
			Integer i=0;
			for (String col : ser.getColumns()) {
				s = s + row.get(col) + "\t";
				System.out.println("row " + row + " " + row.get(col));
				if(i.equals(1)) {
					candle.setOpenPrice(new Double(row.get(col).toString()));
				}
				if(i.equals(2)) {
					candle.setClosePrice(new Double(row.get(col).toString()));
				}
				if(i.equals(3)) {
					candle.setLowPrice(new Double(row.get(col).toString()));
				}
				if(i.equals(4)) {
					candle.setHighPrice(new Double(row.get(col).toString()));
				}
				i++;
			}
			s = s + "\n";
			candles.add(candle);
		}
	}
	System.out.println(s);	
	return candles;
}

// SIZE
public List<Serie> querySize(String dbName, String serieName, String fromDate, String toDate, String sampling) {
	List<Serie> series = new ArrayList<Serie>();
	
	String query = 	"SELECT " +
						"FIRST(size)" + ", " +
						"LAST(size)" + ", " +
						"MIN(size)" + ", " +
						"MAX(size)" + ", " + 
						"SUM(size) " +  
					"FROM " + "\"" + serieName + "\" " +
					"WHERE " +
						"time > " + "'" + fromDate + "' " + 
						"AND " +
						"time < " + "'" + toDate + "' " + 
					"GROUP BY " +
						"time" + "(" + getDBSamplingString(sampling) + ")";
					
	try {
//		System.out.println("QUERY " + query);
		series = getDB().query(	dbName, query, TimeUnit.MILLISECONDS);
	} catch (Exception e) {
//		e.printStackTrace(); some time series don't exist or have data
	}
	
	return series;
}

public List<Integer> queryToTotalSize(List<Serie> series) {
	List<Integer> size = new ArrayList<Integer>();
	
//	Integer num = 0;
//	size.add(num);
	
	String s="";
	for (Serie ser : series) {
		for (String col : ser.getColumns()) {
			s = s + col + "\t";
		}
		s = s + "\n";
		for (Map<String, Object> row : ser.getRows()) {
			for (String col : ser.getColumns()) {
				s = s + row.get(col) + "\t";
			}
			s = s + "\n";
		}
	}
	System.out.println(s);
	return size;
}

public String queryToString(List<Serie> series) {
	String s = "";
	
	for (Serie ser : series) {
		for (String col : ser.getColumns()) {
			System.out.print(col + "\t");
		}
		System.out.println();
		for (Map<String, Object> row : ser.getRows()) {
			for (String col : ser.getColumns()) {
				System.out.print(row.get(col) + "\t");
			}
			System.out.println();
		}
	}
//	System.out.println(series.size() + " entries");
	return s;
}


//SCALPING 5, 15, 60min
//SWINGING 60, 240, daily
//TREND 4hr, daily, weekly
private String getDBSamplingString(String samplingRate) {
	String dbSamplingRate="";
	if(samplingRate.equals("SCALPSHORT")) {
		return "5m";
	}
	if(samplingRate.equals("SCALPMEDIUM")) {
		return "15m";
	}
	if(samplingRate.equals("SCALPLONG")) {
		return "60m";
	}
	if(samplingRate.equals("SWINGSHORT")) {
		return "60m";
	}
	if(samplingRate.equals("SWINGMEDIUM")) {
		return "4h";
	}
	if(samplingRate.equals("SWINGLONG")) {
		return "1d";
	}
	if(samplingRate.equals("TRENDSHORT")) {
		return "4h";
	}
	if(samplingRate.equals("TRENDMEDIUM")) {
		return "1d";
	}
	if(samplingRate.equals("TRENDLONG")) {
		return "1w";
	}
	return dbSamplingRate;
}
// TEST


// PRINT

// SET GET
private InfluxDB getDB() {
	return DB;
}

private void setDB(InfluxDB dB) {
	DB = dB;
}


private Lookup getLookup() {
	return lookup;
}


private void setLookup(Lookup lookup) {
	this.lookup = lookup;
}


}