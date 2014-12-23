package com.enremmeta.onenow.swf;

public class CloudPriceListerTest {

	public CloudPriceListerTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] argv) throws Exception {
		CloudPriceLister c = new CloudPriceListerImpl();
		System.out.println(c.getRegions());
		System.out.println(c.getInstanceTypes());

		System.out.println(c.getProducts());
	}
}
