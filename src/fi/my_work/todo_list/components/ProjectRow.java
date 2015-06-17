package fi.my_work.todo_list.components;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.Button.ClickEvent;

import fi.my_work.todo_list.components.ProjectList;
import fi.my_work.todo_list.components.buttons.DeleteProjectButton;
import fi.my_work.todo_list.components.buttons.EditProjectButton;
import fi.my_work.todo_list.texts.T;
import fi.my_work.todo_list.type.Project;
import fi.my_work.todo_list.view.MainView;

public class ProjectRow extends HorizontalLayout{

	private Project project;
	private MainView mainView;
	private ProjectList projectList;
	
	Button projectName;
	EditProjectButton editProject;
	DeleteProjectButton deleteProject;
	
	public ProjectRow(Project project, MainView mainView, ProjectList projectList) {
		this.project = project;
		this.mainView = mainView;
		this.projectList = projectList;
		
		validateComponents();
		addComponentsToLayout();
		setIds();
		theming();
	}

//	Set the project as selected in the session
	protected void select() {
		UI.getCurrent().getSession().setAttribute(T.system("SESSION_SELECTED_PROJECT"), project.getId());
		
//		Call for a method in mainView to display selected project and its tasks
		mainView.selectProject();
	}

	private void validateComponents() {
		projectName = new Button(project.getName(),
				new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				select();
				
			}
		});
		
		editProject = new EditProjectButton(project, projectList);
		deleteProject = new DeleteProjectButton(project, projectList);
		
	}

	private void addComponentsToLayout() {
		addComponent(projectName);
		addComponent(editProject);
		addComponent(deleteProject);
	}

	private void setIds() {
		editProject.setId(T.system("ID_BUTTON_EDIT_PROJECT") + project.getId());
		deleteProject.setId(T.system("ID_BUTTON_DELETE_PROJECT") + project.getId());
	}

	private void theming() {
		projectName.setStyleName(T.system("STYLE_BUTTON_PROJECT_NAME"));
		editProject.setStyleName(T.system("STYLE_BUTTON_PROJECT_EDIT"));
		deleteProject.setStyleName(T.system("STYLE_BUTTON_PROJECT_DELETE"));
	}
}