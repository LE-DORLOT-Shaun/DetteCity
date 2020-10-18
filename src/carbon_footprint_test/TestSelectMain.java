package carbon_footprint_test;

import puzzle_city_dto.ActualDataProvider;

public class TestSelectMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ActualDataProvider actualDataProvider = new ActualDataProvider();
		System.out.println(actualDataProvider.getAll().toString());
	}

}
