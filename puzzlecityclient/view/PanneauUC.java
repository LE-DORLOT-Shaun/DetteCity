package view;

import java.awt.CardLayout;
import java.awt.Color;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import javax.swing.JPanel;

public class PanneauUC extends JPanel {
	private PanneauBorne borne;
//	private PanneauConfigurationPollution pollution;
//	private NetworkCard carteReseau;
//	private PanneauEmpreinteX empreinte;
//	private PanneauIndicator indicateur;
	private MenuApplication ma;
	private CardLayout cl;
	private PanneauBienvenueDetteCity bienvenue;
	
	
	public PanneauUC() throws UnsupportedEncodingException, SQLException, IOException {
		
//		pollution = new PanneauConfigurationPollution();
		borne = new PanneauBorne();
//		carteReseau = new NetworkCard();
//		empreinte = new PanneauEmpreinteX();
//		indicateur = new PanneauIndicator();
		bienvenue = new PanneauBienvenueDetteCity();
		ma = new MenuApplication();
		//pollution.setBackground(Color.BLUE);
		
		cl = new CardLayout();
		this.setLayout(cl);
		System.out.println("bonjour Je suis ici");
		this.add("panneauBienvenue", bienvenue);
//		this.add("panneauPollution", pollution);
		this.add("panneauBorne", borne);
		System.out.println("après l'ajout de panneauBorne");
//		this.add("networkCard", carteReseau);
//		this.add("panneauEmpreinte", empreinte);
//		this.add("panneauIndicateur", indicateur);
		//cl.show(bienvenue, "panneauBienvenue");
	}
	public void setCard(String name) {
		cl.show(this, name);
	}
	public PanneauBorne getBorne() {
		System.out.println("je suis dans le getBorne");
		return borne;
	}
/*	public PanneauConfigurationPollution getPollution() {
		return pollution;
	}
	public NetworkCard getCarteReseau() {
		return carteReseau;
	}
	public PanneauEmpreinteX getEmpreinte() {
		return empreinte;
	}
	public PanneauIndicator getIndicateur() {
		return indicateur;
	}*/
	public CardLayout getCl() {
		return cl;
	}
	public PanneauBienvenueDetteCity getBienvenue() {
		return bienvenue;
	}

}
