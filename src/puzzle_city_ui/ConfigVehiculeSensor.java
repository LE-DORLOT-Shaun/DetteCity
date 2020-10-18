package puzzle_city_ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import puzzle_city_client.Client;
import puzzle_city_client_model.ApiEnum;
import puzzle_city_client_model.SendPackage;
import puzzle_city_client_model.ThresholdTable;

public class ConfigVehiculeSensor {

	public JFrame frame;
	private int ID;
	private JLabel lbtMess;
	private JTable tblthreshold;
	
	private JComboBox cState;
	protected String currenttextAddress;
	protected int currentID;
	private JTextField txtAddress;

	public Client client;// = new Client("127.0.0.1", 4000);

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 * @wbp.parser.constructor
	 */
	public ConfigVehiculeSensor(Client socket) {
		client = socket;
		initialize();
	}
	
	public ConfigVehiculeSensor() {
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 700, 499);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(Color.WHITE);
		panel.setBounds(10, 11, 664, 439);
		frame.getContentPane().add(panel);

		JPanel panel_cityinfo = new JPanel();
		panel_cityinfo.setBounds(10, 64, 644, 364);
		panel.add(panel_cityinfo);
		panel_cityinfo.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Vehicule number threshold");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setBounds(10, 11, 179, 14);
		panel_cityinfo.add(lblNewLabel_1);

		txtAddress = new JTextField();
		txtAddress.setBounds(212, 11, 315, 20);
		panel_cityinfo.add(txtAddress);
		txtAddress.setColumns(10);

//		JLabel lblNewLabel_1_1 = new JLabel("Latitude");
//		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.RIGHT);
//		lblNewLabel_1_1.setBounds(10, 39, 179, 14);
//		panel_cityinfo.add(lblNewLabel_1_1);
//		
//		txtLat = new JTextField();
//		txtLat.setColumns(10);
//		txtLat.setBounds(212, 39, 315, 20);
//		panel_cityinfo.add(txtLat);
////		
//		JLabel lblNewLabel_1_1_1 = new JLabel("Longitude");
//		lblNewLabel_1_1_1.setHorizontalAlignment(SwingConstants.RIGHT);
//		lblNewLabel_1_1_1.setBounds(10, 67, 179, 14);
//		panel_cityinfo.add(lblNewLabel_1_1_1);
//		
//		txtLong = new JTextField();
//		txtLong.setColumns(10);
//		txtLong.setBounds(212, 67, 315, 20);
//		panel_cityinfo.add(txtLong);
//		
//		txtHeight = new JTextField();
//		txtHeight.setColumns(10);
//		txtHeight.setBounds(212, 95, 315, 20);
//		panel_cityinfo.add(txtHeight);
//		
//		JLabel lblNewLabel_1_1_1_1 = new JLabel("Height");
//		lblNewLabel_1_1_1_1.setHorizontalAlignment(SwingConstants.RIGHT);
//		lblNewLabel_1_1_1_1.setBounds(10, 95, 179, 14);
//		panel_cityinfo.add(lblNewLabel_1_1_1_1);
//		
//		JLabel lblNewLabel_1_1_1_1_1 = new JLabel("Width");
//		lblNewLabel_1_1_1_1_1.setHorizontalAlignment(SwingConstants.RIGHT);
//		lblNewLabel_1_1_1_1_1.setBounds(10, 126, 179, 14);
//		panel_cityinfo.add(lblNewLabel_1_1_1_1_1);
//		
//		txtWidth = new JTextField();
//		txtWidth.setColumns(10);
//		txtWidth.setBounds(212, 126, 315, 20);
//		panel_cityinfo.add(txtWidth);
//		
//		txtMapZoom = new JTextField();
//		txtMapZoom.setColumns(10);
//		txtMapZoom.setBounds(212, 154, 315, 20);
//		panel_cityinfo.add(txtMapZoom);
//		
//		JLabel lbtMapZoom = new JLabel("Map Zoom");
//		lbtMapZoom.setHorizontalAlignment(SwingConstants.RIGHT);
//		lbtMapZoom.setBounds(10, 154, 179, 14);
//		panel_cityinfo.add(lbtMapZoom);

