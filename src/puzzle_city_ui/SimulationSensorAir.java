package puzzle_city_ui;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import puzzle_city_client.Client;
import puzzle_city_client_model.ApiEnum;
import puzzle_city_client_model.SendPackage;

public class SimulationSensorAir {

	public Client client;

	public SimulationSensorAir(Client client) {
		super();
		this.client = client;
	}

	public void launchSimulation() {
		// TODO Auto-generated method stub
		client.setResponseData(null);
		SendPackage sendP = new SendPackage();
		sendP.setApi(ApiEnum.SENSORAIR_FIND_ACTIVE);
		client.setSendP(sendP);
		JSONObject res = null;
		while (res == null) {

			res = client.getResponseData();
			System.out.println("waiting:" + res);
			if (res != null) {
				System.out.println("res________________________"+res);
				
			
				try {
					simulateAll(res.getJSONArray("data"));
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		//

		client.setResponseData(null);

	}

	private void simulateAll(JSONArray jArray) {
		for (int i = 0; i < jArray.length(); i++) {
			JSONObject jb;
			try {
				jb = jArray.getJSONObject(i);
				JSONObject alerteModel = jb.getJSONObject("alerteModel");

				simulate(jb.getInt("no2"), jb.getInt("pm10"), jb.getInt("o3"), (int) alerteModel.get("id"));

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private void simulate(int no2, int pm10, int o3, int alertId) {
		final int no2Simulated = (int) (Math.random() * 500);
		final int pm10Simulated = (int) (Math.random() * 100);
		final int o3Simulated = (int) (Math.random() * 300);
		boolean alert;
		alert = no2Simulated >= no2 || pm10Simulated >= pm10 || o3Simulated >= o3;
		if (alert) {
			try {
				client.setResponseData(null);
				JSONObject bodyItem = new JSONObject();
				bodyItem.put("id", "" + alertId);
				bodyItem.put("no2", "" + no2Simulated);
				bodyItem.put("pm10", "" + pm10Simulated);
				bodyItem.put("o3", "" + o3Simulated);
				bodyItem.put("alert", alert);

				SendPackage sendPa = new SendPackage();
				sendPa.setApi(ApiEnum.ALERT_UPDATE);
				sendPa.setBody(bodyItem);
				client.setSendP(sendPa);
//				JSONObject res = null;
//
//				while (res == null) {
//					// res = client.getResponseData();
//					res = client.getResponseData();
//					System.out.println("wait res:" + res);
//					if (res != null) {
//						// if success
//
//						System.out.println("resp " + res);
//					}
//				}

			} catch (JSONException e) {

				e.printStackTrace();
			}

		}
	}
}
