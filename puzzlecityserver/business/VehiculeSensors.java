package business;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import business.Bollards;
import business.VehiculeManagement;

public class VehiculeSensors extends Thread {
	Connection c;
	InputStream inputStream;
	
	public VehiculeSensors(Connection c, InputStream inputStream) {
		this.c = c;
		this.inputStream = inputStream;
	}
	/*the class CarSensors gets the data used for the simulation and analyze these data and
	 * proceed to the count and the insertion to the vehicules history by using methods from
	 * VehiculeManagement class. every object of the inputed file pass throgh tests
	 * 
	 */
	public void run() {
		
			try {
				System.out.println("entrée dans run");
				this.LaunchSimulation(this.inputStream);
			} catch (ParseException | SQLException | IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
	
	
	public void LaunchSimulation (InputStream inputStream) throws ParseException, UnsupportedEncodingException, SQLException, IOException, InterruptedException{
		System.out.println("entré dans launchSimulation");
		StringBuffer sb = new StringBuffer();
		VehiculeManagement carsSimulation = new VehiculeManagement(this.c);
		Bollards bollards = new Bollards(this.c);
		System.out.println("bollard fin");
		ServerSocket server = new ServerSocket(3001);
		System.out.println("après socket");
		Socket s = server.accept();
		
		System.out.println("après server socket");
		
		DataOutputStream dos = new DataOutputStream(s.getOutputStream());
		
		
		System.out.println("je suis rentre dans launchSimulation et deja lance la socket");
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8")); 
		System.out.println("j'ai recuperer le fichier.JSON");
		try {
			String temp; 
			while ((temp = bufferedReader.readLine()) != null) 
				sb.append(temp); 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace(); 
			}
		}
		System.out.println(sb);
		String myjsonstring = sb.toString(); 
		

		JSONParser parser = new JSONParser(); 
		JSONArray json;
		json = (JSONArray) parser.parse(myjsonstring);
		/* the simulation file is parsed and transformed to a json file in order to be analyzed 
		 * in the steps above
		 */
		System.out.println("impression json " + json);
		int flag;
		if(VehiculeManagement.totalVehicule >= VehiculeManagement.threshold || VehiculeManagement.alertP == true ) {
			flag = 1;
		}
		else {
			flag = 0;
		}
		for (int i = 0; i < json.size(); i++) {
			JSONObject jsonObject = (JSONObject) json.get(i);
			System.out.println(jsonObject.get("id_sensor"));
			int id_sensor = Integer.parseInt(String.valueOf(jsonObject.get("id_sensor"))) ;  
			double taille = Double.parseDouble(String.valueOf(jsonObject.get("taille")));
			String objet =  String.valueOf(jsonObject.get("objet")); 
			JSONObject rep = new JSONObject();
			
			/*this function is implemented but not used because there is no data for tests 
			 * JSONObject alert = VehiculeManagement.PollutionAlert()
			 * if((alert.get("alerte_pollution").toString()).equals("declanche"){
			 * 		bornes.riseBornes();
			 * }
			 * 
			 */
			System.out.println("état de flag = " + flag);
			
			if(objet.equals("ambulance")) {
				carsSimulation.addCarToHistory(objet, "vehicule prioritaire", id_sensor);
				rep.put("special", "vehicule prioritaire");
				sleep(2000);
				taille = 0;
				if(VehiculeManagement.totalVehicule >= VehiculeManagement.threshold) {
					if(flag == 0) {
						bollards.risebollards();
						rep.put("etat",String.valueOf("alert"));
						flag = 1;
						}
				}
			} else {
			
			if(taille >=2.50 && taille <12) {
				try {
					sleep(2000);
					if(flag == 0 || id_sensor == 2 || id_sensor == 4 ) {
					//if(/*VehiculeManagement.totalVehicule < VehiculeManagement.threshold &&*/ flag == 0/*&& !bornes.forbiddenPassage() */ || id_sensor == 2 || id_sensor == 4 || id_sensor == 6 ||id_sensor == 8 ) { 
						carsSimulation.addCarToHistory(objet, "voiture", id_sensor);
						sleep(2000);
						}
						else {
							System.out.println("vous n'etes pas autorisé a entrer pour l'instant");
						}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(taille >=12) {
				try {
					if(flag == 0 || id_sensor == 2 || id_sensor == 4) { 
					carsSimulation.addCarToHistory(objet, "poids-lourd", id_sensor);
					sleep(2000);
					}
					else {
						System.out.println("vous n'etez pas autorisé a entrer pour l'instant");
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(taille <2.50) {
				System.out.println("detection d'un objet de petite taille non identifié");
			}
			
			System.out.println("nombre de voitures :" + VehiculeManagement.totalVehicule);
			
			if(VehiculeManagement.totalVehicule >= VehiculeManagement.threshold) {
				if(flag == 0) {
					bollards.risebollards();
					rep.put("etat",String.valueOf("alert"));
					flag = 1;
					}
			}
			if(VehiculeManagement.alertP == true) {
					bollards.risebollards();
					rep.put("etat",String.valueOf("alert"));
					flag = 1;
			}
			}
			
			/* check if there is not a lot of cars to lower bornes; it also checks if there is no 
			 * active pollution alert in order to do that
			 */
			if(VehiculeManagement.totalVehicule < VehiculeManagement.threshold && VehiculeManagement.alertP == false/* && alert.get("alerte_pollution").toString()).equals("normale") */) {
				if(flag == 1) {
					bollards.lowerbollards();
					rep.put("etat",String.valueOf("normal"));
					flag = 0;
					}
			}
	
			rep.put("vehicules", String.valueOf(VehiculeManagement.totalVehicule));
			System.out.println(rep);
			dos.writeUTF(rep.toString());;
			server.close();

		}
	}
}
