package com.enremmeta.onenow.summit;

import java.util.List;

import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.DescribeRegionsResult;
import com.amazonaws.services.ec2.model.DescribeSpotPriceHistoryRequest;
import com.amazonaws.services.ec2.model.Region;
import com.amazonaws.services.elasticmapreduce.model.ListClustersResult;

public class SpotCollector {

	public SpotCollector() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] argv) throws Exception {
		YakConfig config = Utils.readConfig();
		OpenTsdbConnection tsdb = OpenTsdbConnection.getInstance();

		List<Account> accounts = config.getAccounts();
		
		for (Account acc : accounts) {
			EmrFacade emr = new EmrFacade(acc.getAws().getAccess(), acc
					.getAws().getSecret());
			
			ListClustersResult lsCluRes = emr.getEmr().listClusters();
			List<ClusterSummary> clSum = lsCluRes.getClusters();
			
			
			AmazonEC2Client client = aws.getEC2Client();
			DescribeRegionsResult descRegRes = client.describeRegions();
			for (Region region : descRegRes.getRegions()) {
				
				Logger.log("Region: " + region.getRegionName());
				
			DescribeSpotPriceHistoryRequest descSpotHistReq = new DescribeSpotPriceHistoryRequest();
			descSpotHistReq.setStartTime(startTime);
			
			client.describeSpotPriceHistory(describeSpotPriceHistoryRequest)
			
		}
	}
}
