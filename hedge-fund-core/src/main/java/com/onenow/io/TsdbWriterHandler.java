package com.onenow.io;

import org.influxdb.InfluxDB;

import com.lmax.disruptor.EventHandler;

public class TsdbWriterHandler implements EventHandler<TsdbEvent> {

	private final InfluxDB influxDb;

	private final String db;

	public TsdbWriterHandler(InfluxDB influxDb, String db) {
		super();
		this.influxDb = influxDb;
		this.db = db;
	}

	public void onEvent(TsdbEvent event, long sequence, boolean endOfBatch) {
		// TODO retry
		influxDb.write(db, event.getUnit(), event.getSerie());
	}
}
