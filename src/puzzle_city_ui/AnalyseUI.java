package puzzle_city_ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import puzzle_city_client.Client;
import puzzle_city_client_model.ApiEnum;
import puzzle_city_client_model.SendPackage;

import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Font;

public class AnalyseUI {

	public JFrame frame;
	Client client;
	private int cityID;
	private JLabel lbtSensors;
	private JLabel lblStations ;
	private JLabel lblBollards;
	private JLabel lblDistance;
	private JLabel lblExceeding;
	private JLabel lblRatePollution;
	
	/**
	 * Create the application.
	 */
	
	public AnalyseUI(Client client, int cID) {
		this.client = client;
		this.cityID = cID;
		initialize();
		
		getSensorInfo();
		getCityInfo();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 700, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(Color.WHITE);
		panel.setBounds(10, 11, 664, 439);
		frame.getContentPane().add(panel);
		panel.setLayout(new GridLayout(0, 2, 0, 8));
		
		JLabel label = new JLabel("Statistics of ");
		label.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(label);
		
		JLabel lblNewLabel = new JLabel("2020-07-03");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("The number of sensors installed in the city : ");
		lblNewLabel_1.setForeground(Color.BLACK);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblNewLabel_1);
		
		lbtSensors = new JLabel();
		lbtSensors.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lbtSensors);
		
		JLabel lblTheNumberOf_1 = new JLabel("The number of Stations : ");
		lblTheNumberOf_1.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblTheNumberOf_1);
		
		lblStations = new JLabel("");
		lblStations.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblStations);
		
		JLabel lblNewLabel_7 = new JLabel("The number of bollards : ");
		lblNewLabel_7.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblNewLabel_7);
		
		lblBollards = new JLabel("");
		lblBollards.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblBollards);
		
		JButton btnNewButton_3 = new JButton("Comparison");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Analyse_comparison c = new Analyse_comparison(client, cityID);
				//c.setVisible(true);
				c.getJFrame().setVisible(true);
			}
		});
		
		JLabel lblNewLabel_8 = new JLabel("Distance of public transit : ");
		lblNewLabel_8.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblNewLabel_8);
		
		lblDistance = new JLabel("");
		lblDistance.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblDistance);
		
		JLabel lblExceedingRateOf = new JLabel("Rate of pollution : ");
		lblExceedingRateOf.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblExceedingRateOf);
		
		lblRatePollution = new JLabel("");
		lblRatePollution.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblRatePollution);
		
		JLabel lblExceedingRateOf_1 = new JLabel("Exceeding rate of pollution : ");
		lblExceedingRateOf_1.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblExceedingRateOf_1);
		
		
		lblExceeding = new JLabel("");
		lblExceeding.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblExceeding);
		
		JLabel lblNewLabel_6 = new JLabel("");
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_6);
		panel.add(btnNewButton_3);
		
	}
	
	
	public JFrame getJFrame() {
		return frame;
	}
	
	private void getCityInfo() {
		try {
			client.setResponseData(null);		
			JSONObject bodyItem = new JSONObject();
			bodyItem.put("ID", "" +cityID);

			SendPackage sendPa = new SendPackage();
			sendPa.setApi(ApiEnum.ANALYSE_ONE_CITY);		
			sendPa.setBody(bodyItem);
			client.setSendP(sendPa);

			JSONObject res = null;
			while(res == null) {
				res = client.getResponseData();

				System.out.println("wait res:"+res);
				if(res!= null) {
					// if success true - get data bind to table 
					setDataToField((res.getJSONArray("data")).getJSONObject(0));
				}
			} 			
			//CLOSE

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	private void setDataToField(JSONObject res) {
		try {
			//lbtSensors.setText(res.getString("CountSensor"));
			lblStations.setText(""+ res.getInt("CountStation"));
			lbtSensors.setText("" + res.getInt("CountSensor"));
			lblBollards.setText("" + res.getInt("CountBollard"));
			lblDistance.setText("" + res.getInt("CountDistance") + " km");
			lblRatePollution.setText("" + (res.getInt("CountRatePollution") + 50) + "%");
			lblExceeding.setText("" + (res.getInt("CountExceeding") - 50) + "%");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}	
	private void getSensorInfo() {
		try {
			client.setResponseData(null);		
			JSONObject bodyItem = new JSONObject();
			bodyItem.put("ID", "" +cityID);

			SendPackage sendPa = new SendPackage();
			sendPa.setApi(ApiEnum.ANALYSE_ONE_CITY);		
			sendPa.setBody(bodyItem);
			client.setSendP(sendPa);

			JSONObject res = null;
			while(res == null) {
				res = client.getResponseData();

				System.out.println("wait res:"+res);
				if(res!= null) {
					// if success true - get data bind to table 
					setDataToField((res.getJSONArray("data")).getJSONObject(0));
				}
			} 			
			//CLOSE

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}	
}
