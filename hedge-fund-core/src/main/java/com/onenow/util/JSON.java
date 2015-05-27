package com.onenow.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSON {

	static JSONParser parser = new JSONParser();
	
	public JSON () {
	}
	

	public static JSONObject getJsonFileObject(String fileName) throws IOException,
			ParseException, FileNotFoundException {
		Object obj = parser.parse(new FileReader(fileName));
		JSONObject jsonObject = (JSONObject) obj;
		return jsonObject;
	}

	/**
	 * Sample output: {"Name":"crunchify.com","Author":"App Shah","Company List":["Compnay: eBay","Compnay: Paypal","Compnay: Google"]}
	 * @param fileName
	 * @throws IOException
	 */
	public static void writeJsonFile(String fileName) throws IOException {
	       JSONObject obj = new JSONObject();
	        obj.put("Name", "crunchify.com");
	        obj.put("Author", "App Shah");
	 
	        JSONArray company = new JSONArray();
	        company.add("Compnay: eBay");
	        company.add("Compnay: Paypal");
	        company.add("Compnay: Google");
	        obj.put("Company List", company);
	 
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
	
	
}
