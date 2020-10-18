package puzzle_city_ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.json.JSONException;
import org.json.JSONObject;

import puzzle_city_client.Client;
import puzzle_city_client_model.ApiEnum;
import puzzle_city_client_model.SendPackage;

import javax.swing.JCheckBox;

public class CreateVehiculeSensor {


	public JFrame frame;
	private JTextField txtAddressSensor;
//	private JTextField txtLat;
//	private JTextField txtLong;
//	private JTextField txtHeight;
//	private JTextField txtWidth;
//	private JTextField txtMapZoom;
	private JLabel lbtMess;

	public 	Client client ;//= new Client("127.0.0.1", 4000);
	/**
	 * Launch the application.
	 */
	private JTextField txtIDSensor;

	/**
	 * Create the application.
	 */
	public CreateVehiculeSensor(Client socket) {
		client = socket;
		initialize();
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
		
		JLabel lblNewLabel_1 = new JLabel("Sensor Address");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setBounds(10, 168, 179, 14);
		panel_cityinfo.add(lblNewLabel_1);
		
		txtAddressSensor = new JTextField();
		txtAddressSensor.setBounds(212, 165, 315, 20);
		panel_cityinfo.add(txtAddressSensor);
		txtAddressSensor.setColumns(10);
		
//		JLabel lblNewLabel_1_1 = new JLabel("\u00A7\u00A7");
//		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.RIGHT);
//		lblNewLabel_1_1.setBounds(10, 39, 179, 14);
//		panel_cityinfo.add(lblNewLabel_1_1);
		
//		txtLat = new JTextField();
//		txtLat.setColumns(10);
//		txtLat.setBounds(212, 39, 315, 20);
//		panel_cityinfo.add(txtLat);
		
//		txtLong = new JTextField();
//		txtLong.setColumns(10);
//		txtLong.setBounds(212, 67, 315, 20);
//		panel_cityinfo.add(txtLong);
		
//		txtHeight = new JTextField();
//		txtHeight.setColumns(10);
//		txtHeight.setBounds(212, 95, 315, 20);
//		panel_cityinfo.add(txtHeight);
//		
//		JLabel lblNewLabel_1_1_1_1 = new JLabel("!!");
//		lblNewLabel_1_1_1_1.setHorizontalAlignment(SwingConstants.RIGHT);
//		lblNewLabel_1_1_1_1.setBounds(10, 95, 179, 14);
//		panel_cityinfo.add(lblNewLabel_1_1_1_1);
//		
//		JLabel lblNewLabel_1_1_1_1_1 = new JLabel("!!");
//		lblNewLabel_1_1_1_1_1.setHorizontalAlignment(SwingConstants.RIGHT);
//		lblNewLabel_1_1_1_1_1.setBounds(10, 126, 179, 14);
//		panel_cityinfo.add(lblNewLabel_1_1_1_1_1);
		
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
//		JLabel lbtMapZoom = new JLabel("!!");
//		lbtMapZoom.setHorizontalAlignment(SwingConstants.RIGHT);
//		lbtMapZoom.setBounds(10, 154, 179, 14);
//		panel_cityinfo.add(lbtMapZoom);
		
		JButton btnCreate = new JButton("Create");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				createSensor();
			}
		});
		btnCreate.setBounds(154, 254, 89, 23);
		panel_cityinfo.add(btnCreate);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VehiculeSensorList sa = new VehiculeSensorList(client);
				sa.frame.setVisible(true);
				frame.dispose();
			}
		});
		btnCancel.setBounds(383, 254, 89, 23);
		panel_cityinfo.add(btnCancel);
		
		lbtMess = new JLabel("");
		lbtMess.setBounds(212, 219, 315, 47);
		panel_cityinfo.add(lbtMess);
		
		JLabel lblNewLabel_2 = new JLabel("Create a new sensor");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_2.setBounds(259, 11, 193, 14);
		panel_cityinfo.add(lblNewLabel_2);
		
		JLabel lblNewLabel = new JLabel("Vehicule sensor Manager System");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(160, 11, 342, 27);
		panel.add(lblNewLabel);
	}

	
	private void setField(JSONObject res) {
		// TODO Auto-generated method stub
		try {
			txtAddressSensor.setText(res.getString("Address"));
//			txtHeight.setText(String.valueOf( res.getDouble("Height")));
//			txtWidth.setText(String.valueOf( res.getDouble("Width")));
//			txtLat.setText(String.valueOf( res.getDouble("CenterLat")));
//			txtLong.setText(String.valueOf( res.getDouble("CenterLong")));
//			txtMapZoom.setText(String.valueOf( res.getInt("MapZoom")));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	

	private void createSensor() {
		try {			
			client.setResponseData(null);		
			JSONObject bodyItem = new JSONObject();
			bodyItem.put("ID", "0");
			bodyItem.put("Address", "" +txtAddressSensor.getText());
//			bodyItem.put("height", Double.parseDouble( txtHeight.getText()));
//			bodyItem.put("width", txtWidth.getText());
//			bodyItem.put("centerLat", txtLat.getText());
//			bodyItem.put("centerLong", "" +txtLong.getText());
//			bodyItem.put("mapZoom", "" + txtMapZoom.getText());
			
			SendPackage sendPa = new SendPackage();
			sendPa.setApi(ApiEnum.VEHICULESENSOR_CREATE);		
			sendPa.setBody(bodyItem);
			client.setSendP(sendPa);

			JSONObject res = null;
			while(res == null) {
				res = client.getResponseData();
				System.out.println(""+res);
				if(res!= null) {
					// if success
					boolean sMess = res.getBoolean("success");
					if(sMess) {
						lbtMess.setText("Add Success");
					}else {
						lbtMess.setText("Error :"+res.getString("msg") );						
					}
					System.out.println(""+res.toString());
				}
			} Back();

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	void Back() {
		VehiculeSensorList sa = new VehiculeSensorList(client);
		sa.frame.setVisible(true);
		frame.dispose();
	}

	public JTextField getTxtIDSensor() {
		return txtIDSensor;
	}

	public void setTxtIDSensor(JTextField txtIDSensor) {
		this.txtIDSensor = txtIDSensor;
	}
}