package com.sforce.soap.metadata;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated by SimpleTypeCodeGenerator.java. Please do not edit.
 */
public enum AsyncRequestState {


  
	/**
	 * Enumeration  : Queued
	 */
	Queued("Queued"),

  
	/**
	 * Enumeration  : InProgress
	 */
	InProgress("InProgress"),

  
	/**
	 * Enumeration  : Completed
	 */
	Completed("Completed"),

  
	/**
	 * Enumeration  : Error
	 */
	Error("Error"),

;

	public static Map<String, String> valuesToEnums;

	static {
   		valuesToEnums = new HashMap<String, String>();
   		for (AsyncRequestState e : EnumSet.allOf(AsyncRequestState.class)) {
   			valuesToEnums.put(e.toString(), e.name());
   		}
   	}

   	private String value;

   	private AsyncRequestState(String value) {
   		this.value = value;
   	}

   	@Override
   	public String toString() {
   		return value;
   	}
}
