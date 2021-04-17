package commons;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConnectPostgresSQL {
	
	  public void BDD() {
	    try
	    {
	    	System.out.println("Dans connect postgresql");
	      //étape 1: charger la classe de driver
	      Class.forName("org.postgresql.Driver");
	      //étape 2: créer l'objet de connexion
	      Connection conn = DriverManager.getConnection(
	      "jdbc:postgresql://172.31.249.135:5432/dettecitydb","toto","toto");
	      System.out.println("après adresse");
	      //étape 3: créer l'objet statement 
	      Statement stmt = conn.createStatement();
	      ResultSet res = stmt.executeQuery("SELECT * FROM utilisateur");
	      //étape 4: exécuter la requête
	      while(res.next())
	        System.out.println(res.getInt(1)+"  "+res.getString(2)
	        +"  "+res.getString(3));
	      //étape 5: fermez l'objet de connexion
	      conn.close();
	    }
	    catch(Exception e){ 
	      System.out.println(e);
	    }
	  }
}
