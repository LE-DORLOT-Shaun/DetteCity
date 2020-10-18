package carbon_footprint_test;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import puzzle_city_dto.ActualDataProvider;

public class TestMain {

	public static void main(String[] args) {
				System.out.println("Start test Insert");
				
				readEC readEC = new readEC();
				try {
					String content = readEC.readEC();
					JSONObject list = new JSONObject(content);
					ActualDataProvider actualDataProvider = new ActualDataProvider();
					System.out.println(actualDataProvider.create(list).toString());
				} catch (IOException | JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("End test Insert");
	}

}
