package com.onenow.summit;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClient;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.elasticmapreduce.AmazonElasticMapReduceClient;

public class AwsFacade {

	private String access;
	private String secret;

	public AwsFacade(final String access, final String secret) {
		super();
		this.access = access;
		this.secret = secret;
		creds = new AWSCredentials() {

			public String getAWSSecretKey() {
				return secret;
			}

			public String getAWSAccessKeyId() {
				return access;
			}
		};
		cw = new AmazonCloudWatchClient(creds);
		ec2 = new AmazonEC2Client(creds);
	}

	protected final AWSCredentials creds;

	protected String emrRegion = "us-east-1";

	private AmazonCloudWatchClient cw;
	protected AmazonEC2Client ec2;

	public AmazonEC2Client getEC2Client() {
		return ec2;
	}

}
