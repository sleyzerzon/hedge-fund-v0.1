package com.onenow.salesforce;

import java.util.List;

import com.onenow.swf.CloudEnum;

public class AccountCloud {

	Enum provider;
	
	public AccountCloud() {
		this.provider = CloudEnum.AWS;
	}

	private String access;
	private String secret;
	private String keyPath;
	
	private List<String> emrHosts;

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getAccess() {
		return access;
	}

	public void setAccess(String access) {
		this.access = access;
	}

	public String getKeyPath() {
		return keyPath;
	}

	public void setKeyPath(String keyPath) {
		this.keyPath = keyPath;
	}

	public List<String> getEmrHosts() {
		return emrHosts;
	}

	public void setEmrHosts(List<String> emrHosts) {
		this.emrHosts = emrHosts;
	}
}
