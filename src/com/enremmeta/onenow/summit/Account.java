package com.enremmeta.onenow.summit;

public class Account {

	public Account() {
		// TODO Auto-generated constructor stub
	}

	private String name;
	
	private AwsAccount aws;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AwsAccount getAws() {
		return aws;
	}

	public void setAws(AwsAccount aws) {
		this.aws = aws;
	}
}
