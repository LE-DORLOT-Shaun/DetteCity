package commons;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;



public class SocketClient {

	private static Socket socketClient;
	private static PrintWriter outJson;
	private static BufferedReader inJson;


	public void startConnection(String ip, int port) throws IOException {
		socketClient = new Socket(ip, port);
		outJson = new PrintWriter(socketClient.getOutputStream(), true);
		inJson = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
	}
	
		public static JSONObject sendMessage(JSONObject JsonMsg) throws IOException {
			
			outJson.println(JsonMsg);
			System.out.println("j'ai envoyé le message");
			String resp = inJson.readLine();
			System.out.println("j'ai reçu la réponse");
			Object obj=JSONValue.parse(resp); 
			JSONObject jsonObject = (JSONObject) obj;  
			return jsonObject;
		}
		

	public static JSONObject sendMessage2(JSONObject JsonMsg) {
		JSONObject jsonObject = new JSONObject(); 
		try {
			System.out.println(JsonMsg); 
			outJson.println(JsonMsg);
			String resp = inJson.readLine();
			System.out.println("==================> En String :  "  + resp);
			Object obj=JSONValue.parse(resp); 
			jsonObject = (JSONObject) obj;  
			System.out.println("==================> En JSON : "  + jsonObject);
			//JSONParser parser = new JSONParser();
			//jsonObject=(JSONObject) parser.parse(resp);
			/*if (resp != null ) {
				JSONParser parser = new JSONParser();
				jsonObject=(JSONObject) parser.parse(resp);
				//jsonObject = (JSONObject) obj;  
			}*/
			//return jsonObject;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}

	public static void stopConnection() throws IOException {
		inJson.close();
		outJson.close();
		socketClient.close();
	}
}