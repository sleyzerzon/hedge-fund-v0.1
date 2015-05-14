package com.onenow.aws;

import org.joda.time.DateTime;

public class Tick {

	public Tick() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public Tick(String userId, String clusterId, String region, String az, String instId, String instType, String cost, boolean spot, DateTime time, String priceType) {
		super();
		this.instType = instType;
		this.cost = cost;
		this.spot = spot;
		this.time = time;
		this.clusterId = clusterId;
		this.instId = instId;
		this.userId = userId;
		this.region = region;
		this.az = az;
		this.priceType = priceType;
	}

	public String getAz() {
		return az;
	}

	private String priceType;
	


	public String getPriceType() {
		return priceType;
	}



	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}



	public void setAz(String az) {
		this.az = az;
	}



	public String getRegion() {
		return region;
	}
private String az;


	public void setRegion(String region) {
		this.region = region;
	}

	private String region;
	private String clusterId;
	private String instId;
	private String userId;

	public String getClusterId() {
		return clusterId;
	}



	public void setClusterId(String clusterId) {
		this.clusterId = clusterId;
	}



	public String getInstId() {
		return instId;
	}



	public void setInstId(String instId) {
		this.instId = instId;
	}



	public String getUserId() {
		return userId;
	}



	public void setUserId(String userId) {
		this.userId = userId;
	}



	@Override
	public String toString() {
		return "Tick [instType=" + instType + ", cost=" + cost + ", spot="
				+ spot + ", time=" + time + "]";
	}



	private String instType;
	
	private String cost;
	
	private boolean spot;
	
	private DateTime time;

	public String getInstType() {
		return instType;
	}

	public void setInstType(String instType) {
		this.instType = instType;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public boolean isSpot() {
		return spot;
	}

	public void setSpot(boolean spot) {
		this.spot = spot;
	}

	public DateTime getTime() {
		return time;
	}

	public void setTime(DateTime time) {
		this.time = time;
	}

}
