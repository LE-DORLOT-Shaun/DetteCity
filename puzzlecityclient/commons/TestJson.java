package commons;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import commons.JSONSocket;
//import view.Dashboard;
import commons.AccessServer;

public class TestJson {	
	private static JSONSocket client = new JSONSocket();
	public static Connection c; 
	private static String URL = "jdbc:postgresql://172.31.249.113:5432/dettecitydb";
	private static String login = "toto" ;
	private static String password = "toto";


	public static Connection createConnection() throws SQLException {
		try {

			return  DriverManager.getConnection (URL, login, password);
		} catch (SQLException e) {
			throw new SQLException("Can't create connection", e);
		}

	}
	
	public static void main(String [] args) throws SQLException, IOException, JSONException {
		TestJson t = new TestJson();
	//	Dashboard page = new Dashboard();
	//	page.frame.setVisible(true);
	
		t.getBornes();
	}
	
	
	
	/* function used to launch the cars simulation when the server receive a launch simulation 
	 demand it starts the thread that insert the cars in tables to increase or decrease cars in town  */
	
	public static org.json.simple.JSONObject launchSimulation() throws SQLException, IOException,UnsupportedEncodingException, JSONException, InterruptedException {
		System.out.println("je rentre deja dans la simulation");
		client = new JSONSocket();
		client.startConnection(AccessServer.getSERVER(), AccessServer.getPORT_SERVER());
		JSONObject obj=new JSONObject();  //JSONObject creation
		obj.put("demandType", String.valueOf("launchSimulation")); 
		System.out.println(obj);
		Thread.sleep(2000);
		org.json.simple.JSONObject reponseSimulation = JSONSocket.sendMessage(obj);
		System.out.println(reponseSimulation);
		client.stopConnection(); 

		return reponseSimulation; 
		
	}
	
	/* method called when the program starts it calls the server to get all the bornes states and
	 * also the initial number of cars actually in town and the initial max in the server */
	
	public static org.json.simple.JSONObject getBornes() throws SQLException, IOException,UnsupportedEncodingException, JSONException {
		JSONSocket client = new JSONSocket();
		client.startConnection(AccessServer.getSERVER(), AccessServer.getPORT_SERVER());
//		System.out.println("connexion réussie");
		JSONObject obj=new JSONObject();  //JSONObject creation
//		System.out.println("envoi du demandtype");
		obj.put("demandType",String.valueOf("getInitialInfos")); 
		System.out.println(obj);	
		org.json.simple.JSONObject reponseBornes = client.sendMessage(obj);
		System.out.println(reponseBornes);
		client.stopConnection(); 

		return reponseBornes; 
		
	}
	
	public static org.json.simple.JSONObject getAlertP() throws SQLException, IOException,UnsupportedEncodingException, JSONException {
		JSONSocket client = new JSONSocket();
		client.startConnection(AccessServer.getSERVER(), AccessServer.getPORT_SERVER());
//		System.out.println("connexion réussie");
		JSONObject obj=new JSONObject();  //JSONObject creation
//		System.out.println("envoi du demandtype");
		obj.put("demandType",String.valueOf("getAlertP")); 
		System.out.println(obj);
		org.json.simple.JSONObject reponseAlertP = client.sendMessage(obj);
		System.out.println(reponseAlertP);
		client.stopConnection();

		return reponseAlertP; 
		
	}
	
	/* method that takes in parameter the new max vehicules authorised in town  and updates the 
	 * the table with it by replacing the old one*/
	
	public static org.json.simple.JSONObject changeMax(int max) throws SQLException, IOException,UnsupportedEncodingException, JSONException {
		client = new JSONSocket();
		client.startConnection(AccessServer.getSERVER(), AccessServer.getPORT_SERVER());
		JSONObject obj=new JSONObject();  //JSONObject creation
		obj.put("demandType",String.valueOf("vehiculeThreshold")); 
		obj.put("maxCars",Integer.valueOf(max)); 
		System.out.println(obj);	
		org.json.simple.JSONObject reponseMaxVehicules = client.sendMessage(obj);
		System.out.println(reponseMaxVehicules);
		client.stopConnection(); 

		return reponseMaxVehicules; 
		
	}
	
	//method to set a polution alert
	public static org.json.simple.JSONObject setAlertP() throws SQLException, IOException,UnsupportedEncodingException, JSONException {
		System.out.println("Une alerte pollution va être créée");
		client = new JSONSocket();
		client.startConnection(AccessServer.getSERVER(), AccessServer.getPORT_SERVER());
		JSONObject obj=new JSONObject();  //JSONObject creation
		obj.put("demandType",String.valueOf("setAlertP")); 
		System.out.println(obj);	
		org.json.simple.JSONObject reponseSimulation = client.sendMessage(obj);
		System.out.println(reponseSimulation);
		client.stopConnection(); 

		return reponseSimulation; 
		
	}
	
	//method to delete a polution alert
		public static org.json.simple.JSONObject delAlertP() throws SQLException, IOException,UnsupportedEncodingException, JSONException {
			System.out.println("L'alerte pollution sera annulée");
			client = new JSONSocket();
			client.startConnection(AccessServer.getSERVER(), AccessServer.getPORT_SERVER());
			JSONObject obj=new JSONObject();  //JSONObject creation
			obj.put("demandType",String.valueOf("delAlertP")); 
			System.out.println(obj);	
			org.json.simple.JSONObject reponseSimulation = client.sendMessage(obj);
			System.out.println(reponseSimulation);
			client.stopConnection(); 

			return reponseSimulation; 
			
		}
	
