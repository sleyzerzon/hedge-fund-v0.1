package com.sforce.soap.metadata;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated by SimpleTypeCodeGenerator.java. Please do not edit.
 */
public enum AuthProviderType {


  
	/**
	 * Enumeration  : Facebook
	 */
	Facebook("Facebook"),

  
	/**
	 * Enumeration  : Janrain
	 */
	Janrain("Janrain"),

  
	/**
	 * Enumeration  : Salesforce
	 */
	Salesforce("Salesforce"),

  
	/**
	 * Enumeration  : OpenIdConnect
	 */
	OpenIdConnect("OpenIdConnect"),

  
	/**
	 * Enumeration  : MicrosoftACS
	 */
	MicrosoftACS("MicrosoftACS"),

  
	/**
	 * Enumeration  : LinkedIn
	 */
	LinkedIn("LinkedIn"),

  
	/**
	 * Enumeration  : Twitter
	 */
	Twitter("Twitter"),

;

	public static Map<String, String> valuesToEnums;

	static {
   		valuesToEnums = new HashMap<String, String>();
   		for (AuthProviderType e : EnumSet.allOf(AuthProviderType.class)) {
   			valuesToEnums.put(e.toString(), e.name());
   		}
   	}

   	private String value;

   	private AuthProviderType(String value) {
   		this.value = value;
   	}

   	@Override
   	public String toString() {
   		return value;
   	}
}
