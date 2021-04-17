package view;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import indicator.SensorIndicator;

public class PanneauResultSensor extends JPanel{

	public PanneauResultSensor(String button, String choice, ArrayList<SensorIndicator> liste) {
		if(liste.size()==0) {
			JLabel errorMessage = new JLabel("Pas de données pour cette sélection");
			this.add(errorMessage, BorderLayout.CENTER);
			this.repaint();
		}else {
		if(button== "table") {

			switch(choice) {
			case "Position" : 
				int cptNorth=0; 
				int cptSouth = 0; 
				int cptWest =0; 
				int cptEast = 0;
				for(SensorIndicator s :liste) {
					switch(s.getLocalisation() ){
					case "Nord" :
						cptNorth=cptNorth+s.getSensorPolluantNb();
						break;
					case "Sud" :
						cptSouth= cptSouth+ s.getSensorPolluantNb();
						break;
					case "Ouest" :
						cptWest = cptWest + s.getSensorPolluantNb();
						break;
					case "Est" :
						cptEast = cptEast + s.getSensorPolluantNb();

						break;
					}				
				}

				Object[][] donnees = {
						{"Nord",  cptNorth},
						{"Sud",  cptSouth},
						{"Ouest", cptWest},
						{"Est", cptEast},
						{"Total", cptNorth+cptSouth+cptEast+cptWest}
				};

				String[] entetes = {"Position", "Nombre de capteur relevé"};
				JTable tablePosition = new JTable(donnees, entetes);
				tablePosition.setCellSelectionEnabled(false);
				this.add(tablePosition.getTableHeader(), BorderLayout.NORTH);
				this.add(new JScrollPane(tablePosition), BorderLayout.CENTER);
				this.repaint();
				break; 
			}
		} else if (button== "graphic") {
			switch (choice) {
			case "Position" : 
				int cptNorth=0; 
				int cptSouth = 0; 
				int cptWest =0; 
				int cptEast = 0;
				for(SensorIndicator s :liste) {
					switch(s.getLocalisation()){
					case "Nord" :
						cptNorth=cptNorth+s.getSensorPolluantNb();
						break;
					case "Sud" :
						cptSouth= cptSouth+ s.getSensorPolluantNb();
						break;
					case "Ouest" :
						cptWest = cptWest + s.getSensorPolluantNb();
						break;
					case "Est" :
						cptEast = cptEast + s.getSensorPolluantNb();

						break;
					}				
				}
				DefaultPieDataset pieDataset = new DefaultPieDataset();

				pieDataset.setValue("Nord",cptNorth);
				pieDataset.setValue("Sud",cptSouth);
				pieDataset.setValue("Est",cptEast);
				pieDataset.setValue("Ouest",cptWest);
				
				JFreeChart pieChart = ChartFactory.createPieChart("Résultat concernant le nombre de capteurs polluants", pieDataset, true, false, false);
				ChartPanel cPanel = new ChartPanel(pieChart);
				this.add(cPanel,BorderLayout.CENTER);
				cPanel.setVisible(true);
				break; 

			}
		}
	}
	}
}
