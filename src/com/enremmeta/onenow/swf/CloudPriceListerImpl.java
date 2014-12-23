package com.enremmeta.onenow.swf;

import java.util.ArrayList;
import java.util.List;

import com.enremmeta.onenow.summit.AwsPricing;
import com.enremmeta.onenow.summit.AwsPricing.InstanceType;
import com.enremmeta.onenow.summit.AwsPricing.RegionPricing;
import com.enremmeta.onenow.summit.Yak;

public class CloudPriceListerImpl implements CloudPriceLister {

	@Override
	public AwsPricing onDemandPricing() {
		try {
			AwsPricing pricing = Yak.readPricing();
			return pricing;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<String> getRegions() {
		try {
			AwsPricing pricing = Yak.readPricing();
			List<RegionPricing> regPricing = pricing.getConfig().getRegions();
			List<String> regions = new ArrayList<String>();
			for (RegionPricing rp : regPricing) {
				regions.add(rp.getRegion());
			}
			return regions;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<String> getInstanceTypes() {
		try {
			AwsPricing pricing = Yak.readPricing();
			List<RegionPricing> regPricing = pricing.getConfig().getRegions();
			List<String> instanceTypes = new ArrayList<String>();
			for (RegionPricing rp : regPricing) {
				// Take US east as its the best one
				if (rp.getRegion().equals("us-east")) {
					for (InstanceType iType : rp.getInstanceTypes()) {
						iType.getType();
					}
				}
			}
			return instanceTypes;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
