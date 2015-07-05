package com.onenow.util;

import java.io.IOException;
import java.util.logging.Level;
import java.util.Map;

public class RuntimeEnvironment {
	
	public RuntimeEnvironment() {
		
	}
	
	public static void startProcess() {
		
		String[] command = {"CMD", "/C", "dir"};
        ProcessBuilder probuilder = new ProcessBuilder( command );
        //You can set up your work directory
        //probuilder.directory(new File("c:\\xyzwsdemo"));
        
        //Process process = probuilder.start();
	}

	public static void executeNoThread(final String messageToExecute) {
		try {
			Watchr.log("Will execute: " + messageToExecute);
			Runtime.getRuntime().exec(messageToExecute);
		} catch (Exception e) {
			Watchr.log(Level.SEVERE, e.toString());
		}
	}

	public static void executeThread(final String messageToExecute) {
		new Thread () {
			@Override public void run () {
				executeNoThread(messageToExecute);
			}
		}.start();
	}

	/** 
	 * Gets the value corresponding to an environment variable
	 * @param envVariable
	 * @return
	 */
	public static String getEnv(String envVariable) {
		String value = System.getenv(envVariable);
        if (value != null) {
            System.out.format("%s=%s%n", envVariable, value);
        } else {
            System.out.format("%s is"
                + " not assigned.%n", envVariable);
        }
		return value;
	}
	/**
	 * Gets whole envrionment
	 * @return
	 */
	public static String getEnv() {
		String s = "";
        Map<String, String> env = System.getenv();
        for (String envName : env.keySet()) {
        	s = s + envName + " " + env.get(envName) + "\t";
        }
        return s;
	}
	
}
