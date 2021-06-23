package view;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import commons.DateTimePicker;
import commons.TestJson;

public class PanneauBorne extends JPanel implements ActionListener {
	private JMenuItem historique, limiteVoitures;
	private JButton bouton, BtnAlertP, submit, refresh;
	private JTable tableau;
	private JLabel label, labelNbCars, labelMaxCars, maxVehicule;
	private ArrayList<JSONObject> allBornes;
	private JComboBox liste1, liste2;
	private Object[][] data;
	private JButton boutonFiltre;
	private JPanel Panneau1, Panneau2, Panneau3;
	private DateTimePicker dateTimePicker, dateTimePickerFin;
	private JScrollPane pane;
	private Object[][] dataCars;
	private String title[];
	private JTable tableauCars;
	private JPanel panneauFiltre;
	private JLabel l1, l2;
	private JTextField newNbMax;
	private int maxCars, checkActualCars;
	private boolean isalerted;

	public PanneauBorne() {
		// refresh the Pannel to be able to see the lastest data
		revalidate();
		repaint();
	}

	// method that is launch to get the bornes and their states and calling
	// functions to retreive data
	// and launch simulations

	@SuppressWarnings("unchecked")
	public void init() throws UnsupportedEncodingException, SQLException, IOException, JSONException {
		// creation of tabs one for each different functionnality of the UC
		JTabbedPane selection = new JTabbedPane();
		this.setLayout(new CardLayout());
		this.add(selection);
		System.out.println("je rentre dans le PanneauBorne()");

		/*
		 * JSONObject AlertP = TestJson.getAlertP(); isalerted =
		 * Boolean.valueOf(AlertP.get("alertP").toString());
		 * System.out.println("isalerted = " + isalerted);
		 * System.out.println("recup alertP terminee");
		 */

		// calling static method from testJson to get bornes state
		JSONObject bornes = TestJson.getBornes();

		label = new JLabel();
		label.setText("changement");
		// treatment of the data received from the server and processing them and put
		// in data object[][] to build the table
		this.allBornes = (ArrayList<JSONObject>) bornes.get("bollards");
		this.data = new Object[allBornes.size()][3];
		for (int i = 0; i < allBornes.size(); i++) {

			data[i][0] = allBornes.get(i).get("id");
			data[i][1] = allBornes.get(i).get("address");

			if (allBornes.get(i).get("state").equals("f")) {
				data[i][2] = "baissé";
			} else {
				data[i][2] = "relevé";
			}
		}

		// creating titles of the table
		String title[] = { "borne", "position", "état" };
		tableau = new JTable(data, title);
		System.out.println("fin def table");
		// creating Jpanels one that will contain the table with bornes infos and
		// another
		// Panel that will contain the informations about cars in town
		JPanel panneauBornesOrigine = new JPanel();
		JPanel panneauBornes = new JPanel();
		// initialise variables to store maxCars and actual cars in town
		// the JSON that contains bornes state also contains initial infos about cars in
		// town
		System.out.println("recup des donnees max");
		maxCars = Integer.valueOf((String) bornes.get("threshold"));
		checkActualCars = Integer.valueOf(bornes.get("totalVehicule").toString());
		isalerted = Boolean.valueOf(bornes.get("alertP").toString());

		System.out.println("recup terminee");

		labelNbCars = new JLabel("nombre de voitures actuellement : " + bornes.get("totalVehicule"));
		labelMaxCars = new JLabel("nombre de voitures maximum : " + maxCars);
		System.out.println("affichage max");
		panneauBornesOrigine.add(labelNbCars);
		panneauBornesOrigine.add(labelMaxCars);
		panneauBornes.add(new JScrollPane(tableau));
		labelNbCars.setFont(new Font("Dialog", Font.BOLD, 14));
		labelMaxCars.setFont(new Font("Dialog", Font.BOLD, 14));
		Border border = BorderFactory.createLineBorder(Color.black, 2);
		labelNbCars.setBorder(border);
		labelMaxCars.setBorder(border);

		// creating the table and adding infos to the different pannels
		Dimension d = new Dimension(400, 200);
		tableau.setPreferredScrollableViewportSize(d);
		Panneau1 = new JPanel();
		Panneau1.setLayout(new BoxLayout(Panneau1, BoxLayout.Y_AXIS));

		// adding filters selection to search in cars history
		Panneau2 = new JPanel();
		Panneau2.setLayout(new BorderLayout());
		panneauFiltre = new JPanel();
		JLabel dateDebut = new JLabel("date debut :");
		JLabel dateFin = new JLabel("date fin :");
		JLabel zone = new JLabel("zone :");
		JLabel type = new JLabel("type :");

		// crating instances of dateTimePicker to select the wanted date and time
		Date date = new Date();
		dateTimePicker = new DateTimePicker();
		dateTimePicker.setFormats(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM));
		dateTimePicker.setTimeFormat(DateFormat.getTimeInstance(DateFormat.MEDIUM));
		dateTimePicker.setDate(date);

