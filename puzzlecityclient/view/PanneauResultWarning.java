package view;

import java.awt.BorderLayout;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import indicator.SensorPolluantIndicator;

public class PanneauResultWarning extends JPanel {

	public PanneauResultWarning(Timestamp dateStart, Timestamp dateEnd, String namePolluant, List<Integer> listThreshold, ArrayList<SensorPolluantIndicator> listWarningWithoutDate) {
		System.out.println("début :"+dateStart.toString());
		System.out.println("fin :"+dateEnd.toString());
		
		ArrayList<SensorPolluantIndicator> listWarning = new ArrayList<SensorPolluantIndicator>();
		// recovery of the number of warning
		for(int i =0;i<listWarningWithoutDate.size();i++) {
			if((listWarningWithoutDate.get(i).getDate().before(dateEnd)) && (listWarningWithoutDate.get(i).getDate().after(dateStart))) {
				listWarning.add(listWarningWithoutDate.get(i));
			}
		}
		
		if(listWarning.size()==0){
			
			JLabel errorMessage = new JLabel("Pas d'alertes pour cette plage de date");
			this.add(errorMessage, BorderLayout.CENTER);
			this.repaint();
		}else {			
		
			// calculation of average overrun : polluant value - threshold value of this polluant, we do this for the 3 polluants ( the temperature is besides) 
		if(!namePolluant.equals("TMP")) {
		double dptAvg = 0.00;
		switch(namePolluant) {
		case "CO2" :
			for(int i=0;i<listWarning.size();i++) {
				dptAvg=dptAvg+(Integer.valueOf(listWarning.get(i).getCo2())-listThreshold.get(0));
				
			}
			break;
		case "NO2" :
			for(int i=0;i<listWarning.size();i++) {
				dptAvg=dptAvg+(Integer.valueOf(listWarning.get(i).getNo2())-listThreshold.get(0));
				
			}
			break;
		case "PF" :
			for(int i=0;i<listWarning.size();i++) {
				dptAvg=dptAvg+(Integer.valueOf(listWarning.get(i).getPf())-listThreshold.get(0));
				
			}
			break;
		}
		dptAvg=dptAvg/(listWarning.size());
		double txDpt = (100*dptAvg)/listThreshold.get(0);
		
		// recovery of the result of the request in table
		Object[][] donnees = {
				{"Date de début :",  dateStart.toString()},
				{"Date de fin :",  dateEnd.toString()},
				{"Nombre d’alertes pour le capteur ",  listWarning.size()},
				{"dépassement du seuil de :",  namePolluant},
				{"seuil du polluant :",  listThreshold.get(0)},
				{"Dépassement moyen du seuil",  dptAvg},
				{"Taux de dépassement", txDpt+"%"}
		};

		String[] entetes = {"Indicateur :", " "};
		JTable TableThreshold = new JTable(donnees, entetes);
		TableThreshold.setCellSelectionEnabled(false);
		this.add(TableThreshold.getTableHeader(), BorderLayout.NORTH);
		this.add(new JScrollPane(TableThreshold), BorderLayout.CENTER);
		this.repaint();
		
		}else {
			
			// for the temperature we have 2 thresholds, so we need to aplicate the same calcul for two threshold (min and max) 
			int cptTmpMax = 0;
			double dptAvgMax = 0.00;
			int cptTmpMin = 0;
			double dptAvgMin = 0.00;
			
			for(int i=0;i<listWarning.size();i++) {
				if(Integer.valueOf(listWarning.get(i).getTmp())>listThreshold.get(1)) {
					cptTmpMax++;
					dptAvgMax=dptAvgMax+(Integer.valueOf(listWarning.get(i).getTmp())-listThreshold.get(1));
				}else if(Integer.valueOf(listWarning.get(i).getTmp())<listThreshold.get(0)) {
					cptTmpMin++;
					dptAvgMin=dptAvgMin+(Integer.valueOf(listWarning.get(i).getTmp())-listThreshold.get(0));
				}
			}		
			dptAvgMax=dptAvgMax/cptTmpMax;
			dptAvgMin=dptAvgMin/cptTmpMin;
			double txDptMax = (100*dptAvgMax)/listThreshold.get(1);
			double txDptMin = (100*dptAvgMin)/listThreshold.get(0);
			
			// recovery of the result of the request in table
			Object[][] donnees = {
					{"Date de début :",  dateStart.toString()},
					{"Date de fin :",  dateEnd.toString()},
					{"Nb d’alertes sur le seuil maximal : "+listThreshold.get(1),  cptTmpMax},
					{"Nb d’alertes sous le seuil minimal : "+listThreshold.get(0),  cptTmpMin},
					{"Dépassement moyen du seuil maximal", dptAvgMax},
					{"Taux de dépassement du seuil maximal", txDptMax+"%"},
					{"Dépassement moyen du seuil minimal", dptAvgMin},
					{"Taux de dépassement du seuil minimal", txDptMin+"%"}
			};

			String[] entetes = {"Indicateur :", " "};
			JTable TableThreshold = new JTable(donnees, entetes);
			TableThreshold.setCellSelectionEnabled(false);
			this.add(TableThreshold.getTableHeader(), BorderLayout.NORTH);
			this.add(new JScrollPane(TableThreshold), BorderLayout.CENTER);
			this.repaint();
		}
	}
	}
}
