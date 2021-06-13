package model;

import java.io.IOException;
import java.sql.SQLException;

import commons.DataSource;
import commons.ConnectionPool;

public class ModelTestPool {
	private DataSource data;
	
	public ModelTestPool(int maxCo) throws SQLException, ClassNotFoundException, IOException {
//		data = new DataSource();
		new ConnectionPool(maxCo);
	}

}
