package com.onenow.execution;

public class NetworkService {

	public String user;
	public String pass;

	public String protocol;
	public String URI;
	public Integer port;

	public String endPoint;
	
	public NetworkService() {
	}
	
	public NetworkService(	String user, String pass,
							String protocol, String uri, Integer port,
							String endpoint) {
		this.user = user;
		this.pass = pass;
		this.protocol = protocol;
		this.URI = uri;
		this.port = port;
		this.endPoint = endpoint;
	}
}
