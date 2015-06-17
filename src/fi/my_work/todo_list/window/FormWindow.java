package fi.my_work.todo_list.window;

import com.vaadin.ui.Window;

import fi.my_work.todo_list.components.ProjectList;
import fi.my_work.todo_list.components.TaskList;
import fi.my_work.todo_list.form.ProjectForm;
import fi.my_work.todo_list.form.TaskForm;
import fi.my_work.todo_list.texts.T;
import fi.my_work.todo_list.type.Project;
import fi.my_work.todo_list.type.Task;

//  This class is used to display a Window for forms
public class FormWindow extends Window{

//	This method is used to display a Window for Project form
	public FormWindow(Project project, boolean isNew, ProjectList projectList){
		setContent(new ProjectForm(project, isNew, this, projectList));
		
//		If the user is adding new project the caption of the window will be NEW_PROJECT  
		if(isNew){
			setCaption(T.get("LABEL_NEW_PROJECT_FORM_HEADER"));
		}
//		If the user is editing an existing project the caption of the window will be EDIT_PROJECT  
		else{
			setCaption(T.get("LABEL_EDIT_PROJECT_FORM_HEADER"));
		}
		
		additionalInfo();
	}

//	This method is used to display a Window for Task form
	public FormWindow(Task task, boolean isNew, TaskList taskList){
		setContent(new TaskForm(task, isNew, this, taskList));

//		If the user is adding new task the caption of the window will be NEW_PROJECT  
		if(isNew){
			setCaption(T.get("LABEL_NEW_TASK_FORM_HEADER"));
		}
//		If the user is editing an existing task the caption of the window will be EDIT_PROJECT  
		else{
			setCaption(T.get("LABEL_EDIT_TASK_FORM_HEADER"));
		}

		additionalInfo();
	}

	public void closeWindow(){
		close();
	}

//	Setting additional info
	private void additionalInfo(){
		setResizable(true);
		setDraggable(true);
		setModal(true);
		setWidth(T.system("WINDOW_WIDTH_NORMAL"));
		
		center();
	}
}
