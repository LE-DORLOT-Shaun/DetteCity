package entities;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.simple.JSONObject;

import commons.TestJson;

public class Bollards {
	
	private ArrayList<JSONObject> IdList;
	private Object[][] IdTable;
	public static boolean IDexist;
	
	public boolean checkId(int idBollards) throws UnsupportedEncodingException, SQLException, IOException, JSONException {
		JSONObject bornes = TestJson.getBornes();
		IdList = (ArrayList<JSONObject>) bornes.get("bollards");
		this.IdTable = new Object[IdList.size()][1];
		int testValeur = 0;
		String IDBollardsstring = "" + idBollards;
		for (int i = 0; i < IdList.size(); i++ ) {
			String idString = ((IdList.get(i)).get("id")).toString();
			if (idString.equals(IDBollardsstring)) {
				testValeur = 1;
			} else {
				//Do nothing
			}
		}

		if(testValeur == 1) {
			return true;
		} else {
			return false;
		}
	}
}