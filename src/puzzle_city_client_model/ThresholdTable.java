package puzzle_city_client_model;

import javax.swing.table.AbstractTableModel;

public class ThresholdTable extends AbstractTableModel {
	public final static String[] columnNames = { "ID", "Unit", "Value" };
	public Object[][] values = { { "", "", "" } };

	public int getRowCount() {
		return values.length;
	}

	public int getColumnCount() {
		return values[0].length;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		return values[rowIndex][columnIndex];
	} 

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}
}       