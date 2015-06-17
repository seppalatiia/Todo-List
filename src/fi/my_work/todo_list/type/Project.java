package fi.my_work.todo_list.type;

import java.io.Serializable;
import java.util.ArrayList;

import fi.my_work.todo_list.type.Task;

//  This class works as an project object
public class Project implements Serializable{
	/**
	 * For some reason when running the program I got NotSerializable error.
	 * So I implemented Serializable
	 */
	private static final long serialVersionUID = 1010485241953227637L;
	
	
	private int id;
	private String name;
	private String description;
	private String uid;

	public Project(int id, String name, String desc, String uid) {

		this.id = id;
		this.name = name;
		this.description = desc;
		this.uid = uid;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDesc(String desc) {
		this.description = desc;
	}
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	
	public boolean isDescriptionNull() {
		if (description == null){
			return true;
		}
		else{
			return false;
		}
	}
}