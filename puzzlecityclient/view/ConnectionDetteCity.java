package view;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JFrame;



public class ConnectionDetteCity extends JFrame {
	CardLayout superpos;
//	PanneauLoginNamaiCity pan;
	PanneauBienvenueDetteCity menu;
	PanneauApplication pa;
	
	
	
	public ConnectionDetteCity() {
		super("DETTECITY");
//		pan = new PanneauLoginNamaiCity();
		
			try {
				pa = new PanneauApplication();
			} catch (SQLException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		menu = new PanneauBienvenueDetteCity();
		superpos= new CardLayout();
	
		this.setLayout(superpos);
		this.setSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
//		this.getContentPane().add("fr",pan);
		this.getContentPane().add("de",pa);
		superpos.show(this.getContentPane(), "fr");
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	public CardLayout getSuperpos() {
		return superpos;
	}
/*	public PanneauLoginNamaiCity getPan() {
		return pan;
	}*/
	public PanneauBienvenueDetteCity getMenu() {
		return menu;
	}
	public PanneauApplication getPa() {
		return pa;
	}

}
