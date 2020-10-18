package puzzle_city_model;

public class ActualData {

	
	int ID;
	String Date;
	double AvgDistance ;
	int IDTravelMode ;
	String LabelTravelMode; 
	double CO2Emission;
	int Nb ;
	
	
	public ActualData(int iD, String date, double avgDistance, int iDTravelMode, String labelTravelMode,
			double cO2Emission, int nb) {
		super();
		ID = iD;
		Date = date;
		AvgDistance = avgDistance;
		IDTravelMode = iDTravelMode;
		LabelTravelMode = labelTravelMode;
		CO2Emission = cO2Emission;
		Nb = nb;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getLabelTravelMode() {
		return LabelTravelMode;
	}

	public void setLabelTravelMode(String labelTravelMode) {
		LabelTravelMode = labelTravelMode;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public double getAvgDistance() {
		return AvgDistance;
	}

	public void setAvgDistance(double avgDistance) {
		AvgDistance = avgDistance;
	}

	public int getIDTravelMode() {
		return IDTravelMode;
	}

	public void setIDTravelMode(int iDTravelMode) {
		IDTravelMode = iDTravelMode;
	}

	public double getCO2Emission() {
		return CO2Emission;
	}

	public void setCO2Emission(double cO2Emission) {
		CO2Emission = cO2Emission;
	}

	public int getNb() {
		return Nb;
	}

	public void setNb(int nb) {
		Nb = nb;
	}

	@Override
	public String toString() {
		return "ActualData [ID=" + ID + ", Date=" + Date + ", AvgDistance=" + AvgDistance + ", IDTravelMode="
				+ IDTravelMode + ", LabelTravelMode=" + LabelTravelMode + ", CO2Emission=" + CO2Emission + ", Nb=" + Nb
				+ "]";
	}
	
	
	
	
}
