package indicator;

import java.sql.Timestamp;

import org.json.simple.JSONObject;

public class CarIndicator {
	 
	private Timestamp date; 
	private int carsNb;
	private int carNbGlobal; 
	private int carNbAvg; 
	
	public CarIndicator() {
		
	}
	

	public CarIndicator (int nbV, Timestamp d, int nbG, int avg) {
		carsNb = nbV; 
		date = d; 
		carNbGlobal = nbG ;
		carNbAvg = avg; 
		
		
	}

	public String toString() {
		return "CarIndicator [date=" + date + ", carsNb=" + carsNb + ", carNbGlobal=" + carNbGlobal + ", carNbAvg="
				+ carNbAvg + "]";
	}


	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public int getCarsNb() {
		return carsNb;
	}

	public void setCarsNb(int carsNb) {
		this.carsNb = carsNb;
	}

	
	public int getCarNbGlobal() {
		return carNbGlobal;
	}

	public void setCarNbGlobal(int carNbGlobal) {
		this.carNbGlobal = carNbGlobal;
	}

	public int getCarNbAvg() {
		return carNbAvg;
	}

	public void setCarNbAvg(int carNbAvg) {
		this.carNbAvg = carNbAvg;
	}
	
	public JSONObject convertToJSON() {
		JSONObject json = new JSONObject();
		json.put("nb_voitures", carsNb); 
		if(date != null) {
			json.put("date", date.toString());
		}
		json.put("nombre_voitures_total_date",carNbGlobal); 
		json.put("nombre_voitures_avg",carNbAvg); 
		
		return json;
	}
	
	public void convertFromJson(JSONObject json) {
		this.carsNb = Long.valueOf(String.valueOf(json.get("nb_voitures"))).intValue(); 
		this.date = Timestamp.valueOf(String.valueOf(json.get("date"))); 
		this.carNbGlobal = Long.valueOf(String.valueOf(json.get("nombre_voitures_total_date"))).intValue(); 
		this.carNbAvg = Long.valueOf(String.valueOf(json.get("nombre_voitures_avg"))).intValue(); 
	}

	
}
