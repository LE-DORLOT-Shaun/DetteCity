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
	      //�tape 1: charger la classe de driver
	      Class.forName("org.postgresql.Driver");
	      //�tape 2: cr�er l'objet de connexion
	      Connection conn = DriverManager.getConnection(
	      "jdbc:postgresql://172.31.249.135:5432/dettecitydb","toto","toto");
	      System.out.println("apr�s adresse");
	      //�tape 3: cr�er l'objet statement 
	      Statement stmt = conn.createStatement();
	      ResultSet res = stmt.executeQuery("SELECT * FROM utilisateur");
	      //�tape 4: ex�cuter la requ�te
	      while(res.next())
	        System.out.println(res.getInt(1)+"  "+res.getString(2)
	        +"  "+res.getString(3));
	      //�tape 5: fermez l'objet de connexion
	      conn.close();
	    }
	    catch(Exception e){ 
	      System.out.println(e);
	    }
	  }
}
