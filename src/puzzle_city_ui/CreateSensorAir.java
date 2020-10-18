package puzzle_city_ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.json.JSONException;
import org.json.JSONObject;

import puzzle_city_client.Client;
import puzzle_city_client_model.ApiEnum;
import puzzle_city_client_model.SendPackage;

public class CreateSensorAir {

	
	private int counter;
	private int cron;
	private Timer timer;

	public JFrame frame;
	private JTextField txtAddressSensor;

	private JLabel lbtMess;
	private JLabel labelx;

	public Client client;// = new Client("127.0.0.1", 4000);

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public CreateSensorAir(Client socket,int counter,Timer timer,int cron) {
		client = socket;
		this.counter=counter;
		this.timer = timer;
		this.cron=cron;
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
		panel_cityinfo.setBounds(10, 49, 644, 379);
		panel.add(panel_cityinfo);
		panel_cityinfo.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Address of sensor");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setBounds(10, 168, 179, 14);
		panel_cityinfo.add(lblNewLabel_1);

		txtAddressSensor = new JTextField();
		txtAddressSensor.setBounds(212, 165, 315, 20);
		panel_cityinfo.add(txtAddressSensor);
		txtAddressSensor.setColumns(10);

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
				
				SensorAirList sa = new SensorAirList(client, counter, timer, cron);
				sa.frame.setVisible(true);
				frame.dispose();
			}
		});
		btnCancel.setBounds(383, 254, 89, 23);
		panel_cityinfo.add(btnCancel);

		lbtMess = new JLabel("");
		lbtMess.setFont(new Font("Tahoma", Font.BOLD, 13));
		lbtMess.setForeground(new Color(255, 0, 0));
		lbtMess.setBounds(188, 196, 315, 47);
		lbtMess.setVisible(false);
		panel_cityinfo.add(lbtMess);

		JLabel lblNewLabel_2 = new JLabel("Create new sensor");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 21));
		lblNewLabel_2.setBounds(230, 25, 256, 26);
		panel_cityinfo.add(lblNewLabel_2);

		JLabel lblNewLabel = new JLabel("Air Quality Sensors Manager System");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(174, 11, 342, 27);
		panel.add(lblNewLabel);
	}

	private void setField(JSONObject res) {
		// TODO Auto-generated method stub
		try {
			txtAddressSensor.setText(res.getString("address"));

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void createSensor() {
		try {
			final String address = txtAddressSensor.getText();
			if (!isNotEmpty(address)) {
				JOptionPane.showMessageDialog(frame, "Empty field !");
				
				return;
			}
			if (isInvalidData(address)) {
				JOptionPane.showMessageDialog(frame, "Invalid data !");
				 txtAddressSensor.setText("");
				return;
			}
			if (checkLength(address)) {
				JOptionPane.showMessageDialog(frame, "Too long !");
				txtAddressSensor.setText("");
				return;
				
			}
			client.setResponseData(null);
			JSONObject bodyItem = new JSONObject();
			bodyItem.put("id", "0");
			bodyItem.put("address", "" + address);

			SendPackage sendPa = new SendPackage();
			sendPa.setApi(ApiEnum.SENSORAIR_CREATE);
			sendPa.setBody(bodyItem);
			client.setSendP(sendPa);

			JSONObject res = null;
			while (res == null) {
				res = client.getResponseData();
				System.out.println("cho tra ve:" + res);
				if (res != null) {
					// if success
					boolean sMess = res.getBoolean("success");
					if (sMess) {
						lbtMess.setText("Add Success");
					} else {
						lbtMess.setText("Error :" + res.getString("msg"));
					}
					System.out.println("tra ve:" + res.toString());
				}
			}
			Back();

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean isNotEmpty(String text) {
		return text != null & !text.isEmpty();
	}

	private boolean isInvalidData(String text) {
		Pattern p = Pattern.compile("[^a-z0-9- ]", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(text);
		return m.find();

	}
	private boolean checkLength(String text) {
		return text.length() >= 15;
	}

	void Back() {
		SensorAirList sa = new SensorAirList(client,0,null,0);
		sa.frame.setVisible(true);
		frame.dispose();
	}
	
}
