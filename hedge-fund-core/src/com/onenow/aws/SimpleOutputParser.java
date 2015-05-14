package com.onenow.aws;

public class SimpleOutputParser implements OutputParser {

	public SimpleOutputParser(String name) {
		super();
		this.name = name;
	}

	private String name;

	public void handle(String s) {
		String ss[] = s.split("\n");
		for (String s2 : ss) {
			s2 = s2.trim();
			System.out.println(name + ": " + s2);
		}
	}
}
