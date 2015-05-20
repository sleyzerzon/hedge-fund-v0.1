package com.onenow.research;

import java.util.ArrayList;
import java.util.List;

import com.onenow.constant.AggregateFunction;
import com.onenow.constant.InvType;

public class AnalystActivityImpl implements AnalystActivity {

	@Override
	public List<Tick> getTimeSeries(String cloud, String region, String zone,
			String os, String instance, String source, InvType invType,
			AggregateFunction agg, AggregateFunction downsample) {
		// TODO Auto-generated method stub
		return null;
	}

//	public AnalystActivityImpl() {
//		// TODO Auto-generated constructor stub
//	}
//
//	@Override
//	public List<Tick> getTimeSeries(String cloud, String region, String zone,
//			String os, String instance, String source, InvType invType,
//			AggregateFunction agg, AggregateFunction downsample) {
//		ArrayList<Tick> retval = new ArrayList<Tick>();
//		
//		return retval;
//	}

}
