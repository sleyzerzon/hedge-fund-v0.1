package com.sforce.soap.metadata;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated by SimpleTypeCodeGenerator.java. Please do not edit.
 */
public enum EmailTemplateType {


  
	/**
	 * Enumeration  : text
	 */
	text("text"),

  
	/**
	 * Enumeration  : html
	 */
	html("html"),

  
	/**
	 * Enumeration  : custom
	 */
	custom("custom"),

  
	/**
	 * Enumeration  : visualforce
	 */
	visualforce("visualforce"),

;

	public static Map<String, String> valuesToEnums;

	static {
   		valuesToEnums = new HashMap<String, String>();
   		for (EmailTemplateType e : EnumSet.allOf(EmailTemplateType.class)) {
   			valuesToEnums.put(e.toString(), e.name());
   		}
   	}

   	private String value;

   	private EmailTemplateType(String value) {
   		this.value = value;
   	}

   	@Override
   	public String toString() {
   		return value;
   	}
}
