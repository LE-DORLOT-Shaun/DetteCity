package puzzle_city_dto;

import java.awt.print.Printable;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.text.html.HTMLEditorKit.Parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import puzzle_city_model.ActualData;
import puzzle_city_model.ApiResponse;
import puzzle_city_model.CarbonData;

public class CarbonDataProvider {

	JDBCConnection dbconn;
	static Connection conn;
	static Statement st;
	
	public CarbonDataProvider() {
		// TODO Auto-generated constructor stub
		dbconn = new JDBCConnection();
		conn = dbconn.setConnection();
	}
	
	//get all 
	public static ApiResponse getAll() {
		try {
			
        	st =  conn.createStatement();
        	String sql = "select * from carbondata";
        	ResultSet rs = st.executeQuery(sql);
        	
        	 ArrayList<CarbonData> Data_City = new ArrayList<CarbonData>();

            while(rs.next()){
            	//JSONObject resItem = new JSONObject();           	
                
              
            	
            	int ID = rs.getInt("ID");
            	String TravelMode = rs.getString("TravelMode");
            	double EmissionFactor = rs.getDouble("EmissionFactor");
            	int NbMax = rs.getInt("NbMax");
                
         
                Data_City.add(new CarbonData(ID,TravelMode,EmissionFactor,NbMax));
                
            }
            ApiResponse resturn = new ApiResponse(true, Data_City, "Success");
            System.out.println("Result:"+resturn.toString());
    		return resturn;
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

	
	
	
	// get byID 
	/*
	public static ApiResponse getByID(int id) {
		try {
			
        	st =  conn.createStatement();
        	String sql = "select * from tblcity where cId = " + id;
        	ResultSet rs = st.executeQuery(sql);        	

    		JSONArray cityAll = new JSONArray();
    		if(rs.next() == false) {
        		return new ApiResponse(false, cityAll, "Not Found");
    		}else {
    			 do {
                	JSONObject resItem = new JSONObject();                	

                    resItem.put("ID", rs.getInt("cId"));
                    resItem.put("Name",  rs.getString("cName") );
                    resItem.put("Height", rs.getFloat("cHeight") );
                    resItem.put("Width", rs.getFloat("cWidth") );
                    resItem.put("CenterLat", rs.getFloat("cCenterLat") );
                    resItem.put("CenterLong", rs.getFloat("cCenterLong") );
                    resItem.put("MapZoom",  rs.getInt("cMapZoom") );                    
                    cityAll.put(resItem);                    
                }	while(rs.next());
        		return new ApiResponse(true, cityAll, "Success");
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

	//create
	public static ApiResponse create(JSONObject record) {
		try {
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO tblcity values (null, ?, ?, ?, ?, ?, ?  )");
	         					
            String Name =  record.getString("name");
            Double Height = record.getDouble("height");
            Double Width = record.getDouble("width");
            Double CenterLat = record.getDouble("centerLat");
            Double CenterLong = record.getDouble("centerLong");
            int MapZoom = record.getInt("mapZoom");
            //long date_of_birth = Date.valueOf(date).getTime();
            pstmt.setString(1, Name);
            pstmt.setDouble(2, Height);
            pstmt.setDouble(3, Width);
            pstmt.setDouble(4, CenterLat);
            pstmt.setDouble(5, CenterLong);
            pstmt.setInt(6, MapZoom);
            
            pstmt.executeUpdate();
            
        	// add success
        	return new ApiResponse(true, null, "Create success");
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

	//update
	public static ApiResponse update(JSONObject record) {
		try {        	
            
			PreparedStatement pstmt = conn.prepareStatement("UPDATE tblcity SET cName = ?, cHeight = ?,cWidth = ?, cCenterLat = ? ,cCenterLong = ? ,cMapZoom =?  WHERE cId = ?");
			System.out.println(record);
            int ID =  record.getInt("ID");

            String Name =  record.getString("name");
            Double Height = record.getDouble("height");
            Double Width = record.getDouble("width");
            Double CenterLat = record.getDouble("centerLat");
            Double CenterLong = record.getDouble("centerLong");
            int MapZoom = record.getInt("mapZoom");
            //long date_of_birth = Date.valueOf(date).getTime();
            pstmt.setString(1, Name);
            pstmt.setDouble(2, Height);
            pstmt.setDouble(3, Width);
            pstmt.setDouble(4, CenterLat);
            pstmt.setDouble(5, CenterLong);
            pstmt.setInt(6, MapZoom);
            pstmt.setInt(7, ID);
            
            pstmt.executeUpdate();
            
        	// add success
        	return new ApiResponse(true, null, "Update success");
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
	*/ 
	
	//main for test
//	public static void main(String[] args) throws JSONException {
//		CityProvider qCity = new CityProvider();
//		// get all
////		ApiResponse res = qCity.getAll();
////        System.out.print(res.toString());
//		
//        //Add new
////		String newStringItem ="{\"CenterLong\":48.523101806640625,\"MapZoom\":8,\"CenterLat\":101.02100372314453,\"Height\":3000.1,\"ID\":1,\"Width\":4000.1,\"Name\":\"paris\"}" ;
////		JSONObject newITem = new JSONObject(newStringItem);
////		ApiResponse res = qCity.create(newITem);
////		System.out.print(res.toString());
//     
//        //Update
//		String newStringItem ="{\"CenterLong\":48.523101806640625,\"MapZoom\":8,\"CenterLat\":101.02100372314453,\"Height\":3000.1,\"ID\":1,\"Width\":4000.1,\"Name\":\"moi update\"}" ;
//		JSONObject newITem = new JSONObject(newStringItem);
//		ApiResponse res = qCity.update(newITem);
//		System.out.print(res.toString());
//		res = qCity.getAll();
//		System.out.print(res.toString());
//        //get by id 
////       ApiResponse res = qCity.getByID(1);
////        System.out.print(res.toString());
//        
//	
//	}
	
	
	public static void main(String[] args) throws JSONException {
		CarbonDataProvider qCity = new CarbonDataProvider();
		 //get all
	ApiResponse res = qCity.getAll();
        System.out.print(res.toString()); }
		
	
}
