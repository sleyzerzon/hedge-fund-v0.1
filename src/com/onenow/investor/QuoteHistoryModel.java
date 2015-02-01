package com.onenow.investor;

import com.ib.client.Types.BarSize;
import com.ib.client.Types.DurationUnit;
import com.ib.client.Types.WhatToShow;
import com.onenow.investor.InvestorController.IHistoricalDataHandler;

public class QuoteHistoryModel {
//	final ContractPanel m_contractPanel = new ContractPanel(m_contract);
//	final UpperField m_end = new UpperField();
//	final UpperField m_duration = new UpperField();
//	final TCombo<DurationUnit> m_durationUnit = new TCombo<DurationUnit>( DurationUnit.values() );
//	final TCombo<BarSize> m_barSize = new TCombo<BarSize>( BarSize.values() );
//	final TCombo<WhatToShow> m_whatToShow = new TCombo<WhatToShow>( WhatToShow.values() );
//	final JCheckBox m_rthOnly = new JCheckBox();
	
	private InvestorController controller;
	
	public QuoteHistoryModel() {
	
	}

	public QuoteHistoryModel(InvestorController cont) {
		setController(cont);
	}

	void addContract(Contract cont) {
		int duration = 1;
		boolean rthOnly=false;
		
		QuoteBar panel = new QuoteBar();
		getController().reqHistoricalData(cont, "20120101 12:00:00", duration, 
				DurationUnit.WEEK, BarSize._1_hour, WhatToShow.MIDPOINT, 
				rthOnly, panel);
	}

	private InvestorController getController() {
		return controller;
	}

	private void setController(InvestorController controller) {
		this.controller = controller;
	}
	
//	void addContract( Contract contract) {
//		Quote row = new Quote( this, contract.description() );
//		m_rows.add( row);
//		getController().reqMktData(contract, "", false, row);
//		fireTableRowsInserted( m_rows.size() - 1, m_rows.size() - 1);
//	}

}
	
	
	
	
	
	
	
	
	
