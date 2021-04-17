package indicator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.simple.JSONObject;

public class Car {
	

	public JSONObject carDAO (JSONObject JsonRecu, Connection c) {
		JSONObject obj=new JSONObject();
		if (JsonRecu.get("demandType").equals("CAR_INDICATOR")) {

			try {
				PreparedStatement stmt1 = c.prepareStatement("select * from frequentation_voiture;"); 

				System.out.println("execution de la requ�te");
				ResultSet rs2 = stmt1.executeQuery();
				System.out.println("requ�te ex�cut�e)"); 

				// creation of car list 
				ArrayList<JSONObject> listCars = new ArrayList<JSONObject>();
				
				System.out.println("mapping classe CarIndicator");

				while (rs2.next()) {

					// Mapping de la classe CarIndicator (passage des r�sultats de la BDD en un objet java gr�ce au resultset 
					CarIndicator car = new CarIndicator(rs2.getInt("nb_voitures"),rs2.getTimestamp("date"), 0, 0);
					System.out.println("r�cuperation des r�sultats du select"); 
					JSONObject carJSON = car.convertToJSON();
					// adding each sensor to the list already created
					listCars.add(carJSON);
					System.out.println("ajout d'une frequentation de voitures dans la liste des voitures"); 

				}
				//System.out.println("voici l'arrayList : ");
				// displaying the list 
				//System.out.println(listUsers);

				obj.put("cars", listCars);
				System.out.println("voici le json envoy� avec le select permettant de r�cup�rer le nombre de voiture dans la ville: ");
				// displaying the Json
				System.out.println(obj);
				Thread.sleep(3000);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return obj;
	}
}

