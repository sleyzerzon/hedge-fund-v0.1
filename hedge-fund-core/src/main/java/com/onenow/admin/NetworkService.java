package com.onenow.admin;

public class NetworkService {

	public String user;
	public String pass;

	public String protocol;
	public String URI;
	public String port;

	public String endPoint;
	
	public NetworkService() {
	}
	
	public NetworkService(	String user, String pass,
							String protocol, String uri, String port,
							String endpoint) {
		this.user = user;
		this.pass = pass;
		this.protocol = protocol;
		this.URI = uri;
		this.port = port;
		this.endPoint = endpoint;
	}
}
