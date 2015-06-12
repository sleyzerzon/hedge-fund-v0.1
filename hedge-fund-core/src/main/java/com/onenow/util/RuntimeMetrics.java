package com.onenow.util;

import java.io.IOException;
import java.util.logging.Level;

import com.onenow.constant.StreamName;

public class RuntimeMetrics {

	public RuntimeMetrics() {
		
	}
	
	
	public static void notifyWallstLatency(Long miliseconds, StreamName streamName) {
		
		Watchr.log(Level.WARNING, streamName + " READY TO INVEST IN: " + miliseconds + "ms");

		// mon-put-data -namespace App1 -metric-name Latency -dimensions “Host=host1″ -value 104
		final String message = "mon-put-data -General Investor -metric-name READY-TO-INVEST-IN -dimensions " + streamName + " -value " + miliseconds.toString();

		messageRuntime(message);

	}


	private static void messageRuntime(final String message) {
		new Thread () {
			@Override public void run () {

			try {
				Runtime.getRuntime().exec(message);
			} catch (IOException e) {
				Watchr.log(Level.SEVERE, e.toString());
			}
			}
		}.start();
	}
}
