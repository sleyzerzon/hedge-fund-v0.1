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
	public static void notifyWallstLatency(Long seconds, StreamName streamName) {

		Watchr.log(Level.WARNING, streamName + " READY TO INVEST IN: " + seconds + "ms");

		String app = "aws cloudwatch put-metric-data";
		String metric = "--metric-name";
		String nameSpace = "--namespace";
		String value = "--value";
		String stamp = "--timestamp";
		
		final String message = 	app + " " + "Investor" + streamName + " " +
								metric + " " + "READY-TO-INVEST-IN" + " " +
								nameSpace + " " + "\"General\"" + " " +
								value + " " + seconds.toString() + " " +
								stamp + " " + "2015-06-12T12:00:00.000Z";
		
		RuntimeEnvironment.message(message);

	}


}
