package com.onenow.util;

import java.io.IOException;
import java.util.logging.Level;

import com.onenow.constant.InvestorRole;
import com.onenow.constant.StreamName;

public class RuntimeMetrics {

	public RuntimeMetrics() {
		
	}
	
	
	// http://docs.aws.amazon.com/AmazonCloudWatch/latest/DeveloperGuide/publishingMetrics.html
	// https://aws.amazon.com/blogs/aws/amazon-cloudwatch-user-defined-metrics/
	// http://docs.aws.amazon.com/AWSEC2/latest/UserGuide/mon-scripts.html
	public static void notifyWallstLatency(Long miliSeconds, InvestorRole investorRole) {

		Watchr.log(Level.WARNING, investorRole + " READY TO INVEST IN: " + miliSeconds/1000 + "sec");

		String app = "aws cloudwatch put-metric-data";
		String metric = "--metric-name";
		String nameSpace = "--namespace";
		String value = "--value";
		String stamp = "--timestamp";
		
		final String message = 	app + " " + "Investor" + investorRole + " " +
								metric + " " + "READY-TO-INVEST-IN" + " " +
								nameSpace + " " + "\"General\"" + " " +
								value + " " + miliSeconds.toString() + " " +
								stamp + " " + "2015-06-12T12:00:00.000Z";
		
		RuntimeEnvironment.executeThread(message);

	}


}
