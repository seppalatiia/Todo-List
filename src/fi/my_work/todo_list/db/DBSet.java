package fi.my_work.todo_list.db;

import fi.my_work.todo_list.db.dummy_data.DummyData;
import fi.my_work.todo_list.formatter.FormatTypeToQuery;
import fi.my_work.todo_list.texts.T;
import fi.my_work.todo_list.type.Project;
import fi.my_work.todo_list.type.Task;

public class DBSet {

//	Set data to a project
	public static void project(Project project) {
//		If the useDummyData is true, nothing will be done
		if (DummyData.useDummyData()) {
			return;
		}
		
//		Creating the query
		String table = "`" + T.system("DB_TABLE_PROJECT") + "`";
//		Calling for method that formats the project to SET-query values
		String values = FormatTypeToQuery.set(project);
		String condition = "`" + T.system("DB_TABLE_PROJECT_COLUMN_ID") + "`=" + project.getId();
		
		String query = "UPDATE " + table 
				+ " SET " + values 
				+ " WHERE " + condition;

//		Calling for method that executes query
		ExecuteQueries.executeQuery(query);
	}

//	Set data to a task
	public static void task(Task task) {
//		If the useDummyData is true, nothing will be done
		if (DummyData.useDummyData()) {
			return;
		}
		
//		Creating the query
		String table = "`" + T.system("DB_TABLE_TASK") + "`";
//		Calling for method that formats the task to SET-query values
		String values = FormatTypeToQuery.set(task);
		String condition = "`" + T.system("DB_TABLE_TASK_COLUMN_ID") + "`=" + task.getId();
		
		String query = "UPDATE " + table 
				+ " SET " + values 
				+ " WHERE " + condition;

//		Calling for method that executes query
		ExecuteQueries.executeQuery(query);
	}
}