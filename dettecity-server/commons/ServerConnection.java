package commons;


import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

//import test.NetworkCard;

public class ServerConnection implements Runnable{

   private Socket connection = null;
   private PrintWriter writer = null;
   private BufferedInputStream reader = null;
   
   //Notre liste de commandes. Le serveur nous répondra différemment selon la commande utilisée.
   private String[] listCommands = {"FULL", "DATE", "HOUR", "NONE"};
   private static int count = 0;
   private String name = "Client-";   
   
   public ServerConnection(String host, int port){
      name += ++count;
      try {
         connection = new Socket(host, port);
      } catch (UnknownHostException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
   
   
   public void run(){

      //nous n'allons faire que 10 demandes par thread...
      for(int i =0; i < 10; i++){
         try {
            Thread.currentThread().sleep(1000);
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
         try {

        	 // Here
            
            //On envoie la commande au serveur
            
        	 //NetworkCard window = new NetworkCard();
            String commande = "";
            
            writer.write(commande);
            //TOUJOURS UTILISER flush() POUR ENVOYER RÉELLEMENT DES INFOS AU SERVEUR
            writer.flush();  
            
            System.out.println("Commande " + commande + " envoyée au serveur");
            
            //On attend la réponse
            String response = read();
            System.out.println("\t * " + name + " : Réponse reçue " + response);
            
           //////////////////////////////////////////////
            
         String commande2 = "";//window.getChosenWidth();
         
         writer.write(commande2);
         //TOUJOURS UTILISER flush() POUR ENVOYER RÉELLEMENT DES INFOS AU SERVEUR
         writer.flush();  
         
         System.out.println("Commande " + commande2 + " envoyée au serveur");
         
         //On attend la réponse
         String response2 = read();
         System.out.println("\t * " + name + " : Réponse reçue " + response2);
            
            ///////////////////////////////////////////
            
         String commande3 ="";// window.getChosenLength();
         
         writer.write(commande3);
         //TOUJOURS UTILISER flush() POUR ENVOYER RÉELLEMENT DES INFOS AU SERVEUR
         writer.flush();  
         
         System.out.println("Commande " + commande3 + " envoyée au serveur");
         
         //On attend la réponse
         String response3 = read();
         System.out.println("\t * " + name + " : Réponse reçue " + response3);
          
         ///////////////////////////////////////////
            
         String commande4 = "";//window.getChosenPoints();
         
         writer.write(commande4);
         //TOUJOURS UTILISER flush() POUR ENVOYER RÉELLEMENT DES INFOS AU SERVEUR
         writer.flush();  
         
         System.out.println("Commande " + commande4 + " envoyée au serveur");
         
         //On attend la réponse
         String response4 = read();
         System.out.println("\t * " + name + " : Réponse reçue " + response4);
            
            ///////////////////////////////////////////
            
         } catch (IOException e1) {
            e1.printStackTrace();
         }
         
         try {
            Thread.currentThread().sleep(1000);
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
      }
      
      writer.write("CLOSE");
      writer.flush();
      writer.close();
   }
   
   //Méthode qui permet d'envoyer des commandeS de façon aléatoire
   private String getCommand(){
      Random rand = new Random();
      return listCommands[rand.nextInt(listCommands.length)];
   }
   
   //Méthode pour lire les réponses du serveur
   private String read() throws IOException{      
      String response = "";
      int stream;
      byte[] b = new byte[4096];
      stream = reader.read(b);
      response = new String(b, 0, stream);      
      return response;
   }   
}