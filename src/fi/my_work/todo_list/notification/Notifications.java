package fi.my_work.todo_list.notification;

import java.util.ArrayList;

import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

import fi.my_work.todo_list.formatter.ArrayListFormatter;
import fi.my_work.todo_list.texts.T;

//  This class handles the notifications, that are shown to the user
public class Notifications {

//	If only one error message is to be shown this method can be called.
	public static void throwSingleError(String[] message) {
		showHumanizedMessage(message[0], message[1]);
	}
	
//	If multiple or unknown amount of error messages is to be shown this method can be called.
	public static void throwError(ArrayList<String[]> errors){
		if (errors.size() == 1) {
//			If there is only one error to show, message is shown with an error specific header.
			showHumanizedMessage(errors.get(0)[0], errors.get(0)[1]);
		}
		else{
//			If there is multiple errors to be shown, message is shown with a multiple error header.
			String header = T.get("ERROR_MESSAGE__MULTIPLE_ERRORS__HEADER");
			String message = ArrayListFormatter.getArrayListInHTML(errors);
			showHumanizedMessage(header, message);
		}
	}
	
//	This method handles the showing of the message
	private static void showHumanizedMessage(String header, String message){
		Notification notification = new Notification(header, message, Type.HUMANIZED_MESSAGE, true);
		notification.setDelayMsec(-1);
		notification.show(Page.getCurrent());
	}
}