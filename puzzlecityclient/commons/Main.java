package commons;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;
import commons.TestJson;
import commons.ApplicationController;
import view.ConnectionDetteCity;

public class Main {
	private static TestJson TJ;
	private static ApplicationController ac;
	private static ConnectionDetteCity test = new ConnectionDetteCity();

   public static void main(String[] args) {
      JSONObject json = new JSONObject();
      JSONObject json1 = new JSONObject();
      final Socket clientSocket;
      final BufferedReader in;
      final PrintWriter out;
      final Scanner sc = new Scanner(System.in);//pour lire à partir du clavier
      System.out.println("Client connecté");
//      try {
         /*
         * les informations du serveur ( port et adresse IP ou nom d'hote
         * 127.0.0.1 est l'adresse local de la machine
         */
         //clientSocket = new Socket("127.0.0.1",5000);
//    	  clientSocket = new Socket("172.31.249.84",6666);
    	  //clientSocket = new JSONSocket();
  		  //client.startConnection(AccessServer.getSERVER(), AccessServer.getPORT_SERVER());
    	  System.out.println("connexion réussie");
         //flux pour envoyer
 //   	  out = new PrintWriter(clientSocket.getOutputStream());
         //flux pour recevoir
 //        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
         
         ApplicationController ac = new ApplicationController(test);
         
           
        
   
/*      } catch (IOException e) {
    	  System.out.println("Erreur");
           e.printStackTrace();
      }
*/      
  }
  
}
