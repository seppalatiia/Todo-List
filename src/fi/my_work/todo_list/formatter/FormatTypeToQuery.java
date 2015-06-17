package fi.my_work.todo_list.formatter;

import fi.my_work.todo_list.texts.T;
import fi.my_work.todo_list.type.Project;
import fi.my_work.todo_list.type.Task;

public class FormatTypeToQuery {

//	Creating values for SET query for project
	public static String set(Project project) {
		String query = "";
		
//		Project name
		query = query + "`" + T.system("DB_TABLE_PROJECT_COLUMN_NAME") 
				+ "`='" + project.getName() + "'";
		
//		Project description
		if(project.isDescriptionNull()){
			query = query + ",`" + T.system("DB_TABLE_PROJECT_COLUMN_DESCRIPTION") 
					+ "`=NULL";
		} else{
			query = query + ",`" + T.system("DB_TABLE_PROJECT_COLUMN_DESCRIPTION") 
					+ "`='" + project.getDescription() + "'";
		}
		
		return query;
	}

//	Creating values for INSERT query for project
	public static String insert(Project project) {
		String columns = "";
		String values = "";
		
//		Project name
		columns = columns + "`" + T.system("DB_TABLE_PROJECT_COLUMN_NAME") + "`";
		values = values + "'" + project.getName() + "'";
		
//		User id
		columns = columns
				+ ",`" + T.system("DB_TABLE_PROJECT_COLUMN_UID") + "`";
		values = values + "," + project.getUid();
		
//		Project description
		if(!project.isDescriptionNull()){
			columns = columns + ",`" + T.system("DB_TABLE_PROJECT_COLUMN_DESCRIPTION") + "`";
			values = values + ",'" + project.getDescription() + "'";
		}
		
//		Assemble query values
		String query = "(" + columns + ") VALUES (" + values + ")";
		
		return query;
	}

//	Creating values for SET query for task
	public static String set(Task task){
		String query = "";
		
//		Task name
		query = query + "`" + T.system("DB_TABLE_TASK_COLUMN_NAME") 
				+ "`='" + task.getName() + "'";

//		Task priority
		query = query + ",`" + T.system("DB_TABLE_TASK_COLUMN_PRIORITY") 
				+ "`=" + task.getPriority().getId();

//		Task status
		query = query + ",`" + T.system("DB_TABLE_TASK_COLUMN_STATUS") 
				+ "`=" + task.getStatus().getId();

//		Task due date
		if(task.getDueDate() == null){
			query = query + ",`" + T.system("DB_TABLE_TASK_COLUMN_DUEDATE") 
					+ "`=NULL";
		} else{
			query = query + ",`" + T.system("DB_TABLE_TASK_COLUMN_DUEDATE") 
					+ "`='" + task.getDueDateString() + "'";
		}

//		Task description
		if(task.isDescriptionNull()){
			query = query + ",`" + T.system("DB_TABLE_TASK_COLUMN_DESCRIPTION") 
					+ "`=NULL";
		} else{
			query = query + ",`" + T.system("DB_TABLE_TASK_COLUMN_DESCRIPTION") 
					+ "`='" + task.getDescription() + "'";
		}
		
		return query;
	}

//	Creating values for INSERT query for task
	public static String insert(Task task) {
		String columns = "";
		String values = "";

//		Task name
		columns = columns + "`" + T.system("DB_TABLE_TASK_COLUMN_NAME") + "`";
		values = values + "'" + task.getName() + "'";

//		Project id
		columns = columns + ",`" + T.system("DB_TABLE_TASK_COLUMN_PID") + "`";
		values = values + "," + task.getProjectId();

//		Task priority
		columns = columns + ",`" + T.system("DB_TABLE_TASK_COLUMN_PRIORITY") + "`";
		values = values + "," + task.getPriority().getId();

//		Task status
		columns = columns + ",`" + T.system("DB_TABLE_TASK_COLUMN_STATUS") + "`";
		values = values + "," + task.getStatus().getId();

//		Task due date
		if (task.getDueDate() != null) {
			columns = columns + ",`" + T.system("DB_TABLE_TASK_COLUMN_DUEDATE") + "`";
			values = values + ",'" + task.getDueDateString() + "'";
		}

//		Task description
		if (!task.isDescriptionNull()) {
			columns = columns + ",`" + T.system("DB_TABLE_TASK_COLUMN_DESCRIPTION") + "`";
			values = values + ",'" + task.getDescription() + "'";
		}
		
		String query = "(" + columns + ") VALUES (" + values + ")";
		return query;
	}
}
