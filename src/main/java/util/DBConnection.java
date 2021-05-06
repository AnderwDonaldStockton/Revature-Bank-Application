package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

public class DBConnection {

	// private static final Logger logger =
	// LogManager.getLogger(DBConnection.class);

	private static DBConnection instance; // create an singleton object called DBConnection
	private Connection conn = null; // create a Connection object called conn, set to nothing

	private DBConnection() { // throws Exception 
		// ConfigReader is in the class called ConfigReader
		String url = ConfigReader.getInstance().getProperty("DB_URL"); 		// get the location of the database, place into																		
		String user = ConfigReader.getInstance().getProperty("DB_USER"); 	// get username
		String password = ConfigReader.getInstance().getProperty("DB_PASSWORD");
		
		try {
			this.conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("oof");
		}
	}

	public static DBConnection getInstance() throws Exception { // Singleton instance called DBConnection
		if (instance == null) { // if no new connection
			instance = new DBConnection(); // create new instance called DBConnection
		}
		return instance; // return the DBConnection instance
	}

	public Connection getConnection() {
		return conn;
	} // end of getConnection method
} // end of DBConnection class
