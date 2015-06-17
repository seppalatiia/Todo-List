package fi.my_work.todo_list.components;

import java.util.ArrayList;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;

import fi.my_work.todo_list.components.buttons.DeleteTaskButton;
import fi.my_work.todo_list.components.buttons.EditTaskButton;
import fi.my_work.todo_list.db.DBSelect;
import fi.my_work.todo_list.texts.T;
import fi.my_work.todo_list.type.Priority;
import fi.my_work.todo_list.type.Status;
import fi.my_work.todo_list.type.Task;

public class TaskList extends Table{
	
	ArrayList<Task> tasks;
	String selectedProjectId;

	public void updateTasks(){
		
//		Get the selected project from session
		selectedProjectId = UI.getCurrent().getSession().getAttribute(T.system("SESSION_SELECTED_PROJECT")).toString();
		
		if (selectedProjectId.equals("-1")) {
//			Since no project exists we don't want to show table
			return;
		}
		
//		Create the task list table
		validateComponents();
		addComponentsToLayout();
		theming();
	}

	private void validateComponents() {
//		Since the table is never really destroyed (when updated), it's necessary to remove all tasks
		this.removeAllItems();
		
//		By setting the page length 0, the paging of the table is disabled
		setPageLength(0);
		setColumnCollapsingAllowed(true);
		
//		Get tasks form database
		tasks = DBSelect.tasksByProject(selectedProjectId);
	}

	private void addComponentsToLayout() {
		addContainerProperty(T.get("TASKLIST_TABLEHEADER_NAME"), String.class, null);
		addContainerProperty(T.get("TASKLIST_TABLEHEADER_DUEDATE"), String.class, null);
		addContainerProperty(T.get("TASKLIST_TABLEHEADER_PRIORITY"), Priority.class, null);
		addContainerProperty(T.get("TASKLIST_TABLEHEADER_STATUS"), Status.class, null);
		addContainerProperty("", HorizontalLayout.class, null);

		if(tasks.size() == 0){
			addItem(new Object[] {T.get("TEXT_NO_TASKS_EXISTS"),null,null,null,null},
					1);
		}
		for (Task task : tasks) {
//			Create edit and delete buttons for task
			HorizontalLayout buttonContainer = new HorizontalLayout();
			EditTaskButton editButton = new EditTaskButton(task, this);
			DeleteTaskButton deleteButton = new DeleteTaskButton(task, this);
			
			buttonContainer.addComponent(editButton);
			buttonContainer.addComponent(deleteButton);

//			Add task to the table
			addItem(new Object[] {task.getName(),task.getDueDateString(),task.getPriority(),task.getStatus(),buttonContainer},
					task.getId());
		}
		/*
		 * In order to make buttons to fit in the column
		 */
		setColumnWidth("", 81);
	}

	private void theming() {
		setWidth(T.system("VIEW_MAIN_TASK_LIST_WIDTH_NORMAL"));
	}
}