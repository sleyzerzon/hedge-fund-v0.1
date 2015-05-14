package com.onenow.database;

import java.util.concurrent.TimeUnit;

import org.influxdb.dto.Serie;

import com.lmax.disruptor.RingBuffer;

public class TsdbWriterFrontend {

	private final RingBuffer<TsdbEvent> ringBuffer;

	public TsdbWriterFrontend(RingBuffer<TsdbEvent> ringBuffer) {
		this.ringBuffer = ringBuffer;
	}

	public void write(Serie s, TimeUnit u) {
		long sequence = ringBuffer.next();
		try {
			TsdbEvent event = ringBuffer.get(sequence);
			event.setSerie(s);
			event.setUnit(u);
		} finally {
			ringBuffer.publish(sequence);
		}
	}
}
