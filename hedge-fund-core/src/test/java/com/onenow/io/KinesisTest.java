package com.onenow.io;

import org.junit.Assert;
import org.testng.annotations.Test;

import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorFactory;
import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.StreamName;
import com.onenow.constant.TestValues;
import com.onenow.constant.PriceType;
import com.onenow.data.EventActivityPriceSizeRealtime;
import com.onenow.instrument.Investment;
import com.onenow.util.TimeParser;

public class KinesisTest {

	  @Test
	  public void Kinesis() {
		  
		  Kinesis kin = new Kinesis();
		  Assert.assertTrue(kin.kinesis!=null);
		  
	  }
}
