package puzzle_city_ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import javax.swing.AbstractButton;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import puzzle_city_client.Client;
import puzzle_city_client_model.ApiEnum;
import puzzle_city_client_model.SendPackage;
import puzzle_city_client_model.SensorQualityAirTable;

public class AlertHistory {

	public JFrame frame;
	private JTable tblalerthistory;
	public Client client;// = new Client("127.0.0.1", 4000);
	private int id;
	private String address;
	private int no2;
	private int pm10;
	private int o3;
	private boolean alert;
	private int alert_id;
	private boolean isActivated;
	private int counter;
	private int cron;
	private boolean timerStart;
	private Timer timer;

	List<Object[]> list = new ArrayList<>();

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 * 
	 * @wbp.parser.constructor
	 */
	public AlertHistory(Client socket, int id, String address, int no2, int pm10, int o3, boolean alert, int alert_id,
			boolean isActivated,int counter ,Timer timer,int cron,boolean timerStart) {
		this.id = id;
		this.address = address;
		this.no2 = no2;
		this.pm10 = pm10;
		this.o3 = o3;
		this.alert = alert;
		this.alert_id = alert_id;

		this.isActivated = isActivated;
		this.counter=counter;
		this.timer=timer;
		this.cron=cron;
		this.timerStart=timerStart;
		client = socket;
		initialize();
		getSensorAirData();

//		getSensorAirData();
	}

	public AlertHistory(JTable tblalerthistory) {

		this.tblalerthistory = tblalerthistory;
	}



	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		System.out.println("initialize");
		frame = new JFrame();
		frame.setBounds(100, 100, 700, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(Color.WHITE);
		panel.setBounds(10, 11, 664, 439);
		frame.getContentPane().add(panel);

//		//set data  for table	
		// add city
		JPanel panel_cityinfo_1 = new JPanel();
		panel_cityinfo_1.setLayout(null);
		panel_cityinfo_1.setBounds(10, 64, 644, 364);
		panel.add(panel_cityinfo_1);
		// list city
		JPanel panel_cityinfo = new JPanel();
		panel_cityinfo.setBounds(-10, 0, 644, 364);
		panel_cityinfo_1.add(panel_cityinfo);
		panel_cityinfo.setLayout(null);

		JLabel lblListCity = new JLabel("Alert History");
		lblListCity.setHorizontalAlignment(SwingConstants.LEFT);
		lblListCity.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblListCity.setBounds(264, 28, 189, 27);
		panel_cityinfo.add(lblListCity);

		tblalerthistory = new JTable(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"no2", "pm10", "o3", "Alert time"
			}
		));

		tblalerthistory.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int row = tblalerthistory.getSelectedRow();
				int id = Integer.parseInt(list.get(row)[0].toString());
				String address = list.get(row)[1].toString();
				int no2 = (int) list.get(row)[2];
				int pm10 = (int) list.get(row)[3];
				int o3 = (int) list.get(row)[4];
				boolean alert = (boolean) list.get(row)[5];
				int alertId = (int) list.get(row)[7];
				// System.out.println("ppp" + globalModel.getColumnCount());
				ConfigSensorAir cS = new ConfigSensorAir(client, id, address, no2, pm10, o3, alert, alertId,
						isActivated);
				cS.frame.setVisible(true);
				frame.dispose();

			}
		});

		JScrollPane jsp = new JScrollPane(tblalerthistory);
		jsp.setBounds(23, 66, 611, 217);
		panel_cityinfo.add(jsp);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConfigSensorAir cS = new ConfigSensorAir(client, id, address, no2, pm10, o3, alert, alert_id,
						isActivated,counter,timer,cron,timerStart);
				cS.frame.setVisible(true);
				frame.dispose();
			}
		});
		btnCancel.setBackground(Color.WHITE);
		btnCancel.setBounds(257, 302, 121, 23);
		panel_cityinfo.add(btnCancel);

		JLabel lblNewLabel = new JLabel("Air Quality Sensors Details\r\n");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(159, 11, 337, 27);
		panel.add(lblNewLabel);

	}

	private void getSensorAirData() {

		try {
			// TODO Auto-generated method stub
			client.setResponseData(null);
			SendPackage sendP = new SendPackage();
			sendP.setApi(ApiEnum.ALERT_HISTORY_FIND_ALL);
			JSONObject bodyItem = new JSONObject();

			bodyItem.put("id", "" + this.alert_id);
	

			
			sendP.setBody(bodyItem);
			client.setSendP(sendP);
			JSONObject res = null;
			while (res == null) {

				res = client.getResponseData();
				System.out.println("waiting:" + res);
				if (res != null) {

					// System.out.println("waiting:" + res);

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
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		client.setResponseData(null);
	}

	private void bindDataToTable(JSONArray jArray) {
		// globalModel = new DefaultTableModel();
		DefaultTableModel model = new DefaultTableModel();
		String[] columnNames = { "Alert time","no2" , "pm10" , "o3"   };
		model.setColumnIdentifiers(columnNames);

		
		for (int i = 0; i < jArray.length(); i++) {
			JSONObject jb;
			try {
				jb = jArray.getJSONObject(i);

				Object[] rowData = { jb.getString("dateAlert"),jb.getInt("no2Simulation"),
						jb.getInt("pm10Simulation"), jb.getInt("o3Simulation") };

				// globalModel.addRow(globalrowData);
				model.addRow(rowData);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch blockkan
				e.printStackTrace();
			}
		}

		tblalerthistory.setModel(model);
   

	}

}
