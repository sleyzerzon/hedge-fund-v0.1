package com.onenow.util;

import java.io.IOException;
import java.util.logging.Level;

public class RuntimeMetrics {

	public RuntimeMetrics() {
		
	}
	
	
	public static void notifyWallstLatency(Long miliseconds) {
		
		Watchr.log(Level.WARNING, "READY TO INVEST IN: " + miliseconds + "ms");

		final String message = "mon-put-data -General Investor -metric-name READY-TO-INVEST-IN -value" + miliseconds.toString();

		messageRuntime(message);

	}


	private static void messageRuntime(final String message) {
		new Thread () {
			@Override public void run () {

			try {
				// mon-put-data -namespace App1 -metric-name Latency -dimensions “Host=host1″ -value 104
				Runtime.getRuntime().exec(message);
			} catch (IOException e) {
				Watchr.log(Level.SEVERE, e.toString());
			}
			}
		}.start();
	}
}
