package com.enremmeta.onenow.summit;

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
 */
public abstract class WebServiceReader {
	// ssh –i ~/.ssh/enrado2.pem -N -L
	// 19026:ec2-54-235-51-113.compute-1.amazonaws.com:9026
	// hadoop@ec2-54-235-51-113.compute-1.amazonaws.com

	public WebServiceReader() {
		// TODO Auto-generated constructor stub
	}

}