	/*************************** static method that sends a demand via the socket to the server to 
	rise the bornes by changing their state 
	 * @throws JSONException ***********************/	
	
	public static org.json.simple.JSONObject riseBornes() throws SQLException, IOException,UnsupportedEncodingException, JSONException {
		System.out.println("l'état des bornes va etre modifié");
		client = new JSONSocket();
		client.startConnection(AccessServer.getSERVER(), AccessServer.getPORT_SERVER());
		JSONObject obj=new JSONObject();  //JSONObject creation
		obj.put("demandType",String.valueOf("RiseBornes")); 
		System.out.println(obj);	
		org.json.simple.JSONObject reponseSimulation = client.sendMessage(obj);
		System.out.println(reponseSimulation);
		client.stopConnection(); 

		return reponseSimulation; 
		
	}
	
	/*************************** static method that sends a demand via the socket to the server to 
	lower the bornes by changeing their state 
	 * @throws JSONException ***********************/

	public static org.json.simple.JSONObject LowerBornes() throws SQLException, IOException,UnsupportedEncodingException, JSONException {
			System.out.println("l'état des bornes va etre modifié");
			client = new JSONSocket();
			client.startConnection(AccessServer.getSERVER(), AccessServer.getPORT_SERVER());
			JSONObject obj=new JSONObject();  //JSONObject creation
			obj.put("demandType",String.valueOf("LowerBornes")); 
			System.out.println(obj);	
			org.json.simple.JSONObject reponseSimulation = client.sendMessage(obj);
			System.out.println(reponseSimulation);
			client.stopConnection(); 

			return reponseSimulation; 

	}
	
	/* static method that takes in parameter the filters selected by the user and send them to the user
	 * in order to get from the history table the cars corresponding to the criteria 
	 */
	
	public static org.json.simple.JSONObject searchVehicule(String dateDebut, String dateFin, String zone, String type) throws SQLException, IOException,UnsupportedEncodingException, JSONException {
		System.out.println("je rentre deja dans la recherche vehicules");
		client = new JSONSocket();
		client.startConnection(AccessServer.getSERVER(), AccessServer.getPORT_SERVER());
		JSONObject obj=new JSONObject();  //JSONObject creation
		obj.put("demandType",String.valueOf("filterVehicule"));
		if(type.equals("Les deux")) {
			type = "town";
		}
		if(zone.equals("toute la ville")) {
			zone = "All";
		}
		obj.put("type", String.valueOf(type));
		obj.put("zone", String.valueOf(zone));
		obj.put("dateDebut", String.valueOf(dateDebut));
		obj.put("dateFin", String.valueOf(dateFin));
		
		System.out.println(obj);	
		org.json.simple.JSONObject reponseSearch = client.sendMessage(obj);
		client.stopConnection(); 

		return reponseSearch; 
		
	}

	public static org.json.simple.JSONObject UpdateBollards(int currentID, String currenttextAddress) throws IOException, JSONException {
		// TODO Auto-generated method stub
		client = new JSONSocket();
		client.startConnection(AccessServer.getSERVER(), AccessServer.getPORT_SERVER());
		JSONObject obj=new JSONObject();  //JSONObject creation
		obj.put("demandType",String.valueOf("UpdateBollards")); 
		obj.put("currentID",Integer.valueOf(currentID));
		obj.put("currenttextAddress",String.valueOf(currenttextAddress));
		System.out.println(obj);	
		org.json.simple.JSONObject reponseSimulation = client.sendMessage(obj);
		System.out.println(reponseSimulation);
		client.stopConnection(); 

		return reponseSimulation;
	}

	public static org.json.simple.JSONObject CreateBollards(String address) throws IOException, JSONException {
		// TODO Auto-generated method stub
		client = new JSONSocket();
		client.startConnection(AccessServer.getSERVER(), AccessServer.getPORT_SERVER());
		JSONObject obj=new JSONObject();  //JSONObject creation
		obj.put("demandType",String.valueOf("CreateBollards"));
		obj.put("address",String.valueOf(address));
		System.out.println(obj);	
		org.json.simple.JSONObject reponseSimulation = client.sendMessage(obj);
		System.out.println(reponseSimulation);
		client.stopConnection(); 

		return reponseSimulation;
	}

	public static org.json.simple.JSONObject DeleteBollards(int id) throws IOException, JSONException {
		// TODO Auto-generated method stub
		client = new JSONSocket();
		client.startConnection(AccessServer.getSERVER(), AccessServer.getPORT_SERVER());
		JSONObject obj=new JSONObject();  //JSONObject creation
		obj.put("demandType",String.valueOf("DeleteBollards"));
		obj.put("id",Integer.valueOf(id));
		System.out.println(obj);
		org.json.simple.JSONObject reponseSimulation = client.sendMessage(obj);
		System.out.println(reponseSimulation);
		client.stopConnection(); 

		return reponseSimulation;
	}
	
	
}