		Date dateFinAff = new Date();
		dateTimePickerFin = new DateTimePicker();
		dateTimePickerFin.setFormats(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM));
		dateTimePickerFin.setTimeFormat(DateFormat.getTimeInstance(DateFormat.MEDIUM));
		dateTimePickerFin.setDate(dateFinAff);

		// adding the filters to the search history pannel
		panneauFiltre.add(dateDebut);
		panneauFiltre.add(dateTimePicker);
		panneauFiltre.add(dateFin);
		panneauFiltre.add(dateTimePickerFin);
		boutonFiltre = new JButton("recherche");
		Object[] elements = new Object[] { "Entree", "Sortie", "Les deux" };
		liste1 = new JComboBox(elements);
		liste1.setBackground(Color.white);

		Object[] elements2 = new Object[] { "Nord", "Sud", "Est", "Ouest", "toute la ville" };
		liste2 = new JComboBox(elements2);
		liste2.setBackground(Color.white);

		panneauFiltre.add(type);
		panneauFiltre.add(liste1);
		panneauFiltre.add(zone);
		panneauFiltre.add(liste2);
		panneauFiltre.add(boutonFiltre);
		boutonFiltre.addActionListener(this);

		// creating the button to launch the cars simulation
		bouton = new JButton("lancer simulation");
		bouton.addActionListener(this);

		BtnAlertP = new JButton("Alerte pollution");
		if (isalerted == true) {
			BtnAlertP.setBackground(Color.red);
		} else {
			BtnAlertP.setBackground(Color.green);
		}
		BtnAlertP.addActionListener(this);

		// creating the third panel that is used to change the max cars allowed in town
		Panneau3 = new JPanel();
		Panneau3.setForeground(Couleur.getBgApp());
		Panneau3.setFont(new Font("Arial", Font.BOLD, 14));
		Panneau3.setBorder(new LineBorder(Couleur.getBgTitle()));
		l1 = new JLabel("seuil de véhicules actuel");
		l2 = new JLabel("nouveau seuil");
		maxVehicule = new JLabel("  " + maxCars);
		newNbMax = new JTextField();
		submit = new JButton("Valider");
		submit.addActionListener(this);
		refresh = new JButton("Rafraichir");
		refresh.addActionListener(this);
		GridBagLayout a = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();

		Panneau3.setLayout(a);

		c.gridx = 0;
		c.gridy = 0;
		Panneau3.add(l1, c);

		c.gridx = 1;
		c.gridy = 0;
		Panneau3.add(l2, c);

		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0.3;
		maxVehicule.setPreferredSize(new Dimension(150, 25));
		Panneau3.add(maxVehicule, c);

		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 0.3;
		newNbMax.setPreferredSize(new Dimension(150, 25));
		Panneau3.add(newNbMax, c);

		c.gridx = 2;
		c.gridy = 1;
		c.weightx = 0.2;
		c.insets = new Insets(30, 30, 30, 250);
		submit.setPreferredSize(new Dimension(100, 25));
		Panneau3.add(submit, c);

		c.gridx = 2;
		c.gridy = 1;
		c.weightx = 0.2;
		c.insets = new Insets(30, 30, 30, 30);
		refresh.setPreferredSize(new Dimension(100, 25));
		Panneau3.add(refresh, c);

		// adding pannels to the different tabs and adding all these infos these tabs
		panneauBornesOrigine.add(bouton);
		panneauBornesOrigine.add(BtnAlertP);
		Panneau1.add(panneauBornes);
		Panneau1.add(panneauBornesOrigine);

		Panneau2.add(panneauFiltre, BorderLayout.NORTH);

		selection.addTab("Infos bornes", Panneau1);
		selection.addTab("Historique", Panneau2);
		selection.addTab("changer Seuil Vehicules", Panneau3);

	}

