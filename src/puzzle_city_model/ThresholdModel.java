package puzzle_city_model;

public class ThresholdModel {
	private int ID;
	private String Unit;
	private int Value;


	public ThresholdModel(int ID, String Unit, int Value) {
		super();
		this.ID = ID;
		this.Unit = Unit;
		this.Value = Value;
	
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

	public String getUnit() {
		return Unit;
	}

	public void setUnit(String Unit) {
		this.Unit = Unit;
	}
	
	public int getValue() {
		return Value;
	}

	public void setValue(int Value) {
		this.Value = Value;
	}
	
}
