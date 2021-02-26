package business;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.json.simple.JSONObject;

import business.VehiculeManagement;

	/* class used to interact with the bollards in order to get the status or to change it */

public class Bollards {
	
	private Connection c; 

	/* to initialise the object we must add a connection in order to get data from the data base */
	
	public Bollards(Connection c) {
		this.c = c;
	}
	
	/*method used by the server to get the bollards informations from the dataBase and also the 
	 * the number of cars in town actually and the max cars authorised by getting the static variables */
	public Object bollardsState() throws SQLException, InterruptedException {
		
		PreparedStatement stmt1 = c.prepareStatement("select * from bollards;");
		ResultSet rs2 = stmt1.executeQuery();

		JSONObject obj=new JSONObject();
		// creation of bollards list 
		ArrayList<JSONObject> listbollards = new ArrayList<JSONObject>();

		while (rs2.next()) {
			JSONObject borne=new JSONObject();
			// recovery of each borne's data (id/ state/ position) 
			borne.put("id", rs2.getInt("id"));
			borne.put("state", rs2.getString("state"));
			borne.put("address", rs2.getString("address"));
			System.out.println("je suis ici" + borne);

			// adding each borne to the list already created
			listbollards.add(borne);


		}
		obj.put("bollards", listbollards);
		/*adding the static variables to the response (the user gets immediatly after launching the
		 application the variables on cars) */
		obj.put("totalVehicule", Integer.toString(VehiculeManagement.totalVehicule));
		obj.put("threshold", Integer.toString(VehiculeManagement.threshold));
		System.out.println("voici le json envoyé avec la liste des bornes: ");
		// displaying the Json
		System.out.println(obj);
		return obj; 
	}
	
	/* method to know if the bollards are rised or not in order to let the cars to pass to town or not
	 * it search if one borne is rised if no result returned it means that the borne is lowered 
	 * and also all the bollards and if a result is returned it means that bollards are rised	
	 */
		
	public boolean forbiddenPassage() throws SQLException, InterruptedException {
		
		PreparedStatement stmt1 = c.prepareStatement("select state from bollards where id = 1;");
		ResultSet rs2 = stmt1.executeQuery();
		int i = 0;
		// creation of users list 

		while (rs2.next()) {
			i++;
			JSONObject borne=new JSONObject();
			borne.put("state", rs2.getString("state"));
			System.out.println("les bornes sont actuellement levées : " + borne);
		}
		if(i == 0) {
			return false;
		}
		else {
			return true;
		}
	}
	
/* method to change the bollards state in dataBase in order to rise them by updating the state
 * 1 => bollards rised
 * 0 => bollards lowered 	
 */
	
	public Object risebollards() throws SQLException, InterruptedException{
			PreparedStatement stmt = c.prepareStatement("update bollards set state= ?;");
			stmt.setInt(1, 1); 
			JSONObject obj=new JSONObject();
			if(stmt.executeUpdate()>=1) {
				obj.put("reponse",String.valueOf("les bornes ont bien été rélevé")); 
			}
			else {
				obj.put("reponse",String.valueOf("erreur lors du changement d'état des bornes"));
			}
			System.out.println(obj);
			return obj; 
	}

	
	/* method to change the bollards state in dataBase in order to lower them by updating the state
	 * 1 => bollards rised
	 * 0 => bollards lowered 	
	 */	
	
	public Object lowerbollards() throws SQLException, InterruptedException{
		PreparedStatement stmt = c.prepareStatement("update bollards set state= ?;");
		stmt.setInt(1, 0); 
		JSONObject obj=new JSONObject();
		if(stmt.executeUpdate()>=1) {
			obj.put("reponse",String.valueOf("les bornes ont bien été baissé")); 
		}
		else {
			obj.put("reponse",String.valueOf("erreur lors du changement d'état des bornes"));
		}
		System.out.println(obj);
		return obj; 
}
}
