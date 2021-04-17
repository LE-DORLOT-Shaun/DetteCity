package commons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JMenuItem;

import org.json.JSONException;

import view.ConnectionDetteCity;
import view.MenuApplication;
import view.PanneauApplication;
import view.PanneauUC;

public class ApplicationController implements ActionListener {
	
	
	private PanneauUC puc;
	private MenuApplication ma;
	private ConnectionDetteCity cn;
	private PanneauApplication pa;
	private int i = 0;
	
	public ApplicationController(ConnectionDetteCity cn) {
		try {
			this.ma=ma;
			pa = new PanneauApplication();
			 ma = new MenuApplication();
			this.cn = cn;
			puc = new PanneauUC();
			cn.getPa().getMa().getIndicateur().addActionListener(this);
			cn.getPa().getMa().getPollution().addActionListener(this);
			cn.getPa().getMa().getBorne().addActionListener(this);
			cn.getPa().getMa().getCarte().addActionListener(this);
			cn.getPa().getMa().getEmpreinte().addActionListener(this);
			cn.getPa().getMa().getAccueil().addActionListener(this);
			//pa.getMa().getMen().addActionListener(this);
			 System.out.println("Fin controller");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("bonjour");
		if(e.getSource() instanceof JButton ) {
			String choix = e.getActionCommand();
			System.out.println(choix);
			if(choix.equals("configPollution")) {
				System.out.println("vous y etes");
				cn.getPa().getPuc().setCard("panneauPollution");
			}
			else if(choix.equals("Accueil")) {
				cn.getPa().getPuc().setCard("panneauBienvenue");
			}
			
			else if(choix.equals("empreinteCarbone")) {
				cn.getPa().getPuc().setCard("panneauEmpreinte");
				}
			else if(choix.equals("configBorne")) {
				System.out.println("j'appui sur ce panneau bornes");
				try {
					if(i == 0) {
					cn.getPa().getPuc().getBorne().init();
					i = 1;
					}
					else {
					cn.revalidate();
					cn.repaint();
					}
				} catch (SQLException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				cn.getPa().getPuc().setCard("panneauBorne");
			}
			else if(choix.equals("analyse")) {
				cn.getPa().getPuc().setCard("panneauIndicateur");
			}
			else if(choix.equals("CarteReseau")) {
				cn.getPa().getPuc().setCard("networkCard");
			}
			
			
			
			/*
			 * else if(choix.equals("Neutre")) { cd.show(getContentPane(),"neutre"); }
			 */
		}
		
	}

}
