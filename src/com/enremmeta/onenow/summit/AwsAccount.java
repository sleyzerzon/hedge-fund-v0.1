package com.enremmeta.onenow.summit;

import java.util.List;

public class AwsAccount {

	public AwsAccount() {
		// TODO Auto-generated constructor stub
	}

	
	private String secret;
	private String access;
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
