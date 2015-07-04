package com.onenow.util;

import java.io.IOException;
import java.util.logging.Level;

public class RuntimeEnvironment {
	
	public RuntimeEnvironment() {
		
	}

	public static void message(final String message) {
		new Thread () {
			@Override public void run () {

			try {
				Runtime.getRuntime().exec(message);
			} catch (IOException e) {
				Watchr.log(Level.SEVERE, e.toString());
			}
			}
		}.start();
	}

}
