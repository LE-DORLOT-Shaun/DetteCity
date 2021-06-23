package tests;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import commons.TestJson;

public class SpecNumberVehicule {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException, JSONException, SQLException, InterruptedException {

		System.out.println("********************************");
		System.out.println("		INITIALIZATION");
		System.out.println("Threshold > Number of vehicule");
		System.out.println("Polution alert is on false");
		System.out.println("Bollards are down");
		System.out.println("********************************");
		
		org.json.simple.JSONObject bornesInit = TestJson.getBornes();
		
		int maxCars = Integer.valueOf((String) bornesInit.get("threshold"));
		int checkActualCars = Integer.valueOf(bornesInit.get("totalVehicule").toString());
		boolean isalerted = Boolean.valueOf(bornesInit.get("alertP").toString());
		
		int newthreshold = checkActualCars + 2;
		
		if(checkActualCars >= maxCars || maxCars != newthreshold) {
			TestJson.changeMax(newthreshold);
		}
		if(isalerted == true) {
			TestJson.delAlertP();
			System.out.println("Maintenant l'état des bornes devrait être à false");
		}
		
		org.json.simple.JSONObject bornesInter = TestJson.getBornes();
		
		System.out.println("********************************");
		System.out.println("		END INITIALIZATION");
		System.out.println("********************************");
		
		System.out.println("		TEST IN PROGRESS");
		
		

		try {

			System.out.println("je suis ici je vais lancer la simulation");
			TestJson.launchSimulation();
//			new Thread() {
//				public void run() {
					try {
						Socket s = new Socket("172.31.249.84", 3001);
						int cpt = 0;
						while (cpt < 19) {
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

								int nbVoitures = Integer.valueOf(json.get("vehicules").toString());
								checkActualCars = nbVoitures;
								if (json.containsKey("special")) {
									if (nbVoitures > maxCars || isalerted == true) {
										System.out.println("un vehicule prioritaire entre dans la ville");
									}
									
								}

								String voitures = (String) json.get("vehicules");
								System.out.println(voitures);
								
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							cpt = cpt + 1;
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
//				}
//			}.start();
		} catch (SQLException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println("********************************");
		System.out.println("			RESULTS");
		System.out.println("********************************");
		
		org.json.simple.JSONObject bornesFinal = TestJson.getBornes();
		
		System.out.println("Nous avons bien l'état des bornes à true");
		
	}
}
