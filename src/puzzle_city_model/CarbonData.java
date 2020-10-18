package puzzle_city_model;

public class CarbonData {

	int ID;
	String TravelMode;
	double EmissionFactor;
	int NbMax;

	public CarbonData(int iD, String travelMode, double emissionFactor, int nbMax) {
		super();
		ID = iD;
		TravelMode = travelMode;
		EmissionFactor = emissionFactor;
		NbMax = nbMax;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getTravelMode() {
		return TravelMode;
	}

	public void setTravelMode(String travelMode) {
		TravelMode = travelMode;
	}

	public double getEmissionFactor() {
		return EmissionFactor;
	}

	public void setEmissionFactor(double emissionFactor) {
		EmissionFactor = emissionFactor;
	}

	public int getNbMax() {
		return NbMax;
	}

	public void setNbMax(int nbMax) {
		NbMax = nbMax;
	}

	@Override
	public String toString() {
		return "CarbonData [ID=" + ID + ", TravelMode=" + TravelMode + ", EmissionFactor=" + EmissionFactor + ", NbMax="
				+ NbMax + "]";
	}

}
