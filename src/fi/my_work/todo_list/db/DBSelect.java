package fi.my_work.todo_list.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.vaadin.ui.UI;

import fi.my_work.todo_list.db.connection.CreateConnection;
import fi.my_work.todo_list.db.dummy_data.DummyData;
import fi.my_work.todo_list.notification.Messages;
import fi.my_work.todo_list.notification.Notifications;
import fi.my_work.todo_list.security.PasswordSecurity;
import fi.my_work.todo_list.texts.T;
import fi.my_work.todo_list.type.Priority;
import fi.my_work.todo_list.type.Project;
import fi.my_work.todo_list.type.Status;
import fi.my_work.todo_list.type.Task;

public class DBSelect {

//	Check if the username is already in the database
	public static boolean isUserNameInUse(String user){
//		If the useDummyData is true, use dummy account info
		if (DummyData.useDummyData()) {
			if (user.equals("admin")) {
				return true;
			}
			else {
				return false;
			}
		}
		
		boolean isInUse = true;

//		Creating the query
		String table = "`" + T.system("DB_TABLE_USER") + "`";
		String column = "`" + T.system("DB_TABLE_USER_COLUMN_USERNAME") + "`";
		
		String query = "SELECT * FROM " + table + " WHERE " + column + "='" + user + "'";

//		Calling for method that executes query
		isInUse = ExecuteQueries.getIfRowIsFound(query);
		
		return isInUse;
	}

//	Check if the email is already in the database
	public static boolean isEmailInUse(String email){
//		If the useDummyData is true, use dummy account info
		if (DummyData.useDummyData()) {
			if (email.equals("admin@dummy.test")) {
				return true;
			}
			else {
				return false;
			}
		}
		
		boolean isInUse = true;

//		Creating the query
		String table = "`" + T.system("DB_TABLE_USER") + "`";
		String column = "`" + T.system("DB_TABLE_USER_COLUMN_EMAIL") + "`";
		
		String query = "SELECT * FROM " + table + " WHERE " + column + "='" + email + "'";

//		Calling for method that executes query
		isInUse = ExecuteQueries.getIfRowIsFound(query);
		
		return isInUse;
	}
	
//	Get the user id from database
	public static int userId(String user, String pass) {
//		If the useDummyData is true, use dummy account
		if (DummyData.useDummyData()) {
			if (user.equals("admin") && pass.equals("admin")) {
				return 1;
			}
			else {
				return -1;
			}
		}

//		Default value to the id
		int id = -1;

//		Creating the query
		String table = "`" + T.system("DB_TABLE_USER") + "`";
		String columnUser = "`" + T.system("DB_TABLE_USER_COLUMN_USERNAME") + "`";
		String columnPass = "`" + T.system("DB_TABLE_USER_COLUMN_PASSWORD") + "`";
		String columnId = "`" + T.system("DB_TABLE_USER_COLUMN_ID") + "`";
		
		String hashPass = PasswordSecurity.getHash(pass);
		
		String query = "SELECT " + columnId + " FROM " + table + " WHERE " + columnUser + "='" + user + "' AND " + columnPass + "='" + hashPass + "'";

//		Executing query
		try {
			Connection connection = CreateConnection.connect();
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(query);
			while (result.next()) {
				id = result.getInt(1);
			}
			
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e){
			Notifications.throwSingleError(Messages.getMessageWithHeaderDatabaseConnectionFailed());
			return -2;
		}
		
		return id;
	}

//	Get all  project for the user
	public static ArrayList<Project> selectProjects(String uid) {
//		If the useDummyData is true, dummy projects will be fetched
		if (DummyData.useDummyData()) {
			return DummyData.getProjects(uid);
		}

		ArrayList<Project> projects = new ArrayList<Project>();

//		Creating the query
		String table = "`" + T.system("DB_TABLE_PROJECT") + "`";
		String column = "`" + T.system("DB_TABLE_PROJECT_COLUMN_UID") + "`";
		
		String query = "SELECT * FROM " + table + " WHERE " + column + "=" + uid;

//		Executing query
		try {
			Connection connection = CreateConnection.connect();
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(query);
			while (result.next()) {
				
				int id = result.getInt(T.system("DB_TABLE_PROJECT_COLUMN_ID"));
				String name = result.getString(T.system("DB_TABLE_PROJECT_COLUMN_NAME"));
				String desc = result.getString(T.system("DB_TABLE_PROJECT_COLUMN_DESCRIPTION"));
				
				Project project = new Project(id, name, desc,""+uid);
				projects.add(project);
			}
			
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return projects;
	}

//	Get a specific project
	public static Project projectById(String id) {
//		If the useDummyData is true, dummy project will be fetched
		if (DummyData.useDummyData()) {
			return DummyData.getSelectedProject(id);
		}

		Project project = null;
		String uid = UI.getCurrent().getSession().getAttribute(T.system("SESSION_SELECTED_PROJECT")).toString();

//		Creating the query
		String table = "`" + T.system("DB_TABLE_PROJECT") + "`";
		String column = "`" + T.system("DB_TABLE_PROJECT_COLUMN_ID") + "`";
		
		String query = "SELECT * FROM " + table + " WHERE " + column + "=" + id;

//		Executing query
		try {
			Connection connection = CreateConnection.connect();
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(query);
			while (result.next()) {
				
				int pid = result.getInt(T.system("DB_TABLE_PROJECT_COLUMN_ID"));
				String name = result.getString(T.system("DB_TABLE_PROJECT_COLUMN_NAME"));
				String desc = result.getString(T.system("DB_TABLE_PROJECT_COLUMN_DESCRIPTION"));
				
				project = new Project(pid, name, desc, uid);
			}
			
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return project;
	}

//	Get all tasks for the project
	public static ArrayList<Task> tasksByProject(String projectId) {
//		If the useDummyData is true, dummy task will be fetched
		if (DummyData.useDummyData()) {
			return DummyData.getTasks(projectId);
		}

		ArrayList<Task> tasks = new ArrayList<Task>();

//		Creating the query
		String table = "`" + T.system("DB_TABLE_TASK") + "`";
		String column = "`" + T.system("DB_TABLE_TASK_COLUMN_PID") + "`";
		
		String query = "SELECT * FROM "  + table + " WHERE " + column + "=" + projectId;

//		Executing query
		try {
			Connection connection = CreateConnection.connect();
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(query);
			while (result.next()) {
				
				int id = result.getInt(T.system("DB_TABLE_TASK_COLUMN_ID"));
				String name = result.getString(T.system("DB_TABLE_TASK_COLUMN_NAME"));
				
				Task task = new Task(id, projectId, name);
				
				task.setDueDate(result.getDate(T.system("DB_TABLE_TASK_COLUMN_DUEDATE")));
				task.setPriority(priorityById(result.getInt(T.system("DB_TABLE_TASK_COLUMN_PRIORITY"))));
				task.setStatus(statusById(result.getInt(T.system("DB_TABLE_TASK_COLUMN_STATUS"))));
				task.setDescription(result.getString(T.system("DB_TABLE_TASK_COLUMN_DESCRIPTION")));
				
				tasks.add(task);
			}
			
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return tasks;
	}

//	Get a specific priority
	private static Priority priorityById(int id) {
//		If the useDummyData is true, dummy priority will be fetched
		if (DummyData.useDummyData()) {
			return DummyData.getPriority(id);
		}
		
		Priority priority = null;

//		Creating the query
		String table = "`" + T.system("DB_TABLE_PRIORITY") + "`";
		String column = "`" + T.system("DB_TABLE_PRIORITY_COLUMN_ID") + "`";
		
		String query = "SELECT * FROM " + table + " WHERE " + column + "=" + id;

//		Executing query
		try {
			Connection connection = CreateConnection.connect();
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(query);
			
			while (result.next()) {
				String name = result.getString(T.system("DB_TABLE_PRIORITY_COLUMN_NAME"));
				
				priority = new Priority(id, name);
			}
			
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return priority;
	}

//	Get all priorities
	public static ArrayList<Priority> allPriorities() {
//		If the useDummyData is true, dummy priorities will be fetched
		if (DummyData.useDummyData()) {
			return DummyData.getPriorities();
		}
		
		ArrayList<Priority> priorities = new ArrayList<Priority>();
		
//		Creating the query
		String query = "SELECT * FROM `" + T.system("DB_TABLE_PRIORITY") + "`";

//		Executing query
		try {
			Connection connection = CreateConnection.connect();
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(query);
			while (result.next()) {
				
				int id = result.getInt(T.system("DB_TABLE_PRIORITY_COLUMN_ID"));
				String name = result.getString(T.system("DB_TABLE_PRIORITY_COLUMN_NAME"));
				
				Priority priority = new Priority(id, name);
				
				priorities.add(priority);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return priorities;
	}

//	Get a specific status
	private static Status statusById(int id) {
//		If the useDummyData is true, dummy status will be fetched
		if (DummyData.useDummyData()) {
			return DummyData.getStatus(id);
		}
		
		Status status = null;

//		Creating the query
		String table = "`" + T.system("DB_TABLE_STATUS") + "`";
		String column = "`" + T.system("DB_TABLE_STATUS_COLUMN_ID") + "`";
		
		String query = "SELECT * FROM " + table + " WHERE " + column + "=" + id;

//		Executing query
		try {
			Connection connection = CreateConnection.connect();
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(query);
			while (result.next()) {
				String name = result.getString(T.system("DB_TABLE_STATUS_COLUMN_NAME"));
				
				status = new Status(id, name);
			}
			
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}
	
//	Get all statuses
	public static ArrayList<Status> allStatuses() {
//		If the useDummyData is true, dummy statuses will be fetched
		if (DummyData.useDummyData()) {
			return DummyData.getStatuses();
		}
		
		ArrayList<Status> statuses = new ArrayList<Status>();
		
//		Creating the query
		String query = "SELECT * FROM `" + T.system("DB_TABLE_STATUS") + "`";

//		Executing query
		try {
			Connection connection = CreateConnection.connect();
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(query);
			
			while (result.next()) {
				
				int id = result.getInt(T.system("DB_TABLE_STATUS_COLUMN_ID"));
				String name = result.getString(T.system("DB_TABLE_STATUS_COLUMN_NAME"));
				
				Status status = new Status(id, name);
				
				statuses.add(status);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return statuses;
	}
}