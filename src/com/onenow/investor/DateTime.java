package com.onenow.investor;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTime {

	public DateTime() {
		
	}

	public String getToday() {
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // "yyyy-MM-dd HH:mm"
		return sdf.format(today);
	}
}
