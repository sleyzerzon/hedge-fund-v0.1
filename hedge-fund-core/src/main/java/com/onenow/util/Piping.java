package com.onenow.util;

import com.google.gson.Gson;

public class Piping {
	
	private static Gson gson = new Gson();

	public Piping() {
		
	}

	public static String serialize(Object obj) {
		String json = "";
		try {
			json = gson.toJson(obj);
			// Watchr.log("Serialized " + obj.toString() + " into " + json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	public static Object deserialize(String json, Class recordType) {
		Object obj = null;
		try {
			obj = gson.fromJson(json, recordType);
			// Watchr.log("Deserialized " + json + " into " + obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	public static String compress(String message) {
		String s = "";
		
		return s;
	}
	
	public static String decompress(String message) {
		String s = "";
		
		return s;
	}
}
