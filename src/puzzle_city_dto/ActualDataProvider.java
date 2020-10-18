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

public class ActualDataProvider {

	JDBCConnection dbconn;
	static Connection conn;
	static Statement st;
	
	public ActualDataProvider() {
		// TODO Auto-generated constructor stub
		dbconn = new JDBCConnection();
		conn = dbconn.setConnection();
	}
	
	//get all 
	public static ApiResponse getAll() {
		try {
			
        	st =  conn.createStatement();
        	String sql = "select * from actualdata";
        	ResultSet rs = st.executeQuery(sql);
        	
        	 ArrayList<ActualData> Data_City = new ArrayList<ActualData>();

            while(rs.next()){
            	//JSONObject resItem = new JSONObject();           	
                
                int ID = rs.getInt("ID");
                String Date = rs.getString("Date");
                double AvgDistance = rs.getDouble("AvgDistance");
                int IDTravelMode = rs.getInt("IDTravelMode");
                String LabelTravelMode = rs.getString("LabelTravelMode");
                double CO2Emission = rs.getDouble("CO2Emission");
                int Nb = rs.getInt("Nb"); 
                
         
                Data_City.add(new ActualData(ID,Date,AvgDistance,IDTravelMode,LabelTravelMode,CO2Emission,Nb));
                
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

	
	
	
	
	
	
//Insert
	public static ApiResponse create(JSONObject record) {
		try {
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO actualdata (Date,AvgDistance,IDTravelMode,Nb,LabelTravelMode) values (?, ?, ?, ?, ?)");
	         					
            String Date =  record.getString("Date");
            Double AvgDistance = record.getDouble("AvgDistance");
            String LabelTravelMode = record.getString("LabelTravelMode");
            int Nb = record.getInt("Nb");
            int idTravel = record.getInt("IDTravelMode");
            pstmt.setString(1, Date);
            pstmt.setDouble(2, AvgDistance);
            pstmt.setInt(3, idTravel);
            pstmt.setInt(4, Nb);
            pstmt.setString(5, LabelTravelMode);    
            pstmt.executeUpdate();
            
        	// add success
        	return new ApiResponse(true, null, "Insert success");
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

	
	
	
	
	
	
	public static void main(String[] args) throws JSONException {
		ActualDataProvider qCity = new ActualDataProvider();
		 //get all
	ApiResponse res = qCity.getAll();
        System.out.print(res.toString()); }
		
	
}
