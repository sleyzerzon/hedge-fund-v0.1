package com.onenow.analyst;

import java.util.concurrent.TimeUnit;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Serie;

public class TimeSeriesDatabase {

	public TimeSeriesDatabase() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String [] argv) throws Exception {
		InfluxDB influxDB = InfluxDBFactory.connect("http://tsdb.enremmeta.com:8086", "root", "root");

		influxDB.createDatabase("aTimeSeries");

		Serie serie1 = new Serie.Builder("serie2Name")
		            .columns("column1", "column2")
		            .values(System.currentTimeMillis(), 1)
		            .values(System.currentTimeMillis(), 2)
		            .build();
		Serie serie2 = new Serie.Builder("serie2Name")
		            .columns("column1", "column2")
		            .values(System.currentTimeMillis(), 1)
		            .values(System.currentTimeMillis(), 2)
		            .build();
		influxDB.write("aTimeSeries", TimeUnit.MILLISECONDS, serie1, serie2);
	}

}