package fi.my_work.todo_list.form;

import java.util.ArrayList;

import com.vaadin.server.UserError;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomLayout;

import fi.my_work.todo_list.components.TaskList;
import fi.my_work.todo_list.db.DBInsert;
import fi.my_work.todo_list.db.DBSet;
import fi.my_work.todo_list.db.DBSelect;
import fi.my_work.todo_list.notification.Messages;
import fi.my_work.todo_list.notification.Notifications;
import fi.my_work.todo_list.texts.T;
import fi.my_work.todo_list.type.Priority;
import fi.my_work.todo_list.type.Status;
import fi.my_work.todo_list.type.Task;
import fi.my_work.todo_list.window.FormWindow;

public class TaskForm extends CustomLayout implements Button.ClickListener{
	
	Task task;
	boolean isNew;
	TaskList taskList;
    FormWindow taskWindow;
	
    private TextField name;
    
    private DateField dueDate;
    
    private ComboBox prioritySelect;
    private ComboBox statusSelect;
    
    private TextArea description;
    
    private Button save;
    private Button cancel;

	public TaskForm(Task task, boolean isNew, FormWindow taskWindow, TaskList taskList) {
		super(T.system("LAYOUT_FORM_TASK"));

//		If the task is new, create new task
		if (isNew) {
			this.task = new Task(UI.getCurrent().getSession().getAttribute(T.system("SESSION_SELECTED_PROJECT")).toString());
		}
//		Otherwise use the one passed to the constructor
		else{
			this.task = task;
		}
		
		this.isNew = isNew;
		this.taskWindow = taskWindow;
		this.taskList = taskList;
		
//		Create the layout
		validateComponents();
		addComponentsToLayout();
		setIds();
	}

//	Functionalities for the buttons
	public void buttonClick(ClickEvent event) {
		if(event.getButton().equals(save)){
//			Since it is possible to have two errors
			ArrayList<String[]> errors = new ArrayList<String[]>();
			
//			Check if task name has been given
			if (!name.isValid()) {
				name.setComponentError(new UserError(Messages.getMessageFieldRequired(name.getCaption())));
				errors.add(Messages.getMessageWithHeaderFieldRequired(name.getCaption()));
			}
			else {
				name.setComponentError(null);
			}
			
//			Verify that date is in right format
			if (!dueDate.isValid()) {
				dueDate.setComponentError(new UserError(Messages.getMessageInvalidDateFormat()));
				errors.add(Messages.getMessageWithHeaderInvalidDateFormat());
			}
			else {
				dueDate.setComponentError(null);
			}
			
//			If any errors have happened, show notification and don't do anything else
			if(errors.size() != 0){
				Notifications.throwError(errors);
				return;
			}
			
//			If no errors are found task can be saved
			task.setName(name.getValue());
			task.setDueDate(dueDate.getValue());
			task.setPriority((Priority) prioritySelect.getValue());
			task.setStatus((Status) statusSelect.getValue());
			task.setDescription(description.getValue());
			
//			If the task is new, insert new task. Otherwise update existing one
			if(isNew){
				DBInsert.task(task);
			} else{
				DBSet.task(task);
			}
//			After the task is saved, update tasklist
			taskList.updateTasks();
		}
//		Lastly, close the window
		taskWindow.closeWindow();
	}
	
