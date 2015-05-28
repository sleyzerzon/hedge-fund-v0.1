package com.onenow.admin;

import java.io.IOException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.onenow.constant.AccountSchema;
import com.onenow.util.JSON;

public class CloudAccounts {

//	Set credentials in the AWS credentials profile file on your local system, located at:
//		~/.aws/credentials on Linux, OS X, or Unix
//		This file should contain lines in the following format:
//		[default]
//		aws_access_key_id = your_access_key_id
//		aws_secret_access_key = your_secret_access_key
// More at: http://docs.aws.amazon.com/AWSSdkDocsJava/latest/DeveloperGuide/java-dg-setup.html#java-dg-using-maven
	
	public static String accessKey;
	public static String secretKey;
	public static String keyPath;	

	public CloudAccounts() {
		
		
	}
	
}
