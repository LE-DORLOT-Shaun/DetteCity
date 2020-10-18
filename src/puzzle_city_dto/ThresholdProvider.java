package puzzle_city_dto;
import java.io.*;
import java.awt.print.Printable;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.text.html.HTMLEditorKit.Parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import puzzle_city_model.AlertModel;
import puzzle_city_model.ApiResponse;
import puzzle_city_model.VehiculeSensorModel;

public class ThresholdProvider {

	JDBCConnection dbconn;
	static Connection conn;
	static Statement st;

	public ThresholdProvider() {
		// TODO Auto-generated constructor stub
		dbconn = new JDBCConnection();
		conn = dbconn.setConnection(); 
	}

	// get all
	public static ApiResponse getAll() {
		try {

			st = conn.createStatement();
			String sql = "select * from tblthreshold";
			ResultSet rs = st.executeQuery(sql);

			JSONArray thresholdAll = new JSONArray();

			while (rs.next()) {
				JSONObject resItem = new JSONObject();
				resItem.put("ID",rs.getInt("ID"));
				resItem.put("Unit",rs.getString("Unit"));
				resItem.put("Value", rs.getInt("Value"));
				thresholdAll.put(resItem);
			}
			ApiResponse ret = new ApiResponse(true, thresholdAll, "Success");
			System.out.println("Return:" + ret.toString());
			return ret;
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
	public static ApiResponse getByID(int ID) {
		try {

			st = conn.createStatement();
			String sql = "select * from tblthreshold where ID = ? ";
			ResultSet rs = st.executeQuery(sql);

			JSONArray thresholdAll = new JSONArray();
			if (rs.next() == false) {
				return new ApiResponse(false, thresholdAll, "Not Found");
			} else {
				do {
					JSONObject resItem = new JSONObject();

					resItem.put("ID", rs.getLong("ID"));
					resItem.put("Unit", rs.getString("Unit"));
					resItem.put("Value", rs.getBoolean("Value"));
//	                     resItem.put("isOpen",  rs.getBoolean("isOpen") );

					thresholdAll.put(resItem);
				} while (rs.next());
				return new ApiResponse(true, thresholdAll, "Success");
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

	// create
	public static ApiResponse create(JSONObject record) {
		try {
			PreparedStatement pstmt = conn
					.prepareStatement("INSERT INTO tblthreshold(ID, Unit, Value) values (null ,Nb_vehicule_max, ?)");
			int Value = record.getInt("Value");
			//int idAlert = createAlert();
			pstmt.setInt(2, Value);
			//pstmt.setBoolean(2, true);

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

	// update
	public static ApiResponse update(JSONObject record) {
		try {

			PreparedStatement pstmt = conn
					.prepareStatement("UPDATE tblthreshold SET Value = ?  WHERE Unit = Nb_vehicule_max");
			System.out.println(record);
			//int ID = record.getInt("ID");
			int Value = record.getInt("Value");
			//boolean alert = record.getBoolean("alert");
			//int alert_id = record.getInt("alert_id");
			//updateAlertById(alert_id, alert);
//                 Boolean isOpen = record.getBoolean("isOpen");	          
			// long date_of_birth = Date.valueOf(date).getTime();
			pstmt.setInt(1, Value);
			//pstmt.setInt(5, id);
//	        pstmt.setBoolean(2, isOpen);

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

/*	public static void deleteTById(int ID,int alert_id) {
		try {

			PreparedStatement pt = conn.prepareStatement("delete from tblvehiculesensor where ID like ?");
			pt.setInt(1, ID);
			pt.execute();
			deleteAlertById(alert_id);
		} catch (SQLException ex) {
			System.out.println("error " + ex.getMessage());
		}

	}
*/
	public static puzzle_city_model.Status getStatusByIndex(int status) {

		switch (status) {
		case 0:

			return puzzle_city_model.Status.ALERT_TRAITED;
		case 1:

			return puzzle_city_model.Status.ALERT_NOTTRAITED;

		case 2:

			return puzzle_city_model.Status.ALERT_INPROGRESS;

		default:
			return null;
		}
	}
}