package view;

import java.awt.BorderLayout;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import javax.swing.JPanel;

import commons.ApplicationController;

public class PanneauApplication extends JPanel {
	private MenuApplication ma;
	private PanneauUC puc;
	private BorderLayout bl;
	
	
	public PanneauApplication() throws UnsupportedEncodingException, SQLException, IOException {
		
		
		puc = new PanneauUC();
		System.out.println("ajout panneauUC");
		ma = new MenuApplication();
		System.out.println("ajout MenuApplication");
		bl = new BorderLayout();
		this.setLayout(bl);
		this.add(puc, BorderLayout.CENTER);
		this.add(ma, BorderLayout.WEST);
		
		
	}

	public MenuApplication getMa() {
		return ma;
	}

	public PanneauUC getPuc() {
		return puc;
	}

}
