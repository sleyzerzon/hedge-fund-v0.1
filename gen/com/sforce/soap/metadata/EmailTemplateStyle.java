package com.sforce.soap.metadata;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated by SimpleTypeCodeGenerator.java. Please do not edit.
 */
public enum EmailTemplateStyle {


  
	/**
	 * Enumeration  : none
	 */
	none("none"),

  
	/**
	 * Enumeration  : freeForm
	 */
	freeForm("freeForm"),

  
	/**
	 * Enumeration  : formalLetter
	 */
	formalLetter("formalLetter"),

  
	/**
	 * Enumeration  : promotionRight
	 */
	promotionRight("promotionRight"),

  
	/**
	 * Enumeration  : promotionLeft
	 */
	promotionLeft("promotionLeft"),

  
	/**
	 * Enumeration  : newsletter
	 */
	newsletter("newsletter"),

  
	/**
	 * Enumeration  : products
	 */
	products("products"),

;

	public static Map<String, String> valuesToEnums;

	static {
   		valuesToEnums = new HashMap<String, String>();
   		for (EmailTemplateStyle e : EnumSet.allOf(EmailTemplateStyle.class)) {
   			valuesToEnums.put(e.toString(), e.name());
   		}
   	}

   	private String value;

   	private EmailTemplateStyle(String value) {
   		this.value = value;
   	}

   	@Override
   	public String toString() {
   		return value;
   	}
}
