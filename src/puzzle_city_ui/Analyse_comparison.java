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
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Font;

public class Analyse_comparison{

	public JFrame frame;
	private JTextField textField;
	private JLabel label;
	Client client;
	private int cityID;
	private String date;
	
	/**
	 * Create the application.
	 */
	
	public Analyse_comparison(Client client, int cityID) {
		this.client = client;
		this.cityID = cityID;
		initialize();
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
		
		JLabel lblNewLabel = new JLabel("<html><body><p align=\"center\">Compare with : <br/>(type date format : yyyy-mm-dd)</p></body></html>\" + \"(type date format : yyyy-mm-dd)</p></body></html>");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel.add(textField);
		textField.setColumns(10);
		System.out.println(textField.getText());
		
		JButton btnNewButton_3 = new JButton("Continue");
		btnNewButton_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				date = textField.getText();
				Analyse_comparison_1 c1 = new Analyse_comparison_1(client, cityID, date);
				c1.getJFrame().setVisible(true);
			}
		});
		
		label = new JLabel("");
		panel.add(label);
		panel.add(btnNewButton_3);
		
	}

	public JFrame getJFrame() {
		return frame;
	}
	
	
	
}
