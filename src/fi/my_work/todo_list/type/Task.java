package fi.my_work.todo_list.type;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import fi.my_work.todo_list.texts.T;
import fi.my_work.todo_list.type.Priority;
import fi.my_work.todo_list.type.Status;

//  This class works as an task object
public class Task {
	
	private int id;
	private String projectId;
	private String name = null;
	private Date duedate = null;
	private Priority priority = null;
	private Status status = null;
	private String description = null;

	public Task(int id, String projectId, String name) {
		this.id = id;
		this.projectId = projectId;
		this.name = name;
	}

	public Task(int id, String projectId, String name, Priority priority, Status status) {
		this.id = id;
		this.projectId = projectId;
		this.name = name;
		this.priority = priority;
		this.status = status;
	}

	public Task(String projectId) {
		this.projectId = projectId;
	}

	public int getId() {
		return id;
	}

	public String getProjectId() {
		return projectId;
	}

	public String getName() {
		return name;
	}

	public Date getDueDate() {
		return duedate;
	}

	public String getDueDateString() {
		if (duedate == null) {
			return "-";
		} else {
			DateFormat format = new SimpleDateFormat(T.system("DATE_FORMAT"));
			return format.format(duedate);
		}
	}

	public Priority getPriority() {
		return priority;
	}

	public Status getStatus() {
		return status;
	}

	public String getDescription() {
		return description;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDueDate(Date duedate) {
		if (duedate == null) {
			this.duedate = null;
		}
		else if (duedate.equals("")) {
			this.duedate = null;
		}
		else{
			this.duedate = duedate;
		}
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setDescription(String description) {
		if (description == null) {
			this.description = null;
		}
		else if (description.equals("")) {
			this.description = null;
		}
		else{
			this.description = description;
		}
	}
	
	public boolean isDescriptionNull(){
		if (description == null) {
			return true;
		}
		else {
			return false;
		}
	}
}