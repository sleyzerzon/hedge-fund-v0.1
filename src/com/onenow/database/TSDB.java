package com.onenow.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Serie;

import com.onenow.finance.Investment;

public class TSDB {
	
	InfluxDB DB;
	Lookup lookup;

	public TSDB() {
		setLookup(new Lookup());
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

private void writePrice(Investment inv, String dataType, Long time, Double price) {
	String name = getLookup().getKey(inv, dataType);
	Serie serie = new Serie.Builder(name)
	.columns("Timestamp", "Price ($)")
	.values(time, price)
	.build();
	getDB().write(DBname.PRICE.toString(), TimeUnit.MILLISECONDS, serie);
}

private void writeSize(Investment inv, String dataType, Long time, Integer size) {
	String name = getLookup().getKey(inv, dataType);
	Serie serie = new Serie.Builder(name)
	.columns("Timestamp (ms)", "Size (#)")
	.values(time, size)
	.build();
	getDB().write(DBname.PRICE.toString(), TimeUnit.MILLISECONDS, serie);
}

private List<Serie> dbQuery(String dbName, String serieName, String window) {
	List<Serie> series = new ArrayList<Serie>();
	
	String query = 	"select * from " +
					serieName + 
					"where time > " +
					"now() - " + window; 
	
	series = getDB().query(	dbName, query,
							TimeUnit.MILLISECONDS);
	return series;
}

private String queryToString(List<Serie> series) {
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
	System.out.println(series.size() + " entries");
	return s;
}

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