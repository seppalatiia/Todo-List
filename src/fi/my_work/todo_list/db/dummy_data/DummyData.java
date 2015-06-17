package fi.my_work.todo_list.db.dummy_data;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import fi.my_work.todo_list.type.Priority;
import fi.my_work.todo_list.type.Project;
import fi.my_work.todo_list.type.Status;
import fi.my_work.todo_list.type.Task;

public class DummyData {
/**
 * Sometimes during the development the database is not available, but the program needs to be run
 * In that situation, dummy data can be used.
 * 
 * When the use of dummy data is no longer needed performing next steps will make the system use database only
 * 	- Delete this class
 *  - Remove all parts of the code that requires this class
 *  - remove dummy_data package
 */
	
//	This method tells if the system should use database or dummy data.
//	When this method returns true, methods in classes DBInsert, DBSelect and DBSet doesn't do anything.
//	When this method returns false, the system will use the configured database
	public static boolean useDummyData() {
		return true;
	}
	
	
	/*
	 * Generate dummy projects
	 */
	public static ArrayList<Project> getProjects(String userId){
		ArrayList<Project> projects = new ArrayList<Project>();
		
		projects.add(new Project(1, "Hello", "You are currently using dummy data. This means that functions for adding, editing and deleting tasks and projects are currently disabled. The use of dummy data is meant to help with designing", "1"));
		projects.add(new Project(2, "You are", "", "1"));
		projects.add(new Project(3, "Currently", "", "1"));
		projects.add(new Project(4, "Using", "", "1"));
		projects.add(new Project(5, "Dummy Data", "", "1"));
		
		return projects;
	}
	/*
	 * -----------------------------------------------------------------------------
	 */
	public static Project getSelectedProject(String projectId){
		ArrayList<Project> projects = getProjects("1");
		
		return projects.get(Integer.parseInt(projectId)-1);
	}
	
	/*
	 * Generate random tasks
	 */
	public static ArrayList<Task> getTasks(String projectId){
		ArrayList<Task> tasks = new ArrayList<Task>();
		
//		Create some projects without tasks
		int j = (int) (Math.random()*20);
		if(j == 1){
			return tasks;
		}
		
		int taskAmount = (int) (Math.random()*50) + 1;
		
		for (int i = 0; i < taskAmount; i++) {
			Task task = new Task(i, projectId, getDummyTaskName());
			
			task.setPriority(getPriority((int)(Math.random()*5+1)));
			task.setStatus(getStatus((int)(Math.random()*4+1)));
			task.setDueDate(getDummyDueDate());
			
			tasks.add(task);
		}
		return tasks;
	}
	/*
	 * -----------------------------------------------------------------------------
	 */

	/*
	 * Generate random name for the task
	 */
	private static String getDummyTaskName(){
		String[] noun = {"Flower","Dog","Cat","Ball","Plate","Brush","Mug","Box","Bottle","Car"};
		String[] verb = {"Kick","Throw","Read","Write","Watch","Listen","Eat","Drink","Dye","Run"};
		String[] adjective = {"Red","Blue","Pretty","Hot","Cold","Wooden","Small","Big","Tasty","Greasy"};
		
		return verb[(int) (Math.random()*verb.length)] + " " + 
			adjective[(int) (Math.random()*adjective.length)] + " " + 
			noun[(int) (Math.random()*noun.length)];
	}
	/*
	 * -----------------------------------------------------------------------------
	 */
	
	/*
	 * Generate random date for the task
	 */
	private static Date getDummyDueDate() {
//		Create some tasks without duedate
		int i = (int) (Math.random()*10);
		if(i == 1){
			return null;
		}
		
		GregorianCalendar gc = new GregorianCalendar();

        int year = randBetween(2014, 2015);

        gc.set(gc.YEAR, year);

        int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));

        gc.set(gc.DAY_OF_YEAR, dayOfYear);

        String temp = (gc.get(gc.YEAR) + "-" + gc.get(gc.MONTH) + "-" + gc.get(gc.DAY_OF_MONTH));
        
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
		try {
			date = format.parse(temp);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        
        return date;
	}
	private static int randBetween(int start, int end) {
		return start + (int)Math.round(Math.random() * (end - start));
	}
	/*
	 * -----------------------------------------------------------------------------
	 */


	/*
	 * Generate dummy priority
	 */
	public static Priority getPriority(int id) {
		ArrayList<Priority> priorities = getPriorities();
		return priorities.get(id-1);
	}


	public static ArrayList<Priority> getPriorities() {
		ArrayList<Priority> priorities = new ArrayList<Priority>();
		
		priorities.add(new Priority(1, "PRIORITY_1"));
		priorities.add(new Priority(2, "PRIORITY_2"));
		priorities.add(new Priority(3, "PRIORITY_3"));
		priorities.add(new Priority(4, "PRIORITY_4"));
		priorities.add(new Priority(5, "PRIORITY_5"));
		
		return priorities;
	}
	/*
	 * -----------------------------------------------------------------------------
	 */

	/*
	 * Generate dummy status
	 */
	public static Status getStatus(int id) {
		ArrayList<Status> statuses = getStatuses();
		return statuses.get(id-1);
	}


	public static ArrayList<Status> getStatuses() {
		ArrayList<Status> statuses = new ArrayList<Status>();
		
		statuses.add(new Status(1, "STATUS_1"));
		statuses.add(new Status(2, "STATUS_2"));
		statuses.add(new Status(3, "STATUS_3"));
		statuses.add(new Status(4, "STATUS_4"));
		
		return statuses;
	}
}