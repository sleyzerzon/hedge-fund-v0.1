package com.onenow.main;

import com.onenow.io.S3;
import com.onenow.util.TimeParser;

public class ReporterMain {

	
	public static void main(String[] args) {

		
		String startDate = TimeParser.getDashedToday();
		Integer numDays = 3;
		
		for(int days=0; days<numDays; days++) {

			String date = TimeParser.getDashedDateMinus(startDate, 1);
			
			
			
		}
		
	}

}
