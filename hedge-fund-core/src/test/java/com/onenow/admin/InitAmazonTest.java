package com.onenow.admin;

import org.junit.Assert;
import org.testng.annotations.Test;

public class InitAmazonTest {

  @Test
  public void getCredentialsProvider() {
	  Assert.assertTrue(InitAmazon.getCredentialsProvider()!=null);
  }
}
