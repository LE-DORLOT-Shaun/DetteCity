package bollards;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import bollards.Bollards;
import bollards.VehiculeManagement;

public class BollardsTests {
	Connection c;
	
	public BollardsTests(Connection c) {
		this.c = c;
	}
	
	public JSONArray getDataForTests(String fileName) throws UnsupportedEncodingException, ParseException {
		InputStream inputStream = FileReader.class.getClassLoader().getSystemResourceAsStream(fileName);
		StringBuffer sb = new StringBuffer();
			
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8")); 

		try {
			String temp; 
			while ((temp = bufferedReader.readLine()) != null) 
				sb.append(temp); 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace(); 
			}
		}
		System.out.println(sb);
		String myjsonstring = sb.toString(); 
		

		JSONParser parser = new JSONParser(); 
		JSONArray json;
		json = (JSONArray) parser.parse(myjsonstring);
		/* the test file is parsed and transformed to a json file in order to be analyzed 
		 * in the steps above
		 */
		
		return json;
	}
	
	
	public Object testChangingMax() throws SQLException, InterruptedException, UnsupportedEncodingException, ParseException {
		JSONArray testFile = getDataForTests("maxCars.json");
		VehiculeManagement cars = new VehiculeManagement(this.c); 
		JSONObject obj=new JSONObject();
		System.out.println("max voitures actuels : " + VehiculeManagement.maxCars);
		
		ArrayList<JSONObject> maxLimitsTest = new ArrayList<JSONObject>();
		
		for (int i = 0; i < testFile.size(); i++) {
			JSONObject max = new JSONObject();
			JSONObject jsonObject = (JSONObject) testFile.get(i);
			System.out.println("nouveau max a inserer pour tests : " + jsonObject.get("new_max"));
			int newMax = Integer.parseInt(String.valueOf(jsonObject.get("new_max")));
			max = cars.updateMaxCars(newMax);
			String insert = Integer.toString(newMax);
			max.put("max voitures: ", insert);
			maxLimitsTest.add(max);
			System.out.println("max voitures actuels en base apres insertion: " + cars.carsLimit());
			}
		System.out.println(maxLimitsTest);
		obj.put("testsResults", maxLimitsTest);
		System.out.println(obj);
		
		return obj;
		
	}
	
	public Object SelectOnVehiculeManagement() throws UnsupportedEncodingException, ParseException, SQLException, InterruptedException {
		JSONArray testFile = getDataForTests("filterCarsTest.json");
		VehiculeManagement cars = new VehiculeManagement(this.c); 
		JSONObject obj=new JSONObject();
		
		ArrayList<JSONObject> carsList = new ArrayList<JSONObject>();
		
		for (int i = 0; i < testFile.size(); i++) {
			Object carsFilter = new Object();
			JSONObject jsonObject = (JSONObject) testFile.get(i);
			System.out.println("filtres pour recherche test : " + jsonObject);
			
			String dateDebut = String.valueOf(jsonObject.get("date_debut"));
			String dateFin = String.valueOf(jsonObject.get("date_fin"));
			String type = String.valueOf(jsonObject.get("type"));
			String zone = String.valueOf(jsonObject.get("zone"));
//			carsFilter = cars.SearchCars(dateDebut, dateFin, zone, type);
			System.out.println("voici la liste des voitures retrouvé: ");
			System.out.println(carsFilter);
			carsList.add((JSONObject) carsFilter);
			}
		System.out.println(carsList);
		obj.put("testsResults", carsList);
		System.out.println(obj);
		
		return obj;
		
	}
	
	public Object InsertCarAndAdjustActualNb() throws SQLException, InterruptedException, UnsupportedEncodingException, ParseException {
		JSONArray testFile = getDataForTests("carsAndActualNbInsert.json");
		VehiculeManagement cars = new VehiculeManagement(this.c); 
		JSONObject obj=new JSONObject();
		
		ArrayList<JSONObject> InsertionTest = new ArrayList<JSONObject>();
		
		for (int i = 0; i < testFile.size(); i++) {
			Object insertCars = new Object();
			JSONObject jsonObject = (JSONObject) testFile.get(i);
			System.out.println("voitures a inserer : " + jsonObject);
			int id_sensor = Integer.parseInt(String.valueOf(jsonObject.get("id_sensor"))) ;  
			String type =  String.valueOf(jsonObject.get("type")); 
			String objet =  String.valueOf(jsonObject.get("objet")); 
			insertCars = cars.addCarToHistory(objet, type, id_sensor);
			((JSONObject) insertCars).put("NbVoitures", cars.getCars());
			InsertionTest.add((JSONObject) insertCars);
			}
		obj.put("testsResults", InsertionTest);
		
		return obj;
		
	}

	
}
