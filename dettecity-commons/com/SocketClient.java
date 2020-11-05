package com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;


public class SocketClient {

	private static Socket socketClient;
	private static PrintWriter outJson;
	private static BufferedReader inJson;

//
	public void startConnection(String ip, int port) throws IOException {
		socketClient = new Socket(ip, port);
		outJson = new PrintWriter(socketClient.getOutputStream(), true);
		inJson = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
	}

	public static JSONObject sendMessage(JSONObject JsonMsg) throws IOException {
		System.out.println(JsonMsg);
		outJson.println(JsonMsg);
		String resp = inJson.readLine();
		Object obj=JSONValue.parse(resp); 
		JSONObject jsonObject = (JSONObject) obj;  
		return jsonObject;
	}
	

	public static void stopConnection() throws IOException {
		inJson.close();
		outJson.close();
		socketClient.close();
	}
}