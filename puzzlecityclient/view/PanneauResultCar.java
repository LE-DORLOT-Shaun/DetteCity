package view;

import java.awt.BorderLayout;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import indicator.CarIndicator;
import indicator.SensorPolluantIndicator;

public class PanneauResultCar extends JPanel {

	public PanneauResultCar(Timestamp dateStart, Timestamp dateEnd, ArrayList<CarIndicator> list) {
		System.out.println("début :"+dateStart.toString());
		System.out.println("fin :"+dateEnd.toString());

		// use of a variable (an int) to store the number of cars present in city between the two dates selected by the user 
		int nb_car = 0;
		ArrayList<CarIndicator> listCar = new ArrayList<CarIndicator>();
		for(int i =0;i<list.size();i++) {
			if((list.get(i).getDate().before(dateEnd)) && (list.get(i).getDate().after(dateStart))) {
				listCar.add(list.get(i));
				nb_car=nb_car+list.get(i).getCarsNb();
			}
		}
		
		if(listCar.size()==0){
			JLabel errorMessage = new JLabel("Pas de voitures détectées entre ces dates");
			this.add(errorMessage, BorderLayout.CENTER);
			this.repaint();
		}else {
			
			// then divided by the size of the listCar
			long avg_car = nb_car/listCar.size();
			String result = " - "+avg_car+" - ";
			Object[][] donnees = {
					{"Date de début :",  dateStart.toString()},
					{"Date de fin :",  dateEnd.toString()},
					{"Nombre de voiture moyen entre les 2 dates", result }
					
			};

			String[] entetes = {"Indicateur :", " "};
			JTable tablePosition = new JTable(donnees, entetes);
			tablePosition.setCellSelectionEnabled(false);
			this.add(tablePosition.getTableHeader(), BorderLayout.NORTH);
			this.add(new JScrollPane(tablePosition), BorderLayout.CENTER);
			this.repaint();
			System.out.println("normalement ca marche");
		}
	}

}
