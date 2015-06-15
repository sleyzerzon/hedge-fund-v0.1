package com.onenow.data;

import org.testng.Assert;
import org.testng.annotations.Test;

public class EventTest {

  @Test
  public void Event() {
	  
	  Event event = new Event();
	  
	  Assert.assertTrue(    event.index==null &&
			  				event.option==null &&
			  				event.future==null &&
			  				event.stock==null);
	  
  }
}
