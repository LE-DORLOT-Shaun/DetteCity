package socketserver;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

import commons.GetConnectionData;
import socketserver.EnvoyerRecevoir;
import dto.Provider;
import model.ApiResponse;
import socketserver.Question;
import business.BollardsManagement;
import business.VehiculeSensors;

public class ThreadServer extends Thread {
	private static EnvoyerRecevoir ER;
	private static Provider Provider;
	private static Question Question;
	private static BollardsManagement BM;
	private static VehiculeSensors VS;
	private Connection c;
	
	public static void main(String[] test) throws ClassNotFoundException, SQLException, JSONException, InterruptedException {
		final ServerSocket serveurSocket;
		final Socket clientSocket;
		Connection c;
		
		GetConnectionData coData;
		final BufferedReader in;
		final PrintWriter out;
		final Scanner sc=new Scanner(System.in);
		
		System.out.println("Serveur en attente de connexion");
			try {
				c = DriverManager.getConnection(
					      "jdbc:postgresql://172.31.249.135:5432/dettecitydb","postgres","toto");
				
				serveurSocket = new ServerSocket(6666);
				clientSocket = serveurSocket.accept();
				System.out.println("Connexion établie");
				out = new PrintWriter(clientSocket.getOutputStream());
				in = new BufferedReader (new InputStreamReader (clientSocket.getInputStream()));
				//coData = new GetConnectionData();
				//coData.createDataConnection();
				System.out.println("connexion base terminée");
				
				//appel nb_vehicule
				//Connection conn3 = DriverManager.getConnection(
				//	      "jdbc:postgresql://172.31.249.135:5432/dettecitydb","postgres","toto");
				BM = new BollardsManagement(c);
				
				      
				      //test json vehicule
				      System.out.println("test simu");
				      JSONParse Parse = new JSONParse(clientSocket, c);			      
				      System.out.println("test simu fin");
				     
				      
				      Provider = new Provider();
				      
				      ApiResponse msg2 = Provider.getAll();
				      out.println(msg2);
				      out.flush();
				      
				      //Programme question
				      System.out.println("lancement question");
				      String q = "Voulez-vous changer ce seuil ? o/n";
						out.println(q);
					    out.flush();
					    System.out.println("avant readLine o");
					    String rep = in.readLine();
					    String json = rep;
					    System.out.println("rep : " + rep);
					    JSONObject object = new JSONObject(json);
					    String Modif = object.getString("Modif");
					    System.out.println("Modif : " + Modif);
					    if(Modif.equals("o")) {
					    	String q1 = "Entrez le nouveau seuil : ";
					    	out.println(q1);
							out.flush();
							String newth = in.readLine();
							   	
							String json1 = newth;
							System.out.println("newth = " + newth);
							JSONObject object1 = new JSONObject(json1);
							int Threshold = object1.getInt("Threshold");
							System.out.println("Threshold : " + Threshold);
									    
							
							dto.Provider.updateThreshold(Threshold);
					    	
					    }
				      System.out.println("question exec");
					
				      //étape 1: charger la classe de driver
				      Class.forName("org.postgresql.Driver");
				      System.out.println("driver chargé");
				      //étape 2: créer l'objet de connexion
				      Connection conn2 = DriverManager.getConnection(
				      "jdbc:postgresql://172.31.249.135:5432/dettecitydb","postgres","toto");
				      System.out.println("après adresse");
				      //étape 3: créer l'objet statement 
				      Statement stmt2 = conn2.createStatement();
				      ResultSet res2 = stmt2.executeQuery("SELECT * FROM threshold");
				      //étape 4: exécuter la requête
				      System.out.println("res2 =" + res2);
				      int tv = 0;
				      while(res2.next())
				    	 tv = res2.getInt(1);
				      	System.out.println(tv);
				          //System.out.println(res2.getInt(1)+"  "+res2.getDate(2));
				      String msg = "nombre de véhicules autorisés : " + tv;
				      System.out.println(msg);
				      //ER.main(msg);
				      System.out.println("Envoyé");
				      //envoyer les données au client
				      
				      //étape 5: fermez l'objet de connexion
				      conn2.close();
								      
			      }catch (IOException e) {
			         e.printStackTrace();
			      }
		}
}
