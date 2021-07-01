package business;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.json.simple.JSONObject;

import business.VehiculeManagement;
import commons.ConnectionPool;

	/* class used to interact with the bollards in order to get the status or to change it */

public class Bollards {
	
	private Connection c;
	private VehiculeManagement VM;

	/* to initialise the object we must add a connection in order to get data from the data base */
	
	public Bollards() throws SQLException, InterruptedException {

//		this.c = c;
//		this.c = ConnectionPool.getConnection();
	}
	
	/*method used by the server to get the bollards informations from the dataBase and also the 
	 * the number of cars in town actually and the max cars authorised by getting the static variables */
	public Object bollardsState() throws SQLException, InterruptedException {
		Connection c = ConnectionPool.getConnection();
		System.out.println("dans bornes" + c);
		PreparedStatement stmt1 = c.prepareStatement("select * from bollards;");
		ResultSet rs2 = stmt1.executeQuery();
		System.out.println("post requete");
		JSONObject obj=new JSONObject();
		// creation of bollards list 
		ArrayList<JSONObject> listbollards = new ArrayList<JSONObject>();
		System.out.println("avant boucle");
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
		obj.put("totalVehicule", Integer.toString(VM.totalVehicule));
		obj.put("threshold", Integer.toString(VM.threshold));
		obj.put("alertP", Boolean.toString(VM.alertP));
		System.out.println("voici le json envoyé avec la liste des bornes: ");
		// displaying the Json
		System.out.println(obj);
		ConnectionPool.releaseConnection(c);
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
	
	public Object UpdateBollards(int id, String address) throws SQLException, InterruptedException {
		Connection c = ConnectionPool.getConnection();
		PreparedStatement stmt = c.prepareStatement("update bollards set address = ? where id = ?;");
		stmt.setString(1, address); 
		stmt.setInt(2, id); 
		JSONObject obj = new JSONObject();
		if(stmt.executeUpdate()>=1) {
			obj.put("reponse",String.valueOf("les bornes ont bien été mis à jour")); 
		}
		else {
			obj.put("reponse",String.valueOf("erreur lors de la mis à jour des bornes"));
		}
		ConnectionPool.releaseConnection(c);
		return obj;
	}
	
	public Object DeleteBollards(int id) throws SQLException, InterruptedException {
		// TODO Auto-generated method stub
		Connection c = ConnectionPool.getConnection();
		PreparedStatement stmt = c.prepareStatement("delete from bollards where id = ?;");
		stmt.setInt(1, id);
		JSONObject obj = new JSONObject();
		if(stmt.executeUpdate()>=1) {
			obj.put("reponse",String.valueOf("la borne a bien été supprimée")); 
		}
		else {
			obj.put("reponse",String.valueOf("erreur lors de la suppression des bornes"));
		}
		ConnectionPool.releaseConnection(c);
		return obj;
	}
	
	public Object CreateBollards(String address) throws SQLException, InterruptedException {
		// TODO Auto-generated method stub
		boolean bollardsstate;
/*		if (VM.totalVehicule >= VM.threshold || VM.alertP == true) {
			bollardsstate = true;
		} else {
			bollardsstate = false;
		}
*/		Connection c = ConnectionPool.getConnection();
		PreparedStatement stmt = c.prepareStatement("insert into bollards(state, address) values (?, ?);");
		stmt.setBoolean(1, true); 
		stmt.setString(2, address);
		JSONObject obj = new JSONObject();
		if(stmt.executeUpdate()>=1) {
			obj.put("reponse",String.valueOf("la borne a bien été créée")); 
		}
		else {
			obj.put("reponse",String.valueOf("erreur lors de la création de la borne"));
		}
		
		if(VM.totalVehicule < VM.threshold && VM.alertP == false) {
			lowerbollards();
		} else {
			risebollards();
		}
		
		ConnectionPool.releaseConnection(c);
		return obj;
	}
/* method to change the bollards state in dataBase in order to rise them by updating the state
 * 1 => bollards rised
 * 0 => bollards lowered 	
 */
	
	public Object risebollards() throws SQLException, InterruptedException{
			Connection c = ConnectionPool.getConnection();
			PreparedStatement stmt = c.prepareStatement("update bollards set state= ?;");
			stmt.setBoolean(1, true); 
			JSONObject obj=new JSONObject();
			if(stmt.executeUpdate()>=1) {
				obj.put("reponse",String.valueOf("les bornes ont bien été rélevé")); 
			}
			else {
				obj.put("reponse",String.valueOf("erreur lors du changement d'état des bornes"));
			}
			System.out.println(obj);
			ConnectionPool.releaseConnection(c);
			return obj; 
	}

	
	/* method to change the bollards state in dataBase in order to lower them by updating the state
	 * 1 => bollards rised
	 * 0 => bollards lowered 	
	 */	
	
	public Object lowerbollards() throws SQLException, InterruptedException{
		Connection c = ConnectionPool.getConnection();
		PreparedStatement stmt = c.prepareStatement("update bollards set state= ?;");
		stmt.setBoolean(1, false); 
		JSONObject obj=new JSONObject();
		if(stmt.executeUpdate()>=1) {
			obj.put("reponse",String.valueOf("les bornes ont bien été baissé")); 
		}
		else {
			obj.put("reponse",String.valueOf("erreur lors du changement d'état des bornes"));
		}
		System.out.println(obj);
		ConnectionPool.releaseConnection(c);
		return obj; 
	}
	
	
	public Object setAlertP() throws SQLException, InterruptedException{
		Connection c = ConnectionPool.getConnection();
		System.out.println("methode de levee d'alerteP");
		PreparedStatement stmt = c.prepareStatement("insert into alert values ('pollution', ?, current_timestamp);");
		stmt.setBoolean(1, true); 
		JSONObject obj=new JSONObject();
		if(stmt.executeUpdate()>=1) {
			obj.put("reponse",String.valueOf("Une alerte a bien ete creee")); 
		}
		else {
			obj.put("reponse",String.valueOf("erreur lors de la levee d'une alerte pollution"));
		}
		System.out.println(obj);
		ConnectionPool.releaseConnection(c);
		return obj; 
	}
	
	public Object delAlertP() throws SQLException, InterruptedException{
		Connection c = ConnectionPool.getConnection();
		PreparedStatement stmt = c.prepareStatement("insert into alert values ('pollution', ?, current_timestamp);");
		stmt.setBoolean(1, false); 
		JSONObject obj=new JSONObject();
		if(stmt.executeUpdate()>=1) {
			obj.put("reponse",String.valueOf("Une alerte a bien ete supprimee")); 
		}
		else {
			obj.put("reponse",String.valueOf("erreur lors de la suppression d'une alerte pollution"));
		}
		System.out.println(obj);
		ConnectionPool.releaseConnection(c);
		return obj; 
	}
	
	
	public Object PollutionAlert() throws SQLException, InterruptedException {
		Connection c = ConnectionPool.getConnection();
		PreparedStatement stmt1 = c.prepareStatement("select state from alert ORDER BY date DESC LIMIT 1;");
		ResultSet rs2 = stmt1.executeQuery();
		int i = 0;
		// creation of users list 
		JSONObject alertePollution = new JSONObject();
		while (rs2.next()) {
			i++;
			alertePollution.put("alertP", rs2.getBoolean("state"));
			System.out.println("Alerte pollution : " + alertePollution);
		}
		if(i == 0) {
			alertePollution.put("alertP", String.valueOf("impossible de recuperer les donnees") );
			ConnectionPool.releaseConnection(c);
			return alertePollution;
		}
		else {
			ConnectionPool.releaseConnection(c);
			return alertePollution;
		}
	}
}
