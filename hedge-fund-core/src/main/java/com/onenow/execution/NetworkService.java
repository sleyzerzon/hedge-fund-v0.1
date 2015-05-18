package com.onenow.execution;

public class NetworkService {

	public String user;
	public String pass;

	public String protocol;
	public String URL;
	public Integer port;

	public String endPoint;
	
	public NetworkService() {
	}
	
	public NetworkService(	String user, String pass,
							String protocol, String url, Integer port,
							String endpoint) {
		this.user = user;
		this.pass = pass;
		this.protocol = protocol;
		this.URL = url;
		this.port = port;
		this.endPoint = endpoint;
	}
}