//*********************************** actions on the change cars limit tab *********************		
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource() == submit) {
			try {
				System.out.println(newNbMax.getText());
				int a = Integer.valueOf(newNbMax.getText());
				JSONObject rep = TestJson.changeMax(a);
				System.out.println(rep);
				System.out.println("nombre de vehicules actuellement: " + checkActualCars);
				// when the user submit the new max cars limit compare it to the actual number
				// of cars
				// in the town and rise or lower the bornes depending of the new number
				// submitted
				// refresh the data on the screen
				if (checkActualCars >= a) {
					for (int i = 0; i < allBornes.size(); i++) {
						data[i][2] = "relevé";
					}
					TestJson.riseBornes();
				}
				System.out.println("état d'alerte : " + isalerted);
				if (checkActualCars < a && isalerted == false) {
					for (int i = 0; i < allBornes.size(); i++) {
						data[i][2] = "baissé";
					}
					TestJson.LowerBornes();
				}

				maxCars = a;
				maxVehicule.setText("  " + String.valueOf(a));
				labelMaxCars.setText("nombre de voitures maximum :" + String.valueOf(a));
				Panneau1.revalidate();
				Panneau1.repaint();
				revalidate();
				repaint();

			} catch (NumberFormatException | SQLException | IOException e1) {
				// TODO Auto-generated catch block
				// e1.printStackTrace();
				System.out.println("Caractères non valides");
				JOptionPane.showMessageDialog(Panneau1, "Caractère invalide, veuillez entrer un entier");

			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

		// Button to refresh the window when something is done on an other client
		if (e.getSource() == refresh) {
			try {
				
				JSONObject getAlertP = TestJson.getAlertP();
				isalerted = Boolean.valueOf(getAlertP.get("alertP").toString());
				System.out.println("isalerted = " + isalerted);
				System.out.println("recup alertP terminee");

				if (isalerted == true) {
					BtnAlertP.setBackground(Color.red);
					for (int i = 0; i < allBornes.size(); i++) {
						data[i][2] = "relevé";
					}
				} else {
					BtnAlertP.setBackground(Color.green);
					for (int i = 0; i < allBornes.size(); i++) {
						data[i][2] = "baissé";
					}
				}
				JSONObject bornes = TestJson.getBornes();
				
				maxCars = Integer.valueOf((String) bornes.get("threshold"));
				checkActualCars = Integer.valueOf(bornes.get("totalVehicule").toString());
				System.out.println("maj terminee");
				
				labelNbCars.setText("nombre de voitures actuellement : " + checkActualCars);
				labelMaxCars.setText("nombre de voitures maximum :" + maxCars);
				
				if (checkActualCars >= maxCars) {
					for (int i = 0; i < allBornes.size(); i++) {
						data[i][2] = "relevé";
					}
				}
				System.out.println("état d'alerte : " + isalerted);
				if (checkActualCars < maxCars && isalerted == false) {
					for (int i = 0; i < allBornes.size(); i++) {
						data[i][2] = "baissé";
					}
				}
				
				System.out.println("affichage max");
				
				maxVehicule.setText("  " + String.valueOf(maxCars));
//				labelMaxCars.setText("nombre de voitures maximum :" + String.valueOf(maxCars));

				Panneau1.revalidate();
				Panneau1.repaint();
				Panneau3.revalidate();
				Panneau3.repaint();
				revalidate();
				repaint();
			} catch (SQLException | IOException | JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

		if (e.getSource() == BtnAlertP) {
			if (isalerted == false) {
				try {
					JSONObject setAlertP = TestJson.setAlertP();
					JSONObject getAlertP = TestJson.getAlertP();
					isalerted = Boolean.valueOf(getAlertP.get("alertP").toString());
					System.out.println("isalerted = " + isalerted);
					System.out.println("recup alertP terminee");

					if (isalerted == true) {
						BtnAlertP.setBackground(Color.red);
					} else {
						BtnAlertP.setBackground(Color.green);
					}

					for (int i = 0; i < allBornes.size(); i++) {
						data[i][2] = "relevé";
					}
					
					System.out.println("maj terminee");

					maxVehicule.setText("  " + String.valueOf(maxCars));
					labelMaxCars.setText("nombre de voitures maximum :" + String.valueOf(maxCars));

					Panneau1.revalidate();
					Panneau1.repaint();
					revalidate();
					repaint();
				} catch (SQLException | IOException | JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			} else {
				try {
					JSONObject delAlertP = TestJson.delAlertP();
					JSONObject getAlertP = TestJson.getAlertP();
					isalerted = Boolean.valueOf(getAlertP.get("alertP").toString());
					System.out.println("isalerted = " + isalerted);
					System.out.println("recup alertP terminee");

					if (isalerted == true) {
						BtnAlertP.setBackground(Color.red);
					} else {
						BtnAlertP.setBackground(Color.green);
					}
					
					if (checkActualCars >= maxCars) {
						for (int i = 0; i < allBornes.size(); i++) {
							data[i][2] = "relevé";
						}
					} else {
						for (int i = 0; i < allBornes.size(); i++) {
							data[i][2] = "baissé";
						}
					}
					System.out.println("maj terminee");

					maxVehicule.setText("  " + String.valueOf(maxCars));
					labelMaxCars.setText("nombre de voitures maximum :" + String.valueOf(maxCars));

					Panneau1.revalidate();
					Panneau1.repaint();
					revalidate();
					repaint();
				} catch (SQLException | IOException | JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

		}

		// getting the data chosen by the user and sending them to the server using
		// search vehicule
		// method and printing on the screen the data received in response
		if (e.getSource() == boutonFiltre) {
			pane = new JScrollPane();
			DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.mmm");
			Date a = dateTimePicker.getDate();
			Date b = dateTimePickerFin.getDate();
			String x = String.valueOf(liste1.getSelectedItem());
			String y = String.valueOf(liste2.getSelectedItem());
			System.out.println(x + " " + y);
			try {
				// calling the method to get history cars using the filters of the user and
				// displaying the results
				JSONObject rep = TestJson.searchVehicule(f.format(a), f.format(b), y, x);
				ArrayList<JSONObject> allCars = (ArrayList<JSONObject>) rep.get("voitures");
				dataCars = new Object[allCars.size()][4];
				for (int i = 0; i < allCars.size(); i++) {

					dataCars[i][0] = allCars.get(i).get("vehicule");
					dataCars[i][1] = allCars.get(i).get("date");
					dataCars[i][2] = allCars.get(i).get("position");
					if (allCars.get(i).get("type").equals("IN")) {
						dataCars[i][3] = "entree";
					} else {
						dataCars[i][3] = "sortie";
					}

				}
				String title[] = { "vehicule", "date et heure", "position", "type" };
				tableauCars = new JTable(dataCars, title);
				Panneau2.removeAll();
				Panneau2.add(panneauFiltre, BorderLayout.NORTH);
				Panneau2.add(new JScrollPane(tableauCars), BorderLayout.CENTER);
				Panneau2.revalidate();
				Panneau2.repaint();
				revalidate();
				repaint();
			} catch (SQLException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

		// the button used to launch simulation added for the tests but not present on
		// the application
		// it is used to show on the demonstration how the cars enter and leave the town
		// and the evolution
		// a new thread is created that exclusively communicate with the server and
		// receives the number
		// of cars in town.
		if (e.getSource() == bouton) {
			try {

				System.out.println("je suis ici je vais lancer la simulation");
				TestJson.launchSimulation();
				new Thread() {
					public void run() {
						try {
							Socket s = new Socket("172.31.249.84", 3001);
							while (true) {
								DataInputStream dis;
								dis = new DataInputStream(s.getInputStream());
								String rep = dis.readUTF();
								JSONParser parser = new JSONParser();
								JSONObject json;

								// the JSON used to receive the number of cars in town also contains an alert
								// message
								// if the number of vehicules reachs the nb max to notify the client to change
								// the bornes
								try {
									json = (JSONObject) parser.parse(rep);

									if (json.containsKey("etat")) {
										if (json.get("etat").equals("alert")) {
											isalerted = true;
											for (int i = 0; i < allBornes.size(); i++) {
												data[i][2] = "relevé";
											}
											JOptionPane.showMessageDialog(Panneau1,
													"Alerte, le véhicule ne peut pas entrer");
										}
										if (json.get("etat").equals("normal")) {
											for (int i = 0; i < allBornes.size(); i++) {
												data[i][2] = "baissé";
											}
										}
										revalidate();
										repaint();
									}
									int nbVoitures = Integer.valueOf(json.get("vehicules").toString());
									checkActualCars = nbVoitures;
									if (json.containsKey("special")) {
										if (nbVoitures > maxCars || isalerted == true) {
											System.out.println("un vehicule prioritaire entre dans la ville");
											JOptionPane.showMessageDialog(Panneau1,
													"un vehicule prioritaire entre dans la ville");
										}
										/*
										 * Timer t = new Timer(6000, new ActionListener() {
										 * 
										 * @Override public void actionPerformed(ActionEvent e) {
										 * urgeance.setText(null); revalidate(); repaint(); } }); t.setRepeats(false);
										 * t.start();
										 */
									}

									String voitures = (String) json.get("vehicules");
									System.out.println(voitures);
									labelNbCars.setText("nombre de voitures dans la ville : " + voitures);
									revalidate();
									repaint();
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}.start();
			} catch (SQLException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("Fin panneau borne");
			label.setText("etat change");
		}

	}
}
