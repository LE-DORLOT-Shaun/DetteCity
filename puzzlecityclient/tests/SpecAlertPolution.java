package tests;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import commons.TestJson;

public class SpecAlertPolution {
	private static ArrayList<JSONObject> allBornes;
	private static Object[][] data;
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException, JSONException, SQLException {

		System.out.println("********************************");
		System.out.println("		INITIALIZATION");
		System.out.println("Threshold > Number of vehicule");
		System.out.println("Polution alert is on false");
		System.out.println("Bollards are down");
		System.out.println("********************************");
	
		org.json.simple.JSONObject bornesInit = TestJson.getBornes();
		
		int maxCars = Integer.valueOf((String) bornesInit.get("threshold"));
		int checkActualCars = Integer.valueOf(bornesInit.get("totalVehicule").toString());
		boolean isalerted = Boolean.valueOf(bornesInit.get("alertP").toString());
//		String test = String.valueOf(bornesInit.get("bollards").toString());
//		System.out.println(test);
		if(checkActualCars >= maxCars) {
			int newthreshold = checkActualCars + 5;
			TestJson.changeMax(newthreshold);
		}
		
		if(isalerted == true) {
			TestJson.delAlertP();
			System.out.println("Maintenant l'état des bornes devrait être à false");
		}
		
/*		allBornes = (ArrayList<JSONObject>) bornesInit.get("bollards");
		data = new String[allBornes.size()][3];
		for (int i = 0; i < allBornes.size(); i++) {
			
			String idbornes = allBornes.get(i).get("id").toString();
			data[i][0] = idbornes;
			String address = allBornes.get(i).get("address").toString();
			data[i][1] = address;
			
			if (allBornes.get(i).get("state").equals("f")) {
				data[i][2] = "baissé";
			} else {
				data[i][2] = "relevé";
			}
		}
		System.out.println(data);
	*/	
		System.out.println("********************************");
		System.out.println("		END INITIALIZATION");
		System.out.println("********************************");
		
		System.out.println("		TEST IN PROGRESS");
		
		TestJson.setAlertP();
		
		
		
		
		System.out.println("********************************");
		System.out.println("			RESULTS");
		System.out.println("********************************");
		
		org.json.simple.JSONObject bornesFinal = TestJson.getBornes();
		
/*		allBornes = (ArrayList<JSONObject>) bornesFinal.get("bollards");
		data = new Object[allBornes.size()][3];
		for (int i = 0; i < allBornes.size(); i++) {
			
			data[i][0] = allBornes.get(i).get("id");
			data[i][1] = allBornes.get(i).get("address");
			
			if (allBornes.get(i).get("state").equals("f")) {
				data[i][2] = "baissé";
			} else {
				data[i][2] = "relevé";
			}
		}
		System.out.println(data);
	*/	
		System.out.println("Nous avons bien l'état des bornes à true");
	}
	
}
