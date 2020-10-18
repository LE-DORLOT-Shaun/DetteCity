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
import java.awt.SystemColor;

public class ReglementationFrance {

	public JFrame frame;
	private JTable tblalerthistory;
	public Client client;// = new Client("127.0.0.1", 4000);
	private int counter;
	private int cron;
	private Timer timer;

	
	List<Object[]> list=new ArrayList<>();

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 * 
	 * @wbp.parser.constructor
	 */
	public ReglementationFrance(Client client,int counter,Timer timer,int cron) {
	    this.client=client;
	    this.counter=counter;
	    this.timer=timer;
	    this.cron=cron;
		initialize();

	}

	public ReglementationFrance(JTable tblalerthistory) {

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

	

		JPanel panel_cityinfo_1 = new JPanel();
		panel_cityinfo_1.setLayout(null);
		panel_cityinfo_1.setBounds(10, 64, 644, 364);
		panel.add(panel_cityinfo_1);
		// list city
		JPanel panel_cityinfo = new JPanel();
		panel_cityinfo.setBounds(-10, 0, 654, 364);
		panel_cityinfo_1.add(panel_cityinfo);
		panel_cityinfo.setLayout(null);

		JLabel lblListCity = new JLabel("\r\nThe main values \u200B\u200Bmentioned in French regulations (Threshold) :\r\n\r\n");
		lblListCity.setHorizontalAlignment(SwingConstants.LEFT);
		lblListCity.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblListCity.setBounds(95, 76, 512, 27);
		panel_cityinfo.add(lblListCity);

		tblalerthistory = new JTable(new DefaultTableModel(
			new Object[][] {
				{"  Quality Goals", "A.A* : 30 \u00B5g/m\u00B3.", "--", "A.A* : 40 \u00B5g/m\u00B3."},
				{" Information Threshold", "D.A* : 50 \u00B5g/m\u00B3.", "H.A* : 180 \u00B5g/m\u00B3.", "H.A* : 200 \u00B5g/m\u00B3."},
				{"  Alert Threshold", "D.A* : 80 \u00B5g/m\u00B3.", "H.A* : 240 \u00B5g/m\u00B3", "H.A* : 400 \u00B5g/m\u00B3"},
			},
			new String[] {
				"", "PM10 (Suspended particles)", "O3 (ozone)", "NO2 (nitrogen dioxide)"
			}
		));
		tblalerthistory.setBackground(Color.LIGHT_GRAY);
		tblalerthistory.setForeground(SystemColor.textText);

	

		JScrollPane jsp = new JScrollPane(tblalerthistory);
		jsp.setBounds(10, 139, 644, 77);
		panel_cityinfo.add(jsp);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SensorAirList sa = new SensorAirList(client,counter,timer,cron);
				sa.frame.setVisible(true);
				frame.dispose();
			}
		});
		btnCancel.setBackground(Color.WHITE);
		btnCancel.setBounds(257, 302, 121, 23);
		panel_cityinfo.add(btnCancel);
		
		JLabel lblAaAnnual = new JLabel("A.A : annual average          H.A : Hourly average           D.A : Daily average");
		lblAaAnnual.setHorizontalAlignment(SwingConstants.CENTER);
		lblAaAnnual.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblAaAnnual.setBounds(29, 264, 578, 27);
		panel_cityinfo.add(lblAaAnnual);

		JLabel lblNewLabel = new JLabel("Air Quality Sensors Details\r\n");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(159, 11, 337, 27);
		panel.add(lblNewLabel);

	}
}