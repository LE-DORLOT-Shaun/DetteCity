package puzzle_city_dto;
//import library
import java.sql.*;

import org.json.JSONObject;
//Class Connect

public class JDBCConnection {
	

    Connection conn = null;
    public JDBCConnection() {
		// TODO Auto-generated constructor stub    	

		    //Class.forName("com.mysql.cj.jdbc.Drive");  
		    
	}
    
    public Connection setConnection() {
    	try{
    		//conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3308/puzzle_db?serverTimezone=UTC", "root", "");
    		conn = DriverManager.getConnection("jdbc:mysql://172.31.249.135:3306/puzzle_01?serverTimezone=UTC", "root", "toto");
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
//    public static void main(String[] args) {
//    	JDBCConnection dbconn = new JDBCConnection();
//    	dbconn.setConnection();
//    
// 
//		try{
//
//		    //Class.forName("com.mysql.cj.jdbc.Drive");  
//		    Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3308/smartcity", "root", "");
//            if (conn != null) {
//                System.out.println("Connected to the database!");
//                try {
//                	java.sql.Statement st =  conn.createStatement();
//                	String sql = "select * from tblUser";
//                	ResultSet rs = st.executeQuery(sql);
//                	
//                    while(rs.next()){
//                    	JSONObject res = new JSONObject();
//                        int iId  = rs.getInt("uID");
//                        String strName = rs.getString("uName");
//                    	res.put("ID",iId );
//                    	res.put("Name",strName );
//
//                        System.out.print(res.toString());
//                        
//                    }
//                	st.close();
//                	
//					
//				} catch (Exception e) {
//					// TODO: handle exception
//
//		            e.printStackTrace(); 
//				}
//            } else {
//                System.out.println("Failed to make connection!");
//            }
//
//        } catch (SQLException e) {
//            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//		
//	}
	
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