package com.commons.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AccessServer {
	
	//design pattern singleton

		private static AccessServer access = new AccessServer();
		private static String SERVER;
		private static int PORT_SERVER;
		
		//private Constructor
		private AccessServer(){
			Properties prop = new Properties();
			InputStream input = null;
			
			try {
				
				input = getClass().getClassLoader().getResourceAsStream("client.properties");

				// load a properties file
				prop.load(input);
				
				// get the property value and print it out
				SERVER = prop.getProperty("SERVER");
				PORT_SERVER = Integer.valueOf(prop.getProperty("PORT_SERVER"));

				
			} catch (IOException ex) {
				ex.printStackTrace();
			} finally {
				if (input != null) {
					try {
						input.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		   
	
		public static AccessServer getInstance(){
		if(access == null)
			access = new AccessServer();  
		    return access;
		}
		
		
		public static String getSERVER() {
			return SERVER;
		}

		public static int getPORT_SERVER() {
			return PORT_SERVER;
		}
	

}
