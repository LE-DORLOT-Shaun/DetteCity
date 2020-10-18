package puzzle_city_server_test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import puzzle_city_dto.CityProvider;
import puzzle_city_model.ApiResponse;

public class TestGetOneCity {
	public static void main(String[] args) {
		// test getAllCity()
		System.out.println("Start Test Get One City");
		
		try (FileReader reader = new FileReader("src/puzzle_city_server_test/city_getbyID.json"))
		{ 
			String fileContent= "";
			int i;    
			while((i=reader.read())!=-1)   {	 
				fileContent += (char)i;
			} 
			reader.close();
			JSONArray list = new JSONArray(fileContent);
			System.out.println("TEST Get One CITY:");
			for (int j = 0; j < list.length(); j++) {
				//get json testcase
				JSONObject testItem = list.getJSONObject(j);
				System.out.println("TEST CASE "+j+":");
				// call func with param input
				CityProvider city = new CityProvider();
				ApiResponse resItem =  city.getByID(testItem.getInt("input"));

				//compare resItem.body.success with testItem.output 
				if( resItem.getBody().getBoolean("success") == testItem.getBoolean("output")) {
					//test ok
					System.out.println("Desired output :" + testItem.getBoolean("output"));
					System.out.println("Received output :" + resItem.getBody().getBoolean("success"));
					System.out.println("Test success with input is: "+testItem.getInt("input")); 
				}else {
					//test fail
					System.err.println("Test false with: "+testItem.getInt("input"));

				}
			};


		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}


