package fi.my_work.todo_list.components.buttons;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.Button.ClickEvent;

import fi.my_work.todo_list.components.ProjectList;
import fi.my_work.todo_list.texts.T;
import fi.my_work.todo_list.window.FormWindow;

public class AddProjectButton extends Button{
	
	public AddProjectButton(final ProjectList projectList){
		setCaption(T.get("BUTTON_ADD_PROJECT"));
		
		this.addClickListener(new Button.ClickListener() {
//			inline click-listener
//			Open window with new project form
			public void buttonClick(ClickEvent event) {
				FormWindow newProject = new FormWindow(null, true, projectList);
				UI.getCurrent().addWindow(newProject);
			}
		});
	}
}