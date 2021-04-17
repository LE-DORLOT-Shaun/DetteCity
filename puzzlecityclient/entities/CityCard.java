package entities;

public class CityCard {

	private String libelle;
	private String shape;
	private int length;
	private int width;
	private int nb_points;
	private double cost;

	public CityCard() {
	}

	public CityCard(String libelle, String shape, int length, int width, int nb_points, double cost) {
		this.libelle = libelle;
		this.shape = shape;
		this.length = length;
		this.width = width;
		this.nb_points = nb_points;
		this.cost = cost;
	}
	

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getShape() {
		return shape;
	}

	public void setShape(String shape) {
		this.shape = shape;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getNb_points() {
		return nb_points;
	}

	public void setNb_points(int nb_points) {
		this.nb_points = nb_points;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	@Override
	public String toString() {
		return "CityCard [libelle=" + libelle + ", shape=" + shape + ", length=" + length + ", width=" + width
				+ ", nb_points=" + nb_points + ", cost=" + cost + "]";
	}

}
