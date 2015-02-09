package com.onenow.investor;

public class ParseDate {

	public ParseDate() {
		
	}
	
	
	public String removeDash(String dashed) {
		String end="";
		String year=getDashedYear(dashed);
		String month=getDashedMonth(dashed);
		String day=getDahsedDay(dashed);
		end = year + month + day + " 16:30:00";
//		System.out.println("End " + date + " "+ end);
		return end;
	}
	
	public String getDashedYear(String dashed) {
		String s = "";
		s = dashed.substring(0, 4);
		return s;
	}
	public String getDashedMonth(String dashed) {
		String s = "";
		s= dashed.substring(5, 7);
		return s;
	}
	public String getDahsedDay(String dashed) {
		String s = "";
		s = dashed.substring(8, 10);
		return s;
	}
	public Integer getElapsedDays(String d1, String d2) {
		Integer elapsed = 0;
			try {
				Integer id1 = Integer.parseInt(getDahsedDay(d1));
				Integer id2 = Integer.parseInt(getDahsedDay(d2));
				if(id2>id1) { // same month
					elapsed = id2 - id1; 
				}
				else {
					elapsed = 31 - id1 + id2; // assumes 31d month
				}
			} catch (NumberFormatException e) {
//				e.printStackTrace(); some intentionally come null 
			}
		return elapsed;
	}
}
