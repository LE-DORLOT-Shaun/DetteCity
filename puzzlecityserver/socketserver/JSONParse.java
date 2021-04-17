package socketserver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;

import org.json.JSONObject;
import org.json.simple.JSONValue;

import business.Bollards;
import business.VehiculeSensors;

public class JSONParse {
	private Socket clientSocket; 
	public PrintWriter outJson;
	private BufferedReader inJson;
	private Connection c;
	private static final int NB_FAUSSE_ALERTE=2;
	private Bollards bollards;
	
	public JSONParse(Socket socket, Connection connection) {
		this.clientSocket = socket;
		this.c = connection;
		run();
	}
	
	
	public void run()  {
		try {
			outJson = new PrintWriter(clientSocket.getOutputStream(), true);
			inJson = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		/*	System.out.println("Je me connecte à la base");
			connectiondata = new GetConnectionData();
			System.out.println("Connexion réussie");*/
		//	do {
				System.out.println("en attente du json");
				InputStream inputStream = FileReader.class.getClassLoader().getSystemResourceAsStream("simulation.json"); 
				
				// processing part of Json 
/*				System.out.println(inputStream);
				outJson = new PrintWriter(clientSocket.getOutputStream(), true);
				inJson = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				System.out.println(inJson);
				this.bollards = new Bollards(this.c);
				System.out.println("blocage 4");
				Object obj1 = new Object();
				System.out.println("blocage 5");
				String resp = inJson.readLine();
				System.out.println("----bonjour je viens de récuperer le JSON");
				System.out.println(resp);
				Object obj=JSONValue.parse(resp); 
				System.out.println("----bonjour je parse le JSON");
				System.out.println(resp);
				JSONObject jsonObject = (JSONObject) obj;  
				System.out.println("----bonjour je viens de parser le JSON");
				System.out.println(resp);*/
				
				//if(jsonObject.get("demandType").equals("launchSimulation")) {
						JSONObject obja = new JSONObject();
						obja.put("reponse", String.valueOf("la simulation a ete lancee"));
						outJson.println(obja); 
						VehiculeSensors test = new VehiculeSensors(c, inputStream);
						test.start();  
				 //}
				 
		//	}while(!clientSocket.isClosed());
		} catch (Exception e) {
			//IF CASE : INSTANCE REALDATA ET LANCEMENT METHODE footprint OUTJSON

			//outJson.println(obj);
			/*DataSource.releaseConnection(c); 
			inJson.close();
			outJson.close();
			clientSocket.close();*/

			System.out.println("--------Un client s'est déconnecté de manière précipitée !-------");
			System.out.println(e.getMessage());

		}
	}	
}