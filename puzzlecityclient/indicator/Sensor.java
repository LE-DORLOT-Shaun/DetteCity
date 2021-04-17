package indicator;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import controller.DBConnectController;



public class Sensor {

	public JSONObject getIndicator (JSONObject JsonRecu, Connection c){
		JSONObject obj=new JSONObject();

		if (JsonRecu.get("demandType").equals("SENSOR_INDICATOR")) {
			try {
				PreparedStatement stmt1 = c.prepareStatement("select count(*) as nombre_capteur_position, position, type from capteur group by (position,type);");
				ResultSet rs2 = stmt1.executeQuery();


				// creation of users list 
				ArrayList<JSONObject> listSensors = new ArrayList<JSONObject>();

				while (rs2.next()) {
					SensorIndicator sensor = new SensorIndicator(0, rs2.getString("type"), rs2.getString("position"),null,  rs2.getInt("nombre_capteur_position")); 
					//System.out.println("récuperation des résultats du select"); 
					JSONObject sensorJSON = sensor.convertToJSON();

					// adding each user to the list already created
					listSensors.add(sensorJSON);


				}
				//System.out.println("voici l'arrayList : ");
				// displaying the list 
				//System.out.println(listUsers);

				obj.put("sensors", listSensors);
				System.out.println("voici le json envoyé avec getIndicator ");
				// displaying the Json
				System.out.println(obj);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		return obj;
	}

	public JSONObject getIndicatorBorne (JSONObject JsonRecu, Connection c){
		JSONObject obj=new JSONObject();

		if (JsonRecu.get("demandType").equals("SENSOR_INDICATOR2")) {
			try {
				PreparedStatement stmt1 = c.prepareStatement("select count(*) as nombre_bornes_position, position from bornes group by (position);");
				ResultSet rs2 = stmt1.executeQuery();


				// creation of users list 
				ArrayList<JSONObject> listBornes = new ArrayList<JSONObject>();

				while (rs2.next()) {
					SensorIndicator borne = new SensorIndicator(0, rs2.getString("position"),rs2.getInt("nombre_bornes_position")); 
					System.out.println("voici la position " +rs2.getString("position")); 
					//System.out.println("récuperation des résultats du select"); 
					JSONObject borneJSON = borne.convertToJSON();

					// adding each user to the list already created
					listBornes.add(borneJSON);


				}
				//System.out.println("voici l'arrayList : ");
				// displaying the list 
				//System.out.println(listUsers);

				obj.put("bornes", listBornes);
				System.out.println("voici le json envoyé avec getIndicator ");
				// displaying the Json
				System.out.println(obj);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		return obj;
	}

	public JSONObject getIndicatorSensorPolluant (JSONObject JsonRecu, Connection c){
		JSONObject obj=new JSONObject();

		if (JsonRecu.get("demandType").equals("SENSOR_INDICATOR3")) {
			try {
				PreparedStatement stmt1 = c.prepareStatement("select count(*) as nombre_capteur_polluant, localisation from capteur_polluant group by (localisation);");
				ResultSet rs2 = stmt1.executeQuery();


				// creation of users list 
				ArrayList<JSONObject> listSensorPolluant = new ArrayList<JSONObject>();

				while (rs2.next()) {
					SensorIndicator sensorPolluant = new SensorIndicator(0,null,0, 0,null,0,0,rs2.getString("localisation"),rs2.getInt("nombre_capteur_polluant")); 
					//System.out.println("récuperation des résultats du select"); 
					JSONObject sensorPolluantJSON = sensorPolluant.convertToJSON();

					// adding each user to the list already created
					listSensorPolluant.add(sensorPolluantJSON);


				}
				//System.out.println("voici l'arrayList : ");
				// displaying the list 
				//System.out.println(listUsers);

				obj.put("sensorPolluant", listSensorPolluant);
				System.out.println("voici le json envoyé avec getIndicator ");
				// displaying the Json
				System.out.println(obj);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		return obj;
	}
	
	public JSONObject getIndicatorSensorCar (JSONObject JsonRecu, Connection c){
		JSONObject obj=new JSONObject();

		if (JsonRecu.get("demandType").equals("SENSOR_INDICATOR4")) {
			try {
				PreparedStatement stmt1 = c.prepareStatement("select count(*) as nombre_capteur_voiture, position from capteur_vehicule group by (position);");
				ResultSet rs2 = stmt1.executeQuery();


				// creation of users list 
				ArrayList<JSONObject> listSensorCar = new ArrayList<JSONObject>();

				while (rs2.next()) {
					SensorIndicator sensorCar = new SensorIndicator(0,null,0,0,rs2.getString("position"),rs2.getInt("nombre_capteur_voiture"),0,null,0);
					//System.out.println("récuperation des résultats du select"); 
					JSONObject sensorCarJSON = sensorCar.convertToJSON();

					// adding each user to the list already created
					listSensorCar.add(sensorCarJSON);


				}
				//System.out.println("voici l'arrayList : ");
				// displaying the list 
				//System.out.println(listUsers);

				obj.put("sensorCar", listSensorCar);
				System.out.println("voici le json envoyé avec getIndicator ");
				// displaying the Json
				System.out.println(obj);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		return obj;
	}
	

	public JSONObject getIndicator2 (JSONObject JsonRecu, Connection c) {
		JSONObject obj=new JSONObject();

		if (JsonRecu.get("demandType").equals("getIndicator2")) {
			String type =(String) JsonRecu.get("typeCapteur");
			System.out.println("bonjour voici le polluant recu apres traitement");
			System.out.println(type);

			if (type.equals("capteurPolluant")) {
				System.out.println("Je suis dans le if"); 

				try {
					PreparedStatement stmt1 = c.prepareStatement("select count(*) as nombre_capteur_polluant, localisation from capteur_polluant group by (localisation);");
					ResultSet rs2 = stmt1.executeQuery();


					// creation of users list 
					ArrayList<JSONObject> listSensorPolluant = new ArrayList<JSONObject>();

					while (rs2.next()) {
						SensorIndicator sensorPolluant = new SensorIndicator(0,null,0, 0,null,0,0,rs2.getString("localisation"),rs2.getInt("nombre_capteur_polluant")); 
						//System.out.println("récuperation des résultats du select"); 
						JSONObject sensorPolluantJSON = sensorPolluant.convertToJSON();

						// adding each user to the list already created
						listSensorPolluant.add(sensorPolluantJSON);
					}


					obj.put("sensorPolluant2", listSensorPolluant);
					System.out.println("voici le json envoyé avec getIndicator ");
					// displaying the Json
					System.out.println(obj);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (type.equals("borne")) {
				System.out.println("Je suis dans le if");
				try {
					PreparedStatement stmt1 = c.prepareStatement("select count(*) as nombre_bornes_position, position from bornes group by (position);");
					ResultSet rs2 = stmt1.executeQuery();


					// creation of users list 
					ArrayList<JSONObject> listBornes = new ArrayList<JSONObject>();

					while (rs2.next()) {
						SensorIndicator borne = new SensorIndicator(0, rs2.getString("position"),rs2.getInt("nombre_bornes_position")); 
						System.out.println("voici la position " +rs2.getString("position")); 
						//System.out.println("récuperation des résultats du select"); 
						JSONObject borneJSON = borne.convertToJSON();

						// adding each user to the list already created
						listBornes.add(borneJSON);


					}
					//System.out.println("voici l'arrayList : ");
					// displaying the list 
					//System.out.println(listUsers);

					obj.put("bornes2", listBornes);
					System.out.println("voici le json envoyé avec getIndicator ");
					// displaying the Json
					System.out.println(obj);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
		}
		return obj;

	}
}
