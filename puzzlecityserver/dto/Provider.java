package dto;

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

import model.ApiResponse;

public class Provider {
	JDBCConnection dbconn;
	static Connection conn;
	static Statement st;

	public Provider() {
		// TODO Auto-generated constructor stub
		System.out.println("je suis dans le Provider");
		dbconn = new JDBCConnection();
		conn = dbconn.setConnection();
		
	}
	
	// get all
		public static ApiResponse getAll() {
			try {
				System.out.println("je suis dans le getAll");
				st = conn.createStatement();
				String sql = "select * from threshold";
				ResultSet rs = st.executeQuery(sql);

				JSONArray thresholdAll = new JSONArray();

				while (rs.next()) {
					JSONObject resItem = new JSONObject();
					resItem.put("vehicule_threshold",rs.getInt("vehicule_threshold"));
					resItem.put("last_update", rs.getDate("last_update"));
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
		
		
		//update
		public static ApiResponse updateThreshold(int a) {
			try {
				System.out.println("je suis dans le update");
				st = conn.createStatement();
				String sql = "update threshold set vehicule_threshold = "+ a +", last_update = current_timestamp;";
				ResultSet rs = st.executeQuery(sql);

				JSONArray thresholdAll = new JSONArray();
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
}