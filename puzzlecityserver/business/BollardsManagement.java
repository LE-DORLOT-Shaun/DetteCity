package business;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONObject;

import commons.ConnectionPool;

public class BollardsManagement {
private Connection c;
private int vehicule_nb;
private int vehicule_threshold;

	//après récupération du json véhicule 
	public BollardsManagement(Connection c) throws SQLException, InterruptedException {
		this.c = c;
		vehicule_nb = getVehicule();
		System.out.println("nb_vehicule = " + vehicule_nb);
		vehicule_threshold = VehiculeThreshold();
		System.out.println("vehicule_threshold = " + vehicule_threshold);
		//recup nb_v
		
		ConnectionPool.releaseConnection(c);
	}
	
	public int getVehicule() throws SQLException, InterruptedException {
		PreparedStatement stmtJson = c.prepareStatement("SELECT vehicule_nb FROM traffic ORDER BY date DESC LIMIT 1");
		ResultSet response = stmtJson.executeQuery();
		int cpt = 0;
		int voitures = 0;

		while (response.next())  {
			//recovery of the data of the the table in question 
			cpt++;
			System.out.println("cpt " + cpt);
			voitures = response.getInt("vehicule_nb");
		}
		if(cpt == 0) {
			voitures = 0;
			System.out.println("erreur lors de la recuperation des vehicules dans la ville");
		}
		System.out.println("nombre de vehicules actuels dans la ville: " + voitures);
		// displaying the json 
		return voitures; 
	}
	
	public int VehiculeThreshold() throws SQLException, InterruptedException {
		PreparedStatement stmtJson = c.prepareStatement("SELECT vehicule_threshold FROM threshold");
		ResultSet response = stmtJson.executeQuery();
		int cpt = 0;
		int threshold = 0;

		while (response.next())  {
			//recovery of the data of the user in question 
			cpt++;
			threshold = response.getInt("vehicule_threshold");
		}
		if(cpt == 0) {
			System.out.println("erreur lors de la recuperation du maxVehicules");
		}
		// displaying the json 
		return threshold; 
	}
	
	public void UpdateVehicule_nb(String vehicule, String type) throws SQLException, InterruptedException {
		PreparedStatement stmtGetInfos = c.prepareStatement("SELECT sensor_id FROM vehicule_history where brand=? AND type=? ORDER BY schedule DESC LIMIT 1;");
		stmtGetInfos.setString(1, vehicule);
		stmtGetInfos.setString(2, type);
		ResultSet response = stmtGetInfos.executeQuery();
		int cpt = 0;
		int sensor = 0;

		while (response.next())  { 
			cpt++;
			sensor = response.getInt("sensor_id");
		}
		if(cpt == 0) {
			sensor = 0;
		}
		
		
		PreparedStatement stmtGetSensor = c.prepareStatement("SELECT type FROM vehicule_sensor where id=?;");
		stmtGetSensor.setInt(1, sensor);
		ResultSet responseSensor = stmtGetSensor.executeQuery();
		cpt = 0;
		String sensorType = "";
		
		while (responseSensor.next())  { 
			cpt++;
			sensorType = responseSensor.getString("type");
		}
		if(cpt == 0) {
			sensorType = "SensorTypeError";
		}
		
		int currentNbCars = this.getVehicule();
		JSONObject obj=new JSONObject(); 
		
		if(sensorType.equals("IN")) {
			currentNbCars++;
			PreparedStatement stmt = c.prepareStatement("insert into traffic(vehicule_nb, date) values (?,CURRENT_TIMESTAMP);");
			stmt.setInt(1, currentNbCars);		

		

			if(stmt.executeUpdate()>=1) {
				obj.put("reponse",String.valueOf("nombre de véhicules mis à jour"));

			}
			else {
				obj.put("reponse",String.valueOf("erreur lors de la mise à jour"));
			}
			System.out.println(obj); 
		}
		
		if(sensorType.equals("OUT")) {
			currentNbCars--;
			PreparedStatement stmt = c.prepareStatement("insert into traffic(vehicule_nb, date) values (?,CURRENT_TIMESTAMP);");
			stmt.setInt(1, currentNbCars);		

			if(stmt.executeUpdate()>=1) {
				obj.put("reponse",String.valueOf("nombre de véhicules mis à jour"));

			}
			else {
				obj.put("reponse",String.valueOf("erreur lors de la mise à jour"));
			}
			System.out.println(obj); 
		}
		
		this.vehicule_nb = currentNbCars;
	}
}
