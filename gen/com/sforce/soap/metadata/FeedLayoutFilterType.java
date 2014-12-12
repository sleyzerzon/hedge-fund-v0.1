package com.sforce.soap.metadata;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated by SimpleTypeCodeGenerator.java. Please do not edit.
 */
public enum FeedLayoutFilterType {


  
	/**
	 * Enumeration  : AllUpdates
	 */
	AllUpdates("AllUpdates"),

  
	/**
	 * Enumeration  : FeedItemType
	 */
	FeedItemType("FeedItemType"),

;

	public static Map<String, String> valuesToEnums;

	static {
   		valuesToEnums = new HashMap<String, String>();
   		for (FeedLayoutFilterType e : EnumSet.allOf(FeedLayoutFilterType.class)) {
   			valuesToEnums.put(e.toString(), e.name());
   		}
   	}

   	private String value;

   	private FeedLayoutFilterType(String value) {
   		this.value = value;
   	}

   	@Override
   	public String toString() {
   		return value;
   	}
}
