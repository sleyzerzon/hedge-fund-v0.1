package com.onenow.research;

import java.util.List;
import java.util.Map;
import java.util.Random;
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
		//SELECT FIRST(column1),LAST(column1),MIN(column1),MAX(column1) FROM serie2Name WHERE time > '2015-02-19' AND time < '2015-02-21' GROUP BY time(15m)
		long timeNow = System.currentTimeMillis();
		Random random  = new Random(timeNow);
		
		for (int i = -15000; i < 15000; i++) {
			long ts = timeNow + 5*1000 * i;
			Serie serie1 = new Serie.Builder("TimeSeries")
					.columns("time", "value").values(ts, random.nextInt(100)).
					values(ts, random.nextInt(1000)).build();
			influxDB.write("TEST", TimeUnit.MILLISECONDS, serie1);
			if (i % 100 == 0) {
				System.out.println ("Wrote " + (i + 15000) + " items");
			}
		}

		List<Serie> sers = influxDB
				.query("TEST",
						"SELECT FIRST(value),LAST(value),MIN(value),MAX(value),SUM(value) FROM TimeSeries WHERE time > '2015-02-21' AND time < '2015-02-23' GROUP BY time(15m)",
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
		}

		// System.out.println(sers.size() + " entries");
	}
}