
package puzzle_city_dto;


import java.awt.Point;
import java.sql.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import puzzle_city_model.ApiResponse;

public class StationProvider {

	JDBCConnection dbconn;
	static Connection conn;
	static Statement st;
	
	public StationProvider() {
		// TODO Auto-generated constructor stub
		System.out.println("Create a connection");
		dbconn = new JDBCConnection();
		conn = dbconn.setConnection();
		System.out.println("Create connection successfully");
	}
	

	//get byID 
	public static ApiResponse getByID(int id) {
		try {
			
        	String sql = "select * from tblstation where sIdCity = " + id;
        	System.out.println(sql);

    		st =  conn.createStatement();
        	ResultSet rs = st.executeQuery(sql);        	

    		JSONArray stationAll = new JSONArray();
    		if(rs.next() == false) {
        		return new ApiResponse(false, stationAll, "Not Found");
    		}else {
    			 do {
                	JSONObject resItem = new JSONObject();                	

                    resItem.put("ID", rs.getInt("sId"));
                    resItem.put("Name",  rs.getString("sName") );
                    resItem.put("IdCity", rs.getInt("sIdCity") );
                    resItem.put("Lat", rs.getInt("sLat") );
                    resItem.put("Long", rs.getInt("sLong") );
                    resItem.put("IdLine", rs.getInt("sIdLine") );
                    resItem.put("Position", rs.getInt("sPosition") );          
                    stationAll.put(resItem);                    
                }	while(rs.next());
        		return new ApiResponse(true, stationAll, "Success");
			}     	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			try {
				return new ApiResponse(false, null, e.getMessage());
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return null;
			}

		}
	}
}
