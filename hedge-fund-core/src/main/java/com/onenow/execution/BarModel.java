package com.onenow.execution;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import com.onenow.data.EventActivityHistory;

public class BarModel extends AbstractTableModel {

	ArrayList<EventActivityHistory> m_rows;
	
	public BarModel() {
		
	}

	public BarModel(ArrayList<EventActivityHistory> rows) {
		m_rows = rows;
	}

	@Override public int getRowCount() {
		return m_rows.size();
	}

	@Override public int getColumnCount() {
		return 7;
	}
	
	@Override public String getColumnName(int col) {
		switch( col) {
			case 0: return "Date/time";
			case 1: return "Open";
			case 2: return "High";
			case 3: return "Low";
			case 4: return "Close";
			case 5: return "Volume";
			case 6: return "WAP";
			default: return null;
		}
	}

	@Override public Object getValueAt(int rowIn, int col) {
		EventActivityHistory row = m_rows.get( rowIn);
		switch( col) {
			case 0: return row.formattedTime();
			case 1: return row.open;
			case 2: return row.high;
			case 3: return row.low;
			case 4: return row.close;
			case 5: return row.size;
			case 6: return row.wap;
			default: return null;
		}
	}
}

