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

		// mon-put-data -namespace App1 -metric-name Latency -dimensions “Host=host1″ -value 104
		final String message = "mon-put-data -General Investor -metric-name READY-TO-INVEST-IN -dimensions " + streamName + " -value " + miliseconds.toString();

		
		aws cloudwatch put-metric-data --metric-name PageViewCount --namespace "MyService" --value 2 --timestamp 2014-02-14T12:00:00.000Z

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
