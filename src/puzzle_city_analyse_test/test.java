package puzzle_city_analyse_test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import org.omg.CORBA.Request;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.protocol.Message;

import puzzle_city_client.Client;

public class test {

	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		
		Message msg = new Message();
		Request r = new Request();
		ObjectMapper ob = new ObjectMapper();
		Client c = new Client("172.31.249.155", 4000);
		OutputStreamWriter o = c.start();

		String chalet = "";
		// lecture du fichier texte
		try {
			InputStream is = new FileInputStream("Insertbound.json");
			// InputStream les = new
			// FileInputStream("src/indicator_jsoninsert/Insertbound.json");
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String tes;
			while ((tes = br.readLine()) != null) {
				chalet += tes;
			}
			System.out.println(chalet);
			br.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}

		String re = "";
		try {
			InputStream lie = new FileInputStream("testinsert.json");
			InputStreamReader chi = new InputStreamReader(lie);
			BufferedReader ne = new BufferedReader(chi);
			String ja;
			while ((ja = ne.readLine()) != null) {
				re += ja;
			}
			System.out.println(re);
			ne.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}

}