package puzzle_city_server_test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import puzzle_city_dto.TramwayProvider;
import puzzle_city_model.ApiResponse;

public class TestCreateTramway {
	public static void main(String[] args) {
		// test tramwayprovider
		System.out.println("Start test Insert Information Tramway Network");

		try (FileReader reader = new FileReader("src/puzzle_city_server_test/tramway_create_data.json"))
		{ 
			String fileContent= "";
			int i;    
			while((i=reader.read())!=-1)   {	 
				fileContent += (char)i;
			} 
			reader.close();    			 
			JSONArray list = new JSONArray(fileContent);
			System.out.println("TEST CREATE TRAMWAY:");
			for (int j = 0; j < list.length(); j++) {
				//get json testcase
				JSONObject testItem = list.getJSONObject(j);
				System.out.println("TEST CASE "+j+":");
				// call func with param input
				TramwayProvider tramway = new TramwayProvider();
				ApiResponse resItem =  tramway.createAndUpdate(testItem.getJSONObject("input"));

				//compare resItem.body.success with testItem.output 
				if( resItem.getBody().getBoolean("success") == testItem.getBoolean("output")) {
					//test ok
					System.out.println("Desired output :" + testItem.getBoolean("output"));
					System.out.println("Received output :" + resItem.getBody().getBoolean("success"));
					System.out.println("Test success with input is: "+testItem.getJSONObject("input").toString() ); 
				}else {
					//test fail
					System.err.println("Test false with: "+testItem.getJSONObject("input").toString());

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
