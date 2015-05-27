package com.onenow.admin;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.onenow.constant.AWSAccount;
import com.onenow.util.JSON;

public class Accounts {

	public static String accessKey;
	public static String secretKey;
	public static String keyPath;	

	public Accounts() throws IOException {
		
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
	 * {		
		"accounts" : [ { 	"name" : "abcd", 		
							"aws" : { "access" :  "xyz",		
  			               	"secret" :  "jlkjl",		
  			               	"keyPath" : "/Users/admin/yiuy.pem"		
  			              }		
  			      }		
  			   ]		
		}
	 * @param accessKey
	 * @param secretKey
	 * @param keyPath
	 * @throws IOException
	 */
	public static void printSecretFile(String name, String accessKey, String secretKey, String keyPath) throws IOException {
		
	        JSONObject whole = new JSONObject();

	        // set of credentials
	        JSONArray credentials = new JSONArray();
	        
	        String accessString = AWSAccount.ACCESSKEY.toString() + ":" + accessKey;
	        credentials.add(accessString);
	        
	        String secretString = AWSAccount.SECRETKEY.toString() + ":" + secretKey;
	        credentials.add(AWSAccount.KEYPATH + ":" + secretKey);
	        
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
	        	
	        	if(pair[0].equals(AWSAccount.ACCESSKEY.toString())) {
	        		accessKey = pair[1];
	        	}
	        	if(pair[0].equals(AWSAccount.SECRETKEY.toString())) {
	        		secretKey = pair[1];	        		
	        	}
	        	if(pair[0].equals(AWSAccount.KEYPATH.toString())) {
	        		keyPath = pair[1];
	        	}
	        }
	      
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	
}
