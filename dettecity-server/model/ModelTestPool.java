package model;

import java.sql.SQLException;

import commons.DataSource;

public class ModelTestPool {
	private DataSource data;
	
	public ModelTestPool() throws SQLException, ClassNotFoundException {
		data = new DataSource();
	}

}