	private void validateComponents() {
		
		/*
		 * name:		Task Name
		 * type:		Textfield
		 * validation:	Not Null
		 */
		name = new TextField(T.get("TEXTFIELD_TASK_NAME"));
		name.setRequired(true);
		
		/*-------------------------------------------------------------*/
		
		/*
		 * name:		Due date
		 * type:		Date
		 * validation:	format: yyyy-MM-dd
		 */
		dueDate = new DateField(T.get("DATEFIELD_TASK_DUE_DATE"));
		dueDate.setDateFormat(T.system("DATE_FORMAT"));
		
//		Without this the program shows default error message, when error happens.
//		That is not desired behavior so it is prevented. 
		dueDate.setValidationVisible(false);
		
		/*-------------------------------------------------------------*/
		
		/*
		 * name:		Priority
		 * type:		Select - ComboBox
		 * validation:	Not Null
		 */
		prioritySelect = new ComboBox(T.get("SELECT_TASK_PRIORITY"));
		prioritySelect.setNullSelectionAllowed(false);
		prioritySelect.setTextInputAllowed(false);
		
		ArrayList<Priority> priorities = DBSelect.allPriorities();
		for (Priority priority : priorities) {
			prioritySelect.addItem(priority);
			prioritySelect.setItemCaption(priority, T.get(priority.getName()));
		}
		
		/*-------------------------------------------------------------*/
		
		/*
		 * name:		Status
		 * type:		Select - ComboBox
		 * validation:	Not Null
		 */
		statusSelect = new ComboBox(T.get("SELECT_TASK_STATUS"));
		statusSelect.setNullSelectionAllowed(false);
		statusSelect.setTextInputAllowed(false);
		
		ArrayList<Status> statuses = DBSelect.allStatuses();
		for (Status status : statuses) {
			statusSelect.addItem(status);
			statusSelect.setItemCaption(status, T.get(status.getName()));
		}
		
		/*-------------------------------------------------------------*/

		/*
		 * name:		Description
		 * type:		TextArea
		 * validation:	None
		 */
		description = new TextArea(T.get("TEXTAREA_TASK_DESCRIPTION"));

		/*-------------------------------------------------------------*/
		
		/*
		 * Set values to fields
		 */
		if (isNew) {
//			The priority_id for normal is 3, but since the ArrayLists first id is 0 id has to be 1 less than actual id
			prioritySelect.select(priorities.get(2));
			statusSelect.setValue(statuses.get(0));
		}
//		If the task is not new, fill the form with the task info
		else{
			name.setValue(task.getName());
			dueDate.setValue(task.getDueDate());
			prioritySelect.select(priorities.get(task.getPriority().getId()-1));
			statusSelect.setValue(statuses.get(task.getStatus().getId()-1));
			
			if (!task.isDescriptionNull()) {
				description.setValue(task.getDescription());
			}
		}
		/*-------------------------------------------------------------*/
		
		save = new Button(T.get("BUTTON_SAVE"));
		save.addClickListener(this);
		cancel = new Button(T.get("BUTTON_CANCEL"));
		cancel.addClickListener(this);

	}

	private void addComponentsToLayout() {
		addComponent(name, T.system("LAYOUT_FORM_TASK_LOCATION_TASK_NAME"));
		addComponent(dueDate, T.system("LAYOUT_FORM_TASK_LOCATION_DUE_DATE"));
		addComponent(prioritySelect, T.system("LAYOUT_FORM_TASK_LOCATION_PRIORITY"));
		addComponent(statusSelect, T.system("LAYOUT_FORM_TASK_LOCATION_STATUS"));
		addComponent(description, T.system("LAYOUT_FORM_TASK_LOCATION_TASK_DESCRIPTION"));
		addComponent(save, T.system("LAYOUT_FORM_TASK_LOCATION_BUTTON_SAVE"));
		addComponent(cancel, T.system("LAYOUT_FORM_TASK_LOCATION_BUTTON_CANCEL"));
	}

	private void setIds() {
		name.setId(T.system("ID_TASK_FORM_TEXTFIELD_NAME"));
		dueDate.setId(T.system("ID_TASK_FORM_DATEFIELD_DUEDATE"));
		prioritySelect.setId(T.system("ID_TASK_FORM_COMBOBOX_PRIORITY"));
		statusSelect.setId(T.system("ID_TASK_FORM_COMBOBOX_STATUS"));
		description.setId(T.system("ID_TASK_FORM_TEXTAREA_DESCRIPTION"));
		save.setId(T.system("ID_BUTTON_SAVE"));
		cancel.setId(T.system("ID_BUTTON_CANCEL"));
	}
}