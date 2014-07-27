package com.jcommerce.core.database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Get database connection
 * 
 * @author Sun
 * 
 */
public class DataBaseConnection {
	private String driver = null;
	private String url = null;
	private String username = null;
	private String password = null;
	private Connection conn = null;

	/**
	 * Get connection from database.
	 * 
	 * @return
	 */
	public Connection getConnection() {

		try {

			getConnectionString();

			Class.forName(driver);

			conn = DriverManager.getConnection(url, username, password);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * Get driver,url, username, password from the file values.properties.
	 * 
	 */
	public void getConnectionString() {
		Properties prop = new Properties();

		try {
			InputStream in = getClass().getResourceAsStream("/WEB-INF/values.properties");
			prop.load(in);
			
			driver = prop.getProperty("jdbc.driver");
			url = prop.getProperty("jdbc.url");
			username = prop.getProperty("jdbc.user");
			password = prop.getProperty("jdbc.password");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * Close the connection
	 * 
	 * @param conn
	 */
	public void close(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
