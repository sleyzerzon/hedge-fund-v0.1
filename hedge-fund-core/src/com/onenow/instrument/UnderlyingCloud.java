package com.onenow.instrument;

public class UnderlyingCloud extends Underlying {

	private String cloud;
	private String region;
	private String zone;
	private String os;
	private String instance;

	public String getCloud() {
		return cloud;
	}

	public void setCloud(String cloud) {
		this.cloud = cloud;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getInstance() {
		return instance;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}

	public UnderlyingCloud() {
		// TODO Auto-generated constructor stub
	}

	public UnderlyingCloud(String ticker) {
		super(ticker);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getTicker() {
		return cloud + ":" + region + ":" + zone + ":" + instance + ":" + os;
	}

}
