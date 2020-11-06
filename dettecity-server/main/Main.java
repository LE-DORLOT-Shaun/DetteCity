package main;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.io.IOException;
import java.sql.Connection;
import controller.DBConnectController;
import view.TestPoolView;

public class Main {
	static DBConnectController Controller;

	public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
		Controller = new DBConnectController(new TestPoolView());
		Controller.start();
	}

}