		JButton btnCreate = new JButton("Update");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateThresholdInfo();
			}
		});
		btnCreate.setBounds(212, 185, 89, 23);
		panel_cityinfo.add(btnCreate);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VehiculeSensorList sa = new VehiculeSensorList(client);
				sa.frame.setVisible(true);
				frame.dispose();
			}
		});
		btnCancel.setBounds(314, 185, 89, 23);
		panel_cityinfo.add(btnCancel);

		JLabel lblNewLabel = new JLabel("Configure your Vehicule Sensor");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(199, 13, 285, 27);
		panel.add(lblNewLabel);
		
		//table
				ThresholdTable tv = new ThresholdTable();
				tblthreshold = new JTable(new DefaultTableModel(
						new Object[][] { { null, null, null }, { null, null, null }, { null, null, null }, { null, null, null },
								{ null, null, null }, { null, null, null }, { null, null, null }, },
						new String[] { "ID", "Unit", "Value" }) {
					boolean[] columnEditables = new boolean[] { false, false, false };

					public boolean isCellEditable(int row, int column) {
						return columnEditables[column];
					}
				});
				tblthreshold.addMouseListener(new MouseAdapter() {
				
					@Override
					public void mouseClicked(MouseEvent arg0) {
						int row = tblthreshold.getSelectedRow();
						currentID = Integer.parseInt(tblthreshold.getModel().getValueAt(row, 0).toString()) ;
						currenttextAddress = tblthreshold.getModel().getValueAt(row, 1).toString() ;
						txtAddress.setText(currenttextAddress);

						if(Boolean.parseBoolean( tblthreshold.getModel().getValueAt(row, 2).toString())) {
							cState.setSelectedIndex(1);}else cState.setSelectedIndex(0);
					}
				});
				// 
		        JScrollPane jsp = new JScrollPane(tblthreshold);
		        jsp.setBounds(20, 49, 593, 199);
				panel_cityinfo.add(jsp);
	}

	private void getTresholdData() {
		// TODO Auto-generated method stub		
		client.setResponseData(null);
		SendPackage sendP = new SendPackage();
		sendP.setApi(ApiEnum.THRESHOLD_FIND_ALL);		
		client.setSendP(sendP);
		JSONObject res = null;
		while(res == null) {
			res = client.getResponseData();
			System.out.println("waiting:"+res);
			if(res!= null) {
				// if success true - get data bind to table 
				System.out.println(res.toString());
				boolean sMess;
				try {
					sMess = res.getBoolean("success");				
					if(sMess) {
						bindDataToTable(res.getJSONArray("data"));
					}else {						
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}	
	
	private void bindDataToTable(JSONArray jArray) {
		// TODO Auto-generated method stub	
		DefaultTableModel model = new DefaultTableModel();		
		String[] columnNames = {
				  "ID",  "Unit", "Value"
			    };
		model.setColumnIdentifiers(columnNames);

		ArrayList arrRows = new ArrayList();  
		for (int i = 0; i < jArray.length(); i++) {
            JSONObject jb;
			try {
				jb = jArray.getJSONObject(i);
			
				Object[] rowData = {
					jb.getInt("ID"),
					jb.getString("Unit"),
					jb.getBoolean("Value")
				};
	    		
				model.addRow(rowData);
				arrRows.clear();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	

		tblthreshold.setModel(model);
		
	}
	
	private void updateThresholdInfo() {
		if(true) {	
			try {
				client.setResponseData(null);		
				JSONObject bodyItem = new JSONObject();
				bodyItem.put("Nb_vehicule_max", "" +txtAddress.getText());

				SendPackage sendPa = new SendPackage();
				sendPa.setApi(ApiEnum.THRESHOLD_UPDATE);		
				sendPa.setBody(bodyItem);
				client.setSendP(sendPa);

				JSONObject res = null;
				while(res == null) {
					res = client.getResponseData();
					System.out.println("wait res:"+res);
					if(res!= null) {
						// if success 
						boolean sMess = res.getBoolean("success");
						if(sMess) {
							lbtMess.setText("Update Success");
						}else {
							lbtMess.setText("Error :"+res.getString("msg") );						
						}
						System.out.println("Return:"+res.toString());
					}
				} 
				getTresholdData();

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}