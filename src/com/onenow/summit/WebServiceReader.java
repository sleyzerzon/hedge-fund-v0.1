package com.onenow.summit;

/**
 * Talk via tunnel.
 * 
 * @see JdbcFacade
 * 
 * @see HiveServer
 * 
 * @see TunnelPortManager
 * 
 * @see <a
 *      href="http://docs.aws.amazon.com/ElasticMapReduce/latest/DeveloperGuide/emr-web-interfaces.html">View
 *      Web Interfaces Hosted on Amazon EMR Clusters</a>
 * 
 * @see <a href=
 *      "http://hadoop.apache.org/docs/r2.4.1/hadoop-yarn/hadoop-yarn-site/ResourceManagerRest.html"
 *      >ResourceManager REST API's.</a>
 */

// ssh -i ~/.ssh/enrado2.pem -N -L
// 19026:ec2-54-235-51-113.compute-1.amazonaws.com:9026
// hadoop@ec2-54-235-51-113.compute-1.amazonaws.com

// http://localhost:19026/ws/v1/cluster/metrics

//http://localhost:19026/ws/v1/cluster/apps/application_1412633138189_0025
public abstract class WebServiceReader {
	// ~
	public WebServiceReader() {
		// TODO Auto-generated constructor stub
	}

}
