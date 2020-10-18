package puzzle_city_ui;


import java.awt.Color;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.toedter.calendar.JDateChooser;

import puzzle_city_client.Client;
import puzzle_city_client_model.ActualDataTable;
import puzzle_city_client_model.ApiEnum;
import puzzle_city_client_model.CarbonDataTable;
import puzzle_city_client_model.CityTable;
import puzzle_city_client_model.SendPackage;

import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.table.TableModel;
import java.awt.SystemColor;


public class CarbonFootprint extends JFrame {

	private JPanel contentPane;
	public Client client; // = new Client("127.0.0.1", 4000);
	
	
	//private JTable table;
	private JTable table_1;
	private JTable table_2;
	
	private JTextField txtNumber;
	private JTextField txtAverageDistancekm;
	private JTextField txtDate;
	private JButton btnNewButton;
	private JTextField txtGlobal;
	private JTextField textField_28;
	
	private JSpinner spinner;
	private JSpinner spinner_2;
	private JSpinner spinner_3;
	private JSpinner spinner_4;
	private JSpinner spinner_5;
	private JSpinner spinner_6;
	private JSpinner spinner_7;
	private JSpinner spinner_1;
	private JSpinner spinner_8;
	private JSpinner spinner_9;
	private JSpinner spinner_10;
	private JSpinner spinner_11;
	private JSpinner spinner_12;
	private JSpinner spinner_13;
	private JPanel panel_3;
	private JScrollPane scrollPane_2;
	private JTable table_3;
	
	
	
/*
	  
	// Launch the application. 
	  
	  
	public static void main(String[] args) {
		client = new Client("127.0.0.1", 4000);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CarbonFootprint frame = new CarbonFootprint(client);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	*/

	
	
	
	/**
	 * Create the application.
	 */
	
	  public CarbonFootprint(Client socket) {
		client = socket;
		initialize();

		getActualData();
		getCarbonData();
	}
	
	
	

