package com.onenow.admin;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.onenow.util.JSON;

public class Accounts {

	private static String accessKey;
	private static String secretKey;


	public Accounts() {
		
		// String test = "/Users/pablo/Documents/EclipseWorkspaceMaven/hedge-fund-parent/hedge-fund-core/config/secret/test.txt";
		// JSON.writeJsonAccountFile(test);
		
		String accounts = "/Users/pablo/Documents/EclipseWorkspaceMaven/hedge-fund-parent/hedge-fund-core/config/secret/accounts.txt";
		try {
			readAccounts(accounts);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public static void writeSecretFile(String fileName,
										String access, String secret, String key) throws IOException {
		
	       JSONObject obj = new JSONObject();
	        obj.put("Name", "AWS");
	 
	        JSONArray credentials = new JSONArray();
	        credentials.add("Access: " + access);
	        credentials.add("Secret: " + secret);
	        credentials.add("Key: " + key);
	        obj.put("Credentials", credentials);
	 
	        FileWriter file = new FileWriter(fileName);
	        try {
	            file.write(obj.toJSONString());
	            System.out.println("WROTE: " + obj);
	 
	        } catch (IOException e) {
	            e.printStackTrace();
	 
	        } finally {
	            file.flush();
	            file.close();
	        }
	}
	
	public static void getAccessKey() {
		
	}
	
	public static void getSecretKey() {
		
	}
	
	public static void readAccounts(String fileName) {
		
		try {
	        JSONObject jsonObject = JSON.getJsonFileObject(fileName);
	 
	        String name = "Name";
	        String acc = (String) jsonObject.get(name);
	        System.out.println(name + acc);
	        
	        String creds = "Credentials"; 
	        JSONArray itemList = (JSONArray) jsonObject.get(creds);
	 
	        Iterator<String> iterator = itemList.iterator();
	        while (iterator.hasNext()) {
	            System.out.println(iterator.next());
	        }
	      
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	
}
