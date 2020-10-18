package carbon_footprint_test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class readEC {
	
	public String readEC() throws IOException {
        InputStreamReader ipsr = new InputStreamReader(getClass().getClassLoader().getResourceAsStream("carbon_footprint_test/TestInsert.json"));
        BufferedReader br = new BufferedReader(ipsr);
        String outjsonString = "";
        String chaine = "";

        while ((outjsonString = br.readLine()) != null) {
            chaine = chaine + outjsonString;
        }
        return chaine;
    }
	
	
}

