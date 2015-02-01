package com.onenow.investor;

import samples.rfq.SampleRfq;
import apidemo.ApiDemo;


public class InvestorMain {
	
	public static void main(String[] args) {
		
		InteractiveBrokers ib = new InteractiveBrokers();
		
		ib.run();
		
//	      try {
//	          int rfqId = (int) (System.currentTimeMillis() / 1000);
//	          int mode = (args.length > 0) ? Integer.parseInt(args[0]) : 0;
//	          SampleRfq ut = new SampleRfq(0, rfqId, mode);
//	          ut.testOrder();
//	       } catch (Exception e) {
//	          e.printStackTrace();
//	       }


	}


}
