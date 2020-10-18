package puzzle_city_server_test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import puzzle_city_dto.VehiculeSensorProvider;

public class Test_VehiculeSensor {
	public static void main(String[] args) {
		// test create
		System.out.println("Start test 1");

		try (FileReader reader = new FileReader("src/puzzle_city_server_test/Address.json"))
		{ 
			String fileContent= "";
			int i;    
			while((i=reader.read())!=-1)   {	 
				fileContent += (char)i;
			} 
			reader.close();    			 
			JSONObject list = new JSONObject(fileContent);


			VehiculeSensorProvider bollards = new VehiculeSensorProvider();
			System.out.println( bollards.create(list).toString());

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