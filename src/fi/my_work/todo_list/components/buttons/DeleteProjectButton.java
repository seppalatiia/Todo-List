package fi.my_work.todo_list.components.buttons;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;

import fi.my_work.todo_list.components.ProjectList;
import fi.my_work.todo_list.db.DBDelete;
import fi.my_work.todo_list.texts.T;
import fi.my_work.todo_list.type.Project;
import fi.my_work.todo_list.window.MessageWindow;

public class DeleteProjectButton extends Button{

	public DeleteProjectButton(final Project project, final ProjectList projectList){

//		id and theme for this are set in ProjectRow
		
		this.addClickListener(new Button.ClickListener() {
//			inline click-listener
			public void buttonClick(ClickEvent event) {
				
//				Get the delete-message and replace (?) with project name
				String message = T.get("LABEL_WINDOW_DELETE_PROJECT_MESSAGE");
				message = message.replace("(?)", project.getName());

//				Open message window
				final MessageWindow window = new MessageWindow(T.get("LABEL_WINDOW_DELETE_PROJECT_HEADER"), message);
				
//				Adding CloseListener
				window.addCloseListener(new CloseListener() {
					
					public void windowClose(CloseEvent e) {
//						Before window closes, call for a method in MessageWindow to verify if the project can be deleted
						boolean confirm = window.isConfirmed();

//						If the delete is confirmed, delete the project, set selected project to -1 and update projectlist
						if (confirm) {
							DBDelete.project(project.getId());
							getSession().setAttribute(T.system("SESSION_SELECTED_PROJECT"), "-1");
							projectList.refresh();
						}
//						Otherwise do nothing
					}
				});
			}
		});
	}
}