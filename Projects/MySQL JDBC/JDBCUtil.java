import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/*
	Custom JDBC tool class
	 	1. load dirver
	 	2. get connection
	 	3. close resource
	
	db.properties:
	additional properties file where we save all database property information 	

 	

 */
public class JDBCUtil {
	private static String url = null;
	private static String user = null;
	private static String password = null;
	private static String driverClass = null;
	private static InputStream in = null;

	
	//use static code block, execute code in the statac block 
	//when the class file is loading into the memeory 
	static {
		try {
			// 1. read db.properties file
			Properties props = new Properties();

			//load the properties file to the memory using IO stream
			//edit URL to where the properties file is saved
			in = new FileInputStream("C:\\Zsun\\Eclipse WorkSpace\\JavaPractice\\src\\database\\db.properties");

			// 2. load properties
			props.load(in);

			// 3. get vital information from the properties file
			url = props.getProperty("url");
			user = props.getProperty("user");
			password = props.getProperty("password");
			driverClass = props.getProperty("driver");

			// 4. load driver
			Class.forName(driverClass);

		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Failed to load driver");
		} finally {
			if (in != null) {
				try {
					// 4. close IO recourse
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * getConnection method to connect to MySQL database 
	 * 
	 * @return
	 */
	public static Connection getConnection() {
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * Method to close recourse 
	 * 
	 * @param conn 
	 * @param st   Statement st can be PreparedStatement ps
	 */
	public static void close(Connection conn, Statement st) {
		try {
			if (st != null) {
				st.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// Sugar-coating exception handling to avoid user to see confusing Exceptions
			throw new RuntimeException(e);
		}
	}

	/**
	 *close resource where it has the resultSet 
	 * 
	 * @param conn
	 * @param st	 Statement st can be PreparedStatement ps
	 * @param set
	 */
	public static void close(Connection conn, Statement st, ResultSet set) {
		try {
			if (st != null) {
				st.close();
			}
			if (set != null) {
				set.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// Sugar-coating exception handling to avoid user to see confusing Exceptions
			throw new RuntimeException(e);
		}
	}
}
