package com.onenow.io;

import org.influxdb.InfluxDB;
import org.testng.Assert;
import org.testng.annotations.Test;

public class databaseTimeSeriesTest {

  @Test
  public void dbConnect() {
	  InfluxDB db = databaseTimeSeries.dbConnect();
	  Assert.assertTrue(db!=null);
  }
}
