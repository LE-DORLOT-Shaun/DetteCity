package dto;

//import library
import java.sql.*;

import org.json.JSONObject;
//Class Connect

public class JDBCConnection {
	
	Connection conn = null;
	public JDBCConnection() {
		// TODO Auto-generated constructor stub    	

		    
		    
	}
  
  public Connection setConnection() {
  	try{
  		//conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/dettecitydb","postgres","toto");
  		conn = DriverManager.getConnection("jdbc:postgresql://172.31.249.135:5432/dettecitydb","postgres","toto");
	    	if (conn != null) {
	         //   System.out.println("Connected to the database!");
	            return conn;
	        } else {
	            System.out.println("Failed to make connection!");
	            return null;
	        }
	
	    } catch (SQLException e) {
	        System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
          return null;
	    } catch (Exception e) {
	        e.printStackTrace();
          return null;
	    }
	}
	
	// close connection
  public void close() {
      try {
          if (conn != null) {
          	conn.close();
          }
      } catch (Exception e) {

      }
  }
}