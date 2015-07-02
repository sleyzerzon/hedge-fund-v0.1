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
	
	
	// PRINT
	public String toString() {
		String s = "";
		
		if(openPrice!=null) {
			s = s + "open=" + openPrice + " ";
		}
		
		if(closePrice!=null) {
			s = s + "close=" + closePrice + " ";
		}
		
		if(difference!=null) {
			s = s + "difference=" + difference + " ";
		}
			
		if(lowPrice!=null) {
			s = s + "low=" + lowPrice + " ";
		}
		
		if(highPrice!=null) {
			s = s + "high=" + highPrice + " ";
		}
		
		if(meanPrice!=null) {
			s = s + "mean=" + meanPrice + " ";
		}
		
		if(modePrice!=null) {
			s = s + "mode=" + modePrice + " ";			
		}
		
		if(medianPrice!=null) {
			s = s + "median=" + medianPrice + " ";
		}
		
		if(stddevPrice!=null) {
			s = s + "stddev" + stddevPrice + " ";
		}

		if(distinctPrice!=null) {
			s = s + "distinct" + distinctPrice + " ";
		}
		
		if(countPrice!=null) {
			s = s + "count=" + countPrice + " ";
		}
		
		if(sumPrice!=null) {
			s = s + "sum=" + sumPrice + " ";
		}
		
		if(derivativePrice!=null) {
			s = s + "derivative=" + derivativePrice + " ";
		}
		
		s = s + "\n";

		return s;
	}

}
