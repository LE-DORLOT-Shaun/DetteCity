package puzzle_city_test_sensorAir;

import java.io.FileReader;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import puzzle_city_client.Client;
import puzzle_city_client_model.ApiEnum;
import puzzle_city_client_model.SendPackage;

public class TestAddSensorAir {
	public static void main(String[] args) {
		JSONParser parser = new JSONParser();
		 Client  client =new Client("localhost", 1234);
		 client.start();
		Object obj;
		try {
			obj = parser.parse(new FileReader("src\\puzzle_city_test_sensorAir\\TestAddSensorAir.json"));

			org.json.simple.JSONObject jsonObject =(org.json.simple.JSONObject ) obj;
			int id =Integer.valueOf(jsonObject.get("id").toString());
			int no2 =Integer.valueOf(jsonObject.get("no2").toString());
			int o3 =Integer.valueOf(jsonObject.get("o3").toString());
			int pm10 =Integer.valueOf(jsonObject.get("pm10").toString());
			String address =jsonObject.get("address").toString();
			boolean isActivated =Boolean.valueOf(jsonObject.get("isActivated").toString());

			JSONObject bodyItem = new JSONObject();
			bodyItem.put("id", id);
			bodyItem.put("address", "" + address);
			bodyItem.put("no2", "" + no2);
			bodyItem.put("o3", "" + o3);
			bodyItem.put("pm10", "" + pm10);
			bodyItem.put("isActivated", "" + isActivated);
			SendPackage sendPa = new SendPackage();
			sendPa.setApi(ApiEnum.SENSORAIR_CREATE);
			sendPa.setBody(bodyItem);
			client.setSendP(sendPa);
			client.setResponseData(null);
			JSONObject res = null;
			while (res == null) {
				res = client.getResponseData();
				System.out.println("cho tra ve:" + res.length());
				if(res!=null) {
				
				System.out.println("cho tra ve:" + res);
				}
				
			}
		

		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// A JSON object. Key value pairs are unordered. JSONObject supports
		// java.util.Map interface.

	}
}
