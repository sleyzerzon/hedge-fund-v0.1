package com.onenow.portfolio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Stats {

    double[] data;
//    double size;    
    
    List<Double> nums = new ArrayList<Double>();

//    public Statistics(double[] data) 
//    {
//        this.data = data;
//        size = data.length;
//    } 
    
	public Stats() {
		
	}

	public Stats(List<Double> nums) {
		this.nums = nums;
	}

	public double getMean() {
        double sum = 0.0;
        for(double a : nums)
            sum += a;
        return sum/nums.size();
    }
	
    private double getVariance() {
        double mean = getMean();
        double temp = 0;
        for(double a :data)
            temp += (mean-a)*(mean-a);
        return temp/nums.size();
    }

    private double getStdDev() {
        return Math.sqrt(getVariance());
    }
    
    public boolean isAboveNormal(Double val) {
    	boolean normal=true;
    	if( val>(getMean()+getStdDev()) ) {
    		normal=false;
    	}
    	return normal;
    }
    public boolean isBelowNormal(Double val) {
    	boolean normal=true;
    	if(val<(getMean()-getStdDev())) {
    		normal=false;
    	}
    	return normal;
    }

    // TODO
    public double median() {
       double[] b = new double[data.length];
       System.arraycopy(data, 0, b, 0, b.length);
       Arrays.sort(b);

       if (data.length % 2 == 0) {
          return (b[(b.length / 2) - 1] + b[b.length / 2]) / 2.0;
       } 
       else {
          return b[b.length / 2];
       }
    }
}
