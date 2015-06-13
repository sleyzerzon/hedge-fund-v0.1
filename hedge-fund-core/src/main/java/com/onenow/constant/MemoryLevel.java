package com.onenow.constant;

/**
 * L0: investor application memory
 * L1: ElastiCache
 * L2: Time Series Database
 * L3: 3rd party database via API
 * @author pablo
 *
 */

public enum MemoryLevel {

	L0INPROCESS, L1ELASTICACHE, L2TSDB, L3PARTNER
	
}
