package com.onenow.broker;

import java.util.ArrayList;
import java.util.List;

import com.onenow.finance.Investment;
import com.onenow.finance.Trade;
import com.onenow.finance.Transaction;
import com.onenow.finance.Underlying;
import com.onenow.summit.AwsPricing;
import com.onenow.summit.AwsPricing.InstanceType;
import com.onenow.summit.AwsPricing.RegionPricing;
import com.onenow.summit.AwsPricing.Size;
import com.onenow.summit.AwsPricing.ValueColumn;
import com.onenow.summit.Yak;

public class BrokerAws implements BrokerCloud {

	public BrokerAws() {
		// TODO Auto-generated constructor stub
	}

	private AwsPricing pricing = null;

	private void readPricing() throws Exception {
		if (pricing == null) {
			this.pricing = Yak.readPricing();
		}
	}

	public List<String> getRegions(String provider) {
		try {
			readPricing();
			List<RegionPricing> regPricing = pricing.getConfig().getRegions();
			List<String> regions = new ArrayList<String>();
			for (RegionPricing rp : regPricing) {
				regions.add(rp.getRegion());
			}
			return regions;
		} catch (Exception e) {
			return new ArrayList<String>();
		}
	}

	@Override
	public List<Underlying> getUnderlying() {
		List<Underlying> retval = new ArrayList<Underlying>();
		try {
			readPricing();

		} catch (Exception e) {
			return retval;
		}
	}

	public List<String> getProducts(String provider) {
		try {
			readPricing();
			List<RegionPricing> regPricing = pricing.getConfig().getRegions();
			List<String> products = new ArrayList<String>();
			for (RegionPricing rp : regPricing) {
				// Take US east as its the best one
				if (rp.getRegion().equals("us-east")) {
					for (InstanceType iType : rp.getInstanceTypes()) {
						for (Size size : iType.getSizes()) {
							for (ValueColumn vc : size.getValueColumns()) {
								String prod = vc.getName();
								if (!products.contains(prod)) {
									products.add(prod);
								}
							}
						}
					}
				}
			}
			return products;
		} catch (Exception e) {
			return new ArrayList<String>();
		}
	}

	public List<String> getInstanceTypes(String provider) {
		try {
			readPricing();
			List<RegionPricing> regPricing = pricing.getConfig().getRegions();
			List<String> instanceTypes = new ArrayList<String>();
			for (RegionPricing rp : regPricing) {
				// Take US east as its the best one
				if (rp.getRegion().equals("us-east")) {
					for (InstanceType iType : rp.getInstanceTypes()) {
						for (Size size : iType.getSizes()) {
							instanceTypes.add(size.getSize());
						}
					}
				}
			}
			return instanceTypes;
		} catch (Exception e) {
			return new ArrayList<String>();
		}
	}

	@Override
	public List<Investment> getInvestments(boolean myPortfolio) {

		return null;
	}

	@Override
	public Double getPriceAsk(Investment inv) {
		switch (inv.getInvType()) {
		case STOCK:
		case CALL:
		case PUT:
			return 0;
		case SPOT:
			break;
		case ONDEMAND:
		case RESERVED:
			
		}
	}

	@Override
	public Double getPriceBid(Investment inv) {

		return null;
	}

	@Override
	public Investment getBest(Underlying under, Enum invType) {

		return null;
	}

	@Override
	public List<Trade> getTrades() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addTrade(Transaction transaction) {
		// TODO Auto-generated method stub

	}

}
