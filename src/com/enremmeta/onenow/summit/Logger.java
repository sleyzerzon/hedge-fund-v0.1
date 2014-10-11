package com.enremmeta.onenow.summit;

import java.util.Date;

public class Logger {
	public static final void log(Object o) {
		System.out.println(new Date() + ": " + o);
	}

	public Logger() {
		// TODO Auto-generated constructor stub
	}

}
