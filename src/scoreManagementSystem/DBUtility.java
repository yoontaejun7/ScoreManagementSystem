package scoreManagementSystem;

import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBUtility {
	public static Connection getConnection() {
		Connection con = null;
		try {
			Properties properties = new Properties();
			String filePath = ScoreDB.class.getResource("db.properties").getPath();
			filePath = URLDecoder.decode(filePath, "utf-8");
			properties.load(new FileReader(filePath));
			String driver = properties.getProperty("DRIVER");
			String url = properties.getProperty("URL");
			String userID = properties.getProperty("userID");
			String userPassword = properties.getProperty("userPassword");
			Class.forName(driver);
			con = DriverManager.getConnection(url, userID, userPassword);
		} catch (Exception e) {
			System.out.println("Mysql Database connection failed");
			e.printStackTrace();
		}
		return con;
	}
}
