package puzzle_city_ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

public class Bollards {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Bollards window = new Bollards();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Bollards() {
		initialize();
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
		//list city
		JPanel panel_cityinfo = new JPanel();
		panel_cityinfo.setBounds(10, 64, 644, 364);
		panel.add(panel_cityinfo);
		panel_cityinfo.setLayout(null);
		
		JLabel lblListCity = new JLabel("Treshold");
		lblListCity.setHorizontalAlignment(SwingConstants.LEFT);
		lblListCity.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblListCity.setBounds(10, 11, 99, 27);
		panel_cityinfo.add(lblListCity);
		
/*		//table
		BollardsTable tv = new BollardsTable();
		tblCity = new JTable(tv);
		tblCity.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int row = tblCity.getSelectedRow();
				int cID = Integer.parseInt(tblCity.getModel().getValueAt(row, 0).toString()) ;

				Dashboard ctDetail =	new Dashboard(client, cID);
				Dashboard.frame.setVisible(true);
				frame.dispose();

			}
		});
*/

	
		
		JLabel lblVehiculeMax = new JLabel("Vehicule Max");
		lblVehiculeMax.setHorizontalAlignment(SwingConstants.LEFT);
		lblVehiculeMax.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblVehiculeMax.setBounds(10, 106, 99, 27);
		panel_cityinfo.add(lblVehiculeMax);
		
		JLabel lblCurrentValue = new JLabel("Current Value");
		lblCurrentValue.setHorizontalAlignment(SwingConstants.LEFT);
		lblCurrentValue.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCurrentValue.setBounds(208, 11, 99, 27);
		panel_cityinfo.add(lblCurrentValue);
		

//		//set data  for table	
		// add city
		JPanel panel_cityinfo_1 = new JPanel();
		panel_cityinfo_1.setLayout(null);
		panel_cityinfo_1.setBounds(10, 64, 644, 364);
		panel.add(panel_cityinfo_1);
		
		JLabel lblListCity_1 = new JLabel("Bollards List");
		lblListCity_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblListCity_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblListCity_1.setBounds(10, 11, 99, 27);
		panel_cityinfo_1.add(lblListCity_1);
		
		JLabel lblNewLabel = new JLabel("Treshold Manager System");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(409, 13, 211, 27);
		panel.add(lblNewLabel);
	}
}
