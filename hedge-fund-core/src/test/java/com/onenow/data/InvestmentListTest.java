package com.onenow.data;


import org.testng.Assert;
import org.testng.annotations.Test;

public class InvestmentListTest {

  @Test
  public void getMainIndices() {	  
		Assert.assertTrue(InvestmentList.SNP500.size()>0);
  }

  @Test
  public void getSNP500() {	  
		Assert.assertTrue(InvestmentList.mainIndices.size()>0);
  }
  
  @Test
  public void getFutures() {	  
		Assert.assertTrue(InvestmentList.futures.size()>0);
  }
  
  @Test
  public void getOptions() {	  
		Assert.assertTrue(InvestmentList.options.size()>0);
  }
  
  @Test
  public void getSomeStocks() {	  
		Assert.assertTrue(InvestmentList.someStocks.size()>0);
  }
  
  @Test
  public void getSomeIndices() {	  
		Assert.assertTrue(InvestmentList.someIndices.size()>0);
  }

}
