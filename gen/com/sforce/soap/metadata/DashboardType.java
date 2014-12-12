package com.sforce.soap.metadata;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated by SimpleTypeCodeGenerator.java. Please do not edit.
 */
public enum DashboardType {


  
	/**
	 * Enumeration  : SpecifiedUser
	 */
	SpecifiedUser("SpecifiedUser"),

  
	/**
	 * Enumeration  : LoggedInUser
	 */
	LoggedInUser("LoggedInUser"),

  
	/**
	 * Enumeration  : MyTeamUser
	 */
	MyTeamUser("MyTeamUser"),

;

	public static Map<String, String> valuesToEnums;

	static {
   		valuesToEnums = new HashMap<String, String>();
   		for (DashboardType e : EnumSet.allOf(DashboardType.class)) {
   			valuesToEnums.put(e.toString(), e.name());
   		}
   	}

   	private String value;

   	private DashboardType(String value) {
   		this.value = value;
   	}

   	@Override
   	public String toString() {
   		return value;
   	}
}
