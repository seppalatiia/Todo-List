package fi.my_work.todo_list.db;

import fi.my_work.todo_list.db.dummy_data.DummyData;
import fi.my_work.todo_list.formatter.FormatTypeToQuery;
import fi.my_work.todo_list.type.Priority;
import fi.my_work.todo_list.type.Status;
import fi.my_work.todo_list.security.PasswordSecurity;
import fi.my_work.todo_list.texts.T;
import fi.my_work.todo_list.type.Project;
import fi.my_work.todo_list.type.Task;

public class DBInsert {

	public static int project(Project project) {
//		Check for use of dummy data is done in ProjectForm
		
		int id = -1;
//		Creating the query
		String table = "`" + T.system("DB_TABLE_PROJECT") + "` ";
		String values = FormatTypeToQuery.insert(project);
		
		String query = "INSERT INTO " + table + values;

//		Calling for method that executes query
		id = ExecuteQueries.returnRowId(query);
		
		return id;
	}

	public static void task(Task task) {
//		If the useDummyData is true, nothing will be done
		if (DummyData.useDummyData()) {
			return;
		}

//		Creating the query
		String table = "`" + T.system("DB_TABLE_TASK") + "` ";
		String values = FormatTypeToQuery.insert(task);
		
		String query = "INSERT INTO " + table + values;

//		Calling for method that executes query
		ExecuteQueries.executeQuery(query);
	}

	public static int user(String user, String emailStr, String pass) {
//		Check for use of dummy data is done in RegisterForm
		
		int id = -1;
//		Hashing the password
		String hashPass = PasswordSecurity.getHash(pass);

//		Creating the query
		String table = "`" + T.system("DB_TABLE_USER") + "` ";
		String values = "(`" +T.system("DB_TABLE_USER_COLUMN_USERNAME") + "`,`" + T.system("DB_TABLE_USER_COLUMN_EMAIL") + "`,`" + T.system("DB_TABLE_USER_COLUMN_PASSWORD") 
				+ "`) VALUES ('"+ user + "','" + emailStr + "','" + hashPass +"')";
		
		String query = "INSERT INTO " + table + values;

//		Calling for method that executes query
		id = ExecuteQueries.returnRowId(query);
		
		createFirstProject(id);
		
		return id;
	}

	private static void createFirstProject(int id) {
		Project firstProject = new Project(-1, T.get("TEXT_FIRST_PROJECT_NAME"), T.get("TEXT_FIRST_PROJECT_DESCRIPTION"), ""+id);

		int projectId = project(firstProject);

		Task firstTask = new Task(-1, ""+projectId, T.get("TEXT_FIRST_TASK_NAME"), new Priority(1), new Status(3));		
		Task secondTask = new Task(-1, ""+projectId, T.get("TEXT_SECOND_TASK_NAME"), new Priority(4), new Status(1));

		task(firstTask);
		task(secondTask);
	}
}