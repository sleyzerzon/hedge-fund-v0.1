package com.onenow.aws;


public class SummitWorkflowImpl implements SummitWorkflow {

	@Override
	public void mainFlow() {
		// TODO Auto-generated method stub
		
	}
//
////	private CloudPriceListerClient cloudPriceLister = new CloudPriceListerClientImpl();
////	private DatabaseSystemActivityClient sforce = new DatabaseSystemActivityClientImpl();
//	private SummitWorkflowSelfClient selfClient = new SummitWorkflowSelfClientImpl();
//
//	private int counter = 0;
//
//	@Asynchronous
//	void processCloudList(Promise<List<Cloud__c>> cloudPromise) {
//		List<Cloud__c> clouds = cloudPromise.get();
//		Set<String> providers = new HashSet<String>();
//		for (Cloud__c cloud : clouds) {
//			String provider = cloud.getProvider__c();
//			System.out.println(provider);
//			providers.add(provider);
//		}
//		for (String provider : providers) {
////			Promise<List<String>> instTypes = cloudPriceLister
////					.getInstanceTypes(provider);
////			processInstanceTypes(provider, instTypes);
//
//		}
//	}
//
//	@Asynchronous
//	void processInstanceTypes(String provider,
//			Promise<List<String>> instTypesPromise) {
//		List<String> instTypes = instTypesPromise.get();
//		System.out.println("Provider " + provider
//				+ " has these instance types: " + instTypes);
//	}
//
//	@Override
//	public void mainFlow() {
////		Promise<List<Cloud__c>> clouds = sforce.getClouds();
////		processCloudList(clouds);
//
//	}

}
