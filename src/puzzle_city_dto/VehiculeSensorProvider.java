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

public class VehiculeSensorProvider {

	JDBCConnection dbconn;
	static Connection conn;
	static Statement st;

	public VehiculeSensorProvider() {
		// TODO Auto-generated constructor stub
		dbconn = new JDBCConnection();
		conn = dbconn.setConnection(); 
	}

	// get all
	public static ApiResponse getAll() {
		try {

			st = conn.createStatement();
			String sql = "select * from tblvehiculesensor";
			ResultSet rs = st.executeQuery(sql);

			JSONArray vehiculesensorAll = new JSONArray();

			while (rs.next()) {
				JSONObject resItem = new JSONObject();
				resItem.put("ID",rs.getInt("ID"));
				resItem.put("Address",rs.getString("Address"));
				resItem.put("State", rs.getBoolean("State"));
				vehiculesensorAll.put(resItem);
			}
			ApiResponse ret = new ApiResponse(true, vehiculesensorAll, "Success");
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

	public static AlertModel getAlertById(int id) {
		try {
			st = conn.createStatement();
			PreparedStatement recherchePersonne = conn.prepareStatement("SELECT * FROM tblalert WHERE id = ?");

			recherchePersonne.setInt(1, id);

			ResultSet resultats = recherchePersonne.executeQuery();

			boolean encore = resultats.next();

			if (encore) {
				int idA = resultats.getInt("id");
				System.out.println(resultats.getTimestamp("date"));
				Timestamp date = resultats.getTimestamp("date");
				boolean isAlert = resultats.getBoolean("isAlert");
				int statutInt = resultats.getInt("statut");
				puzzle_city_model.Status status = getStatusByIndex(statutInt);
				return new AlertModel(idA, date, isAlert, status);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	// get byID
	public static ApiResponse getByID(int ID) {
		try {

			st = conn.createStatement();
			String sql = "select * from tblvehiculesensor where ID = ? ";
			ResultSet rs = st.executeQuery(sql);

			JSONArray vehiculesensorAll = new JSONArray();
			if (rs.next() == false) {
				return new ApiResponse(false, vehiculesensorAll, "Not Found");
			} else {
				do {
					JSONObject resItem = new JSONObject();

					resItem.put("ID", rs.getLong("ID"));
					resItem.put("Address", rs.getString("Address"));
					resItem.put("State", rs.getBoolean("State"));
//	                     resItem.put("isOpen",  rs.getBoolean("isOpen") );

					vehiculesensorAll.put(resItem);
				} while (rs.next());
				return new ApiResponse(true, vehiculesensorAll, "Success");
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

	public static int createAlert() {
		int idAlert = 0;
		try {

			ResultSet rs = null;
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO tblalert(isAlert,statut,date) values (?,?,?)",
					Statement.RETURN_GENERATED_KEYS);

			pstmt.setBoolean(1, false);
			pstmt.setInt(2, 0);
			 Timestamp currentTime = new Timestamp(Date.from(Instant.now()).getTime());
			 pstmt.setTimestamp(3, currentTime);

			int rowAffected = pstmt.executeUpdate();
			if (rowAffected == 1) {
				// get candidate id
				rs = pstmt.getGeneratedKeys();
				if (rs.next())
					idAlert = rs.getInt(1);

			}
			// add success
			return idAlert;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return idAlert;

		}

	}

	// create
	public static ApiResponse create(JSONObject record) {
		try {
			PreparedStatement pstmt = conn
					.prepareStatement("INSERT INTO tblvehiculesensor(ID, Address, State) values (null ,?, 0)");
			String Address = record.getString("Address");
			//int idAlert = createAlert();
			pstmt.setString(1, Address);
			//pstmt.setInt(5, idAlert);
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

	public static void updateAlertById(int id, boolean alert) {
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement("UPDATE tblalert SET isAlert = ?,date=?  WHERE id = ?");
			pstmt.setBoolean(1, alert);
			 Timestamp currentTime = new Timestamp(Date.from(Instant.now()).getTime());
			pstmt.setTimestamp(2, currentTime);
			pstmt.setInt(3, id);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// update
	public static ApiResponse update(JSONObject record) {
		try {

			PreparedStatement pstmt = conn
					.prepareStatement("UPDATE tblvehiculesensor SET Address = ?, State = ?  WHERE ID = ?");
			System.out.println(record);
			int ID = record.getInt("ID");
			String Address = record.getString("Address");
			boolean State = record.getBoolean("State");
			//boolean alert = record.getBoolean("alert");
			//int alert_id = record.getInt("alert_id");
			//updateAlertById(alert_id, alert);
//                 Boolean isOpen = record.getBoolean("isOpen");	          
			// long date_of_birth = Date.valueOf(date).getTime();
			pstmt.setString(1, Address);
			pstmt.setInt(3, ID);
			pstmt.setBoolean(2, State);
			//pstmt.setInt(5, id);
//	             pstmt.setBoolean(2, isOpen);

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
	public static void deleteAlertById(int id) {
		try {

			PreparedStatement pt = conn.prepareStatement("delete from tblalert where id = ?");
			pt.setInt(1, id);
			pt.execute();
		} catch (SQLException ex) {
			System.out.println("error " + ex.getMessage());
		}

	}

	public static void deleteVehiculeSensorById(int ID,int alert_id) {
		try {

			PreparedStatement pt = conn.prepareStatement("delete from tblvehiculesensor where ID like ?");
			pt.setInt(1, ID);
			pt.execute();
			deleteAlertById(alert_id);
		} catch (SQLException ex) {
			System.out.println("error " + ex.getMessage());
		}

	}

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