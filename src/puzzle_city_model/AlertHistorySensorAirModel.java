package puzzle_city_model;

import java.util.Date;

public class AlertHistorySensorAirModel {

	private AlertModel alertModel;
	private Date dateAlert;
	private int no2Simulation;
	private int pm10Simulation;
	private int o3Simulation;

	public AlertModel getAlertModel() {
		return alertModel;
	}

	public void setAlertModel(AlertModel alertModel) {
		this.alertModel = alertModel;
	}

	public int getNo2Simulation() {
		return no2Simulation;
	}

	public void setNo2Simulation(int no2Simulation) {
		this.no2Simulation = no2Simulation;
	}

	public int getPm10Simulation() {
		return pm10Simulation;
	}

	public void setPm10Simulation(int pm10Simulation) {
		this.pm10Simulation = pm10Simulation;
	}

	public int getO3Simulation() {
		return o3Simulation;
	}

	public void setO3Simulation(int o3Simulation) {
		this.o3Simulation = o3Simulation;
	}

	public AlertHistorySensorAirModel(Date dateAlert, int no2Simulation, int pm10Simulation,
			int o3Simulation) {
		super();
		this.dateAlert = dateAlert;
		this.no2Simulation = no2Simulation;
		this.pm10Simulation = pm10Simulation;
		this.o3Simulation = o3Simulation;
	}

	public Date getDateAlert() {
		return dateAlert;
	}

	public void setDateAlert(Date dateAlert) {
		this.dateAlert = dateAlert;
	}

}
