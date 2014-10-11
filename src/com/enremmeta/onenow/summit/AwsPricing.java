package com.enremmeta.onenow.summit;

import java.util.List;
// https://a0.awsstatic.com/pricing/1.0.19/ec2/linux-od.min.js
public class AwsPricing {

	public AwsPricing() {
		// TODO Auto-generated constructor stub
	}

	private String vers;
	
	private Config config;
	
	private List<Region> regions;
	
	public static class Config {
		private String rate;
		private List<String> valueColumns;
		private List<String> currencies;
		
	}
	
	public static class Region {
		private String region;
		private List<InstanceType> instanceTypes;
	}
	
	public static class InstanceType {
		private String type;
		private List<Size> sizes;
	}
	
	public static class Size {
		private String size;
		private List<ValueColumn> valueColumns;
	}
	
	public static class ValueColumn {
		private String name;
		
	}
}
