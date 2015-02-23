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

public class TSDB {
	
	InfluxDB DB;
	Lookup lookup;

	public TSDB() {
		setLookup(new Lookup());
		dbConnect();
//		dbCreate();
	}


private void dbConnect() { 
	// http://tsdb.enremmeta.com:8083/ 
	// user: root
	// pass: root
	setDB(InfluxDBFactory.connect("http://tsdb.enremmeta.com:8086", "root", "root"));
}

private void dbCreate() {
	getDB().createDatabase(DBname.PRICE.toString());
	getDB().createDatabase(DBname.SIZE.toString());
}

// WRITE
public void writePrice(Long time, Investment inv, String dataType, Double price) {
	String name = getLookup().getKey(inv, dataType);
	Serie serie = new Serie.Builder(name)
	.columns("Timestamp", "Price ($)")
	.values(time, price)
	.build();
	getDB().write(DBname.PRICE.toString(), TimeUnit.MILLISECONDS, serie);
}

public void writeSize(Long time, Investment inv, String dataType, Integer size) {
	String name = getLookup().getKey(inv, dataType);
	Serie serie = new Serie.Builder(name)
	.columns("Timestamp (ms)", "Size (#)")
	.values(time, size)
	.build();
	getDB().write(DBname.PRICE.toString(), TimeUnit.MILLISECONDS, serie);
}

// READ
public List<Serie> readPrice(	Investment inv, String dataType,
								String fromDate, String toDate, String sampling) {
	String name = getLookup().getKey(inv, dataType);
	List<Serie> series = queryPrice(	DBname.PRICE.toString(), name,
									fromDate, toDate, sampling);
	return series;
}

public List<Serie> readSize(	Investment inv, String dataType,
								String fromDate, String toDate, String sampling) {
	String name = getLookup().getKey(inv, dataType);
	List<Serie> series = querySize(	DBname.SIZE.toString(), name,  
									fromDate, toDate, sampling);
	return series;
}

// QUERY
public List<Serie> queryPrice(String dbName, String serieName, String fromDate, String toDate, String sampling) {
	List<Serie> series = new ArrayList<Serie>();
	
	String query = 	"SELECT " +
						"FIRST(value)" + ", " +
						"LAST(value)" + ", " +
						"MIN(value)" + ", " +
						"MAX(value)" + ", " + 
					"FROM " + serieName + 
					"WHERE " +
						"time > " + "'" + fromDate + "'" +
						"time < " + "'" + toDate + "'" + 
					"GROUP BY " +
						"time" + "(" + sampling + ")";
					
	series = getDB().query(	dbName, query,
							TimeUnit.MILLISECONDS);
	return series;
}

public List<Serie> querySize(String dbName, String serieName, String fromDate, String toDate, String sampling) {
	List<Serie> series = new ArrayList<Serie>();
	
	// TODO
	
	return series;
}


// QUERY CONVERSION
public List<Candle> queryToPriceCandles(List<Serie> series) {
	List<Candle> candles = new ArrayList<Candle>();
	String s="";
	
	for (Serie ser : series) {
		for (String col : ser.getColumns()) {
			s = s + col + "\t";
		}
		s = s + "\n";
		System.out.println("\n");
		for (Map<String, Object> row : ser.getRows()) {
			for (String col : ser.getColumns()) {
				s = s + row.get(col) + "\t";
			}
			s = s + "\n";
		}
	}
	
	return candles;
}

public Integer queryToTotalSize(List<Serie> series) {
	Integer size = 0;
	
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