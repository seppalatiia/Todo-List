package fi.my_work.todo_list.form;

import com.vaadin.server.UserError;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomLayout;

import fi.my_work.todo_list.components.ProjectList;
import fi.my_work.todo_list.db.DBInsert;
import fi.my_work.todo_list.db.DBSet;
import fi.my_work.todo_list.db.dummy_data.DummyData;
import fi.my_work.todo_list.notification.Messages;
import fi.my_work.todo_list.notification.Notifications;
import fi.my_work.todo_list.texts.T;
import fi.my_work.todo_list.type.Project;
import fi.my_work.todo_list.window.FormWindow;

public class ProjectForm extends CustomLayout implements Button.ClickListener{

//	Variables that will be passed on constructor
	Project project;
	boolean isNew;
	FormWindow projectWindow;
	ProjectList projectList;
	
//	Components in the form
	private TextField name;
	private TextArea description;
	
	private Button save;
	private Button cancel;
	
	public ProjectForm(Project project, boolean isNew, FormWindow projectWindow, ProjectList projectList) {
		super(T.system("LAYOUT_FORM_PROJECT"));
		
		if(isNew){
			this.project = new Project(-1, null, null, UI.getCurrent().getSession().getAttribute(T.system("SESSION_UID")).toString());
		}
		else{
			this.project = project;
		}
		
		this.isNew = isNew;
		this.projectWindow = projectWindow;
		this.projectList = projectList;
		
		validateComponents();
		addComponentsToLayout();
		setIds();
	}

	public void buttonClick(ClickEvent event) {
		if (event.getButton().equals(save)) {
			if (!name.isValid()) {
				name.setComponentError(new UserError(Messages.getMessageFieldRequired(name.getCaption())));
				Notifications.throwSingleError(Messages.getMessageWithHeaderFieldRequired(name.getCaption()));
				
				return;
			}
			project.setName(name.getValue());
			
			if (!description.getValue().equals("")) {
				project.setDesc(description.getValue());
			}
			else{
				project.setDesc(null);
			}

//			If the useDummyData is true, nothing will be done
			if (DummyData.useDummyData()) {
				return;
			}
			if(isNew){
				int newProjectId = DBInsert.project(project);
				UI.getCurrent().getSession().setAttribute(T.system("SESSION_SELECTED_PROJECT"), newProjectId);
			} else{
				DBSet.project(project);
			}
//			After the task is saved, update projectlist
			projectList.refresh();
		}
//		Lastly, close the window
		projectWindow.closeWindow();
	}

	private void validateComponents() {
		name = new TextField(T.get("TEXTFIELD_PROJECT_NAME"));
		description = new TextArea(T.get("TEXTAREA_PROJECT_DESCRIPTION"));
		save = new Button(T.get("BUTTON_SAVE"));
		cancel = new Button(T.get("BUTTON_CANCEL"));
		
		name.setRequired(true);
		
		save.addClickListener(this);
		cancel.addClickListener(this);
		
//		If the project is not new, fill the form with the project info
		if(!isNew){
			name.setValue(project.getName());
			
			if (!project.isDescriptionNull()) {
				description.setValue(project.getDescription());
			}
		}
	}

	private void addComponentsToLayout() {
		addComponent(name, T.system("LAYOUT_FORM_PROJECT_LOCATION_PROJECT_NAME"));
		addComponent(description, T.system("LAYOUT_FORM_PROJECT_LOCATION_PROJECT_DESCRIPTION"));
		addComponent(save, T.system("LAYOUT_FORM_PROJECT_LOCATION_BUTTON_SAVE"));
		addComponent(cancel, T.system("LAYOUT_FORM_PROJECT_LOCATION_BUTTON_CANCEL"));
	}

	private void setIds() {
		name.setId(T.system("ID_PROJECT_FORM_TEXTFIELD_PROJECT_NAME"));
		description.setId(T.system("ID_PROJECT_FORM_TEXTAREA_PROJECT_DESCRIPTION"));
		save.setId(T.system("ID_BUTTON_SAVE"));
		cancel.setId(T.system("ID_BUTTON_CANCEL"));
	}
}