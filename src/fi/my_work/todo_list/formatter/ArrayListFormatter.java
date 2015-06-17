package fi.my_work.todo_list.formatter;

import java.util.ArrayList;

public class ArrayListFormatter {

//	This method returns an ArrayList as HTML list
	public static String getArrayListInHTML(ArrayList<String[]> list) {
		String message = "";
		
		for (String[] item : list) {
			message = message + "<li>" + item[1] + "</li>";
		}
		
		return message;
	}

}
