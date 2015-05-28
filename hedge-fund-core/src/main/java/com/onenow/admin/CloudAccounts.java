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

	public CloudAccounts() throws IOException {
		
		try {
			// printSecretFile("AWS", "abc", "cde", "efg");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String accounts = "/Users/pablo/Documents/EclipseWorkspaceMaven/hedge-fund-parent/hedge-fund-core/config/secret/accounts.txt";
		readAccounts(accounts);
		
	}
	
	/**
	 * Creates AWS credentials
	 * Sample output: 
	 * {"AWS":["ACCESSKEY:ABCD","SECRETKEY:EFGH","KEYPATH:IJKL"]}
	 * @param accessKey
	 * @param secretKey
	 * @param keyPath
	 * @throws IOException
	 */
	public static void printSecretFile(String name, String accessKey, String secretKey, String keyPath) throws IOException {
		
	        JSONObject whole = new JSONObject();

	        // set of credentials
	        JSONArray credentials = new JSONArray();
	        
	        String accessString = AccountSchema.ACCESSKEY.toString() + ":" + accessKey;
	        credentials.add(accessString);
	        
	        String secretString = AccountSchema.SECRETKEY.toString() + ":" + secretKey;
	        credentials.add(AccountSchema.KEYPATH + ":" + secretKey);
	        
	        String keyPathString = "Key:" + keyPath;
	        credentials.add(keyPathString);
	        
	        whole.put(name, credentials);
		   	
		    System.out.println(whole);		    
	}
		
	public static void readAccounts(String fileName) {
		
		try {
	        JSONObject jsonObject = JSON.getJsonFileObject(fileName);
	 
	        String name = "AWS";
	        JSONArray credentials = (JSONArray) jsonObject.get(name);
	        // System.out.println(credentials);

	        Iterator<String> iterator = credentials.iterator();
	        while (iterator.hasNext()) {
	        	String next = iterator.next();
	        	
	        	String[] pair = next.split(":", 2); 
	        	
	        	if(pair[0].equals(AccountSchema.ACCESSKEY.toString())) {
	        		accessKey = pair[1];
	        	}
	        	if(pair[0].equals(AccountSchema.SECRETKEY.toString())) {
	        		secretKey = pair[1];	        		
	        	}
	        	if(pair[0].equals(AccountSchema.KEYPATH.toString())) {
	        		keyPath = pair[1];
	        	}
	        }
	      
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	
}
