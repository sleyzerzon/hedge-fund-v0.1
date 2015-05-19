package com.onenow.research;


public class Candle {
	
	public Double openPrice;
	public Double closePrice;
	public Double difference;
	
	public Double lowPrice;
	public Double highPrice;

	public Double meanPrice;
	public Double modePrice;
	public Double medianPrice;
	public Double stddevPrice;

	public Double distinctPrice;
	public Double countPrice;
	public Double sumPrice;
	public Double derivativePrice;


//	private Long timeStart;
//	private Long timeEnd;
//		
//	private Integer volumeOpen;
//	private Integer volumeClose;
	
	
	public Candle() {
		
	}
	
	public Candle(Chart intraDay) {
//		setIntraDay(intraDay);
	}
	

	// PRIVATE

	// TEST
	
	// PRINT
	public String toString() {
		String s = "";
		s = s + "open=" + openPrice + " ";
		s = s + "close=" + closePrice + " ";
		s = s + "difference=" + difference + " ";
				
		s = s + "low=" + lowPrice + " ";
		s = s + "high=" + highPrice + " ";
		
		s = s + "mean=" + meanPrice + " ";
		s = s + "mode=" + modePrice + " ";
		s = s + "median=" + medianPrice + " ";		
		s = s + "stddev" + stddevPrice + " ";

		s = s + "distinct" + distinctPrice + " ";
		s = s + "count=" + countPrice + " ";
		s = s + "sum=" + sumPrice + " ";
		s = s + "derivative=" + derivativePrice + " ";		
		
		s = s + "\n";

		return s;
	}

}
