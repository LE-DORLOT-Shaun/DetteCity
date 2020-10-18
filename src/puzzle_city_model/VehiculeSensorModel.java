package puzzle_city_model;

public class VehiculeSensorModel {

	private int ID;
	private String Address;
	private boolean State;
 
	private AlertModel alerteModel;



	public VehiculeSensorModel(int ID, String Address, boolean State) {
		super();
		this.ID = ID;
		this.Address = Address;
		this.State = State;
	
	}
	

/*	public VehiculeSensorModel(int ID, String Address, AlertModel alerteModel) {
		super();
		this.ID = ID;
		this.Address = Address;
		this.alerteModel = alerteModel;
	}
*/
	public int getId() {
		return ID;
	}

	public void setId(int id) {
		this.ID = ID;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String Address) {
		this.Address = Address;
	}

	public AlertModel getAlerteModel() {
		return alerteModel;
	}

	public void setAlerteModel(AlertModel alerteModel) {
		this.alerteModel = alerteModel;
	}
	
	

}