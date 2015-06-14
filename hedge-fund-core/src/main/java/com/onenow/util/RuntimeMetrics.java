package com.onenow.util;

import java.io.IOException;
import java.util.logging.Level;

import com.onenow.constant.StreamName;

public class RuntimeMetrics {

	public RuntimeMetrics() {
		
	}
	
	
	// http://docs.aws.amazon.com/AmazonCloudWatch/latest/DeveloperGuide/publishingMetrics.html
	// https://aws.amazon.com/blogs/aws/amazon-cloudwatch-user-defined-metrics/
	// http://docs.aws.amazon.com/AWSEC2/latest/UserGuide/mon-scripts.html
	public static void notifyWallstLatency(Long miliseconds, StreamName streamName) {

		Watchr.log(Level.WARNING, streamName + " READY TO INVEST IN: " + miliseconds + "ms");

		String app = "aws cloudwatch put-metric-data";
		String metric = "--metric-name";
		String nameSpace = "--namespace";
		String value = "--value";
		String stamp = "--timestamp";
		
		final String message = 	app + " " + "Investor" + streamName + " " +
								metric + " " + "READY-TO-INVEST-IN" + " " +
								nameSpace + " " + "\"General\"" + " " +
								value + " " + miliseconds.toString() + " " +
								stamp + " " + "2015-06-12T12:00:00.000Z";
		
		messageRuntime(message);

	}


	private static void messageRuntime(final String message) {
		new Thread () {
			@Override public void run () {

			try {
				Runtime.getRuntime().exec(message);
			} catch (IOException e) {
				Watchr.log(Level.WARNING, e.toString());
			}
			}
		}.start();
	}
}