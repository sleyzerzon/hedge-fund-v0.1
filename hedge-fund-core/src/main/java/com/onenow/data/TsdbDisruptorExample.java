package com.onenow.data;

import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.influxdb.InfluxDB;
import org.influxdb.dto.Serie;
import org.influxdb.impl.InfluxDBImpl;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.onenow.io.TsdbEvent;
import com.onenow.io.TsdbEventFactory;
import com.onenow.io.TsdbWriterFrontend;
import com.onenow.io.TsdbWriterHandler;

public class TsdbDisruptorExample {

	public static void main(String[] args) throws Exception {
		// Executor that will be used to construct new threads for consumers
		Executor executor = Executors.newCachedThreadPool();

		// The factory for the event
		TsdbEventFactory factory = new TsdbEventFactory();

		// Specify the size of the ring buffer, must be power of 2.
		int bufferSize = 1024;

		// Construct the Disruptor
		Disruptor<TsdbEvent> disruptor = new Disruptor<>(factory, bufferSize,
				executor);

		// Connect the handler
		InfluxDB idb = new InfluxDBImpl("http://tsdb.enremmeta.com:8086",
				"root", "root");

		disruptor.handleEventsWith(new TsdbWriterHandler(idb, "disrupttest"));

		// Start the Disruptor, starts all threads running
		disruptor.start();

		// Get the ring buffer from the Disruptor to be used for publishing.
		RingBuffer<TsdbEvent> ringBuffer = disruptor.getRingBuffer();

		TsdbWriterFrontend frontend = new TsdbWriterFrontend(ringBuffer);
		Random rnd = new Random(System.currentTimeMillis());
		while (true) {
			Serie s = new Serie.Builder("ts1").columns("time", "price")
					.values(System.currentTimeMillis(), rnd.nextInt()).build();

			frontend.write(s, TimeUnit.MILLISECONDS);
			Thread.sleep(1000);
		}
	}
}
