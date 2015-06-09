package com.onenow.io;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.amazonaws.services.s3.AmazonS3;

public class S3Test {

  @Test
  public void S3() {
	  AmazonS3 connection = S3.connection;
	  Assert.assertTrue(connection!=null);
  }
}
