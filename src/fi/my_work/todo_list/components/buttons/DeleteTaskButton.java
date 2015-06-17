package fi.my_work.todo_list.components.buttons;

import com.vaadin.ui.Button;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;

import fi.my_work.todo_list.components.TaskList;
import fi.my_work.todo_list.db.DBDelete;
import fi.my_work.todo_list.texts.T;
import fi.my_work.todo_list.window.MessageWindow;
import fi.my_work.todo_list.type.Task;

public class DeleteTaskButton extends Button{
	
	public DeleteTaskButton(final Task task, final TaskList taskList){
		setId(T.system("ID_BUTTON_DELETE_TASK") + task.getId());
	    
		setStyleName(T.system("STYLE_BUTTON_TASK_DELETE"));
		
		this.addClickListener(new Button.ClickListener() {
//			inline click-listener
			public void buttonClick(ClickEvent event) {
//				Get the delete-message and replace (?) with task name
				String message = T.get("LABEL_WINDOW_DELETE_TASK_MESSAGE");
				message = message.replace("(?)", task.getName());
				
//				Open message window
				final MessageWindow window = new MessageWindow(T.get("LABEL_WINDOW_DELETE_TASK_HEADER"), message);
				
//				Adding CloseListener
				window.addCloseListener(new CloseListener() {
					
					public void windowClose(CloseEvent e) {
//						Before window closes, call for a method in MessageWindow to verify if the task can be deleted
						boolean confirm = window.isConfirmed();
						
//						If the delete is confirmed, delete the task and update tasklist
						if (confirm) {
							DBDelete.task(task.getId());
							taskList.updateTasks();
						}
//						Otherwise do nothing
					}
				});
				
			}
		});
	}
}