package fi.my_work.todo_list.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import fi.my_work.todo_list.db.connection.CreateConnection;

//	This class executes MySQL Queries 
public class ExecuteQueries {

//	This method checks if the row already exists in database
	public static boolean getIfRowIsFound(String query){
		
//		Default value is true
		boolean isInUse = true;
		
		try {
			Connection connection = CreateConnection.connect();
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(query);
			
//			If no result is found, set false
			if (!result.next()) {
				isInUse = false;
			} else{}
			
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return isInUse;
	}
	
//	This method can handle insert, set and delete queries
	public static void executeQuery(String query){
		
		try{
			Connection connection = CreateConnection.connect();
			
			PreparedStatement statement = connection.prepareStatement(query);
			
			statement.executeUpdate();
			statement.close();
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

//	This method returns the id of a row
//	If no row is found, return 0
	public static int returnRowId(String query) {
		int id = 0;
		
		try{
			Connection connection = CreateConnection.connect();
			
			PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			statement.executeUpdate();
			
			ResultSet result = statement.getGeneratedKeys();
			if (result.next()){
	        	id = result.getInt(1);
	        }
			
	        result.close();
	        statement.close();
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return id;
	}
}