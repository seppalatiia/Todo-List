package fi.my_work.todo_list.db;

import fi.my_work.todo_list.db.dummy_data.DummyData;
import fi.my_work.todo_list.texts.T;

public class DBDelete {

//	Deletes the project
	public static void project(int id) {
//		If the useDummyData is true, nothing will be done
		if (DummyData.useDummyData()) {
			return;
		}
		
//		Before project can be deleted, all the tasks from the project must be removed
		allTaskFromProject(id);
		
//		Creating the query
		String table = "`" + T.system("DB_TABLE_PROJECT") + "`";
		String column = "`" + T.system("DB_TABLE_PROJECT_COLUMN_ID") + "`";
		
		String query = "DELETE FROM " + table + " WHERE " + column + "=" + id;
		
//		Calling for method that executes query
		ExecuteQueries.executeQuery(query);
	}

//	Deletes all the tasks form project
	private static void allTaskFromProject(int id) {
//		If the useDummyData is true, nothing will be done
		if (DummyData.useDummyData()) {
			return;
		}
		
//		Creating the query
		String table = "`" + T.system("DB_TABLE_TASK") + "`";
		String column = "`" + T.system("DB_TABLE_TASK_COLUMN_PID") + "`";
		
		String query = "DELETE FROM " + table + " WHERE " + column + "=" + id;

//		Calling for method that executes query
		ExecuteQueries.executeQuery(query);
	}

//	Deletes the task
	public static void task(int id) {
//		If the useDummyData is true, nothing will be done
		if (DummyData.useDummyData()) {
			return;
		}
		
//		Creating the query
		String table = "`" + T.system("DB_TABLE_TASK") + "`";
		String column = "`" + T.system("DB_TABLE_TASK_COLUMN_ID") + "`";
		
		String query = "DELETE FROM " + table + " WHERE " + column + "=" + id;

//		Calling for method that executes query
		ExecuteQueries.executeQuery(query);
	}
}