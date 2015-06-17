package fi.my_work.todo_list.components.buttons;

import com.vaadin.ui.Button;
import com.vaadin.ui.UI;

import fi.my_work.todo_list.components.ProjectList;
import fi.my_work.todo_list.type.Project;
import fi.my_work.todo_list.window.FormWindow;

public class EditProjectButton extends Button{
	
	public EditProjectButton(final Project project, final ProjectList projectList){
		
//		id and theme for this are set in ProjectRow
		
		this.addClickListener(new Button.ClickListener() {
//			inline click-listener
//			Open window with edit project form
			public void buttonClick(ClickEvent event) {
				FormWindow editProject = new FormWindow(project, false, projectList);
				UI.getCurrent().addWindow(editProject);
			}
		});
	}
}