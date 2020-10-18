package puzzle_city_dto;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import puzzle_city_model.AlertHistorySensorAirModel;
import puzzle_city_model.AlertModel;
import puzzle_city_model.ApiResponse;
import puzzle_city_model.SensorQualityAirModel;

public class SensorQualityAirProvider {

	static JDBCConnection dbconn;
	static Connection conn;
	static Statement st;

	public SensorQualityAirProvider() {
		// TODO Auto-generated constructor stub
		dbconn = new JDBCConnection();
		conn = dbconn.setConnection();
	}

	// get all
	public static ApiResponse getAll() {
		try {

			st = conn.createStatement();
			String sql = "select * from tblsensorair";
			ResultSet rs = st.executeQuery(sql);

			ArrayList<SensorQualityAirModel> airAll = new ArrayList<SensorQualityAirModel>();

			while (rs.next()) {
				int id = rs.getInt("id");
				String address = rs.getString("address");
				int no2 = rs.getInt("no2");
				int pm10 = rs.getInt("pm10");
				int o3 = rs.getInt("o3");
				int alert_id = rs.getInt("alert_id");
				int isActivated = rs.getInt("isActivated");
				AlertModel alertModel = alert_id > 0 ? getAlertById(alert_id) : new AlertModel();

				airAll.add(new SensorQualityAirModel(id, address, no2, pm10, o3, alertModel,
						isActivated == 0 ? false : true));

			}
			ApiResponse ret = new ApiResponse(true, airAll, "Success");
			System.out.println("Tra du lieu:" + ret.toString());
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

	// get all
	public static ApiResponse getActiveSensors() {
		try {

			st = conn.createStatement();
			String sql = "select * from tblsensorair where isActivated=1";
			ResultSet rs = st.executeQuery(sql);

			ArrayList<SensorQualityAirModel> airAll = new ArrayList<SensorQualityAirModel>();

			while (rs.next()) {
				int id = rs.getInt("id");
				String address = rs.getString("address");
				int no2 = rs.getInt("no2");
				int pm10 = rs.getInt("pm10");
				int o3 = rs.getInt("o3");
				int alert_id = rs.getInt("alert_id");
				int isActivated = rs.getInt("isActivated");
				AlertModel alertModel = alert_id > 0 ? getAlertById(alert_id) : new AlertModel();

				airAll.add(new SensorQualityAirModel(id, address, no2, pm10, o3, alertModel,
						isActivated == 0 ? false : true));

			}
			ApiResponse ret = new ApiResponse(true, airAll, "Success");
			System.out.println("Tra du lieu:" + ret.toString());
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

	public static AlertModel getAlertById(int id) throws SQLException {
		System.out.println("getAlertById");
		st = conn.createStatement();
		PreparedStatement recherchePersonne = conn.prepareStatement("SELECT * FROM tblalert WHERE id = ?");

		recherchePersonne.setInt(1, id);

		ResultSet resultats = recherchePersonne.executeQuery();

		boolean en = resultats.next();

		if (en) {
			int idA = resultats.getInt("id");
			System.out.println(resultats.getTimestamp("date"));
			Timestamp date = resultats.getTimestamp("date");
			boolean isAlert = resultats.getBoolean("isAlert");
			System.out.println("getAlertById1");
			return new AlertModel(idA, date, isAlert);
		}

		return null;

	}

	public static ApiResponse getAllAlertHistory(int alert_id) {
		try {
			st = conn.createStatement();
			PreparedStatement recherchePersonne = conn
					.prepareStatement("SELECT * FROM tblalerthistory WHERE alert_id = ?");

			recherchePersonne.setInt(1, alert_id);

			ResultSet resultats = recherchePersonne.executeQuery();

			ArrayList<AlertHistorySensorAirModel> histoicalAlerts = new ArrayList<AlertHistorySensorAirModel>();

			while (resultats.next()) {

				Timestamp date = resultats.getTimestamp("date");
				int no2Simulation = resultats.getInt("no2");
				int pm10Simulation = resultats.getInt("pm10");
				int o3Simulation = resultats.getInt("o3");

				histoicalAlerts.add(new AlertHistorySensorAirModel(date, no2Simulation, pm10Simulation, o3Simulation));
			}
			ApiResponse ret = new ApiResponse(true, histoicalAlerts, "Success");
			System.out.println("Tra du lieu:" + ret.toString());
			return ret;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	// get byID
	public static ApiResponse getByID(int id) {
		try {

			st = conn.createStatement();
			String sql = "select * from tblsensorair where id = ? ";
			ResultSet rs = st.executeQuery(sql);

			JSONArray sensorAir = new JSONArray();
			if (rs.next() == false) {
				return new ApiResponse(false, sensorAir, "Not Found");
			} else {
				do {
					JSONObject resItem = new JSONObject();

					resItem.put("id", rs.getLong("id"));
					resItem.put("address", rs.getString("address"));
//	                     resItem.put("isOpen",  rs.getBoolean("isOpen") );

					sensorAir.put(resItem);
				} while (rs.next());
				return new ApiResponse(true, sensorAir, "Success");
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
			dbconn = new JDBCConnection();
			conn = dbconn.setConnection();
			PreparedStatement pstmt = conn.prepareStatement(
					"INSERT INTO tblsensorair(address,no2,pm10,o3,alert_id,isActivated) values (?,?,?,?,?,?)");
			
		
			String address = record.getString("address");
			
			Integer no2 = record.has("no2")?record.getInt("no2"):0;
			Integer pm10 = record.has("pm10")?record.getInt("pm10"):0;
			Integer o3 =record.has("o3") ?record.getInt("o3"):0;
			Boolean isActivated = record.has("isActivated")?record.getBoolean("isActivated"):false;
			// Boolean isOpen = record.getBoolean("isOpen");
			// long date_of_birth = Date.valueOf(date).getTime();
			int idAlert = createAlert();
			pstmt.setString(1, address);
			pstmt.setInt(2, no2 );
			pstmt.setInt(3, pm10);
			pstmt.setInt(4, o3);
			pstmt.setInt(5, idAlert);
			pstmt.setInt(6,  isActivated ? 1 : 0);
			// pstmt.setBoolean(2, true);

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
//			if (alert) {
//				AlertModel existedAlert = getAlertById(id);
//				createHistoricalAlert(existedAlert);
//			}
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

	public static ApiResponse updateAlerte(JSONObject alertJSON) throws JSONException, SQLException {

		int id = alertJSON.getInt("id");
		int no2 = alertJSON.getInt("no2");
		int pm10 = alertJSON.getInt("pm10");
		int o3 = alertJSON.getInt("o3");
		boolean alert = alertJSON.getBoolean("alert");
		System.out.println("isAlert" + alert);
		if (alert) {
			AlertModel existedAlert = getAlertById(id);
			System.out.println("jalit");
			createHistoricalAlert(existedAlert, no2, pm10, o3);
		}

		System.out.println("alertJSON" + alertJSON.toString());
		PreparedStatement pstmt;

		pstmt = conn.prepareStatement("UPDATE tblalert SET isAlert = ?,date=?  WHERE id = ?");
		pstmt.setBoolean(1, alert);
		Timestamp currentTime = new Timestamp(Date.from(Instant.now()).getTime());
		pstmt.setTimestamp(2, currentTime);
		pstmt.setInt(3, id);

		pstmt.executeUpdate();
		return new ApiResponse(true, null, "Update success");

	}

	public static void createHistoricalAlert(AlertModel alertModel, int no2, int pm10, int o3) throws SQLException {

		System.out.println("createHistoricalAlert");
		PreparedStatement pstmt = conn
				.prepareStatement("INSERT INTO tblalerthistory(date,alert_id,no2,pm10,o3) values (?,?,?,?,?)");
		Timestamp currentTime = new Timestamp(alertModel.getDate().getTime());
		pstmt.setTimestamp(1, currentTime);

		pstmt.setInt(2, alertModel.getId());
		pstmt.setInt(3, no2);
		pstmt.setInt(4, pm10);
		pstmt.setInt(5, o3);

		pstmt.executeUpdate();
		// add success

	}

	// update
	public static ApiResponse update(JSONObject record) {
		try {
			dbconn = new JDBCConnection();
			conn = dbconn.setConnection();
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE tblsensorair SET address = ?,no2=?,pm10=?,o3=?,isActivated=?  WHERE id = ?");

			int id = record.getInt("id");

			String address = record.getString("address");
			int no2 = record.getInt("no2");
			int pm10 = record.getInt("pm10");
			int o3 = record.getInt("o3");
			boolean alert = record.getBoolean("alert");
			int alert_id = record.getInt("alert_id");
			boolean isActivated = record.getBoolean("isActivated");
			updateAlertById(alert_id, alert);

			pstmt.setString(1, address);
			pstmt.setInt(2, no2);
			pstmt.setInt(3, pm10);
			pstmt.setInt(4, o3);
			pstmt.setBoolean(5, isActivated);
			pstmt.setInt(6, id);
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

	public static ApiResponse deleteSensorQualityAirById(int id, int alert_id) {
		try {

			PreparedStatement pt = conn.prepareStatement("delete from tblsensorair where id like ?");
			pt.setInt(1, id);
			pt.execute();
			deleteAlertById(alert_id);
			return new ApiResponse(true, null, "Delete success");
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
