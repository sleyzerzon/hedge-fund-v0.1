package com.sforce.soap.metadata;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated by SimpleTypeCodeGenerator.java. Please do not edit.
 */
public enum Language {


  
	/**
	 * Enumeration  : en_US
	 */
	en_US("en_US"),

  
	/**
	 * Enumeration  : de
	 */
	de("de"),

  
	/**
	 * Enumeration  : es
	 */
	es("es"),

  
	/**
	 * Enumeration  : fr
	 */
	fr("fr"),

  
	/**
	 * Enumeration  : it
	 */
	it("it"),

  
	/**
	 * Enumeration  : ja
	 */
	ja("ja"),

  
	/**
	 * Enumeration  : sv
	 */
	sv("sv"),

  
	/**
	 * Enumeration  : ko
	 */
	ko("ko"),

  
	/**
	 * Enumeration  : zh_TW
	 */
	zh_TW("zh_TW"),

  
	/**
	 * Enumeration  : zh_CN
	 */
	zh_CN("zh_CN"),

  
	/**
	 * Enumeration  : pt_BR
	 */
	pt_BR("pt_BR"),

  
	/**
	 * Enumeration  : nl_NL
	 */
	nl_NL("nl_NL"),

  
	/**
	 * Enumeration  : da
	 */
	da("da"),

  
	/**
	 * Enumeration  : th
	 */
	th("th"),

  
	/**
	 * Enumeration  : fi
	 */
	fi("fi"),

  
	/**
	 * Enumeration  : ru
	 */
	ru("ru"),

  
	/**
	 * Enumeration  : es_MX
	 */
	es_MX("es_MX"),

  
	/**
	 * Enumeration  : no
	 */
	no("no"),

  
	/**
	 * Enumeration  : hu
	 */
	hu("hu"),

  
	/**
	 * Enumeration  : pl
	 */
	pl("pl"),

  
	/**
	 * Enumeration  : cs
	 */
	cs("cs"),

  
	/**
	 * Enumeration  : tr
	 */
	tr("tr"),

  
	/**
	 * Enumeration  : in
	 */
	in("in"),

  
	/**
	 * Enumeration  : ro
	 */
	ro("ro"),

  
	/**
	 * Enumeration  : vi
	 */
	vi("vi"),

  
	/**
	 * Enumeration  : uk
	 */
	uk("uk"),

  
	/**
	 * Enumeration  : iw
	 */
	iw("iw"),

  
	/**
	 * Enumeration  : el
	 */
	el("el"),

  
	/**
	 * Enumeration  : bg
	 */
	bg("bg"),

  
	/**
	 * Enumeration  : en_GB
	 */
	en_GB("en_GB"),

  
	/**
	 * Enumeration  : ar
	 */
	ar("ar"),

  
	/**
	 * Enumeration  : sk
	 */
	sk("sk"),

  
	/**
	 * Enumeration  : pt_PT
	 */
	pt_PT("pt_PT"),

  
	/**
	 * Enumeration  : fr_CA
	 */
	fr_CA("fr_CA"),

  
	/**
	 * Enumeration  : ka
	 */
	ka("ka"),

  
	/**
	 * Enumeration  : sr
	 */
	sr("sr"),

  
	/**
	 * Enumeration  : sh
	 */
	sh("sh"),

  
	/**
	 * Enumeration  : en_AU
	 */
	en_AU("en_AU"),

  
	/**
	 * Enumeration  : en_MY
	 */
	en_MY("en_MY"),

  
	/**
	 * Enumeration  : en_IN
	 */
	en_IN("en_IN"),

  
	/**
	 * Enumeration  : en_PH
	 */
	en_PH("en_PH"),

  
	/**
	 * Enumeration  : en_CA
	 */
	en_CA("en_CA"),

  
	/**
	 * Enumeration  : sl
	 */
	sl("sl"),

  
	/**
	 * Enumeration  : ro_MD
	 */
	ro_MD("ro_MD"),

  
	/**
	 * Enumeration  : hr
	 */
	hr("hr"),

  
	/**
	 * Enumeration  : bs
	 */
	bs("bs"),

  
	/**
	 * Enumeration  : mk
	 */
	mk("mk"),

  
	/**
	 * Enumeration  : lv
	 */
	lv("lv"),

  
	/**
	 * Enumeration  : lt
	 */
	lt("lt"),

  
	/**
	 * Enumeration  : et
	 */
	et("et"),

  
	/**
	 * Enumeration  : sq
	 */
	sq("sq"),

  
	/**
	 * Enumeration  : sh_ME
	 */
	sh_ME("sh_ME"),

  
	/**
	 * Enumeration  : mt
	 */
	mt("mt"),

  
	/**
	 * Enumeration  : ga
	 */
	ga("ga"),

  
	/**
	 * Enumeration  : eu
	 */
	eu("eu"),

  
	/**
	 * Enumeration  : cy
	 */
	cy("cy"),

  
	/**
	 * Enumeration  : is
	 */
	is("is"),

  
	/**
	 * Enumeration  : ms
	 */
	ms("ms"),

  
	/**
	 * Enumeration  : tl
	 */
	tl("tl"),

  
	/**
	 * Enumeration  : lb
	 */
	lb("lb"),

  
	/**
	 * Enumeration  : rm
	 */
	rm("rm"),

  
	/**
	 * Enumeration  : hy
	 */
	hy("hy"),

  
	/**
	 * Enumeration  : hi
	 */
	hi("hi"),

  
	/**
	 * Enumeration  : ur
	 */
	ur("ur"),

  
	/**
	 * Enumeration  : bn
	 */
	bn("bn"),

  
	/**
	 * Enumeration  : de_AT
	 */
	de_AT("de_AT"),

  
	/**
	 * Enumeration  : de_CH
	 */
	de_CH("de_CH"),

  
	/**
	 * Enumeration  : ta
	 */
	ta("ta"),

  
	/**
	 * Enumeration  : eo
	 */
	eo("eo"),

;

	public static Map<String, String> valuesToEnums;

	static {
   		valuesToEnums = new HashMap<String, String>();
   		for (Language e : EnumSet.allOf(Language.class)) {
   			valuesToEnums.put(e.toString(), e.name());
   		}
   	}

   	private String value;

   	private Language(String value) {
   		this.value = value;
   	}

   	@Override
   	public String toString() {
   		return value;
   	}
}
