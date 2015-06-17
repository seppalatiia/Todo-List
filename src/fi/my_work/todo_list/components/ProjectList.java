package fi.my_work.todo_list.components;

import java.util.ArrayList;

import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import fi.my_work.todo_list.components.buttons.AddProjectButton;
import fi.my_work.todo_list.db.DBSelect;
import fi.my_work.todo_list.components.ProjectRow;
import fi.my_work.todo_list.type.Project;
import fi.my_work.todo_list.texts.T;
import fi.my_work.todo_list.view.MainView;

public class ProjectList extends VerticalLayout{

	private String uid;
	String selectedProjectId;
	private MainView mainView;
	
	AddProjectButton addProject;
	ArrayList<ProjectRow> projectRows;
	
	public ProjectList(MainView mainView) {
		this.mainView = mainView;
		
		this.uid = UI.getCurrent().getSession().getAttribute(T.system("SESSION_UID")).toString();
		
		buildLayout();
	}

	private void validateComponents() {
		selectedProjectId = UI.getCurrent().getSession().getAttribute(T.system("SESSION_SELECTED_PROJECT")).toString();
		
//		Create "Add project" button
		addProject = new AddProjectButton(this);
		
//		Get the projects from the database
		ArrayList<Project> projects = DBSelect.selectProjects(uid);
		projectRows = new ArrayList<ProjectRow>();

//		Create the project rows
		for (int i = 0; i < projects.size(); i++) {
			Project project = projects.get(i);
			ProjectRow projectRow = new ProjectRow(project, mainView, this);
			projectRows.add(projectRow);
			
//			if the selected project id is -1 set the first project as selected project
			if(selectedProjectId.equals("-1") & i == 0){
				UI.getCurrent().getSession().setAttribute(T.system("SESSION_SELECTED_PROJECT"), project.getId());
			}
		}
		
	}

//	Create the project list
	private void addComponentsToLayout() {
//		Add the "Add project" button to the list
		addComponent(addProject);
		
//		If the user don't have any projects, add the "No projects" label instead of the list
		if(projectRows.size() == 0){
			addComponent(new Label(T.get("LABEL_NO_PROJECTS_EXISTS")));
		}
//		Otherwise add all project rows to the list
		else{
			for (ProjectRow projectRow : projectRows) {
				addComponent(projectRow);
			}
		}
		
		mainView.selectProject();
	}

	private void setIds() {
		addProject.setId(T.system("ID_BUTTON_ADD_PROJECT"));
	}

	private void theming() {
		setWidth(T.system("VIEW_MAIN_PROJECT_LIST_WIDTH_NORMAL"));
	}
	
	private void buildLayout() {
		validateComponents();
		addComponentsToLayout();
		setIds();
		theming();
	}

	public void refresh() {
		removeAllComponents();
		buildLayout();
	}
}