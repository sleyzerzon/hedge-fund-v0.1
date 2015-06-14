package com.onenow.util;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class Serializer {
	
	private static Gson gson = new Gson();

	public Serializer() {
		
	}

	public static String serialize(Object obj) {
		String json = "";
		try {
			json = gson.toJson(obj);
			Watchr.log("Serialized " + obj.toString() + " into " + json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	public static Object deserialize(String json, Class recordType) {
		Object obj = null;
		try {
			obj = gson.fromJson(json, recordType);
			Watchr.log("Deserialized " + json + " into " + obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}
}
