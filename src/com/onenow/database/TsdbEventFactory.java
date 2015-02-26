package com.onenow.database;

import com.lmax.disruptor.EventFactory;

public class TsdbEventFactory implements EventFactory<TsdbEvent> {

	public TsdbEventFactory() {
		super();
		
	}

	public TsdbEvent newInstance() {
		return new TsdbEvent();
	}

}
