package fi.my_work.todo_list.components.buttons;

import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.Button.ClickEvent;

import fi.my_work.todo_list.components.TaskList;
import fi.my_work.todo_list.texts.T;
import fi.my_work.todo_list.type.Task;
import fi.my_work.todo_list.window.FormWindow;

public class EditTaskButton extends Button{

	public EditTaskButton(final Task task, final TaskList taskList) {

		setId(T.system("ID_BUTTON_EDIT_TASK") + task.getId());
		
		setStyleName(T.system("STYLE_BUTTON_TASK_EDIT"));
		
		this.addClickListener(new Button.ClickListener() {
//			inline click-listener
//			Open window with edit task form
			public void buttonClick(ClickEvent event) {
				FormWindow editTask = new FormWindow(task, false, taskList);
				UI.getCurrent().addWindow(editTask);
			}
		});
	}

}