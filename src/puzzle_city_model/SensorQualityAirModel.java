package puzzle_city_model;

import puzzle_city_model.AlertModel;

public class SensorQualityAirModel {

	private int id;
	private String address;
	private int no2;
	private int pm10;
	private int o3;
	private boolean isActivated;

	private AlertModel alerteModel;

	public SensorQualityAirModel() {
		super();
	}

	public SensorQualityAirModel(int id, String address) {
		super();
		this.id = id;
		this.address = address;

	}

	public SensorQualityAirModel(int id, String address, int no2, int pm10, int o3, AlertModel alerteModel,
			boolean isActivated) {
		super();
		this.id = id;
		this.address = address;
		this.no2 = no2;
		this.pm10 = pm10;
		this.o3 = o3;
		this.alerteModel = alerteModel;
		this.isActivated = isActivated;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getNo2() {
		return no2;
	}

	public void setNo2(int no2) {
		this.no2 = no2;
	}

	public int getPm10() {
		return pm10;
	}

	public void setPm10(int pm10) {
		this.pm10 = pm10;
	}

	public int getO3() {
		return o3;
	}

	public void setO3(int o3) {
		this.o3 = o3;
	}

	public AlertModel getAlerteModel() {
		return alerteModel;
	}

	public void setAlerteModel(AlertModel alerteModel) {
		this.alerteModel = alerteModel;
	}

	public boolean isActivated() {
		return isActivated;
	}

	public void setActivated(boolean isActivated) {
		this.isActivated = isActivated;
	}

}
