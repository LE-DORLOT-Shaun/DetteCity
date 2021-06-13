package main;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.json.JSONException;

import java.io.IOException;
import java.sql.Connection;
import controller.DBConnectController;
import view.TestPoolView;
import socketserver.ThreadServer;

public class Main {
	static DBConnectController Controller;
	static ThreadServer Thread;
	public int maxCo = 0;
	
	public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException, JSONException, InterruptedException {
		//Thread = new ThreadServer();
		//String[] test = null;
		//ThreadServer.main(test);
		//Controller = new DBConnectController(new TestPoolView());
		int maxCo = Integer.parseInt(args[0]);
		Controller = new DBConnectController(maxCo);
		Controller.start(maxCo);
		
	}
}
