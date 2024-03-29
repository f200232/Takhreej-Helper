package DataAccessLayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.InputStream;
import java.util.Properties;
import CustomLogger.Log;
import CustomException.DBConnectionException;

public class DBhandler {
  
  private static DBhandler instance;
  private Connection con;
  String url;
  String user;
  String password;
  private static final String CONFIG_FILE = "config.properties";
  
  private DBhandler() throws DBConnectionException {
    try {
      Properties properties = loadProperties();
      url = properties.getProperty("database.url");
      password = properties.getProperty("database.password");
      user = properties.getProperty("database.user");
      Class.forName("com.mysql.jdbc.Driver");
      this.con = DriverManager.getConnection(url, user, password);
    } catch (Exception ex) {
    	Log.getLogger().error("Error establishing database connection: {}", ex.getMessage());
    	throw new DBConnectionException("Error establishing database connection: " + ex.getMessage(), ex);
    }
  }
  public Connection getConnection() {
    return con;
  }
  public static DBhandler getInstance() throws DBConnectionException {
    if (instance == null) {
      instance = new DBhandler();
    } else
		try {
			if (instance.getConnection().isClosed()) {
			  instance = new DBhandler();
			}
		} catch (DBConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			 Log.getLogger().error("Error checking database connection status: {}", e.getMessage());
			 throw new DBConnectionException("Error checking database connection status: " + e.getMessage(), e);
		}
    return instance;
  }
  
  public static Properties loadProperties() {
      Properties properties = new Properties();

      try (InputStream input = DBhandler.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
          if (input == null) {
        	  Log.getLogger().error("Sorry, unable to find {}", CONFIG_FILE);
              return properties;
          }

          // Load a properties file from the class path
          properties.load(input);

      } catch (Exception e) {
    	  Log.getLogger().error("Error loading properties: {}", e.getMessage());
          e.printStackTrace(); // Log the stack trace to console for now
      }

      return properties;
  }
  
}