package com.onenow.main;

import com.onenow.util.FlexibleLogger;
import com.onenow.util.SysProperties;

public class StreamingMain {

	/** 
	 * Handles VWAP and other ticks streamed down that are not real-time or historical
	 * @param args
	 */
	public static void main(String[] args) {

		SysProperties.setLogProperties();
		FlexibleLogger.setup();

	}

}