	/**
	 * Create the frame.
	 */
	
	
	private void initialize() {
          
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 884, 351);
		contentPane.add(tabbedPane);
		
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(245, 245, 245));
		panel_1.setBounds(0, 5, 854, 346);
		panel_1.setLayout(null);
		tabbedPane.addTab("Simulator", null, panel_1, null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 93, 486, 142);
		panel_1.add(scrollPane);
		
		
		
		
		//int max= table_3.getModel().getValueAt(0,1);
		spinner = new JSpinner();
		spinner.setFont(new Font("Arial", Font.PLAIN, 12));
		spinner.setModel(new SpinnerNumberModel(0, 0, 15000, 1));
		spinner.setBounds(499, 116, 118, 15);
		panel_1.add(spinner);
		
		
		
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setViewportBorder(new LineBorder(new Color(0, 0, 0)));
		scrollPane_1.setBounds(741, 93, 128, 142);
		panel_1.add(scrollPane_1);
		
		
		table_1 = new JTable();
		//table.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		scrollPane_1.setViewportView(table_1);
		
		txtNumber = new JTextField();
		txtNumber.setEditable(false);
		txtNumber.setForeground(new Color(204, 0, 102));
		txtNumber.setFont(new Font("Arial", Font.BOLD, 13));
		txtNumber.setHorizontalAlignment(SwingConstants.CENTER);
		txtNumber.setText("Number\r\n");
		txtNumber.setBounds(498, 92, 118, 23);
		panel_1.add(txtNumber);
		txtNumber.setColumns(10);
		
		txtAverageDistancekm = new JTextField();
		txtAverageDistancekm.setEditable(false);
		txtAverageDistancekm.setForeground(new Color(204, 0, 102));
		txtAverageDistancekm.setFont(new Font("Arial", Font.BOLD, 11));
		txtAverageDistancekm.setHorizontalAlignment(SwingConstants.CENTER);
		txtAverageDistancekm.setText("Avg distance (km)");
		txtAverageDistancekm.setColumns(10);
		txtAverageDistancekm.setBounds(619, 92, 118, 23);
		panel_1.add(txtAverageDistancekm);
		
		txtDate = new JTextField();
		txtDate.setEditable(false);
		txtDate.setForeground(new Color(204, 0, 102));
		txtDate.setBackground(new Color(255, 255, 255));
		txtDate.setFont(new Font("Arial", Font.BOLD, 14));
		txtDate.setHorizontalAlignment(SwingConstants.CENTER);
		txtDate.setText("Date");
		txtDate.setColumns(10);
		txtDate.setBounds(12, 46, 65, 23);
		panel_1.add(txtDate);
		
		btnNewButton = new JButton("Simulate");
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setBackground(new Color(70, 130, 180));
		btnNewButton.setFont(new Font("Arial", Font.BOLD, 15));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				
				setModelTable(table_1);
				
				
				
				
			}
		});
		btnNewButton.setBounds(199, 262, 118, 32);
		panel_1.add(btnNewButton);
		
		txtGlobal = new JTextField();
		txtGlobal.setEditable(false);
		txtGlobal.setForeground(new Color(204, 0, 102));
		txtGlobal.setHorizontalAlignment(SwingConstants.CENTER);
		txtGlobal.setFont(new Font("Arial", Font.BOLD, 14));
		txtGlobal.setText("Global (kg)");
		txtGlobal.setColumns(10);
		txtGlobal.setBounds(741, 241, 128, 24);
		panel_1.add(txtGlobal);
		
		textField_28 = new JTextField();
		textField_28.setEditable(false);
		textField_28.setColumns(10);
		textField_28.setBounds(741, 268, 128, 22);
		panel_1.add(textField_28);
		
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(99, 46, 103, 23);
		panel_1.add(dateChooser);
		
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(0, 5, 854, 346);
		panel_2.setLayout(null);
		tabbedPane.addTab("Actual Data", null, panel_2, null);
		
		
		
		JScrollPane scrollPane_12 = new JScrollPane();
		scrollPane_12.setBounds(10, 55, 784, 177);
		panel_2.add(scrollPane_12);
		ActualDataTable adt = new ActualDataTable();
		table_2 = new JTable(adt);
		table_2.setEnabled(false);
		table_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		spinner_2 = new JSpinner();
		spinner_2.setFont(new Font("Arial", Font.PLAIN, 12));
		spinner_2.setModel(new SpinnerNumberModel(0, 0, 100000, 1));
		spinner_2.setBounds(499, 132, 118, 15);
		panel_1.add(spinner_2);
		
		spinner_3 = new JSpinner();
		spinner_3.setFont(new Font("Arial", Font.PLAIN, 12));
		spinner_3.setModel(new SpinnerNumberModel(0, 0, 90000, 1));
		spinner_3.setBounds(499, 148, 118, 15);
		panel_1.add(spinner_3);
		
		spinner_4 = new JSpinner();
		spinner_4.setFont(new Font("Arial", Font.PLAIN, 12));
		spinner_4.setModel(new SpinnerNumberModel(0, 0, 20000, 1));
		spinner_4.setBounds(499, 164, 118, 15);
		panel_1.add(spinner_4);
		
		spinner_5 = new JSpinner();
		spinner_5.setFont(new Font("Arial", Font.PLAIN, 12));
		spinner_5.setModel(new SpinnerNumberModel(0, 0, 8000, 1));
		spinner_5.setBounds(499, 180, 118, 15);
		panel_1.add(spinner_5);
		
		spinner_6 = new JSpinner();
		spinner_6.setFont(new Font("Arial", Font.PLAIN, 12));
		spinner_6.setModel(new SpinnerNumberModel(0, 0, 200, 1));
		spinner_6.setBounds(499, 196, 118, 15);
		panel_1.add(spinner_6);
		
		spinner_7 = new JSpinner();
		spinner_7.setFont(new Font("Arial", Font.PLAIN, 12));
		spinner_7.setModel(new SpinnerNumberModel(0, 0, 100, 1));
		spinner_7.setBounds(499, 212, 118, 15);
		panel_1.add(spinner_7);
		
		spinner_1 = new JSpinner();
		spinner_1.setFont(new Font("Arial", Font.PLAIN, 12));
		spinner_1.setModel(new SpinnerNumberModel(new Double(0), new Double(0), null, new Double(1)));
		spinner_1.setBounds(619, 116, 118, 15);
		panel_1.add(spinner_1);
		
		spinner_8 = new JSpinner();
		spinner_8.setFont(new Font("Arial", Font.PLAIN, 12));
		spinner_8.setModel(new SpinnerNumberModel(new Double(0), new Double(0), null, new Double(1)));
		spinner_8.setBounds(619, 132, 118, 15);
		panel_1.add(spinner_8);
		
		spinner_9 = new JSpinner();
		spinner_9.setFont(new Font("Arial", Font.PLAIN, 12));
		spinner_9.setModel(new SpinnerNumberModel(new Double(0), new Double(0), null, new Double(1)));
		spinner_9.setBounds(619, 148, 118, 15);
		panel_1.add(spinner_9);
		
		spinner_10 = new JSpinner();
		spinner_10.setFont(new Font("Arial", Font.PLAIN, 12));
		spinner_10.setModel(new SpinnerNumberModel(new Double(0), new Double(0), null, new Double(1)));
		spinner_10.setBounds(619, 164, 118, 15);
		panel_1.add(spinner_10);
		
		spinner_11 = new JSpinner();
		spinner_11.setFont(new Font("Arial", Font.PLAIN, 12));
		spinner_11.setModel(new SpinnerNumberModel(new Double(0), new Double(0), null, new Double(1)));
		spinner_11.setBounds(619, 180, 118, 15);
		panel_1.add(spinner_11);
		
		spinner_12 = new JSpinner();
		spinner_12.setFont(new Font("Arial", Font.PLAIN, 12));
		spinner_12.setModel(new SpinnerNumberModel(new Double(0), new Double(0), null, new Double(1)));
		spinner_12.setBounds(619, 196, 118, 15);
		panel_1.add(spinner_12);
		
		spinner_13 = new JSpinner();
		spinner_13.setFont(new Font("Arial", Font.PLAIN, 12));
		spinner_13.setModel(new SpinnerNumberModel(new Double(0), new Double(0), null, new Double(1)));
		spinner_13.setBounds(619, 212, 118, 15);
		panel_1.add(spinner_13);
		
		
		scrollPane_12.setViewportView(table_2);
		
		JButton btnNewButton_1 = new JButton("Refresh");
		btnNewButton_1.setFont(new Font("Arial", Font.BOLD, 12));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				getActualData() ;
				
				
				
					
					
				//	setModelTable(table_1);
					
					
					
					
				
				
				
			}
		});
		btnNewButton_1.setBounds(10, 264, 109, 30);
		panel_2.add(btnNewButton_1);
		CarbonDataTable cdt = new CarbonDataTable();
		table_3 = new JTable(cdt);
		table_3.setEnabled(false);
		
		table_3.setFont(new Font("Arial", Font.PLAIN, 12));
		table_3.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		scrollPane.setViewportView(table_3);
		
		//scrollPane_3.setViewportView(table_3);
		
		
		
		
	
		JPanel panel = new JPanel();
		panel.setBounds(0, 5, 874, 351);
		contentPane.add(panel);
		panel.setLayout(null);
		this.setVisible(true);
	}	
		
	
	
	
		
		
	
	private void getActualData() {
		// TODO Auto-generated method stub
		client.setResponseData(null);
		SendPackage sendP = new SendPackage();
		sendP.setApi(ApiEnum.ActualData_FIND_ALL);
		client.setSendP(sendP);
		JSONObject res = null;
		while (res == null) {
			res = client.getResponseData();
			System.out.println("waiting:" + res);
			if (res != null) {
				// if success true - get data bind to table
				System.out.println(res.toString());
				boolean sMess;
				try {
					sMess = res.getBoolean("success");
					if (sMess) {
						bindDataToTable(res.getJSONArray("data"));
					} else {
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		//

		client.setResponseData(null);
	}
	
	
	public void setModelTable(JTable table_1) {
		Object [][] object = new Object[][] {
			{(double) (table_3.getModel().getValueAt(0,1))*(Integer)spinner.getValue()*(double)spinner_1.getValue()},
			{(double) (table_3.getModel().getValueAt(1,1))*(Integer)spinner_2.getValue()*(double)spinner_8.getValue()},
			{(double) (table_3.getModel().getValueAt(2,1))*(Integer)spinner_3.getValue()*(double)spinner_9.getValue()},
			{(double) (table_3.getModel().getValueAt(3,1))*(Integer)spinner_4.getValue()*(double)spinner_10.getValue()},
			{(double) (table_3.getModel().getValueAt(4,1))*(Integer)spinner_5.getValue()*(double)spinner_11.getValue()},
			{(double) (table_3.getModel().getValueAt(5,1))*(Integer)spinner_6.getValue()*(double)spinner_12.getValue()},
			{(double) (table_3.getModel().getValueAt(6,1))*(Integer)spinner_7.getValue()*(double)spinner_13.getValue()}, 
			
			
		};
		//double a = (double) (table_3.getModel().getValueAt(0,1)) ;
		table_1.setModel(new DefaultTableModel(
				object,
			new String[] {
				"Carbon footprint (kg)"
			}
		));
		double sum=0;
		for(int i=0 ; i<7;i++) {
			sum+= (Double) object[i][0];
		}
		
		textField_28.setText(String.valueOf(sum));
		//int one=Integer.parseInt(table_1.getModel().getValueAt(0,1).toString());
		//int two=Integer.parseInt(table_1.getModel().getValueAt(1,1).toString());
		//System.out.println( String.valueOf(one+two));
	}
	
	private void bindDataToTable(JSONArray jArray) {
		// TODO Auto-generated method stub
		DefaultTableModel model = new DefaultTableModel();
		String[] columnNames = { "Date" , "Avg Distance (km)" , "Label travel mode" , "CO2 emission (kg) " , "Number" };
		model.setColumnIdentifiers(columnNames);

		ArrayList arrRows = new ArrayList();
		for (int i = 0; i < jArray.length(); i++) {
			JSONObject jb;
			try {
				jb = jArray.getJSONObject(i);
				System.out.println(jb);
				Object[] rowData = { jb.getString("date"), jb.getDouble("avgDistance"), jb.getString("labelTravelMode"), 
						jb.getDouble("CO2Emission"), jb.getInt("nb"),

			};

				model.addRow(rowData);
				arrRows.clear();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}

		table_2.setModel(model);
       }


/////
	
	private void getCarbonData() {
		// TODO Auto-generated method stub
		client.setResponseData(null);
		SendPackage sendP = new SendPackage();
		sendP.setApi(ApiEnum.CarbonData_FIND_ALL);
		client.setSendP(sendP);
		JSONObject res = null;
		while (res == null) {
			res = client.getResponseData();
			System.out.println("waiting:" + res);
			if (res != null) {
				// if success true - get data bind to table
				System.out.println(res.toString());
				boolean sMess;
				try {
					sMess = res.getBoolean("success");
					if (sMess) {
						bindDataToTable1(res.getJSONArray("data"));
					} else {
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		//

		client.setResponseData(null);
	}

	private void bindDataToTable1(JSONArray jArray) {
		// TODO Auto-generated method stub
		DefaultTableModel model = new DefaultTableModel();
		String[] columnNames = { "Travel mode" , "Emission Factor (kgCO2/km)" , "Maximum number" };
		model.setColumnIdentifiers(columnNames);

		ArrayList arrRows = new ArrayList();
		for (int i = 0; i < jArray.length(); i++) {
			JSONObject jb;
			try {
				jb = jArray.getJSONObject(i);
				System.out.println(jb);
				Object[] rowData = { jb.getString("travelMode"), jb.getDouble("emissionFactor"), jb.getInt("nbMax")
						

			};

				model.addRow(rowData);
				arrRows.clear();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}

		table_3.setModel(model);
       }
}



