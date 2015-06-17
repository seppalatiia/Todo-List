package fi.my_work.todo_list.db.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CreateConnection {

//	This class establishes database connection
	public static Connection connect(){
		Connection connection = null;
		try {
		    String driverName = "com.mysql.jdbc.Driver";
		    String serverName = "localhost";
		    String portNumber = "3306";
		    String mydatabase = serverName + ":" + portNumber + "/todo_list";
		    String url = "jdbc:mysql://" + mydatabase;
		    String username = "root";
		    String password = "admin";

//		    Load the JDBC driver
		    Class.forName(driverName);
		    
//		    Creating the connection
		    connection = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
}