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
	String name = getLookup().getKey(inv, dataType);
	List<Serie> series = query(	DBname.PRICE.toString(), name,
									fromDate, toDate, sampling);
	return series;
}

public List<Serie> readSize(	Investment inv, String dataType,
								String fromDate, String toDate, String sampling) {
	String name = getLookup().getKey(inv, dataType);
	List<Serie> series = query(	DBname.SIZE.toString(), name,  
									fromDate, toDate, sampling);
	return series;
}

// QUERY
public List<Serie> query(String dbName, String serieName, String fromDate, String toDate, String sampling) {
	List<Serie> series = new ArrayList<Serie>();
	
	String query = 	"SELECT " +
						"FIRST(value)" + ", " +
						"LAST(value)" + ", " +
						"MIN(value)" + ", " +
						"MAX(value)" + ", " + 
						"SUM(value) " +  
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

//public List<Serie> querySize(String dbName, String serieName, String fromDate, String toDate, String sampling) {
//	List<Serie> series = new ArrayList<Serie>();
//	
//	// TODO
//	
//	return series;
//}


// QUERY CONVERSION
public List<Candle> queryToPriceCandles(List<Serie> series) {
	List<Candle> candles = new ArrayList<Candle>();
	
	Candle candle = new Candle();
//	candle.setClosePrice(closePrice);
//	candle.setOpenPrice(openPrice);
//	candle.setHighPrice(highPrice);
//	candle.setLowPrice(lowPrice);
//	candles.add(candle);
	
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
		System.out.println("\n");
		for (Map<String, Object> row : ser.getRows()) {
			for (String col : ser.getColumns()) {
				s = s + row.get(col) + "\t";
			}
			s = s + "\n";
		}
	}

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