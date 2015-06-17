package fi.my_work.todo_list.type;

import fi.my_work.todo_list.texts.T;

//  This class works as an status object
public class Status {
	
	private int id;
	private String name;
	
	public Status(int id, String name){
		this.id = id;
		this.name = name;
	}
	
	public Status(int id) {
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