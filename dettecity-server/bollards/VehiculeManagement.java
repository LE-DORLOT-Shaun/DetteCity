package bollards;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import org.json.simple.JSONObject;

public class VehiculeManagement {
	public static int totalCars;
	public static int maxCars;
	private Connection c; 
	
	public VehiculeManagement(Connection c) {
		this.c = c;
		try {
			totalCars = getCars();
			maxCars = carsLimit();
		} catch (SQLException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public VehiculeManagement() {
		try {
			totalCars = this.getCars();
		} catch (SQLException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/* method to get the actual number of cars in town it used in the beginning and the data are
	 * used by the Thread server and sent to the Client
	 */
	public int getCars() throws SQLException, InterruptedException {
		PreparedStatement stmtJson = c.prepareStatement("SELECT vehicule_nb FROM traffic ORDER BY date DESC LIMIT 1");
		ResultSet response = stmtJson.executeQuery();
		int cpt = 0;
		int voitures = 0;

		while (response.next())  {
			//recovery of the data of the the table in question 
			cpt++;
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
	
	/* method to get the max number of cars in town it used in the beginning and the data are
	 * used by the Thread server and sent to the Client
	 */
	
	public int carsLimit() throws SQLException, InterruptedException {
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
	
	/* method used to update the max cars in town
	 * 
	 */
	
	public JSONObject updateMaxCars(int maxCars) throws SQLException {
		System.out.println(maxCars);
		PreparedStatement stmt = c.prepareStatement("update threshold set vehicule_threshold= ?, last_update= current_timestamp;");
		stmt.setInt(1, maxCars); 
		JSONObject obj=new JSONObject(); 


		//if (update  bien pass�) => executer les lignes suivantes sinon dire erreur

		if(stmt.executeUpdate()>=1) {
			obj.put("reponse",String.valueOf("mise � jour reussie du nb_max vehicules"));
			System.out.println("mise � jour reussie du nb_max vehicules");
		}
		else {
			obj.put("reponse",String.valueOf("erreur lors de la mise a jour du max vehicules"));
		}
		System.out.println(obj);
		return obj; 
	}

	
	
	
	
	public Object addCarToHistory(String vehicule, String type, int sensorId ) throws SQLException, InterruptedException {
		System.out.println("je suis ici");
		PreparedStatement stmt = c.prepareStatement("insert into vehicule_history values(?,?,CURRENT_TIMESTAMP,?);");
		 
		stmt.setString(1, vehicule);
		stmt.setString(2, type);
		stmt.setInt(3, sensorId);		

		JSONObject obj=new JSONObject(); 

		// if (insertion bien pass�) => executer les lignes suivantes sinon dire erreur
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
		
		int currentNbCars = this.getCars();
		JSONObject obj=new JSONObject(); 
		
		if(sensorType.equals("IN")) {
			currentNbCars++;
			PreparedStatement stmt = c.prepareStatement("insert into traffic(vehicule_nb, date) values (?,CURRENT_TIMESTAMP);");
			stmt.setInt(1, currentNbCars);		

		

			if(stmt.executeUpdate()>=1) {
				obj.put("reponse",String.valueOf("nombre de v�hicules mis � jour"));

			}
			else {
				obj.put("reponse",String.valueOf("erreur lors de la mise � jour"));
			}
			System.out.println(obj); 
		}
		
		if(sensorType.equals("OUT")) {
			currentNbCars--;
			PreparedStatement stmt = c.prepareStatement("insert into traffic(vehicule_nb, date) values (?,CURRENT_TIMESTAMP);");
			stmt.setInt(1, currentNbCars);		

			if(stmt.executeUpdate()>=1) {
				obj.put("reponse",String.valueOf("nombre de v�hicules mis � jour"));

			}
			else {
				obj.put("reponse",String.valueOf("erreur lors de la mise � jour"));
			}
			System.out.println(obj); 
		}
		
		this.totalCars = currentNbCars;
	}
	
/*	public Object SearchCars(String dateDebut, String dateFin, String zone, String type) throws SQLException, InterruptedException {
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
		System.out.println("requete bien execut�");
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
	*/
	//function used to get the pollution alert if it is raised or not
	
	public Object PollutionAlert() throws SQLException, InterruptedException {
		
		PreparedStatement stmt1 = c.prepareStatement("select alerte_etat from historique_alerte ORDER BY date_debut DESC LIMIT 1;");
		ResultSet rs2 = stmt1.executeQuery();
		int i = 0;
		// creation of users list 
		JSONObject alertePollution=new JSONObject();
		while (rs2.next()) {
			i++;
			alertePollution.put("alertePollution", rs2.getString("alerte_etat"));
			System.out.println("les bornes sont actuellement lev�: " + alertePollution);
		}
		if(i == 0) {
			alertePollution.put("alertePollution", String.valueOf("impossible de recuperer les donnees") );
			return alertePollution;
		}
		else {
			return alertePollution;
		}
	}
	
}
