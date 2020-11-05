package main;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.io.IOException;
import java.sql.Connection;
import controller.DBConnectController;
import view.TestPoolView;

public class Main {
	static DBConnectController namaicityController;

	public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
		namaicityController = new DBConnectController(new TestPoolView());
		namaicityController.start();
	}

}
