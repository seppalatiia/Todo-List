package fi.my_work.todo_list.components.buttons;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;

import fi.my_work.todo_list.components.TaskList;
import fi.my_work.todo_list.texts.T;
import fi.my_work.todo_list.window.FormWindow;

public class AddTaskButton extends Button{

	public AddTaskButton(final TaskList taskList){
		setCaption(T.get("BUTTON_ADD_TASK"));
		
		this.addClickListener(new Button.ClickListener() {
//			inline click-listener
//			Open window with new task form
			public void buttonClick(ClickEvent event) {
				FormWindow addTask = new FormWindow(null, true, taskList);
				UI.getCurrent().addWindow(addTask);
			}
		});
	}
}