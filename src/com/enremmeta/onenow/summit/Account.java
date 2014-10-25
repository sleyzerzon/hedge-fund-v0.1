package com.enremmeta.onenow.summit;

public class Account {

	public Account() {
		// TODO Auto-generated constructor stub
	}

	private String name;
	
	private AWSAccount aws;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AWSAccount getAws() {
		return aws;
	}

	public void setAws(AWSAccount aws) {
		this.aws = aws;
	}
}
