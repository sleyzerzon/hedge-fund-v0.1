package com.onenow.analyst;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Serie;

public class InfluxWriteSample {

	public InfluxWriteSample() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] argv) throws Exception {
		InfluxDB influxDB = InfluxDBFactory.connect(
				"http://tsdb.enremmeta.com:8086", "root", "root");

		// influxDB.createDatabase("aTimeSeries");
		for (int i = -10; i < 10; i++) {
			Serie serie1 = new Serie.Builder("serie2Name")
					.columns("column1", "column2")
					.values(System.currentTimeMillis() + 3600000 * i, 1)
					.values(System.currentTimeMillis() + 3600000 * i, 2)
					.build();
			Serie serie2 = new Serie.Builder("serie2Name")
					.columns("column1", "column2")
					.values(System.currentTimeMillis() + 3600000 * i, 1)
					.values(System.currentTimeMillis() + 3600000 * i, 2)
					.build();
			influxDB.write("aTimeSeries", TimeUnit.MILLISECONDS, serie1, serie2);
		}
		List<Serie> sers = influxDB
				.query("aTimeSeries",
						"select * from serie2Name where time > now() - 3h",
						TimeUnit.MILLISECONDS);
		
		
		for (Serie ser : sers) {
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
		}	}
}