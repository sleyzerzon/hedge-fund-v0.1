package com.onenow.main;

import com.onenow.util.FlexibleLogger;
import com.onenow.util.SysProperties;

public class RealTimeMain {

	/**
	 * Gather complete accurate real-time market data
	 * @param args
	 */
	public static void main(String[] args) {

		SysProperties.setLogProperties();
		FlexibleLogger.setup();

	}

}
