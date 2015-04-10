package com.onenow.aws;

import java.util.List;

public class OpenTsdbActivityImpl  //implements CloudPriceLister
//
{

//	@Override
	public AwsPricing onDemandPricing() {
		try {
			AwsPricing pricing = Yak.readPricing();
			return pricing;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<String> getInstanceTypes() {
		return null;
	}

	public List<String> getProducts() {
		return null;
	}

	public List<String> getRegions() {
		return null;
	}
}
