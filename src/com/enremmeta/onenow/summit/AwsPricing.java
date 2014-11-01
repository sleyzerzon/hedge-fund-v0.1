package com.enremmeta.onenow.summit;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

// https://a0.awsstatic.com/pricing/1.0.19/ec2/linux-od.min.js
public class AwsPricing {

	public AwsPricing() {
		// TODO Auto-generated constructor stub
		
	}

	private String vers;

	private Config config;

	public String getVers() {
		return vers;
	}

	public void setVers(String vers) {
		this.vers = vers;
	}

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	public static class Config {
		private String rate;
		private List<String> valueColumns;
		private List<RegionPricing> regionPricings;

		public RegionPricing findRegion(String r) {
			if (r.equals("us-east-1")) {
				r = "us-east";
			}
			for (RegionPricing regionPricing : regionPricings) {
				if (regionPricing.getRegion().equals(r)) {
					return regionPricing;
				}
			}
			throw new RuntimeException("Region not found: " + r);
		}

		public List<RegionPricing> getRegions() {
			return regionPricings;
		}

		public void setRegions(List<RegionPricing> regionPricings) {
			this.regionPricings = regionPricings;
		}

		public String getRate() {
			return rate;
		}

		public void setRate(String rate) {
			this.rate = rate;
		}

		public List<String> getValueColumns() {
			return valueColumns;
		}

		public void setValueColumns(List<String> valueColumns) {
			this.valueColumns = valueColumns;
		}

		public List<String> getCurrencies() {
			return currencies;
		}

		public void setCurrencies(List<String> currencies) {
			this.currencies = currencies;
		}

		private List<String> currencies;

	}

	public static class RegionPricing {
		public String toString() {
			return region;
		}

		public String getRegion() {
			return region;
		}

		public void setRegion(String region) {
			this.region = region;
		}

		public List<InstanceType> getInstanceTypes() {
			return instanceTypes;
		}

		public Size findSize(String sizeTypeStr) {
			for (InstanceType instType : instanceTypes) {
				List<Size> sizes = instType.getSizes();
				for (Size size : sizes) {
					if (size.getSize().equalsIgnoreCase(sizeTypeStr)) {
						return size;
					}
				}
			}
			return null;
		}

		public void setInstanceTypes(List<InstanceType> instanceTypes) {
			this.instanceTypes = instanceTypes;
		}

		private String region;
		private List<InstanceType> instanceTypes;
	}

	public static class InstanceType {
		private String type;

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public List<Size> getSizes() {
			return sizes;
		}

		public void setSizes(List<Size> sizes) {
			this.sizes = sizes;
		}

		private List<Size> sizes;
	}

	public static class Size {
		private String size;
		private List<ValueColumn> valueColumns;

		@JsonProperty("vCPU")
		private int vcpu;

		@JsonProperty("ECU")
		private double ecu;

		@JsonProperty("memoryGiB")
		private float memoryGiB;

		@JsonProperty("storageGB")
		private String storageGB;

		public int getVcpu() {
			return vcpu;
		}

		public void setVcpu(int vcpu) {
			this.vcpu = vcpu;
		}

		public double getEcu() {
			return ecu;
		}

		public void setEcu(String ecu) {
			if (ecu.equalsIgnoreCase("variable")) {
				this.ecu = 0.0;
			} else {
				this.ecu = Double.valueOf(ecu);
			}
		}

		public float getMemoryGiB() {
			return memoryGiB;
		}

		public void setMemoryGiB(float memoryGiB) {
			this.memoryGiB = memoryGiB;
		}

		public String getStorageGB() {
			return storageGB;
		}

		public void setStorageGB(String storageGB) {
			this.storageGB = storageGB;
		}

		public String getSize() {
			return size;
		}

		public void setSize(String size) {
			this.size = size;
		}

		public List<ValueColumn> getValueColumns() {
			return valueColumns;
		}

		public void setValueColumns(List<ValueColumn> valueColumns) {
			this.valueColumns = valueColumns;
		}
	}

	public static class ValueColumn {
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		private Prices prices;

		public Prices getPrices() {
			return prices;
		}

		public void setPrices(Prices prices) {
			this.prices = prices;
		}

		private String name;

	}

	public static class Prices {
		@JsonProperty("USD")
		private String usd;

		public String getUsd() {
			return usd;
		}

		public void setUsd(String usd) {
			this.usd = usd;
		}
	}
}
