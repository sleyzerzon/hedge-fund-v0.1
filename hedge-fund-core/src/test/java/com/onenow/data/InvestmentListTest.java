package com.onenow.data;


import org.testng.Assert;
import org.testng.annotations.Test;

public class InvestmentListTest {

  @Test
  public void getMainIndices() {	  
		Assert.assertTrue(InvestmentList.getMainIndices().size()>0);
  }

  @Test
  public void getSNP500() {	  
		Assert.assertTrue(InvestmentList.getSomeIndices().size()>0);
  }
  
  @Test
  public void getFutures() {	  
		Assert.assertTrue(InvestmentList.getFutures().size()>0);
  }
  
  @Test
  public void getOptions() {	  
		Assert.assertTrue(InvestmentList.getOptions().size()>0);
  }
  
  @Test
  public void getSomeStocks() {	  
		Assert.assertTrue(InvestmentList.getSomeStocks().size()>0);
  }
  
  @Test
  public void getSomeIndices() {	  
		Assert.assertTrue(InvestmentList.getSomeIndices().size()>0);
  }

}
