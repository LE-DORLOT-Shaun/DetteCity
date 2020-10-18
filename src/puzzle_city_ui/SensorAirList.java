package puzzle_city_ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
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
import javax.swing.JSlider;
import java.awt.SystemColor;
import javax.swing.JTextField;

public class SensorAirList {

	public JFrame frame;
	private JTable tblsensorair;
	public Client client;// = new Client("127.0.0.1", 4000);
	private int counter;
	private int no2;
	private int pm10;
	private int o3;
	private boolean alert;
	List<Object[]> list = new ArrayList<>();
	private Timer timer;
	private boolean isStart = false;

	private JTextField textField;
	private JLabel timeLeft;
	int cron;
	private JSlider sliderTimer;
	private TimerTask task;
	private JLabel labelx;
	SimulationSensorAir simulation;
	boolean timerStart;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 * 
	 * @wbp.parser.constructor
	 */
	public SensorAirList(Client socket, int counter, Timer timer, int cron) {
		this.timer = timer;
		this.counter = counter;

		this.cron = cron;

		client = socket;
		this.simulation = new SimulationSensorAir(socket);
		initialize();

		getSensorAirData();

	}

	public SensorAirList(Client socket) {

		client = socket;
		this.simulation = new SimulationSensorAir(socket);
		initialize();

		getSensorAirData();
	}

