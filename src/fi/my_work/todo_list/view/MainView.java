package fi.my_work.todo_list.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

import fi.my_work.todo_list.components.Footer;
import fi.my_work.todo_list.components.ProjectList;
import fi.my_work.todo_list.components.TaskList;
import fi.my_work.todo_list.components.TopBar;
import fi.my_work.todo_list.components.buttons.AddTaskButton;
import fi.my_work.todo_list.db.DBSelect;
import fi.my_work.todo_list.texts.T;
import fi.my_work.todo_list.type.Project;

//  This class creates the main view
public class MainView extends CustomComponent implements View{
	
    public static final String NAME = "";

	CustomLayout layout = new CustomLayout(T.system("LAYOUT_VIEW_LAYOUT"));
	CustomLayout content = new CustomLayout(T.system("LAYOUT_MAIN_VIEW"));
	
	TopBar header;
	Footer footer;
	
	ProjectList projectList;
	TaskList taskList;

	Label projectName;
	Label projectDescription;
	AddTaskButton addTaskButton;
	
    public MainView(){
    	Page.getCurrent().setTitle(T.get("PAGE_TITLE"));	
    }

    private void validateComponents() {
		header = new TopBar(this);
		footer = new Footer();
		
		projectName = new Label();
		projectDescription = new Label();
		
		taskList = new TaskList();
		addTaskButton = new AddTaskButton(taskList);
		
		projectList = new ProjectList(this);
    }
    
    private void addComponentsToLayout() {
    	content.addComponent(projectList,			T.system("LAYOUT_MAIN_VIEW_LOCATION_PROJECTLIST"));
		
		content.addComponent(projectName,			T.system("LAYOUT_MAIN_VIEW_LOCATION_PROJECT_NAME"));
		content.addComponent(projectDescription,	T.system("LAYOUT_MAIN_VIEW_LOCATION_PROJECT_DESCRIPTION"));
		content.addComponent(addTaskButton,			T.system("LAYOUT_MAIN_VIEW_LOCATION_ADD_TASK_BUTTON"));

		content.addComponent(taskList,				T.system("LAYOUT_MAIN_VIEW_LOCATION_TASKLIST"));
		
		
		layout.addComponent(header,		T.system("LAYOUT_VIEW_LAYOUT_LOCATION_TOP_BAR"));
		layout.addComponent(content,	T.system("LAYOUT_VIEW_LAYOUT_LOCATION_CONTENT"));
		layout.addComponent(footer,		T.system("LAYOUT_VIEW_LAYOUT_LOCATION_FOOTER"));
		
		setCompositionRoot(layout);
    }

	private void setIds() {
		projectList.setId(T.system("ID_VERTICALLAYOUT_PROJECT_LIST"));
		projectName.setId(T.system("ID_LABEL_PROJECT_NAME"));
		projectDescription.setId(T.system("ID_LABEL_PROJECT_DESCRIPTION"));
		addTaskButton.setId(T.system("ID_BUTTON_ADD_TASK"));
		taskList.setId(T.system("ID_TABLE_TASKLIST"));
	}
    
    private void theming() {
    	content.setWidth(T.system("VIEW_MAIN_WIDTH_NORMAL"));
    	content.setStyleName(T.system("STYLE_VIEW_CONTENT_CONTAINER_MAIN"));
    	
		layout.setSizeFull();
		layout.setStyleName(T.system("STYLE_MAIN_VIEW"));
	}
    
    public void enter(ViewChangeEvent event) {
    	buildLayout();
    }

	private void buildLayout() {
		validateComponents();
		addComponentsToLayout();
		setIds();
		theming();
		selectProject();
	}

	public void updateComponents() {
		layout.removeAllComponents();
		buildLayout();
	}

	public void selectProject() {
		
		Project selectedProject = null;
		
		if(!getSession().getAttribute(T.system("SESSION_SELECTED_PROJECT")).toString().equals("-1")){
			selectedProject = DBSelect.projectById(UI.getCurrent().getSession().getAttribute(T.system("SESSION_SELECTED_PROJECT")).toString());
		}

		
		if(selectedProject == null){
			projectName = new Label("<h1>" + T.get("LABEL_NO_PROJECTS_EXISTS") + "</h1>", ContentMode.HTML);
			projectDescription = new Label(T.get("LABEL_DESCRIPTION_NO_PROJECTS_EXISTS"));
			content.removeComponent(addTaskButton);
			content.removeComponent(taskList);
		}
		else{
			projectName = new Label("<h1>" + selectedProject.getName() + "</h1>", ContentMode.HTML);
			projectDescription = new Label(selectedProject.getDescription());

			content.addComponent(addTaskButton, T.system("LAYOUT_MAIN_VIEW_LOCATION_ADD_TASK_BUTTON"));
			
			taskList.updateTasks();
			content.addComponent(taskList, T.system("LAYOUT_MAIN_VIEW_LOCATION_TASKLIST"));
			
		}
		projectName.setId(T.system("ID_LABEL_PROJECT_NAME"));
		projectDescription.setId(T.system("ID_LABEL_PROJECT_DESCRIPTION"));

		content.addComponent(projectName, T.system("LAYOUT_MAIN_VIEW_LOCATION_PROJECT_NAME"));
		content.addComponent(projectDescription, T.system("LAYOUT_MAIN_VIEW_LOCATION_PROJECT_DESCRIPTION"));
	}
}