package puzzle_city_server_test;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONObject;

import puzzle_city_dto.VehiculeSensorProvider;

public class TestCountCity {
	public static void main(String[] args) {
		// test create
		System.out.println("testCountCity");

		try (FileReader reader = new FileReader("src/puzzle_city_analyse_test/city_getbyID.json"))
		{ 
			String fileContent= "";
			int i;    
			while((i=reader.read())!=-1)   {	 
				fileContent += (char)i;
			} 
			reader.close();    			 
			JSONObject list = new JSONObject(fileContent);
			System.out.println( VehiculeSensorProvider.create(list).toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
