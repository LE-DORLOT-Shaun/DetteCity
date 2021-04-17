package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import java.awt.event.ActionEvent;

public class Home extends JPanel {
	
	public JFrame frame;
	private JTable tblCity;

	/**
	 * Launch the application.
	 */
	
	private static final long serialVersionUID = 1L;
	private JMenuBar mbar;
	private JMenu men;
	
	private JButton empreinte;
	private JButton pollution; 
	private JButton borne; 
	private JButton indicateur;
	private JButton carte;
	private JButton accueil;
	
	
	/**
	 * Create the application.
	 */
	
	public Home() {
		
		initialize();
	}
	
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
		
		JLabel lblListCity = new JLabel("List City");
		lblListCity.setHorizontalAlignment(SwingConstants.LEFT);
		lblListCity.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblListCity.setBounds(10, 11, 99, 27);
		panel_cityinfo.add(lblListCity);
		
		//table
//		CityTable tv = new CityTable();
//		tblCity = new JTable(tv);
/*		tblCity.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int row = tblCity.getSelectedRow();
				int cID = Integer.parseInt(tblCity.getModel().getValueAt(row, 0).toString()) ;

//				Dashboard ctDetail =	new Dashboard(client, cID);
//				Dashboard.frame.setVisible(true);
				frame.dispose();

			}
		});
*/
		// create scrollpane with param which is the table needed (make it display )
        JScrollPane jsp = new JScrollPane(tblCity);
        jsp.setBounds(20, 49, 593, 278);
		panel_cityinfo.add(jsp);

		JButton btnCreateButton = new JButton("Add new City");
		btnCreateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				CityAddNew ctAdd =	new CityAddNew(client);
//				ctAdd.frame.setVisible(true);
				frame.dispose();
			}
		});
		btnCreateButton.setBackground(Color.WHITE);
		btnCreateButton.setBounds(471, 11, 142, 23);
		panel_cityinfo.add(btnCreateButton);
		

//		//set data  for table	
		// add city
		JPanel panel_cityinfo_1 = new JPanel();
		panel_cityinfo_1.setLayout(null);
		panel_cityinfo_1.setBounds(10, 64, 644, 364);
		panel.add(panel_cityinfo_1);
		
		JLabel lblListCity_1 = new JLabel("List City");
		lblListCity_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblListCity_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblListCity_1.setBounds(10, 11, 99, 27);
		panel_cityinfo_1.add(lblListCity_1);
		
		JLabel lblNewLabel = new JLabel("City Manager System");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(266, 11, 197, 27);
		panel.add(lblNewLabel);
		
	}
}
