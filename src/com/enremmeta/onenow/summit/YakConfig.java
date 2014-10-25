package com.enremmeta.onenow.summit;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class YakConfig {

	public YakConfig() {
		// TODO Auto-generated constructor stub
	}

	@JsonProperty("opentsdb_host")
	private String openTSDBHost;

	@JsonProperty("opentsdb_port")
	private int openTSDBPort;
	
	public String getOpenTSDBHost() {
		return openTSDBHost;
	}

	public void setOpenTSDBHost(String openTSDBHost) {
		this.openTSDBHost = openTSDBHost;
	}

	public int getOpenTSDBPort() {
		return openTSDBPort;
	}

	public void setOpenTSDBPort(int openTSDBPort) {
		this.openTSDBPort = openTSDBPort;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	private List<Account> accounts;
}
