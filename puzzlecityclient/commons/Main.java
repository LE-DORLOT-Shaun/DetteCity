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
      try {
         /*
         * les informations du serveur ( port et adresse IP ou nom d'hote
         * 127.0.0.1 est l'adresse local de la machine
         */
         //clientSocket = new Socket("127.0.0.1",5000);
    	  clientSocket = new Socket("172.31.249.155",6666);
    	  
    	  System.out.println("connexion réussie");
         //flux pour envoyer
         out = new PrintWriter(clientSocket.getOutputStream());
         //flux pour recevoir
         in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
   
         Thread envoyer = new Thread(new Runnable() {
             String msg, msg1;
             @Override
              public void run() {
            	 //String msg1 = sc.nextLine();
            	  /*if (msg1.contentEquals("o") ) {
            		  out.println("o");
                      out.flush();
            		  System.out.println("entrez le nouveau seuil en ppm");
            		  int newth = sc.nextInt();
            		  out.println(newth);
                      out.flush();
            	  } else {
            		  
            	  }*/
            /*	TJ = new TestJson();
            	try {
					TJ.launchSimulation();
				} catch (SQLException | IOException | JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
            	
            	
            	 ApplicationController ac = new ApplicationController(test);
            	 
                while(true){
                  msg = sc.nextLine();
                  try {
                	 
					json.put("Modif", msg);
					String msgjson = json.toString();
					out.println(msgjson);
					System.out.println("message transmis : " + msgjson);
					System.out.println("avant flush");
	                out.flush();
	                
	                msg1 = sc.nextLine();
	                json1.put("Threshold", msg1);
					String msgjson1 = json1.toString();
					out.println(msgjson1);
					System.out.println("message transmis : " + msgjson1);
					System.out.println("avant flush");
	                out.flush();
	                
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                  
                }
             }
         });
         envoyer.start();
   
        Thread recevoir = new Thread(new Runnable() {
            String msg;
            @Override
            public void run() {
               try {
                 msg = in.readLine();
                 while(msg!=null){
                    System.out.println("Serveur : "+msg);
                    msg = in.readLine();
                 }
                 System.out.println("Serveur déconnecté");
                 out.close();
                 clientSocket.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
            }
        });
        recevoir.start();
   
      } catch (IOException e) {
    	  System.out.println("Erreur");
           e.printStackTrace();
      }
  }
}
