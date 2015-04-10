package com.onenow.risk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.onenow.execution.Contract;

public class RiskReturn {
	
	Contract contract = new Contract();
	
	private HashMap price = new HashMap();
	private HashMap vttr = new HashMap();
	private HashMap dtrr = new HashMap();
	private HashMap rtrr = new HashMap();

	private List<String> strike = new ArrayList<String>();

	
	public RiskReturn(Contract cont) {
		this.contract = cont;
	}

}
