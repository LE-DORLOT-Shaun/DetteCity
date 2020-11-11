package tests;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.json.simple.JSONObject;

import commons.SocketClient;
import com.AccessServer;

//import indicator.CarIndicator;
//import indicator.SensorIndicator;
//import indicator.StationIndicator;


public class TestCLI {
	private SocketClient client = new SocketClient();
	public static Connection c; 
	private static String URL = "jdbc:postgresql://172.31.249.135:5432/dettecitydb";
	private static String login = "toto" ;
	private static String password = "toto";


	public static Connection createConnection() throws SQLException {
		try {

			return  DriverManager.getConnection (URL, login, password);
		} catch (SQLException e) {
			throw new SQLException("Can't create connection", e);
		}

	}
	
	public static void main(String [] args) throws SQLException, IOException {
		TestCLI t = new TestCLI();
		t.technicalTests();
	//	t.testCLI();
		
		
	//	PagePrincipale page = new PagePrincipale();
	//	page.setVisible(true);
	
		
	}
	
	public void technicalTests() throws IOException {
		client.startConnection(AccessServer.getSERVER(), AccessServer.getPORT_SERVER());
		JSONObject obj=new JSONObject();  //JSONObject creation
		System.out.println("***************** TEST 1 : Insertion nouveaux max voitures + affichage*********************");
		obj.put("demandType",String.valueOf("ChangeLimitTest")); 
		System.out.println(obj);	
		JSONObject ChangeLimitTest = client.sendMessage(obj);
		System.out.println("voici les resultats des tests :" + ChangeLimitTest);
		
		ArrayList<JSONObject> allValues = new ArrayList<JSONObject>();// Creation d'un tableau de type SensorIndicator
		allValues = (ArrayList<JSONObject>) ChangeLimitTest.get("testsResults");
		for(int i = 0; i<allValues.size();i++) { // Creating a loop to display all sensors in the table sensors
			System.out.println("reponse: "+allValues.get(i).get("reponse")+
					" | max voitures: "+allValues.get(i).get("max voitures: ")); 
			
		}
		client.stopConnection(); 
		
		client.startConnection(AccessServer.getSERVER(), AccessServer.getPORT_SERVER());
		obj=new JSONObject();
		System.out.println("***************** TEST 2 : Test rechreche et affichage vehicules selon filtres*********************");
		obj.put("demandType",String.valueOf("selectHistoryTest")); 
		System.out.println(obj);	
		JSONObject selectInHistorytest = client.sendMessage(obj);
		System.out.println("voici les resultats des tests :" + selectInHistorytest);
		allValues = (ArrayList<JSONObject>) selectInHistorytest.get("testsResults");
		for(int i = 0; i<allValues.size();i++) { // Creating a loop to display all sensors in the table sensors 
			System.out.println("************** rechreche  " + i + " *********************");
			ArrayList<JSONObject> allCars = (ArrayList<JSONObject>) allValues.get(i).get("voitures");
			if(allCars.size() == 0) {
				System.out.println("la rechreche ne retourne pas de resultats");
			}
			for(int j = 0; j<allCars.size(); j++) {
					
					System.out.println("voiture: " + allCars.get(j).get("vehicule") +
										" | date: "   + allCars.get(j).get("date") +
										" | type: "   + allCars.get(j).get("type") +
										" | position: " + allCars.get(j).get("position"));
			
			}

		}
		client.stopConnection(); 
		
		client.startConnection(AccessServer.getSERVER(), AccessServer.getPORT_SERVER());
		obj=new JSONObject();
		System.out.println("***************** TEST 3 : insertion voitures et insertion nouveau nb vehicules*********************");
		obj.put("demandType",String.valueOf("insertCarsAndActualNb")); 
		System.out.println(obj);	
		JSONObject InsertCarsAndNbVoitures = client.sendMessage(obj);
		System.out.println("voici les resultats des tests :" + InsertCarsAndNbVoitures);
		
		ArrayList<JSONObject> InsertedCars = (ArrayList<JSONObject>) InsertCarsAndNbVoitures.get("testsResults");
		for(int j = 0; j<InsertedCars.size(); j++) {
				
				System.out.println("reponse: " + InsertedCars.get(j).get("reponse") +
									" | nombre de vehicules: "   + InsertedCars.get(j).get("NbVoitures") +
									" | vehicule: "   + InsertedCars.get(j).get("vehicule") +
									" | type: "   + InsertedCars.get(j).get("type"));
		
		}
		client.stopConnection(); 
	}
	
	
}
	

