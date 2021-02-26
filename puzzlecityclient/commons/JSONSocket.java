package commons;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONValue;

public class JSONSocket {
	private static Socket socketClient;
	private static PrintWriter outJson;
	private static BufferedReader inJson;
	JSONObject json = new JSONObject();
	
	
/*	public JSONSocket(String motif, String msg) throws JSONException, IOException {
		final Socket clientSocket;
		clientSocket = new Socket("172.31.249.155",6666);
		final PrintWriter out;
		out = new PrintWriter(clientSocket.getOutputStream());
		// TODO Auto-generated constructor stub
		json.put("modif", msg);
		String msgjson = json.toString();
		out.println(msgjson);
		System.out.println("message transmis : " + msgjson);
		System.out.println("avant flush");
        out.flush();
	}*/
	public void startConnection(String ip, int port) throws IOException {
				socketClient = new Socket(ip, port);
				outJson = new PrintWriter(socketClient.getOutputStream(), true);
				inJson = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
	}
			
	public static JSONObject sendMessage(JSONObject JsonMsg) throws IOException {
					
		outJson.println(JsonMsg);
		System.out.println("j'ai envoyé le message");
		String resp = inJson.readLine();
		System.out.println("j'ai reçu la réponse" + resp);
		Object obj=JSONValue.parse(resp);
		System.out.println("après obj =" + obj);
		JSONObject jsonObject = (JSONObject) obj; 
		System.out.println("jsonObject =" + jsonObject);
		return jsonObject;
	}
	public static void stopConnection() throws IOException {
		inJson.close();
		outJson.close();
		socketClient.close();
	}
	
}
