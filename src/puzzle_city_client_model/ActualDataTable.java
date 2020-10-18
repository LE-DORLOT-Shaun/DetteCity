package puzzle_city_client_model;

import javax.swing.table.AbstractTableModel;

public class ActualDataTable extends AbstractTableModel {
	  public final static String[] columnNames = {
			  "Date" , "AvgDistance" , "LabelTravelMode" , "CO2Emission" , "Nb" 
		    };
	  public Object[][] values = {{"","","","",""}};
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
	    public String getColumnName(int column){
	        return columnNames[column];
	    }
}
