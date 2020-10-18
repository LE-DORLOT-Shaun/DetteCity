package puzzle_city_model;

import java.util.Date;

public class AlertModel {
	private int id;
	private Date date;
	private boolean isAlert;
	private int nbVehicules;
	private int co2;
private Status status;

	public AlertModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AlertModel(int id, Date date, boolean isAlert,Status status) {
		super();
		this.id = id;
		this.date = date;
		this.isAlert=isAlert;
this.status=status;
	}

	public AlertModel(int id, Date date, boolean isAlert) {
		super();
		this.id = id;
		this.date = date;
		this.isAlert=isAlert;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean isAlert() {
		return isAlert;
	}

	public void setAlerte(boolean isAlert) {
		this.isAlert = isAlert;
	}

	public int getNbVehicules() {
		return nbVehicules;
	}

	public void setNbVehicules(int nbVehicules) {
		this.nbVehicules = nbVehicules;
	}

	public int getCo2() {
		return co2;
	}

	public void setCo2(int co2) {
		this.co2 = co2;
	}

}
