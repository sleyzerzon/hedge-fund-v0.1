package com.onenow.io;

import java.util.concurrent.TimeUnit;

import org.influxdb.dto.Serie;

public class TsdbEvent {

	public TsdbEvent() {
		// TODO Auto-generated constructor stub
	}

	public TsdbEvent(Serie serie, TimeUnit unit) {
		super();
		this.serie = serie;
		this.unit = unit;
	}

	private Serie serie;

	private TimeUnit unit;

	public Serie getSerie() {
		return serie;
	}

	public void setSerie(Serie serie) {
		this.serie = serie;
	}

	public TimeUnit getUnit() {
		return unit;
	}

	public void setUnit(TimeUnit unit) {
		this.unit = unit;
	}
}