	public SensorAirList(JTable tblsensorair) {

		this.tblsensorair = tblsensorair;
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
		panel.setBounds(10, 0, 664, 450);
		frame.getContentPane().add(panel);

		// table
		SensorQualityAirTable tcb = new SensorQualityAirTable();

//		//set data  for table	
		// add city
		JPanel panel_cityinfo_1 = new JPanel();
		panel_cityinfo_1.setLayout(null);
		panel_cityinfo_1.setBounds(10, 43, 644, 385);
		panel.add(panel_cityinfo_1);
		// list city
		JPanel panel_cityinfo = new JPanel();
		panel_cityinfo.setBounds(-11, 0, 645, 385);
		panel_cityinfo_1.add(panel_cityinfo);
		panel_cityinfo.setLayout(null);

		JLabel lblListCity = new JLabel("Air quality sensors list");
		lblListCity.setHorizontalAlignment(SwingConstants.LEFT);
		lblListCity.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblListCity.setBounds(230, 11, 189, 27);
		panel_cityinfo.add(lblListCity);

		tblsensorair = new JTable(new DefaultTableModel(
				new Object[][] { { null, null, null }, { null, null, null }, { null, null, null }, { null, null, null },
						{ null, null, null }, { null, null, null }, { null, null, null }, },
				new String[] { "id", "address", "Action" }) {
			boolean[] columnEditables = new boolean[] { false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});

		tblsensorair.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int row = tblsensorair.getSelectedRow();
				int id = Integer.parseInt(list.get(row)[0].toString());
				String address = list.get(row)[1].toString();
				int no2 = (int) list.get(row)[2];
				int pm10 = (int) list.get(row)[3];
				int o3 = (int) list.get(row)[4];
				boolean alert = (boolean) list.get(row)[5];
				int alertId = (int) list.get(row)[7];
				boolean isActivated = (boolean) list.get(row)[8];
				// System.out.println("ppp" + globalModel.getColumnCount());
				ConfigSensorAir cS = new ConfigSensorAir(client, id, address, no2, pm10, o3, alert, alertId,
						isActivated, counter, timer, Integer.parseInt(textField.getText()),timerStart);
				cS.frame.setVisible(true);
				frame.dispose();

			}
		});

		// tao scrollpane roi cho table chui vao thi table thi tieu de moi hien thi
		JScrollPane jsp = new JScrollPane(tblsensorair);
		jsp.setBounds(91, 195, 482, 88);
		panel_cityinfo.add(jsp);

		JButton btnCreateButton = new JButton("Add new sensor ");
		btnCreateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CreateSensorAir saAdd = new CreateSensorAir(client, counter, timer, cron);
				saAdd.frame.setVisible(true);
				frame.dispose();
			}
		});
		btnCreateButton.setBackground(Color.WHITE);
		btnCreateButton.setBounds(91, 351, 139, 23);
		panel_cityinfo.add(btnCreateButton);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Dashboard db = new Dashboard();
				db.frame.setVisible(true);
				frame.dispose();
			}
		});
		btnCancel.setBackground(Color.WHITE);
		btnCancel.setBounds(422, 351, 121, 23);
		panel_cityinfo.add(btnCancel);

		JLabel lblClickOnThe = new JLabel("*Click on the desired sensor for more details");
		lblClickOnThe.setForeground(Color.BLACK);
		lblClickOnThe.setHorizontalAlignment(SwingConstants.LEFT);
		lblClickOnThe.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblClickOnThe.setBounds(202, 299, 265, 26);
		panel_cityinfo.add(lblClickOnThe);

		JButton btnThresholdDetails = new JButton("Threshold details\r\n");
		btnThresholdDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReglementationFrance rf = new ReglementationFrance(client, counter, timer,
						Integer.parseInt(textField.getText()));
				rf.frame.setVisible(true);
				frame.dispose();
			}
		});
		btnThresholdDetails.setBackground(Color.WHITE);
		btnThresholdDetails.setBounds(253, 351, 139, 23);
		panel_cityinfo.add(btnThresholdDetails);

		JLabel lblNewLabel_1_1_2_1 = new JLabel("Set up timer for results (in seconds) :");
		lblNewLabel_1_1_2_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1_1_2_1.setBounds(24, 59, 184, 14);
		panel_cityinfo.add(lblNewLabel_1_1_2_1);

		sliderTimer = new JSlider();
		sliderTimer.setValue(70);
		sliderTimer.setPaintTicks(true);
		sliderTimer.setPaintLabels(true);
		sliderTimer.setMaximum(30);
		sliderTimer.setMajorTickSpacing(5);
		sliderTimer.setForeground(Color.WHITE);
		sliderTimer.setFont(new Font("Tahoma", Font.BOLD, 12));
		sliderTimer.setEnabled(false);
		sliderTimer.setBackground(SystemColor.menu);
		sliderTimer.setBounds(214, 56, 231, 52);
		panel_cityinfo.add(sliderTimer);

		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {

			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || c == KeyEvent.VK_DELETE)) {
					e.consume();

				}
			}
		});

		textField.setText(String.valueOf(cron));

		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField.setColumns(10);
		int e = Integer.parseInt(textField.getText());
		sliderTimer.setValue(e);
		textField.setBounds(467, 59, 37, 20);
		panel_cityinfo.add(textField);

		JButton buttonTimer = new JButton("set value");
		buttonTimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!(textField.getText().isEmpty())) {
					int d = Integer.parseInt(textField.getText());
					if (isStart)
						timer.cancel();
					btnStartMouseClicked(e);
				} else {
					JOptionPane.showMessageDialog(frame, "Empty field !");
					textField.setText("0");
					sliderTimer.setValue(0);
				}

			}
		});
		buttonTimer.setBounds(524, 55, 98, 23);
		panel_cityinfo.add(buttonTimer);

		JLabel lblNewLabel_1_1_1 = new JLabel("Remaining time for next release");
		lblNewLabel_1_1_1.setForeground(Color.DARK_GRAY);
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1_1_1.setBounds(107, 132, 188, 37);
		panel_cityinfo.add(lblNewLabel_1_1_1);

		timeLeft = new JLabel("");
		timeLeft.setHorizontalAlignment(SwingConstants.CENTER);
		timeLeft.setForeground(Color.RED);
		timeLeft.setFont(new Font("Tahoma", Font.BOLD, 15));
		timeLeft.setBounds(305, 119, 64, 40);
		panel_cityinfo.add(timeLeft);

		JButton btnStopTimer = new JButton("reset\r\n");
		btnStopTimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int d = Integer.parseInt(textField.getText());
				timerStart=false;
				timer.cancel();
				task.cancel();
				counter = 0;
				textField.setText("0");
				sliderTimer.setValue(0);
				timeLeft.setText("");
			}
		});
		btnStopTimer.setBounds(395, 139, 64, 23);
		panel_cityinfo.add(btnStopTimer);

		labelx = new JLabel("resultats are out!");
		labelx.setForeground(Color.RED);
		labelx.setFont(new Font("Tahoma", Font.PLAIN, 11));
		labelx.setBounds(301, 170, 184, 14);
		labelx.setVisible(false);
		panel_cityinfo.add(labelx);

		JLabel lblNewLabel = new JLabel("Air Quality Sensors Manager System");
		lblNewLabel.setBounds(166, 11, 337, 27);
		panel.add(lblNewLabel);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		if (cron > 0) {
			btnStartMouseClicked(null);
		}

	}

	private void getSensorAirData() {
		// TODO Auto-generated method stub
		client.setResponseData(null);
		SendPackage sendP = new SendPackage();
		sendP.setApi(ApiEnum.SENSORAIR_FIND_ALL);
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

		client.setResponseData(null);
	}

	void simulate() {
		final int no2Simulate = (int) (Math.random() * 500);
		final int pm10Simulate = (int) (Math.random() * 100);
		final int o3simulate = (int) (Math.random() * 300);
		this.alert = no2Simulate >= this.no2 || pm10Simulate >= this.pm10 || o3simulate >= this.o3;

	}

	private void bindDataToTable(JSONArray jArray) {
		// globalModel = new DefaultTableModel();
		DefaultTableModel model = new DefaultTableModel();
		String[] columnNames = { "Address", "Alert status", "Alert time" };
		model.setColumnIdentifiers(columnNames);

		ArrayList arrRows = new ArrayList();
		for (int i = 0; i < jArray.length(); i++) {
			JSONObject jb;
			try {
				jb = jArray.getJSONObject(i);

				JSONObject alerteModel = jb.getJSONObject("alerteModel");
				boolean isAlerted = alerteModel.getBoolean("alert");
				Object[] rowData = { jb.getString("address"), isAlerted ? "Alerted" : "Not alerted",
						isAlerted ? alerteModel.getString("date") : "---" };

				Object[] globalrowData = { jb.getInt("id"), jb.getString("address"), jb.getInt("no2"),
						jb.getInt("pm10"), jb.getInt("o3"), alerteModel.getBoolean("alert"),
						alerteModel.has("date") ? alerteModel.getString("date") : "---", alerteModel.get("id"),
						jb.getBoolean("activated")

				};
				// globalModel.addRow(globalrowData);
				list.add(globalrowData);
				model.addRow(rowData);
				arrRows.clear();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		tblsensorair.setModel(model);
//		tblsensorair.getColumn("Delete").setCellRenderer(new ButtonRenderer());
//		tblsensorair.getColumn("Delete").setCellEditor(
//		        new ButtonEditor(new JCheckBox()));
//		tblsensorair.setVisible(true);   

	}

	private boolean sliderLength3(int c) {
		return c > 30;
	}

	private boolean sliderLength2(int b) {
		return b < 0;
	}

	private void btnStartMouseClicked(ActionEvent evt) {
		timerStart=true;
		timer = new Timer();
		int d = Integer.parseInt(textField.getText());

		isStart = true;
		if (sliderLength3(d)) {
			JOptionPane.showMessageDialog(frame, "excessive value !");
			textField.setText("0");
			sliderTimer.setValue(0);
			timer.cancel();
			return;
		} else if (sliderLength2(d)) {
			JOptionPane.showMessageDialog(frame, "Impossible value !");
			textField.setText("0");
			sliderTimer.setValue(0);
			timer.cancel();
			return;
		}

		else {
			sliderTimer.setValue(d);
		}

		counter = counter > 0 ? counter : d;

		task = new TimerTask() {
			public void run() {
				timeLeft.setText(Integer.toString(counter));
				counter--;
				if (counter == -1) {
					simulate();
					labelx.setVisible(true);

				} else {
					labelx.setVisible(false);
				}
				if (counter == -1) {
//					timer.cancel();
                   simulation.launchSimulation();
					counter = d;

				}
			}
		};
		timer.scheduleAtFixedRate(task, 0, 1000);
	}

}
