package business;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import org.json.simple.JSONObject;

public class VehiculeManagement {
	public static int totalVehicule;
	public static int threshold;
	public static boolean alertP;
	private Connection c; 
	
	public VehiculeManagement(Connection c) {
		this.c = c;
		try {
			totalVehicule = getVehicule();
			threshold = vehiculeThreshold();
			alertP = getPollutionAlert();
		} catch (SQLException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public VehiculeManagement() {
		try {
			totalVehicule = this.getVehicule();
		} catch (SQLException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/* method to get the actual number of cars in town it used in the beginning and the data are
	 * used by the Thread server and sent to the Client
	 */
	public int getVehicule() throws SQLException, InterruptedException {
		PreparedStatement stmtJson = c.prepareStatement("SELECT vehicule_nb FROM traffic ORDER BY date DESC LIMIT 1");
		ResultSet response = stmtJson.executeQuery();
		int cpt = 0;
		int vehicule = 0;

		while (response.next())  {
			//recovery of the data of the the table in question 
			cpt++;
			vehicule = response.getInt("vehicule_nb");
		}
		if(cpt == 0) {
			vehicule = 0;
			System.out.println("erreur lors de la recuperation des vehicules dans la ville");
		}
		System.out.println("nombre de vehicules actuels dans la ville: " + vehicule);
		// displaying the json 
		return vehicule; 
	}
	
	/* method to get the max number of cars in town it used in the beginning and the data are
	 * used by the Thread server and sent to the Client
	 */
	
	public int vehiculeThreshold() throws SQLException, InterruptedException {
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
		System.out.println("le seuil de vehicule autorise est : " + threshold);
		// displaying the json 
		return threshold; 
	}
	
	/* method used to update the max cars in town
	 * 
	 */
	
	public JSONObject updateMaxCars(int maxCars) throws SQLException {
		System.out.println("Seuil de vehicule voulu : " + maxCars);
		PreparedStatement stmt = c.prepareStatement("update threshold set vehicule_threshold= ?, last_update= current_timestamp;");
		stmt.setInt(1, maxCars); 
		JSONObject obj=new JSONObject(); 


		//if (update  bien passé) => executer les lignes suivantes sinon dire erreur

		if(stmt.executeUpdate()>=1) {
			obj.put("reponse",String.valueOf("mise à jour reussie du nb_max vehicules"));
			System.out.println("mise à jour reussie du nb_max vehicules");
		}
		else {
			obj.put("reponse",String.valueOf("erreur lors de la mise a jour du max vehicules"));
		}
		System.out.println(obj);
		return obj; 
	}

	
	
	
	
	public Object addCarToHistory(String vehicule, String type, int sensorId ) throws SQLException, InterruptedException {
		System.out.println("add to vehicule history");
		PreparedStatement stmt = c.prepareStatement("insert into vehicule_history values(?,?,CURRENT_TIMESTAMP,?);");
		 
		stmt.setString(1, vehicule);
		stmt.setString(2, type);
		stmt.setInt(3, sensorId);		

		JSONObject obj=new JSONObject(); 

		// if (insertion bien passé) => executer les lignes suivantes sinon dire erreur
		if(stmt.executeUpdate()>=1) {
			obj.put("reponse",String.valueOf("insertion reussi"));
			obj.put("vehicule",String.valueOf(vehicule));
			obj.put("type",String.valueOf(type));
		}
		else {
			obj.put("reponse",String.valueOf("erreur lors de l'insertion"));
		}
		this.adjustCarsNb(vehicule,type);
		System.out.println(obj);
		return obj; 
	}
	
	
	public void adjustCarsNb(String vehicule, String type) throws SQLException, InterruptedException {
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
		
		this.totalVehicule = currentNbCars;
	}
	
	public Object SearchCars(String dateDebut, String dateFin, String zone, String type) throws SQLException, InterruptedException {
		String request = "SELECT brand, schedule, position, cap.type as type_cap   FROM vehicule_history hv, (select * from vehicule_sensor) cap where hv.sensor_id = cap.id AND ";
		if(type.equals("Sortie")) {
			type = "OUT";
		}
		if(type.equals("Entree")) 
			type = "IN";
		
		request +="schedule between '" + dateDebut + "' AND '" + dateFin + "'";
		
		if(!zone.equals("All")) {
			request += " AND position ='" + zone + "'";
			
		}
		
		if(!type.equals("town")) {
			request += " AND cap.type='" + type + "'";
		}
		request += ";";
		
		System.out.println(request);
		PreparedStatement stmtGetInfos = c.prepareStatement(request);
		ResultSet rs2 = stmtGetInfos.executeQuery();
		System.out.println("requete bien executé");
		JSONObject obj=new JSONObject();
		// creation of users list 
		ArrayList<JSONObject> listvoitures = new ArrayList<JSONObject>();

		while (rs2.next()) {
			JSONObject voiture=new JSONObject();
			// recovery of each user's data (id/ name/ first name) 
			voiture.put("vehicule", rs2.getString("voiture"));
			String date = (rs2.getDate("schedule")).toString();
			voiture.put("date", date);
			voiture.put("position", rs2.getString("position"));
			voiture.put("type", rs2.getString("type_cap"));
			System.out.println("je suis ici" + voiture);

			// adding each user to the list already created
			listvoitures.add(voiture);
		}
		obj.put("voitures", listvoitures);
		return obj; 
	}
	
	//function used to get the pollution alert if it is raised or not
	
	public Object PollutionAlert() throws SQLException, InterruptedException {
		System.out.println("dans PollutionAlert");
		PreparedStatement stmt1 = c.prepareStatement("select state from alert ORDER BY date DESC LIMIT 1;");
		ResultSet rs2 = stmt1.executeQuery();
		int i = 0;
		// creation of users list 
		JSONObject alertePollution = new JSONObject();
		System.out.println("avant while");
		while (rs2.next()) {
			i++;
			alertePollution.put("alertP", rs2.getBoolean("state"));
			System.out.println("Alerte pollution : " + alertePollution);
		}
		if(i == 0) {
			alertePollution.put("alertP", String.valueOf("impossible de recuperer les donnees") );
			return alertePollution;
		}
		else {
			return alertePollution;
		}
	}
	
	public boolean getPollutionAlert() throws SQLException, InterruptedException {
		PreparedStatement stmtJson = c.prepareStatement("select state from alert ORDER BY date DESC LIMIT 1;");
		ResultSet rs3 = stmtJson.executeQuery();
		int cpt = 0;
		boolean pollutionAlert = false;

		while (rs3.next())  {
			//recovery of the data of the the table in question 
			cpt++;
			pollutionAlert = rs3.getBoolean("state");
		}
		if(cpt == 0) {
			pollutionAlert = false;
			System.out.println("erreur lors de la recuperation des alertes");
		}
		System.out.println("L'etat d'alerte de la ville est a : " + pollutionAlert);
		// displaying the json 
		return pollutionAlert; 
	}
	
}
