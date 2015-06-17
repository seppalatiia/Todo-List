package fi.my_work.todo_list.type;

import java.io.Serializable;

import fi.my_work.todo_list.db.DBSelect;
import fi.my_work.todo_list.texts.T;

//  This class works as an priority object
public class Priority implements Serializable{
	
	/**
	 * For some reason when running the program I got NotSerializable error.
	 * So I implemented Serializable
	 */
	private static final long serialVersionUID = 5767605153135894856L;
	
	
	private int id;
	private String name;
	
	public Priority(int id, String name){
		this.id = id;
		this.name = name;
	}
	
	public Priority(int id) {
		this.id = id;
	}

	public int getId(){
		return this.id;
	}
	public void setId(int id){
		this.id = id;
	}
	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}
	public String toString(){
		return T.get(name);
	}
}