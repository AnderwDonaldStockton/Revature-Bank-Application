package util;

import java.io.FileReader;
import java.util.Properties;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
/**
 * Essentially this code reads the inputs in the config.properties file, to make the connection to the database
 * Starts with config.load(reader), reader is a new object with the CONFIG_FILE_PATH passed into this object
 * the "instance" is a singleton that is called in getInstance() to check to make sure if no other object is
 * created. getInstance is called in DBConnection.java.
 * 
 *  TLDR: this code is for making the connection to the database and is ran in DBConnection
 * @author Vimal Krishnan
 *
 */
public class ConfigReader {
	
//	private static final Logger logger = LogManager.getLogger(ConfigReader.class);
	
	private static ConfigReader instance;									// create a singleton called instance
	private Properties config = new Properties();							// create a new object called config for reading the file
	
	final String CONFIG_FILE_PATH = "C:\\Users\\andre\\eclipse-workspace\\bank-application\\Revature-Bank-Application\\src\\main\\resources\\config.properties";
	
	// private constructor
	private ConfigReader() { // private ConfigReader() throws Exception
		try {
			FileReader reader = new FileReader(CONFIG_FILE_PATH);
//			logger.info("Loading configuration from " + CONFIG_FILE_PATH);
			config.load(reader);											// implements the reading of the file through .load
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ConfigReader getInstance() { 
		if(instance == null) {												// check to see if singleton
			instance = new ConfigReader();									// for the first time, create the singleton called instance
		}
		return instance;
	}
	// this feels like recursion sort of
	public String getProperty(String key) {									// used in DBConnection
		return config.getProperty(key);										// returns object config string that is located in DBConnection
																			// searches config.properties for that string and returns the result to a string
	}
}
