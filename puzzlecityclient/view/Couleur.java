package view;

import java.awt.Color;

public class Couleur {
	
	
	private static int WIDTH =1600 ;
	private static int HEIGHT =850 ;
	private static int MENU_WIDTH =200 ;
	private static Color bgThem = Color.decode("#5b9bd5");
	private static Color bgTitle = Color.decode("#1f4e79");
	private static Color bgApp = Color.BLACK;
	
	
	public Couleur() {
		
	}

	public static int getWIDTH() {
		return WIDTH;
	}

	public static void setWIDTH(int wIDTH) {
		WIDTH = wIDTH;
	}

	public static int getHEIGHT() {
		return HEIGHT;
	}

	
	public static Color getBgThem() {
		return bgThem;
	}

	

	public static Color getBgTitle() {
		return bgTitle;
	}


	public static Color getBgApp() {
		return bgApp;
	}

	

	public static int getMENU_WIDTH() {
		return MENU_WIDTH;
	}

}
