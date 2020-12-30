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
   
   //Notre liste de commandes. Le serveur nous r�pondra diff�remment selon la commande utilis�e.
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
            //TOUJOURS UTILISER flush() POUR ENVOYER R�ELLEMENT DES INFOS AU SERVEUR
            writer.flush();  
            
            System.out.println("Commande " + commande + " envoy�e au serveur");
            
            //On attend la r�ponse
            String response = read();
            System.out.println("\t * " + name + " : R�ponse re�ue " + response);
            
           //////////////////////////////////////////////
            
         String commande2 = "";//window.getChosenWidth();
         
         writer.write(commande2);
         //TOUJOURS UTILISER flush() POUR ENVOYER R�ELLEMENT DES INFOS AU SERVEUR
         writer.flush();  
         
         System.out.println("Commande " + commande2 + " envoy�e au serveur");
         
         //On attend la r�ponse
         String response2 = read();
         System.out.println("\t * " + name + " : R�ponse re�ue " + response2);
            
            ///////////////////////////////////////////
            
         String commande3 ="";// window.getChosenLength();
         
         writer.write(commande3);
         //TOUJOURS UTILISER flush() POUR ENVOYER R�ELLEMENT DES INFOS AU SERVEUR
         writer.flush();  
         
         System.out.println("Commande " + commande3 + " envoy�e au serveur");
         
         //On attend la r�ponse
         String response3 = read();
         System.out.println("\t * " + name + " : R�ponse re�ue " + response3);
          
         ///////////////////////////////////////////
            
         String commande4 = "";//window.getChosenPoints();
         
         writer.write(commande4);
         //TOUJOURS UTILISER flush() POUR ENVOYER R�ELLEMENT DES INFOS AU SERVEUR
         writer.flush();  
         
         System.out.println("Commande " + commande4 + " envoy�e au serveur");
         
         //On attend la r�ponse
         String response4 = read();
         System.out.println("\t * " + name + " : R�ponse re�ue " + response4);
            
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
   
   //M�thode qui permet d'envoyer des commandeS de fa�on al�atoire
   private String getCommand(){
      Random rand = new Random();
      return listCommands[rand.nextInt(listCommands.length)];
   }
   
   //M�thode pour lire les r�ponses du serveur
   private String read() throws IOException{      
      String response = "";
      int stream;
      byte[] b = new byte[4096];
      stream = reader.read(b);
      response = new String(b, 0, stream);      
      return response;
   }   
}